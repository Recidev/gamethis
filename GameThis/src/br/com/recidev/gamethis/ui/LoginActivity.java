package br.com.recidev.gamethis.ui;

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
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.com.recidev.gamethis.R;
import br.com.recidev.gamethis.dominio.Usuario;
import br.com.recidev.gamethis.util.ConstantesGameThis;
import br.com.recidev.gamethis.util.GerenciadorSessao;
import br.com.recidev.gamethis.util.HttpSincronizacaoClient;
import br.com.recidev.gamethis.util.Util;

import com.google.gson.Gson;

public class LoginActivity extends Activity {
	
	GerenciadorSessao sessao;
	private String stringUsuario;
	
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
						 loginRemoto(email, senha);
					 } else {
						 Toast.makeText(getApplicationContext(), "Não foi possível realizar login, por favor tente mais tarde.", 
								 Toast.LENGTH_LONG).show();
					 }
				 } else {
					 Toast.makeText(getApplicationContext(), resultValidacao, Toast.LENGTH_LONG).show();
				 }	
			 }
		});
	}
	
	
	public void loginRemoto(String email, String senha){
		ArrayList<HashMap<String, Object>> listaPalavras = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		senha = Util.stringToSha1(senha);
		map.put("email", email);
		map.put("senha", senha);
		
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
		    dialogo.setTitle("Realizando login");
		    dialogo.setMessage("Por favor, aguarde...");
		    dialogo.show(); 
		};
		
		
		@Override
		protected String doInBackground(String... params) {
			String stringResposta = "";
			int indice = 0;
			String json = params[indice++].toString();
			HttpSincronizacaoClient httpClient = new HttpSincronizacaoClient();
			
			try {
				stringUsuario = httpClient.post(ConstantesGameThis.PATH_LOGIN, json);
				if (!stringUsuario.equals("")) {
					stringResposta = "sucesso";
				} else {
					stringResposta = "Usuário ou senha incorreta.";
				}
			} catch (IOException e) {
				stringResposta = "Erro no servidor.";
				e.printStackTrace();
			}
			return stringResposta;
		}; 
		
	 
		@Override
		protected void onPostExecute(String stringResposta){
			dialogo.dismiss(); 
			
			if(stringResposta.equals("sucesso")){
				InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
				Gson gson = new Gson();
				Usuario usuario = gson.fromJson(stringUsuario, Usuario.class);
				
				sessao.criarSessaoLogin(usuario.getEmail(), usuario.getNome(), usuario.getAvatar(), usuario.getGcm_id());
				Toast.makeText(getApplicationContext(), "Bem-vindo!", Toast.LENGTH_LONG).show();
				Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
				startActivity(homeIntent);
				finish();
			} else {
				Toast.makeText(getApplicationContext(), stringResposta, Toast.LENGTH_LONG).show();
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
		} else if (!Pattern.matches(ConstantesGameThis.EMAIL_REGEX, email)) {
			msgErro = "Email inválido.";
		} 
		
		return msgErro;
	}
	

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
