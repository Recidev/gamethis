package br.com.recidev.gamethis.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpSincronizacaoClient {
	
	private static final String ENCODING = "UTF-8";
	//private final String URL = "http://192.168.25.5/webservice";
	private final String URL = "http://gamethis-recidev.rhcloud.com/webservice";
	
	
    public HttpSincronizacaoClient(){   
    }


    public String get(String path) throws IOException {
    	URL url = new URL(URL + path);
    	HttpURLConnection conn = (HttpURLConnection) url.openConnection();

    	//Le dados da requisicao GET (RESPONSE)
    	BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    	String line;
    	StringBuffer response = new StringBuffer(); 
    	while ((line = reader.readLine()) != null) {
    		response.append(line);
            response.append('\r');
    	}
    	reader.close();
    	
    	String retorno = response.toString();
        return retorno;
    }
    
    
    public String post(String path, String json) throws IOException {
    	URL url = new URL(URL + path);
    	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    	conn.setRequestMethod("POST");
    	conn.setRequestProperty("Content-Type", "application/json; charset=" + ENCODING);
    	conn.setRequestProperty("Accept", "application/json; charset=" + ENCODING);
    	conn.setDoOutput(true);
    	conn.setDoInput(true);
    	
    	//Escreve parametros a serem enviados via POST (Request)
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(json);
        wr.flush();
        wr.close();
    	
    	//Le dados da requisicao POST (RESPONSE)
    	BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    	String line;
    	StringBuffer response = new StringBuffer(); 
    	while ((line = reader.readLine()) != null) {
    		response.append(line);
            response.append('\r');
    	}
    	reader.close();
    	
    	String retorno = response.toString();
        return retorno;
    }

}
