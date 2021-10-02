package br.com.caelum.jms;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class TestarQueueBrowser {

	public static void main(String[] args) throws Exception {

		Properties properties = new Properties();

		properties.setProperty("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		properties.setProperty("java.naming.provider.url", "tcp://localhost:61616");
		properties.setProperty("queue.Financeiro", "fila.financeiro");

		// E PASSANDO O properties em = "InicialContext(properties);"

		InitialContext context = new InitialContext(properties);
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Destination fila = (Destination) context.lookup("Financeiro");
		QueueBrowser browser = session.createBrowser((Queue) fila);

		Enumeration msgs = browser.getEnumeration();
		while (msgs.hasMoreElements()) {
			TextMessage msg = (TextMessage) msgs.nextElement();
			System.out.println("Message: " + msg.getText());
		}

		new Scanner(System.in).nextLine();

		session.close();
		connection.close();
		context.close();

	}

}
