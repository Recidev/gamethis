package br.com.recidev.gamethis.repositorio;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import br.com.recidev.gamethis.dominio.Usuario;
import br.com.recidev.gamethis.util.SQLiteHelper;

public class RepositorioUsuarioSQLite {
	
	DateFormat dateFormatTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
	
	private static final String NOME_TABELA = "usuario";
	private static final String EMAIL = "email";
	private static final String SENHA = "senha";
	private static final String NOME = "nome";
	private static final String AVATAR = "avatar";
	private static final String TIMESTAMP = "ts_usuario";
	private static final String SYNC_STATUS = "sync_sts";
	
	protected SQLiteDatabase db;
	protected Context context;
	
	
	public RepositorioUsuarioSQLite() {
	}
	
	
	private ContentValues carregarContentValues(Usuario usuario) {
		ContentValues valores = new ContentValues();
		
		valores.put(EMAIL, usuario.getEmail());
		valores.put(SENHA, usuario.getSenha());
		valores.put(NOME, usuario.getNome());
		valores.put(AVATAR, usuario.getAvatar());
		valores.put(TIMESTAMP, dateFormatTimestamp.format(usuario.getTs_usuario()));
		valores.put(SYNC_STATUS, usuario.getSync_sts());
		
		return valores;
	}

	
	public void inserirUsuario(Usuario usuario, Context c) {
		context = c;
		SQLiteHelper dbHelper = SQLiteHelper.getInstance(context);
		SQLiteDatabase db;
		
		ContentValues values = carregarContentValues(usuario);
		db = dbHelper.getWritableDatabase();
		try {
			db.insert(NOME_TABELA, "", values);
		} catch (Exception e) {
			System.out.println("Erro !");
		}
	}
	
}

