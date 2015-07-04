package br.com.recidev.gamethis.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import br.com.recidev.gamethis.R;
import br.com.recidev.gamethis.adapter.JogadorAdapter;
import br.com.recidev.gamethis.dominio.Usuario;

import com.google.gson.Gson;

public class JogadoresActivity extends Activity {

	private JogadorAdapter jogadorAdapter;
	private ListView jogadoresListView;
	private JogadorAdapter jogadorAdicionadoAdapter;
	private ListView jogadoresAddedListView;
	
	final ArrayList<Usuario> listaJogadores = new ArrayList<Usuario>();
	final ArrayList<Usuario> listaJogadoresAdded = new ArrayList<Usuario>();
	

    SearchManager searchManager;
    SearchView searchView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jogadores);
		
		jogadorAdapter = new JogadorAdapter(this, listaJogadores);
		jogadoresListView = (ListView) findViewById(R.id.jogadoresListView);
		jogadoresListView.setAdapter(jogadorAdapter);
		
		jogadorAdicionadoAdapter = new JogadorAdapter(this, listaJogadoresAdded);
		jogadoresAddedListView = (ListView) findViewById(R.id.jogadoresAddedListView);
		jogadoresAddedListView.setAdapter(jogadorAdicionadoAdapter);
		
	}

			 
			 
	
	
	 @Override
	 protected void onNewIntent(Intent intent) {
		 String result;
		 
		 jogadoresListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			 @Override
			 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				 Object listItem = parent.getItemAtPosition(position);
				 
				 listaJogadoresAdded.add((Usuario) listItem);
				 jogadorAdicionadoAdapter.notifyDataSetChanged();
				 
				 listaJogadores.remove(listItem);
				 jogadorAdapter.notifyDataSetChanged();
				 
				 Toast.makeText(getApplicationContext(), "Jogador adicionado!", Toast.LENGTH_LONG).show();
				 searchView.setQuery("", false);
				 InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				 imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
			 } 
		 });
		 
		 if(intent.getStringExtra("resultadoPesquisa") != null){
			 result = intent.getStringExtra("resultadoPesquisa");
		
			 Gson gson = new Gson();
			 Usuario jogador = gson.fromJson(result, Usuario.class);
			 
			 listaJogadores.add(jogador);
			 jogadorAdapter.notifyDataSetChanged();
			 
			 searchView.setQuery("", false);
			 InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			 imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
			 
		 	} else {
		 		Toast.makeText(getApplicationContext(), "Jogador n�o encontrado.", Toast.LENGTH_LONG).show();
		 		searchView.setQuery("", false);
		 		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		 		imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		 	}
	 }

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.jogadores, menu);
		
	    searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    searchView = (SearchView) menu.findItem(R.id.searchJogador).getActionView();
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
