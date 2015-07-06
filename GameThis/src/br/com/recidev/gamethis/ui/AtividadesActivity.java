package br.com.recidev.gamethis.ui;

import java.io.Serializable;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import br.com.recidev.gamethis.R;
import br.com.recidev.gamethis.adapter.AtividadeAdapter;
import br.com.recidev.gamethis.dominio.Atividade;

public class AtividadesActivity extends Activity {

	private AtividadeAdapter atividadesAdicionadasAdapter;
	private ListView atividadesAddedListView;
	ArrayList<Atividade> listaAtividadesAdded = new ArrayList<Atividade>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_atividades);
		
		Serializable dadosAtividadesAdicionadas = getIntent().getSerializableExtra("listaAtividadesAdded");
		if(dadosAtividadesAdicionadas != null){
			@SuppressWarnings("unchecked")
			ArrayList<Atividade> listaAtividadesAdicionadas = (ArrayList<Atividade>) dadosAtividadesAdicionadas;
			listaAtividadesAdded = listaAtividadesAdicionadas;
		}
		
		atividadesAdicionadasAdapter = new AtividadeAdapter(this, listaAtividadesAdded);
		atividadesAddedListView = (ListView) findViewById(R.id.atividadesAddedListView);
		atividadesAddedListView.setAdapter(atividadesAdicionadasAdapter);
		
		
		final Button botaoCadastrarNovaAtividade = (Button) findViewById(R.id.botao_add_nova_atividade);
		botaoCadastrarNovaAtividade.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent cadastroAtividadeIntent = new Intent(getApplicationContext(), CadastroAtividadeActivity.class);
	            startActivity(cadastroAtividadeIntent);
			}
		});
		
		
		final Button botaoConfirmarListaAtividades = (Button) findViewById(R.id.botao_confirmar_lista_atividades);
		botaoConfirmarListaAtividades.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				if(!listaAtividadesAdded.isEmpty()){
					Intent novoJogoIntent = new Intent(getApplicationContext(), NovoJogoActivity.class);
					novoJogoIntent.putExtra("listaAtividadesAdded", listaAtividadesAdded);
					startActivity(novoJogoIntent);
					finish();
				} else {
					Toast.makeText(getApplicationContext(), "Nenhuma atividade adicionada à lista.", Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	
	@Override
	protected void onNewIntent(Intent intent) {
		Atividade atividade;
		
		 if(intent.getSerializableExtra("atividadeCadastrada") != null){
			 atividade = (Atividade) intent.getSerializableExtra("atividadeCadastrada");
			 
			 listaAtividadesAdded.add(atividade);
			 atividadesAdicionadasAdapter.notifyDataSetChanged();
		 }
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.atividades, menu);
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
