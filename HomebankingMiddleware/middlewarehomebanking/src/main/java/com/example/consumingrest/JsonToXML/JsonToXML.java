package com.example.consumingrest.JsonToXML;

import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;

//Método que toma un json y lo convierte a XML
public class JsonToXML {
    public static String JsonToXML(String... s) {
        String jsonxmlv = "";
        try {
            String json_data = "{\n" +
                    "    \"Transaction\": {\n" +
                    "       \"Fecha\": \"2021-04-13T04:00:00Z\",\n" +
                    "       \"NombreOrigen\": \"Graciani Jose\",\n" +
                    "       \"CuentaOrigen\": \"8049\",\n" +
                    "       \"NombreDestinatario\": \"Jorge Leonardo\",\n" +
                    "       \"CuentaDestino\": \"0004592819\",\n" +
                    "       \"cbuDestinatario\": \"1234567891012131415169\",\n" +
                    "       \"Moneda\": \"Dolar\",\n" +
                    "       \"Importe\": \"6000.745\",\n" +
                    "       \"PlazoAcreditacion\": \"Transferencia inmediata\",\n" +
                    "       \"Concepto\": \"Varios\",\n" +
                    "       \"NroComprobante\": \"7550\"\n" +
                    "    }\n" +
                    " }";
            JSONObject obj = new JSONObject(json_data);
            //converting json to xml
            String xml_data = XML.toString(obj);
            System.out.println(xml_data);
            jsonxmlv = xml_data;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return jsonxmlv;
    }
}
