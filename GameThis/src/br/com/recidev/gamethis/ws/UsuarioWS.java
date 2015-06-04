package br.com.recidev.gamethis.ws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import br.com.recidev.gamethis.http.HttpSincronizacaoClient;


public class UsuarioWS {
	
	private String PATH = "/usuarios/create";
	
	public void inserirUsuario(String login, String senha, Context context){
		ArrayList<HashMap<String, String>> listaPalavras = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("login", login);
		map.put("senha", senha);
		
		listaPalavras.add(map);
		
		//Gson gson = new Gson();
		//String convertedJson = gson.toJson(listaPalavras);
		//String json = convertedJson.substring(1, convertedJson.length() - 1);
		//new InserirUsuarioTask().execute(PATH, json);
		
		Toast.makeText(context, "Sincronização com DB realizado com sucesso!", 
			Toast.LENGTH_LONG).show();
	}

	
	public class InserirUsuarioTask extends AsyncTask<Object, Object, Object> {
		
		@Override
		protected Object doInBackground(Object... params) {			
			String path = (String) params[0].toString();
			String json = (String) params[1].toString();
			
			HttpSincronizacaoClient httpClient = new HttpSincronizacaoClient();
			
			try {
				httpClient.post(path, json);
			} catch (IOException e) {
				e.printStackTrace();
			}
				
			return null;
		}
	}
	
}
