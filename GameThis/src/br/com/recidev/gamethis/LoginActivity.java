package br.com.recidev.gamethis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	GerenciadorSessao sessao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		sessao = new GerenciadorSessao(getApplicationContext()); 
		
		final Button botaoLogin = (Button) findViewById(R.id.botao_login_entrar);
		botaoLogin.setOnClickListener(new View.OnClickListener() {
			 public void onClick(View v) {
				 AutoCompleteTextView textEmail = (AutoCompleteTextView) findViewById(R.id.email);
				 EditText textPassword = (EditText) findViewById(R.id.password);
				 
				 String email = textEmail.getText().toString();
				 String password = textPassword.getText().toString(); 
			      
				 Toast.makeText(getApplicationContext(), 
						 email + " - " + password, Toast.LENGTH_LONG).show();
				 
				 sessao.criarSessaoLogin(email, password);
				 
				 Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
	             startActivity(homeIntent);
	             finish();
			 }
		});
		
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
