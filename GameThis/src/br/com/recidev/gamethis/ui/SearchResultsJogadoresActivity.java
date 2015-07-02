package br.com.recidev.gamethis.ui;

import java.io.IOException;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import br.com.recidev.gamethis.R;
import br.com.recidev.gamethis.util.ConstantesGameThis;
import br.com.recidev.gamethis.util.HttpSincronizacaoClient;

public class SearchResultsJogadoresActivity extends Activity {

	private String query;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
		 	super.onCreate(savedInstanceState);
		 	setContentView(R.layout.activity_jogadores);
	        handleIntent(getIntent());
	    }

	    @Override
	    protected void onNewIntent(Intent intent) {
	        handleIntent(intent);
	    }

	    private void handleIntent(Intent intent) {

	    	//use the query to search your data somehow
	        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	            query = intent.getStringExtra(SearchManager.QUERY);
	            
				new ConsultarUsuarioTask().execute();
	            
//	            Usuario usuarioTeste = new Usuario();
//	            usuarioTeste.setEmail("new@gmail.com");
//	            usuarioTeste.setAvatar(2);
//	            
//	            System.out.println(query);
	            
	            
//	            Intent jogadoresIntent = new Intent(getApplicationContext(), JogadoresActivity.class);
//	            jogadoresIntent.putExtra("resultadoPesquisa", "{email:new@gmail.com, avatar:2}");
//	            startActivity(jogadoresIntent);
	            
	        }
	    }
	
	    
	    private class ConsultarUsuarioTask extends AsyncTask<String, String, String> {
			
			@Override
			protected void onPreExecute(){
			};
			
			@Override
			protected String doInBackground(String... params) {
				String resultado = "";
				HttpSincronizacaoClient httpClient = new HttpSincronizacaoClient();
				
				try {
					String path = ConstantesGameThis.PATH_SHOW + query;
					resultado = httpClient.get(path);
					
					if (resultado.equals("")) {
						resultado = "Usuário já existe.";
					} 
				} catch (IOException e) {
					resultado = "Erro no servidor.";
					e.printStackTrace();
				}
				return resultado;
			}; 
			
		 
			@Override
			protected void onPostExecute(String resultado){
				
				Intent jogadoresIntent = new Intent(getApplicationContext(), JogadoresActivity.class);
	            jogadoresIntent.putExtra("resultadoPesquisa", resultado);
	            startActivity(jogadoresIntent);
			};
		}
	    
}
