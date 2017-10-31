/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package learnws;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author rahul
 */
//@Stateless
@RequestScoped
@ServerEndpoint("/fruits")
public class FruitsSocket {
    
    private Session session;
    private String jmsMessage = "{ }";
    
    //@Resource(lookup = "jms/connectionFactory")
    //private ConnectionFactory factory;
    
    //@Resource(lookup = "jms/warehouseQueue")
    //private Queue fruitsQueue;
    
    @OnOpen
    public void open(Session session) throws IOException, JMSException{
        this.session = session;
        System.out.println("Client connected!!");
        
//        try (JMSContext jmsCtx = factory.createContext()) {
//			JMSConsumer consumer = jmsCtx.createConsumer(fruitsQueue);
//			TextMessage txtMsg = jmsCtx.createTextMessage();
//                        TextMessage textMsg = (TextMessage)consumer.receive();
//                        
//                        //Message msg = consumer.receive();
//                        String text = textMsg.getText();
//                        
//                        System.out.println("Recieved Text: " + text);
//			//txtMsg.setText(new Date() + ">> " + contents.toString());
//			
//
//		}
        
        WarehouseWebSocketSessions.getInstance().addSession(session);
        //JMSConsumer consumer;
        //MessageConsumer consumer = session.createConsumer(fruitsQueue);
       /* this.session.getBasicRemote().sendText("{\n" +
"            \"name\": \"Bhondu Prasad\",\n" +
"            \"address\": \"Gali Nukkad Mohalla Sheher\",\n" +
"            \"comment\": \"Bhabhi ji ghar par hain\",\n" +
"            \"cart\": [\n" +
"                    {\"item\": \"Banana\", \"quantity\": 2},\n" +
"                    {\"item\": \"Mango\", \"quantity\": 5}\n" +
"                    ]\n" +
"        }");*/
        /*
        {
            "name": "Bhondu Prasad",
            "address": "Gali Nukkad Mohalla Sheher",
            "comment": "Bhabhi ji ghar par hain",
            "cart": [
                    {"item": "Banana", "quantity": 2},
                    {"item": "Mango", "quantity": 5}
                    ]
        }
        */
    }
    
    @OnMessage
    public void message(String message, Session session) throws IOException{
        
        //Print the message received
        System.out.println("Message received:" + message);
        
        //this.session.getAsyncRemote().sendText("Asyn Message");
        //Send message
        //this.session.getBasicRemote().sendText(message);
        session.getBasicRemote().sendText(message);
        
        //close(session);
    }
    
    @OnClose
    public void close(Session session){
        try {
            this.session.close();
        } catch (IOException ex) {
            Logger.getLogger(FruitsSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*@Override
    public void onMessage(Message message) {
            TextMessage txtMsg = (TextMessage)message;
            System.out.println("... received");
            try {
                jmsMessage = txtMsg.getText();
                    System.out.println("\t" + txtMsg.getText());
            } catch (JMSException ex) {
                    ex.printStackTrace();
            }
    }*/
    
}
