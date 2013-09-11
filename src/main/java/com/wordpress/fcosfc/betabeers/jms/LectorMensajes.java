package com.wordpress.fcosfc.betabeers.jms;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import org.jboss.logging.Logger;

/**
 * Clase que se queda a la espera de la recepción de mensajes JMS
 * 
 * @author Paco Saucedo
 * @see http://fcosfc.wordpress.com
 */
public class LectorMensajes implements MessageListener  {

    private static final Logger bitacora = Logger.getLogger(LectorMensajes.class);
    private long numMensajesRecibidos;
    
    /**
     * Método que procesa un mensaje cuando se recibe
     * 
     * @param msg Mensaje JMS
     */
    public void onMessage(Message msg) {
        TextMessage mensajeJMS;
        
        try {
            if (msg instanceof TextMessage) {
                mensajeJMS = (TextMessage) msg;                        
                bitacora.info(mensajeJMS.getText() + " --> recibido");
                
                numMensajesRecibidos++;
            } else {
                bitacora.warn("Mensaje recibido de un tipo no soportado");
            }
        } catch (Exception ex) {
            bitacora.error(ex.getMessage(), ex);
        }
    }

    /**
     * Método que devuelve el número de mensajes recibidos por el lector
     * 
     * @return Número de mensajes recibidos por el lector
     */
    public long getNumMensajesRecibidos() {
        return numMensajesRecibidos;
    }
    
}
