package com.penske.apps.suppliermgmt.controller;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.penske.apps.smccore.base.beans.LookupManager;
import com.penske.apps.smccore.base.domain.LookupContainer;
import com.penske.apps.smccore.base.domain.User;
import com.penske.apps.smccore.base.domain.UserSecurity;
import com.penske.apps.smccore.base.service.UserService;
import com.penske.apps.suppliermgmt.annotation.CommonStaticUrl;

/**
 * A controller to answer AJAX requests from the two factor authentication screen
 * 
 */
@RestController
@RequestMapping("/login")
public class LoginRestController {

	@Autowired
	private UserService userService;
	@Autowired
	private LookupManager lookupManager;
	@Autowired
	@CommonStaticUrl
	private URL commonStaticUrl;
	
	/**
	 * Controller method to check the access code. Will return AccessCodeResult with the results of the 
	 */
	@RequestMapping("/check-access-code")
	public AccessCodeResult checkAccessCode(@RequestParam("userId") int userId, @RequestParam("accessCode") String accessCode) {
		
		LookupContainer lookups = lookupManager.getLookupContainer();
		
		User user = userService.getUser(userId, false, false);
		UserSecurity userSecurity = userService.getUserSecurity(user);
		
		// If access code is not valid, we have to determine why it isn't valid so that the correct actions can take place
		// and the correct message can be displayed to the user
		boolean accessCodeValid = userSecurity.isAccessCodeValid(accessCode);
		if(accessCodeValid) {
			userService.recordTwoFactorAuthSuccess(user,userSecurity);
			return new AccessCodeResult(true, true);
		}
		else {
			if(!userSecurity.isAccessCodeGeneratedRecently()) {
				userService.generateAndSendAccessCode(user, userSecurity, lookups, commonStaticUrl);
				return new AccessCodeResult(false, false);
			}
			else {
				return new AccessCodeResult(false, true);
			}
		}
	}
	
	/**
	 * Controller method to generate a new access code and resend the email
	 */
	@RequestMapping("/resend-access-code")
	public void resendAccessCode(@RequestParam("userId") int userId) {
		
		LookupContainer lookups = lookupManager.getLookupContainer();
		
		User user = userService.getUser(userId, false, false);
		UserSecurity userSecurity = userService.getUserSecurity(user);
		
		userService.generateAndSendAccessCode(user, userSecurity, lookups, commonStaticUrl);
	}
	
	/**
	 * The result of checking whether the access code matches and was generated recently
	 */
	public static class AccessCodeResult {
		/**
		 * Whether the access code matched what was in the DB
		 */
		private boolean accessCodeMatched;
		/**
		 * Whether the access code was generated in the last 60 minutes
		 */
		private boolean codeGeneratedRecently;
		
		private AccessCodeResult(boolean accessCodeMatched, boolean codeGeneratedRecently) {
			this.accessCodeMatched = accessCodeMatched;
			this.codeGeneratedRecently = codeGeneratedRecently;
		}
		
		public boolean isAccessCodeMatched() {
			return accessCodeMatched;
		}
		
		public boolean isCodeGeneratedRecently() {
			return codeGeneratedRecently;
		}
	}
	
}

