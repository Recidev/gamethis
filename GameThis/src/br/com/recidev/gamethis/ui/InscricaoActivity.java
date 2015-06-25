package br.com.recidev.gamethis.ui;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import br.com.recidev.gamethis.R;
import br.com.recidev.gamethis.adapter.AvatarAdapter;
import br.com.recidev.gamethis.repositorio.RepositorioUsuarioSQLite;
import br.com.recidev.gamethis.util.ConstantesGameThis;
import br.com.recidev.gamethis.util.GerenciadorSessao;
import br.com.recidev.gamethis.util.HttpSincronizacaoClient;
import br.com.recidev.gamethis.util.Util;

import com.google.gson.Gson;

public class InscricaoActivity extends Activity {
	
	private String emailUsuario; 
	private String senhaUsuario; 
	private String nomeUsuario; 
	private int avatarUsuario;
	private String timestampUsuario;
	private int syncStatus;
	
	GerenciadorSessao sessao;
	final String[] AVATAR = new String[] { "Warrior", "Mage", "Thief"};
	int tipoAvatar = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inscricao);
		
		sessao = new GerenciadorSessao(getApplicationContext()); 

		// Cria��o de di�logo para sele��o do avatar
		final AlertDialog.Builder dialogAvatar = new AlertDialog.Builder(this);
		dialogAvatar.setTitle("Selecione seu Avatar!!");
		AvatarAdapter avatarAdapter = new AvatarAdapter(this, AVATAR);
		
		dialogAvatar.setAdapter(avatarAdapter, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				tipoAvatar = which;
				mudarImagem();
			}
		});
		
		
		final Button botaoAvatarInscricao = (Button) findViewById(R.id.botao_avatar_inscricao);
		botaoAvatarInscricao.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dialogAvatar.show();
			}
		});
		
		
		final Button botaoInscricaoEnviar = (Button) findViewById(R.id.botao_inscricao_enviar);
		botaoInscricaoEnviar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				boolean conectado = false;
				EditText inputNome = (EditText) findViewById(R.id.input_nome);
				EditText inputEmail = (EditText) findViewById(R.id.input_email);
				EditText inputSenha = (EditText) findViewById(R.id.input_senha);
				
				String email = inputEmail.getText().toString();
				String senha = inputSenha.getText().toString(); 
				String nome = inputNome.getText().toString();
				int avatar = tipoAvatar;
				
				String resultValidacao = validarCampos(email, senha, nome);
				
				if(resultValidacao.equals("")){
					conectado = Util.temConexao(getApplicationContext());
					if(conectado){
						inserirUsuarioRemoto(email, senha,  nome, avatar);
					}
				} else {
					Toast.makeText(getApplicationContext(), resultValidacao, Toast.LENGTH_LONG).show();
				}			
			}
		});
	}
	
	
	public String validarCampos(String email, String senha, String nome){
		String msgErro = "";
		
		if(senha.equals("")){
			msgErro = "Campo senha deve ser preenchido.";
		}
		if(email.equals("")){
			msgErro = "Campo email deve ser preenchido.";
		} else if (!Pattern.matches(ConstantesGameThis.EMAIL_REGEX, email)) {
			msgErro = "Email inv�lido.";
		} 
		if(nome.equals("")){
			msgErro = "Campo nome deve ser preenchido";
		}
		return msgErro;
	}
	
	
	public void mudarImagem(){
		ImageView imagemDefinida = (ImageView) findViewById(R.id.imagemDefinida);
		imagemDefinida.setImageResource(GerenciadorSessao.TIPOS_AVATAR[tipoAvatar]);
	}
	
	
	public void inserirUsuarioRemoto(String email, String senha, String nome, int avatar){
		this.emailUsuario = email;
		this.senhaUsuario = Util.stringToSha1(senha);
		this.nomeUsuario = nome;
		this.avatarUsuario = avatar;
		String path = ConstantesGameThis.PATH_SHOW + this.emailUsuario;
		DateFormat dateFormatSQLite = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		this.timestampUsuario = dateFormatSQLite.format(new Timestamp(System.currentTimeMillis()));
		this.syncStatus = 0;
		
		ArrayList<HashMap<String, Object>> listaParametros = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("email", emailUsuario);
		map.put("senha", senhaUsuario);
		map.put("nome", nomeUsuario);
		map.put("avatar", avatarUsuario);
		map.put("ts_usuario", timestampUsuario);
		map.put("sync_sts", syncStatus);
		
		
		listaParametros.add(map);
		Gson gson = new Gson();
		String convertedJson = gson.toJson(listaParametros);
		String json = convertedJson.substring(1, convertedJson.length() - 1);
		new InserirUsuarioTask().execute(path, json);
	}
	
	
	private class InserirUsuarioTask extends AsyncTask<String, String, String> {
		protected ProgressDialog dialogo = new ProgressDialog(InscricaoActivity.this);
		
		@Override
		protected void onPreExecute(){
		    dialogo.setProgressStyle(ProgressDialog.THEME_HOLO_DARK);
		    dialogo.setCancelable(false);
		    dialogo.setTitle("Realizando inscri��o.");
		    dialogo.setMessage("Por favor, aguarde...");
		    dialogo.show(); 
		};
		
		
		@Override
		protected String doInBackground(String... params) {
			String msgResposta = "";
			int indice = 0;
			String path = params[indice++].toString();
			String json = params[indice++].toString();
			HttpSincronizacaoClient httpClient = new HttpSincronizacaoClient();
			
			try {
				msgResposta = httpClient.get(path);
				if (msgResposta.equals("")) {
					httpClient.post(ConstantesGameThis.PATH_CREATE, json);
					msgResposta = "sucesso";
				} else {
					msgResposta = "Usu�rio j� existe.";
				}
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
				// Insere usu�rio localmente no SQLite
				inserirUsuario(emailUsuario, senhaUsuario, nomeUsuario, avatarUsuario, timestampUsuario, syncStatus);
				
				sessao.criarSessaoLogin(emailUsuario, nomeUsuario, avatarUsuario);
				Toast.makeText(getApplicationContext(), "Inscri��o realizada com sucesso!", Toast.LENGTH_LONG).show();
				Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
				startActivity(homeIntent);
				finish();
			} else {
				Toast.makeText(getApplicationContext(), msgResposta, Toast.LENGTH_LONG).show();
			}
		};
	}

	
	public void inserirUsuario(String email, String senha, String nome, int avatar, String ts_usuario, int syncStatus){
		RepositorioUsuarioSQLite repUsuario = new RepositorioUsuarioSQLite();
		repUsuario.inserirUsuario(email, senha, nome, avatar, ts_usuario, syncStatus, getApplicationContext());
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inscricao, menu);
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
