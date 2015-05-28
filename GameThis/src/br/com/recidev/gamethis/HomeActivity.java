package br.com.recidev.gamethis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends Activity {

	GerenciadorSessao sessao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		sessao = new GerenciadorSessao(getApplicationContext());
		
		Bundle bundle = getIntent().getExtras();
		
		TextView nome = (TextView) findViewById(R.id.nome);
		if(bundle != null && bundle.getString("nome") != null){
			nome.setText(bundle.getString("nome"));
		}
		
		final Button botaoNovoJogo = (Button) findViewById(R.id.botao_novo_jogo);
		botaoNovoJogo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
	            startActivity(homeIntent);
	            finish();
				
			}
		});
		
		final Button botaoMeusJogos = (Button) findViewById(R.id.botao_meus_jogos);
		botaoNovoJogo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
	            startActivity(homeIntent);
	            finish();
				
			}
		});
		
		final Button botaoLogout = (Button) findViewById(R.id.botao_logout_sair);
		botaoLogout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
	            sessao.logoutUsuario();
			}
		});
	}
	
	
	
	
	@Override
	public void onBackPressed() {
		boolean estaLogado = sessao.estaLogado();
		
		if(!estaLogado){
			Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
			startActivity(loginIntent);
		} else {
			Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
			mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			mainIntent.putExtra("EXIT", true);
			startActivity(mainIntent);
		}
		super.onBackPressed();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
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
