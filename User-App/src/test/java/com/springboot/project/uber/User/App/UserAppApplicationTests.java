package com.springboot.project.uber.User.App;

import com.springboot.project.uber.User.App.services.EmailSenderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserAppApplicationTests {

	@Autowired
	private EmailSenderService emailSenderService;

	@Test
	void contextLoads() {
		emailSenderService.sendEmail(
				"kexopaj165@kwalah.com",
				"This is the Testing Email",
				"Body of my email");
	}

	@Test
	void sendEmailMultiple() {
		String email[]= {
				"kexopaj165@kwalah.com",
				"nilayj30@gmail.com"
		};
		emailSenderService.sendEmail(email,
				"Hello This is from my UBER Application demo mail",
				"Body of my email ,Happy coding!");
	}


}
