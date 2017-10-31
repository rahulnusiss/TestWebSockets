
//public class WarehouseBean{}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.*/
 
package learnws;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.ws.rs.client.Client;

/**
 *
 * @author rahul
 */
@MessageDriven(mappedName = "jms/warehouseQueue",
		activationConfig = {
			@ActivationConfigProperty(
					propertyName = "destinationType",
					propertyValue = "javax.jms.Queue"
			)
		}
)
public class WarehouseBean implements MessageListener {
    
    @Override
    public void onMessage(Message message) {
            TextMessage txtMsg = (TextMessage)message;
            System.out.println("... received");
            try {
                    System.out.println("\t" + txtMsg.getText());
            } catch (JMSException ex) {
                    ex.printStackTrace();
            }
            
            ArrayList<Session> webSocketSessions = WarehouseWebSocketSessions.getInstance().getSessions();
            for(Session session: webSocketSessions){
                try {
                    session.getBasicRemote().sendText("{\n" +"\"name\": \"Dhondu Prasad\",\n" +
                            "\"address\": \"Gali Nukkad Mohalla Sheher\",\n" +"\"comment\": \"Bhabhi ji ghar par hain\",\n" +
                            "\"cart\": [\n" +
                            "{\"item\": \"Banana\", \"quantity\": 2},\n" +
                            "{\"item\": \"Mango\", \"quantity\": 5}\n" +
                            "]\n" +
                            "        }");
                } catch (IOException ex) {
                    Logger.getLogger(WarehouseBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            //try {
            //WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            //String uri = "ws://localhost:8080/TestWebSockets/fruits";
            //System.out.println("Connecting to " + uri);
            
            /*Session session = container.connectToServer(FruitsSocket.class, URI.create(uri));
            session.getBasicRemote().sendText("{\n" +"\"name\": \"Dhondu Prasad\",\n" +
             "\"address\": \"Gali Nukkad Mohalla Sheher\",\n" +"\"comment\": \"Bhabhi ji ghar par hain\",\n" +
              "\"cart\": [\n" +
              "{\"item\": \"Banana\", \"quantity\": 2},\n" +
              "{\"item\": \"Mango\", \"quantity\": 5}\n" +
              "]\n" +
              "        }");
            
            //messageLatch.await(100, TimeUnit.SECONDS);
        } catch (DeploymentException | IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }*/
            
    }
    
}
