package br.com.recidev.gamethis.ui;

import java.util.ArrayList;

import com.google.gson.Gson;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;
import br.com.recidev.gamethis.R;
import br.com.recidev.gamethis.adapter.JogadorAdapter;
import br.com.recidev.gamethis.dominio.Usuario;

public class JogadoresActivity extends Activity {


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jogadores);
		
		 Intent resultadoIntent = getIntent();
		 if(resultadoIntent.getExtras() != null){
			 String result = resultadoIntent.getStringExtra("resultadoPesquisa");
			 
			 Gson gson = new Gson();
			 
			 ArrayList<Usuario> listaJogadores = new ArrayList<Usuario>();
			 Usuario jogador = gson.fromJson(result, Usuario.class);
			 listaJogadores.add(jogador);
			 
			 
			 JogadorAdapter jogadorAdapter = new JogadorAdapter(this, listaJogadores);
			 ListView jogadoresListView = (ListView) findViewById(R.id.jogadoresListView);
			 jogadoresListView.setAdapter(jogadorAdapter);
			 
		 }		
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.jogadores, menu);
		
	    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    SearchView searchView = (SearchView) menu.findItem(R.id.searchJogador).getActionView();
	    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		
		
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
