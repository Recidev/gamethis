package br.com.recidev.gamethis.ui;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.recidev.gamethis.R;
import br.com.recidev.gamethis.adapter.AtividadesConvidadoAdapter;
import br.com.recidev.gamethis.dominio.Atividade;
import br.com.recidev.gamethis.dominio.Jogo;
import br.com.recidev.gamethis.util.ConstantesGameThis;
import br.com.recidev.gamethis.util.HttpSincronizacaoClient;

public class DetalhesJogoConvidadoActivity extends Activity {
	
	private Atividade atividadeConvidado;
	private Jogo jogoSelecionado;
	private ArrayList<Atividade> listaAtividadesJogoConvidado;
	private String dadosRequisicao;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalhes_jogo_convidado);
		
		jogoSelecionado = (Jogo) getIntent().getSerializableExtra("jogoConvidadoSelecionado");
		listaAtividadesJogoConvidado = (ArrayList<Atividade>) getIntent().getSerializableExtra("listaAtividadesJogoConvidado");
		
		TextView textoDescricaoJogo  = (TextView) findViewById(R.id.textoDescricaoJogoConvidadoDetalhesValor);
		textoDescricaoJogo.setText(jogoSelecionado.getDescricao());
		
		TextView textoLoginCriador  = (TextView) (TextView)findViewById(R.id.textoLoginCriadorJogoValor);
		textoLoginCriador.setText(jogoSelecionado.getLoginCriador());
		
		
		final Button botaoRanking = (Button) findViewById(R.id.botao_ranking);
		botaoRanking.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new RankingTask().execute();		
			}
		});
		
		if(listaAtividadesJogoConvidado != null){
			AtividadesConvidadoAdapter atividadesConvidadoAdapter = new AtividadesConvidadoAdapter(this, listaAtividadesJogoConvidado);
			ListView atividadesConvidadoListView = (ListView) findViewById(R.id.atividadesConvidadoListView);
			atividadesConvidadoListView.setAdapter(atividadesConvidadoAdapter);
			
			atividadesConvidadoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Object listItem = parent.getItemAtPosition(position);
					atividadeConvidado = (Atividade) listItem;
					
					Intent detalhesAtividadeConvidadoIntent = new Intent(getApplicationContext(), 
							DetalhesAtividadeConvidadoActivity.class);
					detalhesAtividadeConvidadoIntent.putExtra("atividadeConvidado", atividadeConvidado);
		            startActivity(detalhesAtividadeConvidadoIntent);
				} 
			});
		}
	}
	
	
	private class RankingTask extends AsyncTask<String, String, String> {
		protected ProgressDialog dialogo = new ProgressDialog(DetalhesJogoConvidadoActivity.this);
		
		@Override
		protected void onPreExecute(){
		    dialogo.setProgressStyle(ProgressDialog.THEME_HOLO_DARK);
		    dialogo.setCancelable(false);
		    dialogo.setTitle("Consultando ranking");
		    dialogo.setMessage("Por favor, aguarde...");
		    dialogo.show();
		};
		
		@Override
		protected String doInBackground(String... params) {
			String msgResposta = "";
			HttpSincronizacaoClient httpClient = new HttpSincronizacaoClient();
			String path = "";
			
			try {
				path = ConstantesGameThis.PATH_RANKING_SHOW + jogoSelecionado.getNaturalId();
				dadosRequisicao = httpClient.get(path);
				msgResposta = "sucesso";
			} catch (IOException e) {
				msgResposta = "Erro no servidor.";
				e.printStackTrace();
			}
			return msgResposta;
		}; 
	 
		@Override
		protected void onPostExecute(String msgResposta){
			dialogo.dismiss(); 
			
			if(msgResposta.equals("sucesso")){
				Intent rankingIntent = new Intent(getApplicationContext(), RankingActivity.class);
				rankingIntent.putExtra("dadosRequisicao", dadosRequisicao);
				startActivity(rankingIntent);
			} else {
				Toast.makeText(getApplicationContext(), msgResposta, Toast.LENGTH_LONG).show();
			}
		};
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detalhes_jogo_convidado, menu);
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
