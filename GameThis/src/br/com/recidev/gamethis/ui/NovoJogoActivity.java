package br.com.recidev.gamethis.ui;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import br.com.recidev.gamethis.R;
import br.com.recidev.gamethis.util.ConstantesGameThis;
import br.com.recidev.gamethis.util.GerenciadorSessao;
import br.com.recidev.gamethis.util.HttpSincronizacaoClient;
import br.com.recidev.gamethis.util.Util;

import com.google.gson.Gson;

public class NovoJogoActivity extends Activity {
	
	GerenciadorSessao sessao;
	
	private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
	private Calendar dataInicio;
	private Calendar dataTermino;
	private TextView inputDataInicio;
	private TextView inputDataTermino;
	
	private String descricaoJogo;
	private String dataInicioJogo; 
	private String dataTerminoJogo; 
	private String emailUsuario;
	private int flagAtivado;
	
	
	private DatePickerDialog.OnDateSetListener datePickerInicio = new DatePickerDialog.OnDateSetListener() {
	    @Override
	    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
	    	dataInicio = Calendar.getInstance();
	    	dataInicio.set(Calendar.YEAR, year);
	    	dataInicio.set(Calendar.MONTH, monthOfYear);
	    	dataInicio.set(Calendar.DAY_OF_MONTH, dayOfMonth); 
	    	inputDataInicio.setText(dateFormat.format(dataInicio.getTime()));   	
	    }
	};
	
	private DatePickerDialog.OnDateSetListener datePickerTermino = new DatePickerDialog.OnDateSetListener() {
	    @Override
	    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {	
	    	dataTermino = Calendar.getInstance();
	    	dataTermino.set(Calendar.YEAR, year);
	    	dataTermino.set(Calendar.MONTH, monthOfYear);
	    	dataTermino.set(Calendar.DAY_OF_MONTH, dayOfMonth); 
	    	inputDataTermino.setText(dateFormat.format(dataTermino.getTime()));
	    }
	};
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_novo_jogo);
		
		sessao = new GerenciadorSessao(getApplicationContext());
		
		//Data de Inicio
		dataInicio = Calendar.getInstance();
		inputDataInicio = ((EditText)findViewById(R.id.data_inicio));
		inputDataInicio.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	new DatePickerDialog(NovoJogoActivity.this, datePickerInicio, dataInicio.get(Calendar.YEAR), 
	        			dataInicio.get(Calendar.MONTH), dataInicio.get(Calendar.DAY_OF_MONTH)).show();
	        }
	    });
		
		//Data Termino
		dataTermino = Calendar.getInstance();
		inputDataTermino = ((EditText)findViewById(R.id.data_termino));
		inputDataTermino.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	new DatePickerDialog(NovoJogoActivity.this, datePickerTermino, dataTermino.get(Calendar.YEAR), 
	        			dataTermino.get(Calendar.MONTH), dataTermino.get(Calendar.DAY_OF_MONTH)).show();
	        }
		});
		
		
		//Botao para adicionar jogadores
		final Button botaoAddJogadores = (Button) findViewById(R.id.botao_add_jogadores);
		botaoAddJogadores.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent jogadoresIntent = new Intent(getApplicationContext(), JogadoresActivity.class);
	            startActivity(jogadoresIntent);
			}
		});
		
		
		//Botao para adicionar atividades
		final Button botaoAddAtividades = (Button) findViewById(R.id.botao_add_atividades);
		botaoAddAtividades.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent atividadesIntent = new Intent(getApplicationContext(), AtividadesActivity.class);
				startActivity(atividadesIntent);
			}
		});
		
		
		
		//Botao para criar o jogo
		final Button botaoCriarJogo = (Button) findViewById(R.id.botao_criar_jogo);
		botaoCriarJogo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean conectado = false;
				EditText inputDescricaoJogo = (EditText) findViewById(R.id.input_descricao_jogo);
				
				String descricao = inputDescricaoJogo.getText().toString();
				
				//String resultValidacao = validarCampos(email, senha, nome);
				String resultValidacao = "";
				
				if(resultValidacao.equals("")){
					conectado = Util.temConexao(getApplicationContext());
					if(conectado){
						inserirJogoRemoto(descricao, dataInicio.getTime(),  dataTermino.getTime());
					}
				} else {
					Toast.makeText(getApplicationContext(), resultValidacao, Toast.LENGTH_LONG).show();
				}
			}
		});
		
	}


	public void inserirJogoRemoto(String descricao, Date dataInicio, Date dataTermino){
		this.descricaoJogo = descricao;		
		
		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		this.dataInicioJogo = dateFormat2.format(dataInicio);
		this.dataTerminoJogo = dateFormat2.format(dataTermino);
		this.flagAtivado = 1;  
		this.emailUsuario = sessao.preferencias.getString(GerenciadorSessao.EMAIL_KEY, "");
	
		String path = ConstantesGameThis.PATH_JOGO_CREATE;
		
		ArrayList<HashMap<String, Object>> listaParametros = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("descricao", descricaoJogo);
		map.put("dataInicio", dataInicioJogo);
		map.put("dataTermino", dataTerminoJogo);
		map.put("flagAtivado", this.flagAtivado);
		map.put("emailCriadorJogo", this.emailUsuario);
		
		listaParametros.add(map);
		Gson gson = new Gson();
		String convertedJson = gson.toJson(listaParametros);
		String json = convertedJson.substring(1, convertedJson.length() - 1);
		new InserirJogoTask().execute(path, json);
	}
	
	
	
	
	private class InserirJogoTask extends AsyncTask<String, String, String> {
		protected ProgressDialog dialogo = new ProgressDialog(NovoJogoActivity.this);
		
		@Override
		protected void onPreExecute(){
		    dialogo.setProgressStyle(ProgressDialog.THEME_HOLO_DARK);
		    dialogo.setCancelable(false);
		    dialogo.setTitle("Criando jogo.");
		    dialogo.setMessage("Por favor, aguarde...");
		    dialogo.show(); 
		};
		
		
		@Override
		protected String doInBackground(String... params) {
			String msgResposta = "";
			int indice = 0;
			String path = params[indice++].toString();
			String json = params[indice++].toString();
			HttpSincronizacaoClient httpClient = new HttpSincronizacaoClient();
			
			try {
				httpClient.post(path, json);
				msgResposta = "sucesso";
			} catch (IOException e) {
				msgResposta = "Erro no servidor.";
				e.printStackTrace();
			}
			return msgResposta;
		}; 
		
	 
		@Override
		protected void onPostExecute(String msgResposta){
			dialogo.dismiss(); 
			
			if(msgResposta.equals("sucesso")){
				// Insere jogo localmente no SQLite
				//inserirUsuario(emailUsuario, senhaUsuario, nomeUsuario, avatarUsuario, timestampUsuario);
				
				Toast.makeText(getApplicationContext(), "Jogo criado com sucesso", Toast.LENGTH_LONG).show();
				Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
				startActivity(homeIntent);
				finish();
			} else {
				Toast.makeText(getApplicationContext(), msgResposta, Toast.LENGTH_LONG).show();
			}
		};
	}
	
	
	
	
	
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.novo_jogo, menu);
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
