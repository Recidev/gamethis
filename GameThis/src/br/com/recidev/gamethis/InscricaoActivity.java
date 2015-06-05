package br.com.recidev.gamethis;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import br.com.recidev.gamethis.repositorio.RepositorioUsuarioSQLite;
import br.com.recidev.gamethis.ws.UsuarioWS;

public class InscricaoActivity extends Activity {
	
	GerenciadorSessao sessao;
	final String[] AVATAR = new String[] { "Warior", "Mage", "Thiev"};
	int tipoAvatar = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inscricao);
		
		sessao = new GerenciadorSessao(getApplicationContext()); 

		//Criacao de dialogo para selecao do avatar
		final AlertDialog.Builder dialogAvatar = new AlertDialog.Builder(this);
		dialogAvatar.setTitle("Selecione seu Avatar!!");
		AvatarAdapter avatarAdapter = new AvatarAdapter(this, AVATAR);
		dialogAvatar.setAdapter(avatarAdapter, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				tipoAvatar = which;
				mudarImagem();
			}
		});
		
		final Button botaoAvatarInscricao = (Button) findViewById(R.id.botao_avatar_inscricao);
		botaoAvatarInscricao.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dialogAvatar.show();
			}
		});
		
		final Button botaoInscricaoEnviar = (Button) findViewById(R.id.botao_inscricao_enviar);
		botaoInscricaoEnviar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				UsuarioWS usuarioWS = new UsuarioWS();
				boolean conectado = false;
				EditText inputNome = (EditText) findViewById(R.id.input_nome);
				EditText inputEmail = (EditText) findViewById(R.id.input_email);
				EditText inputSenha = (EditText) findViewById(R.id.input_senha);
				
				String email = inputEmail.getText().toString();
				String senha = inputSenha.getText().toString(); 
				String nome = inputNome.getText().toString();
				int avatar = tipoAvatar;
				
				conectado = Util.temConexao(getApplicationContext());
				if(conectado){
					//verifica se o usuario ja existe
					usuarioWS.consultarUsuario(email, getApplicationContext());
					
					//insere usuario remotamente
					usuarioWS.inserirUsuario(email, senha, nome, avatar, getApplicationContext());
					
					//insere usuario localmente no sqlite
					inserirUsuario(email, senha, nome, avatar);
					
					Toast.makeText(getApplicationContext(), "Inscrição realizada com sucesso", Toast.LENGTH_LONG).show();
					sessao.criarSessaoLogin(email, senha, nome, avatar);
					Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
					startActivity(homeIntent);
					finish();
				}
				
			}
		});
	}
	
	
	public void inserirUsuario(String email, String senha, String nome, int avatar){
		RepositorioUsuarioSQLite repUsuario = new RepositorioUsuarioSQLite();
		repUsuario.inserirUsuario(email, senha, nome, avatar, getApplicationContext());
		
		Toast.makeText(getApplicationContext(), "Inscrição realizada com sucesso", Toast.LENGTH_LONG).show();		
	}
	
	
	public void mudarImagem(){
		ImageView imagemDefinida = (ImageView) findViewById(R.id.imagemDefinida);
		imagemDefinida.setImageResource(GerenciadorSessao.TIPOS_AVATAR[tipoAvatar]);
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
