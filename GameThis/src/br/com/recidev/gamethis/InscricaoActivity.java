package br.com.recidev.gamethis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import br.com.recidev.gamethis.http.HttpSincronizacaoClient;
import br.com.recidev.gamethis.repositorio.RepositorioUsuarioSQLite;

import com.google.gson.Gson;

public class InscricaoActivity extends Activity {
	
	// Posteriormente serão criados arquivos com as constantes
	private final String PATH_CREATE = "/usuarios/create";
	//private final String PATH_INDEX = "/usuarios";
	private final String PATH_SHOW = "/usuarios/";
	//private final String PATH_UPDATE = "/usuarios/:id"; 
	//private final String PATH_DELETE = "/usuarios/:id";
	private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private String emailUsuario; 
	private String senhaUsuario; 
	private String nomeUsuario; 
	private int avatarUsuario;
	GerenciadorSessao sessao;
	final String[] AVATAR = new String[] { "Warior", "Mage", "Thiev"};
	int tipoAvatar = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inscricao);
		
		sessao = new GerenciadorSessao(getApplicationContext()); 

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
					conectado = Util.temConexao(getApplicationContext());
					if(conectado){
						// Verifica se o usuário ja existe
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
		} else if (!Pattern.matches(EMAIL_REGEX, email)) {
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
	
	
	public void inserirUsuarioRemoto(String email, String senha, String nome, int avatar){
		this.emailUsuario = email;
		this.senhaUsuario = senha;
		this.nomeUsuario = nome;
		this.avatarUsuario = avatar;
		String path = PATH_SHOW + this.emailUsuario;
		
		ArrayList<HashMap<String, Object>> listaPalavras = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("email", emailUsuario);
		map.put("senha", senhaUsuario);
		map.put("nome", nomeUsuario);
		map.put("avatar", avatarUsuario);
		
		listaPalavras.add(map);
		
		Gson gson = new Gson();
		String convertedJson = gson.toJson(listaPalavras);
		String json = convertedJson.substring(1, convertedJson.length() - 1);
		new InserirUsuarioTask().execute(path, json);
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
			int indice = 0;
			String path = params[indice++].toString();
			String json = params[indice++].toString();
			HttpSincronizacaoClient httpClient = new HttpSincronizacaoClient();
			
			try {
				msgResposta = httpClient.get(path);
				if (msgResposta.equals("")) {
					httpClient.post(PATH_CREATE, json);
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
				inserirUsuario(emailUsuario, senhaUsuario, nomeUsuario, avatarUsuario);
				
				sessao.criarSessaoLogin(emailUsuario, senhaUsuario, nomeUsuario, avatarUsuario);
				Toast.makeText(getApplicationContext(), "Inscrição realizada com sucesso", Toast.LENGTH_LONG).show();
				Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
				startActivity(homeIntent);
				finish();
			} else {
				Toast.makeText(getApplicationContext(), msgResposta, Toast.LENGTH_LONG).show();
			}
		};
	}

	
	public void inserirUsuario(String email, String senha, String nome, int avatar){
		RepositorioUsuarioSQLite repUsuario = new RepositorioUsuarioSQLite();
		repUsuario.inserirUsuario(email, senha, nome, avatar, getApplicationContext());
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
