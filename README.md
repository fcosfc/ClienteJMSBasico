ClienteJMSBasico
================

Cliente JMS básico que muestra cómo enviar y recibir mensajes.

El proyecto ha sido creado para ser presentado como apoyo a una charla sobre JMS en el Betabeers Estrecho de Gibraltar. Dispone de un productor de mensajes, un lector síncrono y otro asíncrono.

Ha sido desarrollado usando NetBeans 7.3 (es un proyecto maven, lo que implica que se pueden usar otros entornos de desarollo compatibles con maven) y prevé conectarse a un servidor de aplicaciones JBoss 6.1.0, con HornetQ 2.2.5 instalado (viene por defecto en la instalación de JBoss).

Si se tiene instalado maven, git y jboss, se pueden seguir las siguientes instrucciones (Linux, pero fácilmente trasladables a MS Windows) para ejecutarlo:

   git clone https://github.com/fcosfc/ClienteJMSBasico.git
   
   cd ClienteJMSBasico
   
   mvn install
   
   cp target/ClienteJMSBasico-1.0-bin.zip ~
   
   cd ~
   
   unzip ClienteJMSBasico-1.0-bin
   
   cd ClienteJMSBasico-1.0
   
   java -jar ClienteJMSBasico-1.0.jar

El comando anterior muestra la ayuda de la aplicación, para una prueba real se tiene que crear una cola en JBoss, editando el fichero hornetq-jms.xml, situado en $JBOSS_HOME/server/default/deploy/hornetq, y añadiendo, por ejemplo:

   &ltqueue name="Betabeers.Test"&gt
      &ltentry name="jms/Colas/Betabeers/Test"/&gt
   &lt/queue>
   
Iniciar JBoss y dejar el consumidor asíncrono esperando datos:

   java -jar ClienteJMSBasico-1.0.jar r /ConnectionFactory jms/Colas/Betabeers/Test
   
Enviar, a continuación, algunos mensajes de prueba:

   java -jar ClienteJMSBasico-1.0.jar e /ConnectionFactory jms/Colas/Betabeers/Test Prueba 10
