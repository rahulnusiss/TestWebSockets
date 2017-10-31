/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package learnws;

import java.util.ArrayList;
import javax.websocket.Session;

/**
 *
 * @author rahul
 */
public class WarehouseWebSocketSessions {
    
   private ArrayList<Session> listSession = new ArrayList<Session>();
   
   private static WarehouseWebSocketSessions webSocketSessions = null;
    
   private WarehouseWebSocketSessions(){}
   
   public static WarehouseWebSocketSessions getInstance(){   
       if ( null == webSocketSessions)  {
           webSocketSessions = new WarehouseWebSocketSessions();
       }
           return webSocketSessions;
    }       
    
    public void addSession(Session iSession){
        listSession.add(iSession);
    }
    
    public void removeSession(Session iSession){
        listSession.remove(iSession);
    }
    
    public ArrayList<Session> getSessions(){
        return listSession;
    }
    
}
