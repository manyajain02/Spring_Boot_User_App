package com.springboot.project.uber.User.App.services;

public interface EmailSenderService{

    public void sendEmail(String toEmail, String subject, String body);

    public void sendEmail(String toEmail[], String subject, String body);
 }
