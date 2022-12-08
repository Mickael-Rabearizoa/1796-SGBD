package client;
import java.io.*;
import java.util.Date;
import java.net.*;
import java.util.Scanner;

import relation.Relation;

public class Client {
    public static void main(String[] args) {

        try {   
            Socket client = new Socket("localhost" , 1796); //serveur dans ce pc local et le port pour communiquer
            PrintWriter writer = new PrintWriter(client.getOutputStream());
            Scanner sc = new Scanner(System.in);
            boolean end = false;
            InputStream is = client.getInputStream();                                      
            ObjectInputStream message = new ObjectInputStream(is);

            while(end == false){
                String req = sc.nextLine();
                if(req.compareToIgnoreCase("quit") == 0){
                    end = true;
                }
                writer.println(req);
                writer.flush();
                Object obj = message.readObject();
                
                if(obj instanceof Relation){
                    Relation table = (Relation)(obj);
                    table.affiche();
                }
                if(obj instanceof Exception){
                    Exception e = (Exception)(obj);
                    System.out.println(e.getMessage());
                }
            }
            // InputStream is = client.getInputStream();                                      //lire le message envoyé
            // ObjectInputStream message = new ObjectInputStream(is);
            
            // OutputStream os=client.getOutputStream();
            // ObjectOutputStream mess=new ObjectOutputStream(os);                           //pour avoir l'objet envoyé
            // try{
            //     Object obj  = message.readObject(); 
            //     System.out.println(" obj :  "+obj); 
 
            //     mess.writeObject("Harena: Coucou ralph");                                           //lecture de l'objet envoyé
            // }
            // catch( EOFException e ){
                
            // }
            // finally{
            //     is.close();                                                         ///fermeture de la mémoire tampon 
            //     os.close();
            // }                                 
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        
    }
}