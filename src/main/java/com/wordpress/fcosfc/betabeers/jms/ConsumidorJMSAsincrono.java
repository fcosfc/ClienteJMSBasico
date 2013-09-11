package com.wordpress.fcosfc.betabeers.jms;

import java.io.IOException;
import java.io.InputStreamReader;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
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
public class ConsumidorJMSAsincrono {

    private static final Logger bitacora = Logger.getLogger(ConsumidorJMSAsincrono.class);

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
        LectorMensajes lectorMensajes;
        InputStreamReader lectorEntradaConsola;
        char respuesta = '\0';

        try {
            // Busca los recursos proporcionados por el servidor en el árbol
            // JNDI que contiene los objetos
            // Ver los parámetros de conexión en el fichero jndi.properties
            contextoInicial = new InitialContext();
            factoria = (ConnectionFactory) contextoInicial.lookup(factoriaConexiones);
            cola = (Queue) contextoInicial.lookup(colaMensajes);

            // Crea los objetos que dan soporte a la recepción asíncrona
            conexion = factoria.createConnection();
            sesion = conexion.createSession(false, Session.AUTO_ACKNOWLEDGE);
            consumidorMensajes = sesion.createConsumer(cola);
            lectorMensajes = new LectorMensajes();
            consumidorMensajes.setMessageListener(lectorMensajes);

            // Espera la recepción de mensajes
            conexion.start();
            System.out.println("Presione 's' + INTRO para salir");
            lectorEntradaConsola = new InputStreamReader(System.in);
            while (respuesta != 's') {
                respuesta = (char) lectorEntradaConsola.read();
            }
            System.out.println("Numero total de mensajes recibidos: " + lectorMensajes.getNumMensajesRecibidos());
        } catch (NamingException ex) {
            bitacora.error(ex.getMessage(), ex);
        } catch (JMSException ex) {
            bitacora.error(ex.getMessage(), ex);
        } catch (IOException ex) {
            bitacora.error(ex.getMessage(), ex);
        } finally { // Libera los recursos
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
