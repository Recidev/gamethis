package br.com.recidev.gamethis;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InscricaoActivity extends Activity {

	GerenciadorSessao sessao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inscricao);
		
		sessao = new GerenciadorSessao(getApplicationContext()); 
		
		final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
		alertBuilder.setTitle("Selecione seu Avatar!!");
		
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
				android.R.layout.simple_list_item_2, android.R.id.text1);
		
		adapter.add("Warior");
		adapter.add("Princess");
		adapter.add("Mage");
		adapter.add("Thiev");
		
		// Assign adapter to ListView
		alertBuilder.setAdapter(adapter, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//String strName = adapter.getItem(which);
				// AlertDialog.Builder builderInner = new AlertDialog.Builder(
				//         MainActivity.this);
				//builderInner.setMessage(strName);
				//builderInner.setTitle("Your Selected Item is");
				//builderInner.setPositiveButton("Ok",
				//     new DialogInterface.OnClickListener() {
				
				//          @Override
				//           public void onClick(
				//                  DialogInterface dialog,
				//                   int which) {
				//               dialog.dismiss();
				//          }
				//       });
				//builderInner.show();
			}
		});
		
		
		//Direciona para a tela de incricao
		final Button botaoAvatarInscricao = (Button) findViewById(R.id.botao_avatar_inscricao);
		botaoAvatarInscricao.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				alertBuilder.show();
			}
		});
		
		
		//Direciona para a tela de inscricao
		final Button botaoInscricaoEnviar = (Button) findViewById(R.id.botao_inscricao_enviar);
		botaoInscricaoEnviar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				EditText inputNome = (EditText) findViewById(R.id.input_nome);
				EditText inputEmail = (EditText) findViewById(R.id.input_email);
				EditText inputPassword = (EditText) findViewById(R.id.input_password);
				
				String nome = inputNome.getText().toString();
				String email = inputEmail.getText().toString();
				String password = inputPassword.getText().toString(); 
				
				Toast.makeText(getApplicationContext(), 
						"Inscrição realizada com sucesso", Toast.LENGTH_LONG).show();
				
				sessao.criarSessaoLogin(email, password);
				
				Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
				homeIntent.putExtra("nome", nome);
				startActivity(homeIntent);
				finish();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inscricao, menu);
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
