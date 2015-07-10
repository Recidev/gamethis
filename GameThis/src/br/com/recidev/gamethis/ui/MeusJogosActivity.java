package br.com.recidev.gamethis.ui;

import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import br.com.recidev.gamethis.R;
import br.com.recidev.gamethis.util.ConstantesGameThis;
import br.com.recidev.gamethis.util.GerenciadorSessao;
import br.com.recidev.gamethis.util.HttpSincronizacaoClient;

public class MeusJogosActivity extends Activity {

	GerenciadorSessao sessao;
	private boolean opcaoGerenciados = false;
	private boolean opcaoConvidado = false;
	String dadosRequisicao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meus_jogos);
		
		sessao = new GerenciadorSessao(getApplicationContext());
		
		final Button botaoJogosGerenciados = (Button) findViewById(R.id.botao_jogos_gerenciados);
		botaoJogosGerenciados.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				opcaoGerenciados = true;
				opcaoConvidado = false;
				new MeusJogosTask().execute();
			}
		});
		
		
		final Button botaoJogosConvidado = (Button) findViewById(R.id.botao_jogos_convidados);
		botaoJogosConvidado.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				opcaoGerenciados = false;
				opcaoConvidado = true;
				new MeusJogosTask().execute();
			}
		});
	}
	
	
	
	private class MeusJogosTask extends AsyncTask<String, String, String> {
		protected ProgressDialog dialogo = new ProgressDialog(MeusJogosActivity.this);
		
		@Override
		protected void onPreExecute(){
		    dialogo.setProgressStyle(ProgressDialog.THEME_HOLO_DARK);
		    dialogo.setCancelable(false);
		    dialogo.setTitle("Buscando jogos");
		    dialogo.setMessage("Por favor, aguarde...");
		    dialogo.show(); 
		};
		
		
		@Override
		protected String doInBackground(String... params) {
			String msgResposta = "";
			HttpSincronizacaoClient httpClient = new HttpSincronizacaoClient();
			String path = "";
			
			try{
				String loginUsuario = sessao.preferencias.getString(GerenciadorSessao.EMAIL_KEY, "none");
				
				if(opcaoGerenciados){
					path = ConstantesGameThis.PATH_JOGO_USUARIO_CRIADOR_SHOW + loginUsuario;
				} 
				if(opcaoConvidado) {
					path = ConstantesGameThis.PATH_JOGO_JOGADOR_SHOW + loginUsuario;
				}
				
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
				Intent intent = null;
				if(opcaoGerenciados){
					intent = new Intent(getApplicationContext(), JogosGerenciadosActivity.class);
				}
				if(opcaoConvidado){
					intent = new Intent(getApplicationContext(), JogosConvidadoActivity.class);
				}
				
				intent.putExtra("dadosRequisicao", dadosRequisicao);
				startActivity(intent);
			} else {
				Toast.makeText(getApplicationContext(), "Erro de conexão com o servidor", Toast.LENGTH_LONG).show();
			}
		};
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.meus_jogos, menu);
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
