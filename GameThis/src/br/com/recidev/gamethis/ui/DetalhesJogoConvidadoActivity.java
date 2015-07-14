package br.com.recidev.gamethis.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import br.com.recidev.gamethis.R;
import br.com.recidev.gamethis.adapter.AtividadesConvidadoAdapter;
import br.com.recidev.gamethis.dominio.Atividade;
import br.com.recidev.gamethis.dominio.Jogo;

public class DetalhesJogoConvidadoActivity extends Activity {
	
	private Atividade atividadeConvidado;
	private Jogo jogoSelecionado;
	private ArrayList<Atividade> listaAtividadesJogoConvidado;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalhes_jogo_convidado);
		
		jogoSelecionado = (Jogo) getIntent().getSerializableExtra("jogoConvidadoSelecionado");
		listaAtividadesJogoConvidado = 
			(ArrayList<Atividade>) getIntent().getSerializableExtra("listaAtividadesJogoConvidado");
		
		TextView textoDescricaoJogo  = (TextView) findViewById(R.id.textoDescricaoJogoConvidadoDetalhesValor);
		textoDescricaoJogo.setText(jogoSelecionado.getDescricao());
		
		TextView textoLoginCriador  = (TextView) (TextView)findViewById(R.id.textoLoginCriadorJogoValor);
		textoLoginCriador.setText(jogoSelecionado.getLoginCriador());
		
		if(listaAtividadesJogoConvidado != null){
			AtividadesConvidadoAdapter atividadesConvidadoAdapter = new AtividadesConvidadoAdapter(this, listaAtividadesJogoConvidado);
			ListView atividadesConvidadoListView = (ListView) findViewById(R.id.atividadesConvidadoListView);
			atividadesConvidadoListView.setAdapter(atividadesConvidadoAdapter);
			
			atividadesConvidadoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Object listItem = parent.getItemAtPosition(position);
					atividadeConvidado = (Atividade) listItem;
					
					Intent detalhesAtividadeConvidadoIntent = new Intent(getApplicationContext(), 
							DetalhesAtividadeConvidadoActivity.class);
					detalhesAtividadeConvidadoIntent.putExtra("atividadeConvidado", atividadeConvidado);
		            startActivity(detalhesAtividadeConvidadoIntent);
				} 
			});
		}
	}
	
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detalhes_jogo_convidado, menu);
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
