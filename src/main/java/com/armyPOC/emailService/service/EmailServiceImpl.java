package com.armyPOC.emailService.service;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;



@Service
public class EmailServiceImpl implements EmailService {

   private JavaMailSender emailSender;



   public EmailServiceImpl(JavaMailSender javaMailSender) {
      this.emailSender = javaMailSender;
   }


   public void sendSimpleMessage(
         String to, String subject, String text) {

      SimpleMailMessage message = new SimpleMailMessage();
      message.setTo(to);
      message.setSubject(subject);
      message.setText(text);
      emailSender.send(message);

   }


}

