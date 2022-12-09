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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}