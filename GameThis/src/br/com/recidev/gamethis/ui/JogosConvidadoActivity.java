package br.com.recidev.gamethis.ui;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import br.com.recidev.gamethis.R;
import br.com.recidev.gamethis.adapter.JogosConvidadoAdapter;
import br.com.recidev.gamethis.dominio.Atividade;
import br.com.recidev.gamethis.dominio.Jogo;
import br.com.recidev.gamethis.util.ConstantesGameThis;
import br.com.recidev.gamethis.util.HttpSincronizacaoClient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JogosConvidadoActivity extends Activity {
	
	private JogosConvidadoAdapter jogosConvidadoAdapter;
	private ListView jogosConvidadoListView;
	private Jogo jogoConvidado;
	private ArrayList<Atividade> listaAtividadesJogoConvidado;
	
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jogos_convidado);
		context = getApplicationContext();
		
		String dadosRequisicao = getIntent().getStringExtra("dadosRequisicao");
		
		ArrayList<Jogo> jogos = new ArrayList<Jogo>();
		Gson gson = new Gson();
		jogos = gson.fromJson(dadosRequisicao, new TypeToken<ArrayList<Jogo>>(){}.getType());
		
		jogosConvidadoAdapter = new JogosConvidadoAdapter(this, jogos);
		jogosConvidadoListView = (ListView) findViewById(R.id.jogosConvidadoListView);
		jogosConvidadoListView.setAdapter(jogosConvidadoAdapter);
		
		jogosConvidadoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				 Object listItem = parent.getItemAtPosition(position);
				 jogoConvidado = (Jogo) listItem;

				 new JogoConvidadoTask().execute();
			} 
		});
	}

	

	private class JogoConvidadoTask extends AsyncTask<String, String, String> {
		protected ProgressDialog dialogo = new ProgressDialog(JogosConvidadoActivity.this);
		
		@Override
		protected void onPreExecute(){
			dialogo.setProgressStyle(ProgressDialog.THEME_HOLO_DARK);
		    dialogo.setCancelable(false);
		    dialogo.setTitle("Buscando informações do Jogo");
		    dialogo.setMessage("Por favor, aguarde...");
		    dialogo.show(); 
		};
		
		
		@Override
		protected String doInBackground(String... params) {
			String atividadesJogoConvidado = "";
			String msgResposta = "";
			HttpSincronizacaoClient httpClient = new HttpSincronizacaoClient();
			
			try {
				String path = ConstantesGameThis.PATH_ATIVIDADE_SHOW_ID_JOGO + jogoConvidado.getNaturalId();
				
				//Envia requisicao para inscricao
				atividadesJogoConvidado = httpClient.get(path);
				
				Gson gson = new Gson();
				listaAtividadesJogoConvidado = gson.fromJson(atividadesJogoConvidado, new TypeToken<ArrayList<Atividade>>(){}.getType());
				
				msgResposta = "sucesso";
			} catch (IOException e) {
				atividadesJogoConvidado = "Erro no servidor.";
				e.printStackTrace();
			}
			return msgResposta;
		}; 
		
	 
		@Override
		protected void onPostExecute(String msgResposta){
			dialogo.dismiss(); 
			
			if(msgResposta.equals("sucesso")){
				 Intent detalhesJogoConvidadoIntent = new Intent(getApplicationContext(), DetalhesJogoConvidadoActivity.class);
				 detalhesJogoConvidadoIntent.putExtra("jogoConvidadoSelecionado", jogoConvidado);
				 detalhesJogoConvidadoIntent.putExtra("listaAtividadesJogoConvidado", listaAtividadesJogoConvidado);
				 startActivity(detalhesJogoConvidadoIntent);
			} else {
				Toast.makeText(context, msgResposta, Toast.LENGTH_LONG).show();
			}
		};
	}
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.jogos_convidado, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
