package br.com.recidev.gamethis;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class InscricaoUsuario {

	private Context contexto;
	private String PATH = "/usuarios/create";
	
	
	public void inserirUsuario(String email, String senha, 
			String nome, String avatar, Context context){
		ArrayList<HashMap<String, String>> listaPalavras = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map = new HashMap<String, String>();
		
		contexto = context;
		
		map.put("email", email);
		map.put("nome", nome);
		map.put("senha", senha);
		map.put("avatar", avatar);
		
		listaPalavras.add(map);
		
	    //Gson gson = new Gson();
		//String convertedJson = gson.toJson(listaPalavras);
		//String json = convertedJson.substring(1, convertedJson.length() - 1);
		String json = "";
		new InscricaoUsuarioTask().execute(PATH, json);
		
		Toast.makeText(context, "Sincronização com DB realizado com sucesso!", 
			Toast.LENGTH_LONG).show();
	}

	
	public class InscricaoUsuarioTask extends AsyncTask<Object, Object, Object> {
		
		@Override
		protected Object doInBackground(Object... params) {			
			String path = (String) params[0].toString();
			String json = (String) params[1].toString();
			
			//HttpSincronizacaoClient httpClient = new HttpSincronizacaoClient();
			
//			try {
//				httpClient.post(path, json);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
				
			return null;
		}
	}
	
	protected void onPreExecute() {
	    System.out.println("pre execute");
	    ProgressDialog.show(contexto, "Titulo", "Aguarde...", false, true);
	}

}


