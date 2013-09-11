package com.wordpress.fcosfc.betabeers.jms;

/**
 * Clase cliente para operaciones de envío y recepción de mensajes
 *
 * @author Paco Saucedo
 * @see http://fcosfc.wordpress.com
 */
public class ClienteJMS {        

    /**
     * Método principal, analiza la línea de comando y realiza la operación solicitada
     *
     * @param args Argumentos de la línea de comando
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            mostrarAyuda();
        } else {
            if (args[0].equals("e")) {
                if (args.length != 5) {
                    System.out.println("El número de argumentos no es el esperado");
                    mostrarAyuda();
                } else {                    
                    ProductorJMS.enviarMensajes(args[1], args[2], args[3], Integer.parseInt(args[4]));
                }
            } else if (args[0].equals("r") || args[0].equals("s")) {
                if (args.length != 3) {
                    System.out.println("El número de argumentos no es el esperado");
                    mostrarAyuda();
                } else {
                    if (args[0].equals("r")) {
                        ConsumidorJMSAsincrono.recibirMensajes(args[1], args[2]);
                    } else {
                        ConsumidorJMSSincrono.recibirMensajes(args[1], args[2]);
                    }
                }
            } else {
                System.out.println("Operación no válida");
                mostrarAyuda();
            }
        }
    }

    /**
     * Método que muestra la ayuda de la aplicación
     */
    private static void mostrarAyuda() {
        System.out.println("Uso: java -jar ClienteJMS-1.0.jar Operacion FactoriaConexionesJMS ColaJMS [NombreProductor] [NumeroMensajes]");
        System.out.println("   Operacion: (e)nviar | (r)ecibir de forma asíncrona | recibir de forma (s)íncrona");
        System.out.println("   FactoriaConexionesJMS: nombre JNDI de la factoría de conexiones JMS");
        System.out.println("   ColaJMS: nombre JNDI de la cola JMS");
        System.out.println("   NombreProductor: nombre del productor JMS que envía los mensajes");
        System.out.println("   NumeroMensajes: número de mensajes a enviar");
    }
}
