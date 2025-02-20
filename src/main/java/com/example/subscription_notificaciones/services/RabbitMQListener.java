package com.example.subscription_notificaciones.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.subscription_notificaciones.DTO.ClienteNotificacionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;


@Service
public class RabbitMQListener {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JavaMailSender mailSender;

    @RabbitListener(queues = "notificaciones")
    public void recibirMensaje(String mensajeJson) {
        try {
            ClienteNotificacionDTO cliente = objectMapper.readValue(mensajeJson, ClienteNotificacionDTO.class);
            enviarCorreo(cliente);
            System.out.println("Correo enviado a: " + cliente.getCorreo());
        } catch (Exception e) {
            System.err.println("Error al procesar el mensaje: " + e.getMessage());
        }
    }

    private void enviarCorreo(ClienteNotificacionDTO cliente) throws MessagingException {
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

        helper.setTo(cliente.getCorreo());
        helper.setSubject("Confirmación de cita médica");

        String contenidoHtml = "<html>" +
                "<body style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;'>" +
                "    <div style='max-width: 600px; margin: auto; background: white; padding: 20px; border-radius: 10px; box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);'>" +
                "        <h2 style='color: #2c3e50;'>Confirmación de Cita Médica</h2>" +
                "        <p>Hola <strong>" + cliente.getNombre() + "</strong>,</p>" +
                "        <p>Tu cita ha sido confirmada con éxito.</p>" +
                "        <p>Gracias por confiar en <strong>Arkham Hospital</strong>.</p>" +
                "        <p style='text-align: center;'>" +
                "            <a href='https://www.arkhamhospital.com' style='background-color: #3498db; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;'>Ver detalles</a>" +
                "        </p>" +
                "        <p style='color: #7f8c8d; font-size: 12px; text-align: center;'>© 2025 Arkham Hospital. Todos los derechos reservados.</p>" +
                "    </div>" +
                "</body>" +
                "</html>";

        helper.setText(contenidoHtml, true);

        mailSender.send(mensaje);
    }
}
