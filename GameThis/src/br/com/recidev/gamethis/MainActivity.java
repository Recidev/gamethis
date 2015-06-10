package br.com.recidev.gamethis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	
	GerenciadorSessao sessao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		new SQLiteHelper(this);
		
		if (getIntent().getBooleanExtra("EXIT", false)) {
		    finish();
		} else {
			sessao = new GerenciadorSessao(getApplicationContext()); 
			
			if(sessao.estaLogado()){
				Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
				startActivity(homeIntent);
			} else {
				//Direciona para a tela de login
				final Button botaoTelaLogin = (Button) findViewById(R.id.botao_tela_login);
				botaoTelaLogin.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
						startActivity(loginIntent);
					}
				});
				
				//Direciona para a tela de incricao
				final Button botaoTelaInscricao = (Button) findViewById(R.id.botao_tela_inscricao);
				botaoTelaInscricao.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Intent inscricaoIntent = new Intent(getApplicationContext(), InscricaoActivity.class);
						startActivity(inscricaoIntent);
					}
				});
				
			}
		}
	}
	
	@Override
	public void onBackPressed() {
		Intent mainIntent = this.getIntent();
		mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mainIntent.putExtra("EXIT", true);
		finish();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
