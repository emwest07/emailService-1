package com.armyPOC.emailService;

import com.armyPOC.emailService.service.EmailService;
import com.armyPOC.emailService.service.EmailServiceImpl;
import com.armyPOC.emailService.service.WebsocketListenerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.core.JmsTemplate;

@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class EmailServiceApplication {

	public static void main(String[] args) {


		SpringApplication.run(EmailServiceApplication.class, args);

		WebsocketListenerService websocketListenerService = new WebsocketListenerService();

		websocketListenerService.startClient();

	}

}
