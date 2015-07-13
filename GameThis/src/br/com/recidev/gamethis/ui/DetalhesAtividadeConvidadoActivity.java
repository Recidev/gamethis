package br.com.recidev.gamethis.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import br.com.recidev.gamethis.R;
import br.com.recidev.gamethis.dominio.Atividade;

public class DetalhesAtividadeConvidadoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalhes_atividade_convidado);
		
		Atividade atividadeSelecionada = (Atividade) getIntent().getSerializableExtra("atividadeConvidado");
		
		TextView textoDescricaoAtividade = (TextView) findViewById(R.id.textoDescricaoAtividadeConvidadoValor);
		TextView textoPontosAtividadeConvidado = (TextView) findViewById(R.id.textoPontosAtividadeConvidadoDetalheValor);
		TextView textoDuracaoAtividadeConvidado = (TextView) findViewById(R.id.textoDuracaoAtividadeConvidadoValor);
		TextView textoJogadorAtividade = (TextView) findViewById(R.id.login_jogador_atividade);
		
		textoDescricaoAtividade.setText(atividadeSelecionada.getDescricao());
		textoPontosAtividadeConvidado.setText(String.valueOf(atividadeSelecionada.getPontos()));
		textoDuracaoAtividadeConvidado.setText(String.valueOf(atividadeSelecionada.getDuracao()) + " dia(s)");
		
		if(atividadeSelecionada.getLoginJogador() != null){
			textoJogadorAtividade.setText(atividadeSelecionada.getLoginJogador());
		}
	}

	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detalhes_atividade_convidado, menu);
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
