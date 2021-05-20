package com.example.consumingrest.xpath;

import javax.xml.parsers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.*;
import javax.xml.xpath.*;

public class Xpath {
    public static String displayTipoDni(){
        String tipoDniFinal = "";
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document d = db.parse("src\\main\\java\\com\\example\\consumingrest\\data\\clients.xml");
            XPath xp = XPathFactory.newInstance().newXPath();
            NodeList nl = (NodeList)xp.compile("//client").evaluate(d, XPathConstants.NODESET);
            for (int i = 0; i < nl.getLength(); i++){
                // Get tipoDni node
                String tipoDni;
                tipoDni = xp.compile("./tipoDni").evaluate(nl.item(i));
                if (tipoDni.equals("DNI")){
                    tipoDni = "1";
                }
                if (tipoDni.equals("LE")){
                    tipoDni = "2";
                }
                if (tipoDni.equals("PAS")){
                    tipoDni = "3";
                }
                System.out.println(tipoDni);
                tipoDniFinal = tipoDni;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tipoDniFinal;
    }
    public static String displayDni(){
        String dni = "";
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document d = db.parse("src\\main\\java\\com\\example\\consumingrest\\data\\clients.xml");
            XPath xp = XPathFactory.newInstance().newXPath();
            NodeList nl = (NodeList)xp.compile("//client").evaluate(d, XPathConstants.NODESET);
            for (int i = 0; i < nl.getLength(); i++){
                // Get dni node
                String userDni = xp.compile("./userDni").evaluate(nl.item(i));
                System.out.println(userDni);
                dni = userDni;
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return dni;
    }
}
