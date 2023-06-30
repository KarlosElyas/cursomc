package com.karlos.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.karlos.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);

}
