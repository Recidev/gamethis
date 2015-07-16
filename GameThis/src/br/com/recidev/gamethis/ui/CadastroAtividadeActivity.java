package br.com.recidev.gamethis.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import br.com.recidev.gamethis.R;
import br.com.recidev.gamethis.adapter.PontosAdapter;
import br.com.recidev.gamethis.dominio.Atividade;

public class CadastroAtividadeActivity extends Activity {
	
	final String[] PONTOS = new String[] { "1", "3", "5"};
	private Atividade atividade;
	private TextView inputPontosAtividades;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastro_atividade);
		
		
		final AlertDialog.Builder dialogPontos = new AlertDialog.Builder(this);
		dialogPontos.setTitle("Pontuação");
		PontosAdapter pontosAdapter = new PontosAdapter(this, PONTOS);
		dialogPontos.setAdapter(pontosAdapter, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				inputPontosAtividades.setText(PONTOS[which]);
			}
		});
		
		
		inputPontosAtividades = ((EditText)findViewById(R.id.input_pontos_atividade));
		inputPontosAtividades.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	dialogPontos.show();
	        }
	    });
		
		
		//Botao para criar a atividade
		final Button botaoCriarAtividade = (Button) findViewById(R.id.botao_add_atividade);
		botaoCriarAtividade.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//boolean conectado = false;
				EditText inputDescricaoAtividade = (EditText) findViewById(R.id.input_descricao_atividade);
				EditText inputPontos = (EditText) findViewById(R.id.input_pontos_atividade);
				EditText inputDuracao = (EditText) findViewById(R.id.input_duracao_atividade);
				
				String descricao = inputDescricaoAtividade.getText().toString();
				String pontos = inputPontos.getText().toString();
				String duracao = inputDuracao.getText().toString();
				
				String resultValidacao = validarCampos(descricao, pontos, duracao);
				
				if(resultValidacao.equals("")){
					int syncStatus = 0;
					int flagConcluida = 0;
					atividade = new Atividade();
					atividade.setDescricao(descricao);
					atividade.setPontos(Integer.parseInt(pontos));
					atividade.setDuracao(Integer.parseInt(duracao));
					atividade.setFlagConcluida(flagConcluida);
					atividade.setSync_sts(syncStatus);
					
					Intent atividadesIntent = new Intent(getApplicationContext(), AtividadesActivity.class);
					if(atividade != null){
						atividadesIntent.putExtra("atividadeCadastrada", atividade);
					}
					startActivity(atividadesIntent);
					finish();

				} else {
					Toast.makeText(getApplicationContext(), resultValidacao, Toast.LENGTH_LONG).show();
				}
			}
		});
	}


	
	public String validarCampos(String descricao, String pontos, String duracao){
		String msgErro = "";
		
		if(duracao.equals("")){
			msgErro = "A Duração da atividade deve ser definida.";
		}
		if(pontos.equals("")){
			msgErro = "A Pontuação da atividade deve ser definida.";
		} 
		if(descricao.equals("")){
			msgErro = "Campo descrição deve ser preenchido";
		}
		return msgErro;
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cadastro_atividade, menu);
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
