// parasoft-begin-suppress SECURITY.WSC.APIBS "This is a class only intended for unit tests, not production code."
// parasoft-begin-suppress UC.UCATCH "This is a class only intended for unit tests, not production code."
/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.suppliermgmt;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.MatcherAssert.assertThat;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.AfterClass;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.penske.apps.smccore.CoreTestUtil;
import com.penske.apps.smccore.base.annotation.NonVendorQuery;
import com.penske.apps.smccore.base.annotation.SkipQueryTest;
import com.penske.apps.smccore.base.domain.User;
import com.penske.apps.smccore.base.domain.enums.UserType;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;


/**
 * Common superclass for DAOs that asserts that all methods in the DAO were run after the test class completes.
 */
public abstract class MyBatisDaoTest
{
	private static final Set<String> ALWAYS_SKIP = new HashSet<String>(Arrays.asList("hashCode", "equals", "toString"));
	
	private static final Set<String> PROXY_SKIP_METHODS = new HashSet<String>();
	
	private static final Map<Class<?>, Map<Method, Set<User>>> METHODS_RUN = new HashMap<Class<?>, Map<Method, Set<User>>>();
	private static final Map<Class<?>, Map<Method, Boolean>> METHODS_TO_RUN = new HashMap<Class<?>, Map<Method, Boolean>>();
	
	@Autowired
	private SuppliermgmtSessionBean sessionBean;
	
	static {
		for(Method method : AdvisedSupport.class.getMethods())
			PROXY_SKIP_METHODS.add(method.getName());
	}
	
	protected <T> T trackMethodCalls(T daoObject, Class<T> daoClass)
	{
		Map<Method, Boolean> methodsForClass = METHODS_TO_RUN.get(daoClass);
		
		if(methodsForClass == null)
		{
			methodsForClass = new HashMap<>();
			METHODS_TO_RUN.put(daoClass, methodsForClass);
			Map<Method, Set<User>> methodsRun = new HashMap<>();
			METHODS_RUN.put(daoClass, methodsRun);
			
			for(Method method : daoObject.getClass().getDeclaredMethods())
			{
				Method realTargetMethod = findMethod(daoClass, method);
				if(realTargetMethod != null && realTargetMethod.isAnnotationPresent(SkipQueryTest.class))
					continue;
				
				if(ALWAYS_SKIP.contains(method.getName()) || PROXY_SKIP_METHODS.contains(method.getName()))
					continue;
				
				Boolean isVendorQuery = realTargetMethod.isAnnotationPresent(NonVendorQuery.class) ? false : true;

				methodsForClass.put(method, isVendorQuery);
			}
		}
		
		MyBatisTestInvocationHandler testHandler = new MyBatisTestInvocationHandler(daoObject, METHODS_RUN.get(daoClass), sessionBean);
		Object proxy = Proxy.newProxyInstance(daoClass.getClassLoader(), new Class<?>[]{daoClass}, testHandler);
		return daoClass.cast(proxy);
	}

	protected void setPenskeUser()
	{
		User user = CoreTestUtil.createUser(123456789, "600555555", "Joe", "Test", "test@penske.com", UserType.PENSKE);
		CoreTestUtil.addAssociatedVendors(user, 1);
		sessionBean.initialize(user, "penske.com", LocalDateTime.now(), false, false, false);
	}
	
	protected void setVendorUser()
	{
		User user = CoreTestUtil.createUser(987654321, "suppliertest", "Supplier", "Test", "test@morgan.com", UserType.VENDOR);
		CoreTestUtil.addAssociatedVendors(user, 1);
		sessionBean.initialize(user, "penske.com", LocalDateTime.now(), false, false, false);
	}	
	
	@AfterClass
	public static void testAllRun()
	{
		//Make copies of the static maps and clear out the maps themselves, so that they are clean for the next test class, no matter what happens
		Map<Class<?>, Map<Method, Boolean>> methodsToRun = new HashMap<Class<?>, Map<Method, Boolean>>(METHODS_TO_RUN);
		Map<Class<?>, Map<Method, Set<User>>> methodsRun = new HashMap<>(METHODS_RUN);
		METHODS_TO_RUN.clear();
		METHODS_RUN.clear();
		
		//Check to make sure all targeted methods were run
		for(Class<?> daoClass : methodsToRun.keySet())
		{
			Map<Method,Boolean> methodsToRunForClass = methodsToRun.get(daoClass);
			Map<Method,Set<User>> methodsRunForClass = methodsRun.get(daoClass);
			if(methodsRunForClass == null)
				methodsRunForClass = Collections.emptyMap();
			
			for(Entry<Method, Boolean> entry: methodsToRunForClass.entrySet())
			{
				String methodName = entry.getKey().toString();
				String methodRun = methodsRunForClass.containsKey(entry.getKey()) ? methodName : null;
				
				assertThat("Did not run method - did you remember to replace the DAO object with the proxy generated from trackMethodCalls()?", methodRun, is(methodName));
				
				Boolean isVendorQuery = entry.getValue();
				Set<User> users = methodsRunForClass.get(entry.getKey());
				
				if(isVendorQuery){
					List<UserType> userTypes = new ArrayList<>();
					for(User user : users){
						userTypes.add(user.getUserType());
					}
					assertThat("Did not run method as both Penske and Vendor User: " + methodName, userTypes, containsInAnyOrder(UserType.values()));
				}
				
				
			}
		}
	}
	
	private static Method findMethod(Class<?> claz, Method method)
	{
		try {
			return claz.getDeclaredMethod(method.getName(), method.getParameterTypes());
		} catch(NoSuchMethodException e) {
			return null;
		}
	}
	
	private static class MyBatisTestInvocationHandler implements InvocationHandler
	{
		private final Object delegate;
		private final Map<Method, Set<User>> methodsRun;
		private SuppliermgmtSessionBean sessionBean;
		
		public MyBatisTestInvocationHandler(Object delegate, Map<Method, Set<User>> map, SuppliermgmtSessionBean sessionBean)
		{
			this.delegate = delegate;
			this.methodsRun = map;
			this.sessionBean = sessionBean;
		}
		
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
		{
			Method m = findMethod(this.getClass(), method);
			if(m == null)
				m = findMethod(delegate.getClass(), method);
			
			if(m == null)
				return null;
			
			User user = sessionBean.getUser();
			
			if(methodsRun.containsKey(m)){
				if(user != null) methodsRun.get(m).add(user);
			}
			else{
				Set<User> users = new HashSet<>();
				if(user != null) users.add(user);
				methodsRun.put(m, users);
			}
			
			return m.invoke(delegate, args);
		}
	}
}
