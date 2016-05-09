package com.penske.apps.adminconsole.service;

import java.util.List;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService implements IMailService{

	@Autowired
	private JavaMailSenderImpl mailSender;

	public int sendEmail(MailRequest mailRequest) throws Exception {
		
		  MimeMessage message = this.mailSender.createMimeMessage();

	        //		 use the true flag to indicate you need a multipart message
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);

	        //Set Email From
	        if (!"".equals(mailRequest.getFromAddress())) {
	            helper.setFrom(mailRequest.getFromAddress());
	        } 

	        //Set Email To be
	        List<String> emailToList = mailRequest.getToRecipientsList();
	        InternetAddress[] addressTo = null;
	        if (emailToList != null && emailToList.size() > 0)
	        {
	        	addressTo = new InternetAddress[emailToList.size()];
	        	 for (int i = 0; i < emailToList.size(); i++) {
	        		 addressTo[i] = new InternetAddress((String) emailToList.get(i));
	        	 }	        	 
	        }
	        	       	   
	        //set Recipients
	        helper.setTo(addressTo);
	        
	        //send a blind copy to from email address
	        helper.setBcc(new InternetAddress(mailRequest.getFromAddress()));
	        
	        helper.setReplyTo(new InternetAddress(mailRequest.getFromAddress()));
	        
	        //set Subject
	        helper.setSubject(mailRequest.getSubject());
	        //helper.setSubject("UsedTrucks Redesign Test Mail");
	        //set content
	      //  helper.setText(mailRequest.getMessageContent());
	        message.setContent(mailRequest.getMessageContent(), "text/html");
	        //send email
	        this.mailSender.send(message);
	        
	        return 1;
	}
}
