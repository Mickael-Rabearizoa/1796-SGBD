package thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import grammaire.Grammaire;
import dataBase.Racine;
import java.util.Vector;
import java.util.Scanner;
import relation.Relation;

public class ThreadRequest extends Thread{
    Socket client;
    public ThreadRequest(Socket client){
        setClient(client);
    }

    public Socket getClient() {
        return client;
    }
    public void setClient(Socket client) {
        this.client = client;
    }

    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            Racine racine = new Racine();
            Grammaire gram = racine.getGram();
            boolean end = false;
            OutputStream os = client.getOutputStream();                         
            ObjectOutputStream message = new ObjectOutputStream(os);      
            // message.writeObject(obj);

            while(end == false){
                String requete = reader.readLine();

                Scanner scan = new Scanner(requete);
                Vector request = new Vector();
                while(scan.hasNext()){
                    request.add(scan.next());
                }

                if(request.isEmpty()){

                } else {
                    String quit = String.valueOf(request.get(0)); 
                    if(quit.compareToIgnoreCase("quit") == 0){
                        end = true;
                    } else {
                        try {
                            Relation table = gram.traitementReq(request);
                            message.writeObject(table);
                            // table.affiche();
                        } catch (Exception e) {
                            // TODO: handle exception
                            // System.out.println(e.getMessage());
                            message.writeObject(e);
                        }
                    }
                }
                // System.out.println(requete);
            }
        } catch (Exception e) {
            // TODO: handle exception
            // e.printStackTrace();
        }
        
    }
}