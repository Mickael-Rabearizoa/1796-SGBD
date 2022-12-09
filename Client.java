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
            System.out.println("connected");
            PrintWriter writer = new PrintWriter(client.getOutputStream());
            Scanner sc = new Scanner(System.in);
            boolean end = false;
            InputStream is = client.getInputStream();                                      
            ObjectInputStream message = new ObjectInputStream(is);

            while(end == false){
                System.out.println();
                System.out.print("sql>");
                String req = sc.nextLine();
                if(req.compareToIgnoreCase("quit") == 0){
                    end = true;
                    is.close();
                } else {
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
                
            }                      
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        
    }
}