package br.com.caelum.jms;

import java.util.Iterator;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class TesteProdutorFila {

	public static void main(String[] args) throws Exception {

		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination fila = (Destination) context.lookup("Financeiro");
		
		MessageProducer producer = session.createProducer(fila);
		
		
		for (int i = 0; i < 10; i++) {
			
			Message message = session.createTextMessage("<pedido><id>" + i + "</id></pedido>");

			producer.send(message);

		}
				
		session.close();
		connection.close();
		context.close();
	}

}
