package com.example.demo.external;

import javax.mail.*;
import javax.mail.internet.*;

import com.example.demo.views.nologueado.Registrar;

import java.util.Properties;

public class ServidordeCorreo {
    public Registrar _registrar;

    public void EnviarCorreo(String destinatario, String asunto, String mensaje, String remitente, String password) {
        // Configuración del servidor SMTP (ejemplo con Gmail)
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            message.setText(mensaje);

            Transport.send(message);
            System.out.println("Correo enviado correctamente a " + destinatario);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al enviar el correo: " + e.getMessage());
        }
    }
}