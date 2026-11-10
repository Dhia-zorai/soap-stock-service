package com.tp.soap;

import com.tp.soap.service.StockServiceImpl;
import jakarta.xml.ws.Endpoint;

public class ServerMain {
    public static void main(String[] args) {

        //also fama &quot; at the end of the link 9bal wsdl na7inaha

        String url = "http://localhost:8080/ws/stock";
        System.out.println("Lancement du serveur sur : " + url);
        Endpoint.publish(url, new StockServiceImpl());
        System.out.println("Le contrat WSDL est disponible à l'adresse : " + url + "?wsdl");
    }
}