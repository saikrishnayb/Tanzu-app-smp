package com.penske.apps.suppliermgmt.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class SerializableFilter implements Filter{
	
	private static Logger LOGGER = Logger.getLogger(SerializableFilter.class);
	
	public SerializableFilter() 
	{
		super();
	}

	public void destroy() {	}

	/**
	 * 
	 */
	public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
            HttpServletRequest request = (HttpServletRequest)req;
            HttpSession session = request.getSession();
            
			List<String> attributeNames = Collections.list(session.getAttributeNames());
            
            try {
            	LOGGER.info("Validating session data size in suppliermgmt");
                    long totalBytes = 0;
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ObjectOutput out = new ObjectOutputStream(bos);
                    Object attribute = null;
                    for ( String attributeName : attributeNames ) {
                            attribute = session.getAttribute(attributeName);
                            out.writeObject(attribute);
                            totalBytes += bos.toByteArray().length;
                            bos.reset();
                    }
                    LOGGER.info("Session data size in suppliermgmt: " + totalBytes);
                    if ( totalBytes > 4096 ) {
                    	LOGGER.warn("The session size is getting large. Total bytes: " + totalBytes );
                    }
            }
            catch ( Throwable th ) {
            	LOGGER.error("There was an error serializing the session.", th);
            }
            finally {
                    chain.doFilter(req, res);
            }
   }
	/**
	* Method init.
	* @param config
	* @throws javax.servlet.ServletException
	*/
	public void init(FilterConfig config) throws ServletException {
	}


}
