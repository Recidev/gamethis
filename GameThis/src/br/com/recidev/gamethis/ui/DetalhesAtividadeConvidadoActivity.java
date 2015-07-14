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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.recidev.gamethis.R;
import br.com.recidev.gamethis.dominio.Atividade;
import br.com.recidev.gamethis.util.ConstantesGameThis;
import br.com.recidev.gamethis.util.GerenciadorSessao;
import br.com.recidev.gamethis.util.HttpSincronizacaoClient;

import com.google.gson.Gson;

public class DetalhesAtividadeConvidadoActivity extends Activity {

	GerenciadorSessao sessao;
	private Atividade atividadeSelecionada;
	private String login;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalhes_atividade_convidado);
		
		atividadeSelecionada = (Atividade) getIntent().getSerializableExtra("atividadeConvidado");
		sessao = new GerenciadorSessao(getApplicationContext());
		login = sessao.preferencias.getString(GerenciadorSessao.EMAIL_KEY, "none");
		
		TextView textoDescricaoAtividade = (TextView) findViewById(R.id.textoDescricaoAtividadeConvidadoValor);
		TextView textoPontosAtividadeConvidado = (TextView) findViewById(R.id.textoPontosAtividadeConvidadoDetalheValor);
		TextView textoDuracaoAtividadeConvidado = (TextView) findViewById(R.id.textoDuracaoAtividadeConvidadoValor);
		final ImageView avatarJogador = (ImageView) findViewById(R.id.avatarJogador);
		final TextView textoJogadorAtividade = (TextView) findViewById(R.id.login_jogador_atividade);
		
		final Button botaoConfirmarAtribuicaoAtividade = (Button) findViewById(R.id.botao_confirmar_atribuicao_atividade);
		botaoConfirmarAtribuicaoAtividade.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				atividadeSelecionada.setLoginJogador(login);
				new AtividadeConfirmacaoAtribuicaoTask().execute();
			}
		});
		
		final Button botaoConcluirAtividade = (Button) findViewById(R.id.botao_concluir_atividade);
		botaoConcluirAtividade.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//dialogAvatar.show();
			}
		});
		
		
		botaoConfirmarAtribuicaoAtividade.setEnabled(false);
		botaoConcluirAtividade.setEnabled(false);
		textoDescricaoAtividade.setText(atividadeSelecionada.getDescricao());
		textoPontosAtividadeConvidado.setText(String.valueOf(atividadeSelecionada.getPontos()));
		textoDuracaoAtividadeConvidado.setText(String.valueOf(atividadeSelecionada.getDuracao()) + " dia(s)");
		

		final CheckBox checkBoxRealizarAtividade = (CheckBox) findViewById(R.id.checkBoxRealizarAtividade);
		checkBoxRealizarAtividade.setEnabled(true);
		checkBoxRealizarAtividade.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
		        if (isChecked) {
		        	
		        	int tipoAvatar = sessao.preferencias.getInt(GerenciadorSessao.AVATAR_KEY, 0);
		        	avatarJogador.setImageResource(GerenciadorSessao.TIPOS_AVATAR[tipoAvatar]);
		        	textoJogadorAtividade.setText(login);
		        	if(atividadeSelecionada.getLoginJogador() != null && atividadeSelecionada.getLoginJogador().equals(login)){
		        		botaoConfirmarAtribuicaoAtividade.setEnabled(false);
		        		botaoConcluirAtividade.setEnabled(true);
		        	} else {
		        		botaoConfirmarAtribuicaoAtividade.setEnabled(true);
			    		botaoConcluirAtividade.setEnabled(false);
		        	}
		        } else {
		        	avatarJogador.setImageResource(R.drawable.interrogacao);
		        	textoJogadorAtividade.setText("");
		        	botaoConfirmarAtribuicaoAtividade.setEnabled(true);
		    		botaoConcluirAtividade.setEnabled(false);
		        }
		    }
		});
		
		
		if(atividadeSelecionada.getLoginJogador() != null && !atividadeSelecionada.getLoginJogador().equals("")){
			//avatarJogador.setImageResource(GerenciadorSessao.TIPOS_AVATAR[tipoAvatar]);
			textoJogadorAtividade.setText(atividadeSelecionada.getLoginJogador());
			if(!atividadeSelecionada.getLoginJogador().equals(login)){
				checkBoxRealizarAtividade.setEnabled(false);
				botaoConfirmarAtribuicaoAtividade.setEnabled(false);
	    		botaoConcluirAtividade.setEnabled(false);
			} else {
				checkBoxRealizarAtividade.setChecked(true);
				botaoConfirmarAtribuicaoAtividade.setEnabled(false);
				botaoConcluirAtividade.setEnabled(true);
			}
		}
	}

	
	
	private class AtividadeConfirmacaoAtribuicaoTask extends AsyncTask<String, String, String> {
		protected ProgressDialog dialogo = new ProgressDialog(DetalhesAtividadeConvidadoActivity.this);
		
		@Override
		protected void onPreExecute(){
		    dialogo.setProgressStyle(ProgressDialog.THEME_HOLO_DARK);
		    dialogo.setCancelable(false);
		    dialogo.setTitle("Atribuindo atividade");
		    dialogo.setMessage("Por favor, aguarde...");
		    dialogo.show();
		};
		
		@Override
		protected String doInBackground(String... params) {
			String msgResposta = "";
			HttpSincronizacaoClient httpClient = new HttpSincronizacaoClient();
			
			try {
				String path = ConstantesGameThis.PATH_ATIVIDADE_UPDATE;

				Gson gson = new Gson();
				String json = gson.toJson(atividadeSelecionada);
				
				httpClient.post(path, json);
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
				// Insere usuário localmente no SQLite
				//inserirUsuario();
				
				Toast.makeText(getApplicationContext(), "Atividade atribuída com sucesso!", Toast.LENGTH_LONG).show();
				Intent meusJogosIntent = new Intent(getApplicationContext(), MeusJogosActivity.class);
				startActivity(meusJogosIntent);
				finish();
			} else {
				Toast.makeText(getApplicationContext(), msgResposta, Toast.LENGTH_LONG).show();
			}
		};
	}
	
	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detalhes_atividade_convidado, menu);
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
