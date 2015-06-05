package br.com.recidev.gamethis.ws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import br.com.recidev.gamethis.dominio.Usuario;
import br.com.recidev.gamethis.http.HttpSincronizacaoClient;

import com.google.gson.Gson;


public class UsuarioWS {
	String resultado;
	
	private final String PATH_CREATE = "/usuarios/create";
	private final String PATH_INDEX = "/usuarios";
	private final String PATH_SHOW = "/usuarios/";
	private final String PATH_UPDATE = "/usuarios/:id"; 
	private final String PATH_DELETE = "/usuarios/:id";
	
	Context contexto;
	
	public void inserirUsuario(String email, String senha, String nome, int avatar, Context context){
		String tipoRequest = "POST";
		ArrayList<HashMap<String, Object>> listaPalavras = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("email", email);
		map.put("senha", senha);
		map.put("nome", nome);
		map.put("avatar", avatar);
		
		listaPalavras.add(map);
		
		Gson gson = new Gson();
		String convertedJson = gson.toJson(listaPalavras);
		String json = convertedJson.substring(1, convertedJson.length() - 1);
		new InserirUsuarioTask().execute(PATH_CREATE, tipoRequest, json);
	}
	
	
	public Usuario consultarUsuario(String email, Context context){
		contexto = context;
		Usuario usuario = new Usuario();
		String tipoRequest = "GET";
//		ArrayList<HashMap<String, Object>> listaPalavras = new ArrayList<HashMap<String, Object>>();
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		
//		map.put("email", email);
//		
//		listaPalavras.add(map);
//		
//		Gson gson = new Gson();
//		String convertedJson = gson.toJson(listaPalavras);
//		String json = convertedJson.substring(1, convertedJson.length() - 1);
		
		String path = PATH_SHOW + email;
		new InserirUsuarioTask().execute(path, tipoRequest);
		
		//String convertedString = gson.
		
		
		return usuario;
	}
	

	
	public class InserirUsuarioTask extends AsyncTask<Object, Object, Object> {
		
		
		@Override
		protected Object doInBackground(Object... params) {			
			//String resposta = "";
			int indice = 0;
			String json;
			
			String path = (String) params[indice++].toString();
			String tipoRequest = (String) params[indice++].toString();
			
			HttpSincronizacaoClient httpClient = new HttpSincronizacaoClient();
			
			try {
				
				if(tipoRequest.equals("POST")){
					json = (String) params[indice++].toString();
					resultado = httpClient.post(path, json);
				} else if(tipoRequest.equals("GET")){
					//resultado = httpClient.get(path);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
				
			return resultado;
		}
		
	//	@Override
//		protected void onPreExecute(){
//		    ProgressDialog dialogo = new ProgressDialog(contexto);
//		    dialogo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//		    dialogo.setCancelable(false);
//		    dialogo.setMessage("Aguarde...");
//		    dialogo.show(); 
//		}; 
		
		
	}
	
}
