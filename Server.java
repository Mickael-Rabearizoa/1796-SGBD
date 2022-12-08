package server;
import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.Vector;
import java.util.Scanner;
import relation.Relation;
import requete.FunctReq;
import thread.ThreadRequest;
import grammaire.Grammaire;
import relation.Ligne;
import dataBase.Bdd;
import dataBase.Racine;

public class Server{
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket( 1796); 
            
            while(true){
                Socket client = server.accept(); 
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

                ThreadRequest thread = new ThreadRequest(client);
                thread.start();
            }
            // ServerSocket server = new ServerSocket( 1796);             //création du serveur
            // while(true){
            //     Socket client = server.accept(); 
            //     try {   
            //         Racine racine = new Racine();
            //         Grammaire gram = racine.getGram();
        
            //         Scanner sc = new Scanner(System.in);
                    
            //         boolean end = false;
                    
            //         while(end == false){
            //             String req = sc.nextLine();
            //             Scanner scan = new Scanner(req);
            //             Vector request = new Vector();
            //             while(scan.hasNext()){
            //                 request.add(scan.next());
            //             }
        
            //             if(request.isEmpty()){
        
            //             } else {
            //                 String quit = String.valueOf(request.get(0)); 
            //                 if(quit.compareToIgnoreCase("quit") == 0){
            //                     end = true;
            //                 } else {
            //                     gram.traitementReq(request);
            //                 }
            //             }
            //         }
            //     } catch (Exception e) {
            //         e.printStackTrace();
            //     }
            // }
            //                                       //client pour communiquer
        
            // Object obj  = "Covid!!!" ;                                         //objet a envoyé
            // OutputStream os = client.getOutputStream();                         //send message au client
            // ObjectOutputStream message = new ObjectOutputStream(os);            //pour l'envoie de l'objet
            // message.writeObject(obj);                                           

            // InputStream is=client.getInputStream();
            // ObjectInputStream mess=new ObjectInputStream(is);
            // obj=mess.readObject();
            // System.out.println("Avy tany @ client "+obj);
            //                                                         //fermer la mémoire tampon
            // os.close();
            // is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}