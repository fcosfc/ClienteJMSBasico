package com.wordpress.fcosfc.betabeers.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.jboss.logging.Logger;

/**
 * Clase que recibe mensajes desde una cola JMS
 *
 * @author Paco Saucedo
 * @see http://fcosfc.wordpress.com
 */
public class ConsumidorJMSSincrono {

    private static final Logger bitacora = Logger.getLogger(ConsumidorJMSSincrono.class);
    private static final long TIMEOUT = 60000; // Un minuto
    

    /**
     * Método que recibe mensajes JMS desde una cola
     *
     * @param factoriaConexiones Nombre JNDI de la factoría de conexiones JMS
     * @param colaMensajes Nombre JNDI de la cola de mensajes JMS
     */
    public static void recibirMensajes(String factoriaConexiones, String colaMensajes) {
        Context contextoInicial;
        ConnectionFactory factoria;
        Connection conexion = null;
        Session sesion = null;
        Queue cola;
        MessageConsumer consumidorMensajes = null;
        Message mensaje;
        TextMessage mensajeTexto;
        long numMensajesRecibidos = 0;

        try {
            // Busca los recursos proporcionados por el servidor en el árbol
            // JNDI que contiene los objetos
            // Ver los parámetros de conexión en el fichero jndi.properties
            contextoInicial = new InitialContext();
            factoria = (ConnectionFactory) contextoInicial.lookup(factoriaConexiones);
            cola = (Queue) contextoInicial.lookup(colaMensajes);

            // Crea los objetos que dan soporte a la recepción síncrona
            conexion = factoria.createConnection();
            sesion = conexion.createSession(false, Session.AUTO_ACKNOWLEDGE);
            consumidorMensajes = sesion.createConsumer(cola);
            
            // Espera la recepción de mensajes, si no llega ninguno durante un
            // tiempo determinado, finaliza la espera para que no se bloquee la
            // aplicación
            conexion.start();
            while ((mensaje = consumidorMensajes.receive(TIMEOUT)) != null) {
                if (mensaje instanceof TextMessage) {
                    mensajeTexto = (TextMessage) mensaje;
                    bitacora.info(mensajeTexto.getText() + " --> recibido");

                    numMensajesRecibidos++;
                } else {
                    bitacora.warn("Mensaje recibido de un tipo no soportado");
                }
            }
            System.out.println("Numero total de mensajes recibidos: " + numMensajesRecibidos);
        } catch (NamingException ex) {
            bitacora.error(ex.getMessage(), ex);
        } catch (JMSException ex) {
            bitacora.error(ex.getMessage(), ex);
        } finally {
            try {
                if (consumidorMensajes != null) {
                    consumidorMensajes.close();
                }
            } catch (Exception ignorar) {
            }
            try {
                if (sesion != null) {
                    sesion.close();
                }
            } catch (Exception ignorar) {
            }
            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (Exception ignorar) {
            }
        }

    }
}
