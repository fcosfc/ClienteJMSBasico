package com.wordpress.fcosfc.betabeers.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.jboss.logging.Logger;

/**
 * Clase que permite envíar mensajes a una cola JMS
 *
 * @author Paco Saucedo
 * @see http://fcosfc.wordpress.com
 */
public class ProductorJMS {

    private static final Logger bitacora = Logger.getLogger(ProductorJMS.class);

    /**
     * Método que envía mensajes a una cola JMS
     *
     * @param factoriaConexiones Nombre JNDI de la factoría de conexiones JMS
     * @param colaMensajes Nombre JNDI de la cola de mensajes JMS
     * @param nombreProductor Nombre del productor de los mensajes
     * @param numMensajes Número de mensajes a enviar
     */
    public static void enviarMensajes(String factoriaConexiones,
            String colaMensajes,
            String nombreProductor,
            int numMensajes) {
        Context contextoInicial;
        ConnectionFactory factoria;
        Connection conexion = null;
        Session sesion = null;
        Queue cola;
        MessageProducer productorMensajes = null;
        TextMessage mensajeJMS;
        String mensaje;

        try {
            // Busca los recursos proporcionados por el servidor en el árbol
            // JNDI que contiene los objetos
            // Ver los parámetros de conexión en el fichero jndi.properties
            contextoInicial = new InitialContext();
            factoria = (ConnectionFactory) contextoInicial.lookup(factoriaConexiones);
            cola = (Queue) contextoInicial.lookup(colaMensajes);

            // Crea los objetos que dan soporte al envío
            conexion = factoria.createConnection();
            sesion = conexion.createSession(false, Session.AUTO_ACKNOWLEDGE);
            productorMensajes = sesion.createProducer(cola);

            // Envía los mensajes
            mensajeJMS = sesion.createTextMessage();
            for (int i = 1; i <= numMensajes; i++) {
                mensaje = nombreProductor + ": mensaje numero " + i;
                mensajeJMS.setText(mensaje);
                productorMensajes.send(mensajeJMS);

                bitacora.info(mensaje + " --> enviado");
            }

        } catch (NamingException ex) {
            bitacora.error(ex.getMessage(), ex);
        } catch (JMSException ex) {
            bitacora.error(ex.getMessage(), ex);
        } finally { // Libera los recursos
            try {
                if (productorMensajes != null) {
                    productorMensajes.close();
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
