ClienteJMSBasico
================

Cliente JMS básico que muestra cómo enviar y recibir mensajes.

El proyecto sirvió de apoyo a una charla sobre intercambio de datos mediante JMS, dentro del Betabeers Estrecho de Gibraltar, celebrado en Algeciras el día 24 de octubre de 2013. Dispone de un productor de mensajes, un lector síncrono y otro asíncrono.

Ha sido desarrollado usando NetBeans 7.3 (es un proyecto maven, lo que implica que se pueden usar otros entornos de desarollo compatibles con maven) y prevé conectarse a un proveedor de mensajería HornetQ 2.2.5 instalado en localhost.

El siguiente vídeo explica como construir y ejecutar el proyecto en Windows XP: http://youtu.be/ss3FHrqx_vU 

De todas formas, si se tiene instalado maven, git y hornetq, se pueden seguir las siguientes instrucciones (probadas en Linux Ubuntu 12.10) para ejecutarlo:

   git clone https://github.com/fcosfc/ClienteJMSBasico.git
   
   cd ClienteJMSBasico
   
   mvn install
   
   cp target/ClienteJMSBasico-1.1-SNAPSHOT-bin.zip ~
   
   cd ~
   
   unzip ClienteJMSBasico-1.1-SNAPSHOT-bin
   
   cd ClienteJMSBasico-1.1-SNAPSHOT
   
   java -jar ClienteJMSBasico-1.1-SNAPSHOT.jar

El comando anterior muestra la ayuda de la aplicación, para una prueba real se tiene que crear una cola en HornetQ, editando el fichero hornetq-jms.xml, situado en $HORNETQ_HOME/config/stand-alone/non-clustered, y añadiendo, por ejemplo:

   \<queue name="Betabeers.Test"\>
      \<entry name="jms/Colas/Betabeers/Test"/\>
   \</queue\>
   
Iniciar HornetQ ($HORNETQ_HOME/bin/run.sh) y dejar el consumidor asíncrono esperando mensajes:

   java -jar ClienteJMSBasico-1.1-SNAPSHOT.jar r /ConnectionFactory jms/Colas/Betabeers/Test
   
Enviar, a continuación, algunos mensajes de prueba:

   java -jar ClienteJMSBasico-1.1-SNAPSHOT.jar e /ConnectionFactory jms/Colas/Betabeers/Test Prueba 10
