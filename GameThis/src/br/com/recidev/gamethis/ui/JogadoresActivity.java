package br.com.recidev.gamethis.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import br.com.recidev.gamethis.R;
import br.com.recidev.gamethis.adapter.JogadorAdapter;
import br.com.recidev.gamethis.dominio.Usuario;
import br.com.recidev.gamethis.util.ConstantesGameThis;
import br.com.recidev.gamethis.util.GerenciadorSessao;

import com.google.gson.Gson;

public class JogadoresActivity extends Activity {

	private JogadorAdapter jogadorAdapter;
	private ListView jogadoresListView;
	private JogadorAdapter jogadorAdicionadoAdapter;
	private ListView jogadoresAddedListView;
	
	ArrayList<Usuario> listaJogadores = new ArrayList<Usuario>();
	ArrayList<Usuario> listaJogadoresAdded = new ArrayList<Usuario>();

    SearchManager searchManager;
    SearchView searchView;
	
    GerenciadorSessao sessao;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jogadores);
		sessao = new GerenciadorSessao(getApplicationContext());
		
		Serializable dadosJogadoresAdicionados = getIntent().getSerializableExtra("listaJogadoresAdded");
		if(dadosJogadoresAdicionados != null){
			@SuppressWarnings("unchecked")
			ArrayList<Usuario> listaJogadoresAdicionados = (ArrayList<Usuario>) dadosJogadoresAdicionados;
			listaJogadoresAdded = listaJogadoresAdicionados;
		}
		
		
		jogadorAdapter = new JogadorAdapter(this, listaJogadores);
		jogadoresListView = (ListView) findViewById(R.id.jogadoresListView);
		jogadoresListView.setAdapter(jogadorAdapter);
		
		jogadorAdicionadoAdapter = new JogadorAdapter(this, listaJogadoresAdded);
		jogadoresAddedListView = (ListView) findViewById(R.id.jogadoresAddedListView);
		jogadoresAddedListView.setAdapter(jogadorAdicionadoAdapter);
		
		
		final Button botaoConfirmarListaJogadores = (Button) findViewById(R.id.botao_confirmar_lista_jogadores);
		botaoConfirmarListaJogadores.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				if(!listaJogadoresAdded.isEmpty()){
					Intent novoJogoIntent = new Intent(getApplicationContext(), NovoJogoActivity.class);
					novoJogoIntent.putExtra("listaJogadoresAdded", listaJogadoresAdded);
					startActivity(novoJogoIntent);
					finish();
				} else {
					Toast.makeText(getApplicationContext(), "Nenhum jogador adicionado à lista.", Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	
	
	 @Override
	 protected void onNewIntent(Intent intent) {
		 String result;
		 
		 if(intent.getStringExtra("resultadoPesquisa") != null){
			 result = intent.getStringExtra("resultadoPesquisa");
			 
			 Gson gson = new Gson();
			 Usuario jogador = gson.fromJson(result, Usuario.class);
			 
			 listaJogadores.clear();
			 listaJogadores.add(jogador);
			 jogadorAdapter.notifyDataSetChanged();
			 
			 searchView.setQuery("", false);
			 InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			 imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
			 
		 } else {
			 Toast.makeText(getApplicationContext(), "Jogador não encontrado.", Toast.LENGTH_LONG).show();
			 searchView.setQuery("", false);
			 InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			 imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		 }
		 
		 jogadoresListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			 @Override
			 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				 Object listItem = parent.getItemAtPosition(position);
				 Usuario jogadorAdicionado = (Usuario) listItem;
				 
				 String msg = "";
				 String loginCriadorJogo = sessao.preferencias.getString(GerenciadorSessao.EMAIL_KEY, "none");
				 
				 boolean ehJogadorJaAdicionado = false;
				 Iterator<Usuario> itListaJogadoresAdded = listaJogadoresAdded.iterator();
				 
				 while(itListaJogadoresAdded.hasNext() && !ehJogadorJaAdicionado){
					 Usuario jogadorJaAdicionado = itListaJogadoresAdded.next();
					 if(jogadorJaAdicionado.getEmail().equals(jogadorAdicionado.getEmail())){
						 ehJogadorJaAdicionado = true;
						 msg = "Jogador já adicionado à lista.";
					 }
				 }
				 if(loginCriadorJogo.equals(jogadorAdicionado.getEmail())){
					 ehJogadorJaAdicionado = true;
					 msg = "Você não pode ser adicionado à lista de jogadores.";
				 }
				 
				 if(!ehJogadorJaAdicionado){
					 listaJogadoresAdded.add(jogadorAdicionado);
					 jogadorAdicionadoAdapter.notifyDataSetChanged();
					 
					 listaJogadores.remove(listItem);
					 jogadorAdapter.notifyDataSetChanged();
					 
					 searchView.setQuery("", false);
					 InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
					 imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
				 } else {
					 Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
				 }
			 } 
		 });
	 }

	 
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.jogadores, menu);
		
	    searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    searchView = (SearchView) menu.findItem(R.id.searchJogador).getActionView();
	    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	    
	    SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
	        @Override
	        public boolean onQueryTextSubmit(String s) {
	        	boolean ok = false;
	        	if (!Pattern.matches(ConstantesGameThis.EMAIL_REGEX, s)) {
	        		Toast.makeText(getApplicationContext(), "Email inválido.", Toast.LENGTH_LONG).show();
	        		ok = true;
	        	}
	        	return ok;
	        }

	        @Override
	        public boolean onQueryTextChange(String s) {
	            return true;
	        }
	    };
	    
	    searchView.setOnQueryTextListener(queryTextListener);
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
