package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class TesteConsumidorFila {

	public static void main(String[] args) throws Exception {
		
//		FORMA DE CRIAR O PROPERTIES ATRVÉS DE CLASSE
//		Properties properties = new Properties();
//		
//		properties.setProperty("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
//		properties.setProperty("java.naming.provider.url", "tcp://192.168.0.94:61616");
//		properties.setProperty("queue.Financeiro", "fila.financeiro");
//		E PASSANDO O properties em = "InicialContext(properties);"

		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination fila = (Destination) context.lookup("Financeiro");
	//	QueueBrowser browser = session.createBrowser((Queue) fila);
	//	System.out.println(browser.getEnumeration());
		MessageConsumer consumer = session.createConsumer(fila);
		
		//Message receive = consumer.receive();
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				
				TextMessage text = (TextMessage) message;
				
				try {
					System.out.println(text.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
			
		});
		
		new Scanner(System.in).nextLine();
		
		session.close();
		connection.close();
		context.close();
	}

}
