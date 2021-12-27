package edu.egg.AgendaJJ.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender sender;

    //Anotación nueva (se importa desde org)
    @Value("${spring.mail.username}")

    private String from;
    private static final String SUBJECT = "Agenda Pública de Juicios por Jurado";
    private static final String TEXT = "Bienvenido/a a la Agenda Pública de Juicios por Jurado. Gracias por registrarte.";

    public void sendThread(String to) {
        new Thread(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setFrom(from);
            message.setSubject(SUBJECT);
            message.setText(TEXT);
            sender.send(message);
        }).start();
    }
}