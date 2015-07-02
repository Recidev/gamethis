package br.com.recidev.gamethis.ui;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import br.com.recidev.gamethis.dominio.Usuario;
import br.com.recidev.gamethis.repositorio.RepositorioUsuarioSQLite;
import br.com.recidev.gamethis.util.ConstantesGameThis;
import br.com.recidev.gamethis.util.GCMMensagem;
import br.com.recidev.gamethis.util.GerenciadorSessao;
import br.com.recidev.gamethis.util.HttpSincronizacaoClient;
import br.com.recidev.gamethis.util.Util;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;

public class InscricaoActivity extends Activity {
	
	private Usuario usuario;
	
	GerenciadorSessao sessao;
	final String[] AVATAR = new String[] { "Warrior", "Mage", "Thief"};
	int tipoAvatar = 0;
	Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inscricao);
		
		context = getApplicationContext();
		
		sessao = new GerenciadorSessao(context); 

		// Criação de diálogo para seleção do avatar
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
					int sync_sts = 0;
					usuario = new Usuario();
					usuario.setEmail(email);
					usuario.setSenha(Util.stringToSha1(senha));
					usuario.setNome(nome);
					usuario.setAvatar(avatar);
					usuario.setTs_usuario(new Timestamp(System.currentTimeMillis()));
					usuario.setSync_sts(sync_sts);
					
					conectado = Util.temConexao(context);
					if(conectado){
						new InserirUsuarioTask().execute();
					}
				} else {
					Toast.makeText(context, resultValidacao, Toast.LENGTH_LONG).show();
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
			msgErro = "Email inválido.";
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
	
	
	
	private class InserirUsuarioTask extends AsyncTask<String, String, String> {
		protected ProgressDialog dialogo = new ProgressDialog(InscricaoActivity.this);
		
		@Override
		protected void onPreExecute(){
		    dialogo.setProgressStyle(ProgressDialog.THEME_HOLO_DARK);
		    dialogo.setCancelable(false);
		    dialogo.setTitle("Realizando inscrição.");
		    dialogo.setMessage("Por favor, aguarde...");
		    dialogo.show();
		};
		
		
		@Override
		protected String doInBackground(String... params) {
			String msgResposta = "";
			HttpSincronizacaoClient httpClient = new HttpSincronizacaoClient();
			
			try {
				//Gera GCM Id para usuario
				GoogleCloudMessaging gcm;
				gcm = GoogleCloudMessaging.getInstance(context);
				String gcm_id;
				gcm_id = gcm.register(ConstantesGameThis.SENDER_ID);
				usuario.setGcm_id(gcm_id);
				
				//Prepara dados para envio
				DateFormat dateFormatSQLite = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
				String timestampUsuario = dateFormatSQLite.format(usuario.getTs_usuario());
				
				String path = ConstantesGameThis.PATH_SHOW + usuario.getEmail();

				
				String[] usuariosGcm = {usuario.getGcm_id()};
				Object dadosGcm = new Object();
				dadosGcm = usuario;
				
				GCMMensagem gcmMsg = new GCMMensagem();
				gcmMsg.setRegistration_ids(usuariosGcm);
				gcmMsg.setData(dadosGcm);
				gcmMsg.setCollapse_key("Novo Usuario");
				
				Gson gsonGcm = new Gson();
				String convertedJsonGcm = gsonGcm.toJson(gcmMsg);
				
				//Envia requisicao para inscricao
				msgResposta = httpClient.get(path);
				if (msgResposta.equals("")) {
					httpClient.post(ConstantesGameThis.PATH_CREATE, convertedJsonGcm);
					msgResposta = "sucesso";
				} else {
					msgResposta = "Usuário já existe.";
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
				// Insere usuário localmente no SQLite
				inserirUsuario();
				
				sessao.criarSessaoLogin(usuario.getEmail(), usuario.getNome(), usuario.getAvatar(), usuario.getGcm_id());
				Toast.makeText(context, "Inscrição realizada com sucesso!", Toast.LENGTH_LONG).show();
				Intent homeIntent = new Intent(context, HomeActivity.class);
				startActivity(homeIntent);
				finish();
			} else {
				Toast.makeText(context, msgResposta, Toast.LENGTH_LONG).show();
			}
		};
	}

	
	public void inserirUsuario(){
		RepositorioUsuarioSQLite repUsuario = new RepositorioUsuarioSQLite();
		repUsuario.inserirUsuario(usuario, context);
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
