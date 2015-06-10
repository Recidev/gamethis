package br.com.recidev.gamethis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.com.recidev.gamethis.http.HttpSincronizacaoClient;

import com.google.gson.Gson;

public class LoginActivity extends Activity {
	
	GerenciadorSessao sessao;
	private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private String emailUsuario; 
	private String senhaUsuario;
	private final String PATH_LOGIN = "/usuarios/";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		sessao = new GerenciadorSessao(getApplicationContext()); 
		
		final Button botaoLogin = (Button) findViewById(R.id.botao_login_entrar);
		botaoLogin.setOnClickListener(new View.OnClickListener() {
			 public void onClick(View v) {
				 boolean conectado = false;
				 AutoCompleteTextView textEmail = (AutoCompleteTextView) findViewById(R.id.email);
				 EditText textSenha = (EditText) findViewById(R.id.senha);
				 
				 String email = textEmail.getText().toString();
				 String senha = textSenha.getText().toString(); 
				 
				 String resultValidacao = validarCampos(email, senha);
				 
				 if(resultValidacao.equals("")){
					 conectado = Util.temConexao(getApplicationContext());
					 if(conectado){
						 // Verifica se o usuário ja existe
						 loginRemoto(email, senha);
					 }
				 } else {
					 Toast.makeText(getApplicationContext(), resultValidacao, Toast.LENGTH_LONG).show();
				 }	
			 }
		});
		
	}
	
	
	public void loginRemoto(String email, String senha){
		this.emailUsuario = email;
		this.senhaUsuario = senha;
		
		ArrayList<HashMap<String, Object>> listaPalavras = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("email", emailUsuario);
		map.put("senha", senhaUsuario);

		
		listaPalavras.add(map);
		
		Gson gson = new Gson();
		String convertedJson = gson.toJson(listaPalavras);
		String json = convertedJson.substring(1, convertedJson.length() - 1);
		new LoginTask().execute(json);
	}
	
	
	private class LoginTask extends AsyncTask<String, String, String> {
		protected ProgressDialog dialogo = new ProgressDialog(LoginActivity.this);
		
		@Override
		protected void onPreExecute(){
		    dialogo.setProgressStyle(ProgressDialog.THEME_HOLO_DARK);
		    dialogo.setCancelable(false);
		    dialogo.setTitle("Realizando login.");
		    dialogo.setMessage("Por favor, aguarde...");
		    dialogo.show(); 
		};
		
		
		@Override
		protected String doInBackground(String... params) {
			String msgResposta = "";
			int indice = 0;
			String json = params[indice++].toString();
			HttpSincronizacaoClient httpClient = new HttpSincronizacaoClient();
			
			try {
				msgResposta = httpClient.post(PATH_LOGIN, json);
				if (!msgResposta.equals("")) {
					msgResposta = "sucesso";
				} else {
					msgResposta = "Usuário ou senha incorreta.";
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
				sessao.criarSessaoLogin(emailUsuario, senhaUsuario, "", 0);
				Toast.makeText(getApplicationContext(), "Bem vindo", Toast.LENGTH_LONG).show();
				Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
				startActivity(homeIntent);
				finish();
			} else {
				Toast.makeText(getApplicationContext(), msgResposta, Toast.LENGTH_LONG).show();
			}
		};
	}
	
	
	
	public String validarCampos(String email, String senha){
		String msgErro = "";
		
		if(senha.equals("")){
			msgErro = "Campo senha deve ser preenchido.";
		}
		
		if(email.equals("")){
			msgErro = "Campo email deve ser preenchido.";
		} else if (!Pattern.matches(EMAIL_REGEX, email)) {
			msgErro = "Email inválido.";
		} 
		
		return msgErro;
	}
	
//	@Override
//	protected void onResume() {
//		
//		sessao.checaLogin();
//		Toast.makeText(getApplicationContext(), 
//				 "Toast de novo", Toast.LENGTH_LONG).show();
//		super.onResume();
//	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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
