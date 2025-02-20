package com.example.subscription_notificaciones.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.subscription_notificaciones.DTO.ClienteNotificacionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RabbitMQListener {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JavaMailSender mailSender; // Enviará los correos

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

    private void enviarCorreo(ClienteNotificacionDTO cliente) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(cliente.getCorreo());
        mensaje.setSubject("Confirmación de cita médica");
        mensaje.setText("Hola " + cliente.getNombre() + ",\n\nTu cita ha sido confirmada con éxito.\n\nSaludos,\nArkham Hospital");

        mailSender.send(mensaje);
    }
}