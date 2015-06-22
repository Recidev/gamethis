package br.com.recidev.gamethis.repositorio;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import br.com.recidev.gamethis.util.SQLiteHelper;

public class RepositorioUsuarioSQLite {
	
	private static final String NOME_TABELA = "usuario";
	private static final String EMAIL = "email";
	private static final String SENHA = "senha";
	private static final String NOME = "nome";
	private static final String AVATAR = "avatar";
	private static final String TIMESTAMP = "ts_usuario";
	
	protected SQLiteDatabase db;
	protected Context context;
	
	
	public RepositorioUsuarioSQLite() {
	}
	
	private ContentValues carregarContentValues(String email, String senha, String nome, int avatar, String ts_usuario) {
		ContentValues valores = new ContentValues();
		
		valores.put(EMAIL, email);
		valores.put(SENHA, senha);
		valores.put(NOME, nome);
		valores.put(AVATAR, avatar);
		valores.put(TIMESTAMP, ts_usuario);
		
		return valores;
	}

	
	public void inserirUsuario(String email, String senha, String nome, int avatar, String ts_usuario, Context c) {
		context = c;

		SQLiteHelper dbHelper = SQLiteHelper.getInstance(context);
		SQLiteDatabase db;
		
		ContentValues values = carregarContentValues(email, senha, nome, avatar, ts_usuario);
		db = dbHelper.getWritableDatabase();
		
		try {
			db.insert(NOME_TABELA, "", values);
		} catch (Exception e) {
			System.out.println("Erro !");
		}
	}
	
}

