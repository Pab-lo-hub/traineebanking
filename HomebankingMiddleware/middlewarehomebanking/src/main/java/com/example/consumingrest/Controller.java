package com.example.consumingrest;

import com.example.consumingrest.xpath.Xpath;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST}) //CORS
public class Controller {
    @Autowired
    RestTemplate restTemplate;
    //Las comunicaciones con el backend se hacen a través de RestTemplate

    @RequestMapping(value = "/middleware")
    public String getDni() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        return restTemplate.exchange("https://homebanking-citi-backend.herokuapp.com/api/account", HttpMethod.GET, entity, String.class).getBody();
    }

    @RequestMapping(value = "/middleware", method = RequestMethod.POST)
    public String createDni() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        return restTemplate.exchange("https://homebanking-citi-backend.herokuapp.com/api/account", HttpMethod.POST, entity, String.class).getBody();
    }

    @RequestMapping(value = "/middleware/{userDni}/{tipoDni}")
    public ResponseEntity getAccount(@PathVariable String userDni, @PathVariable String tipoDni) {
        System.out.println(tipoDni + "0");
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        if (tipoDni.equals("DNI")) {
            tipoDni = "1";
            System.out.println(tipoDni);
        }
        if (tipoDni.equals("LE")) {
            System.out.println(tipoDni + "estoy en if");
            tipoDni = "2";
        }
        if (tipoDni.equals("PAS")) {
            tipoDni = "3";
            System.out.println(tipoDni);
        }
        if (tipoDni.length() == 0) {
            tipoDni = "0";
            System.out.println("Ocurrió un error. Revise los datos ingresados");
            return new ResponseEntity("Ocurrió un error. Revise los datos ingresados", HttpStatus.BAD_REQUEST);
        }
        System.out.println(tipoDni + "4");
        System.out.println(restTemplate.exchange("https://homebanking-citi-backend.herokuapp.com/api/account/" + userDni + "/" + tipoDni, HttpMethod.GET, entity, String.class).getBody());
        return new ResponseEntity<>(restTemplate.exchange("https://homebanking-citi-backend.herokuapp.com/api/account/" + userDni + "/" + tipoDni, HttpMethod.GET, entity, String.class).getBody(), HttpStatus.ACCEPTED);
    }

    //Esta método toma un Json, logra las conversiones pedidas y envía un xml al backend.
    @RequestMapping(value = "/middleware/xml", method = RequestMethod.POST, produces = {MediaType.TEXT_HTML_VALUE})
    public ResponseEntity getAccountFromXML (@RequestBody Account newAccount) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        /*Redondeo*/
        Integer cuentaOrigen = Math.round(newAccount.getCuentaOrigen());
        Integer importe = Math.round(newAccount.getImporte());
        Integer cuentaDestino = Math.round(newAccount.getCuentaDestino());
        /*Formato de fecha y hora*/
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMMM-yyyy  HH:mm:ss");
        String date = simpleDateFormat.format(new Date());
        /*Reemplazo primeros digitos por x*/
        String cbu = newAccount.getCbuDestinatario().substring(14, 22);
        String cbu1 = "xxxxxxxxxxxxxx".concat(cbu);
        /*Traduccion 1 2 3*/
        String moneda = newAccount.getMoneda();
        if (newAccount.getMoneda().equals("Dolar")){
            moneda = "1";
        }
        if (newAccount.getMoneda().equals("Pesos Chilenos")){
            moneda = "2";
        }
        if (newAccount.getMoneda().equals("Pesos Argentinos")){
            moneda = "3";
        }
        String xml_data2 = null;
        try {
            String json_data = "{\n" +
                    "    \"transferencia\": {\n" +
                    "       \"fecha\": "+"\"" + date + "\",\n" +
                    "       \"nombreOrigen\": "+"\"" + newAccount.getNombreOrigen() + "\",\n" +
                    "       \"cuentaOrigen\": "+"\"" + cuentaOrigen + "\",\n" +
                    "       \"nombreDestinatario\": "+"\"" + newAccount.getNombreDestinatario() + "\",\n" +
                    "       \"cuentaDestino\": "+"\"" + cuentaDestino + "\",\n" +
                    "       \"cbuDestinatario\": "+"\"" + cbu1 + "\",\n" +
                    "       \"moneda\": "+"\"" + moneda + "\",\n" +
                    "       \"importe\": "+"\"" + importe + "\",\n" +
                    "       \"plazoAcreditacion\": "+"\"" + newAccount.getPlazoAcreditacion() + "\",\n" +
                    "       \"concepto\": "+"\"" + newAccount.getConcepto() + "\",\n" +
                    "    }\n" +
                    " }";
            JSONObject obj = new JSONObject(json_data);
            //converting json to xml
            String xml_data = XML.toString(obj);
            System.out.println(xml_data);
            xml_data2 = xml_data;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(xml_data2);
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + xml_data2;
        System.out.println(xmlString);
        RestTemplate restTemplate =  new RestTemplate();
        //Create a list for the message converters
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        //Add the String Message converter
        messageConverters.add(new StringHttpMessageConverter());
        //Add the message converters to the restTemplate
        restTemplate.setMessageConverters(messageConverters);
        HttpHeaders headers2 = new HttpHeaders();
        headers2.setContentType(MediaType.APPLICATION_XML);
        HttpEntity<String> request = new HttpEntity<String>(xmlString, headers2);
        return new  ResponseEntity(restTemplate.postForEntity("https://homebanking-citi-backend.herokuapp.com/api/transferencia/", request, String.class).getBody(), HttpStatus.ACCEPTED);
    }
}
