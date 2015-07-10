package br.com.recidev.gamethis.ui;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import br.com.recidev.gamethis.R;
import br.com.recidev.gamethis.adapter.JogosConvidadoAdapter;
import br.com.recidev.gamethis.dominio.Jogo;
import br.com.recidev.gamethis.dominio.Usuario;
import br.com.recidev.gamethis.util.GerenciadorSessao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JogosConvidadoActivity extends Activity {
	
	private JogosConvidadoAdapter jogosConvidadoAdapter;
	private ListView jogosConvidadoListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jogos_convidado);
		
		String dadosRequisicao = getIntent().getStringExtra("dadosRequisicao");
		
		ArrayList<Jogo> jogos = new ArrayList<Jogo>();
		Gson gson = new Gson();
		jogos = gson.fromJson(dadosRequisicao, new TypeToken<ArrayList<Jogo>>(){}.getType());
		
		jogosConvidadoAdapter = new JogosConvidadoAdapter(this, jogos);
		jogosConvidadoListView = (ListView) findViewById(R.id.jogosConvidadoListView);
		jogosConvidadoListView.setAdapter(jogosConvidadoAdapter);
		
		jogosConvidadoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				 Object listItem = parent.getItemAtPosition(position);
				 Jogo jogoConvidado = (Jogo) listItem;
				 System.out.println(jogoConvidado.getDescricao() + " - " + jogoConvidado.getLoginCriador();
				 System.out.println("uhu");
				 //String loginCriadorJogo = sessao.preferencias.getString(GerenciadorSessao.EMAIL_KEY, "none");
			} 
		});
	}

	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.jogos_convidado, menu);
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
