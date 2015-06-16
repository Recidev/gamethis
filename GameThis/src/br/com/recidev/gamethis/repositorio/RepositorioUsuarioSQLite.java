package br.com.recidev.gamethis.repositorio;

import br.com.recidev.gamethis.util.SQLiteHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class RepositorioUsuarioSQLite {
	
	private static final String NOME_TABELA = "usuario";
	private static final String EMAIL = "email";
	private static final String SENHA = "senha";
	private static final String NOME = "nome";
	private static final String AVATAR = "avatar";
	protected SQLiteDatabase db;
	protected Context context;
	
	
	public RepositorioUsuarioSQLite() {
	}
	
	private ContentValues carregarContentValues(String email, String senha, String nome, int avatar) {
		ContentValues valores = new ContentValues();
		valores.put(EMAIL, email);
		valores.put(SENHA, senha);
		valores.put(NOME, nome);
		valores.put(AVATAR, avatar);
		
		return valores;
	}

	
	public void inserirUsuario(String email, String senha, String nome, int avatar, Context c) {
		context = c;

		SQLiteHelper dbHelper = SQLiteHelper.getInstance(context);
		SQLiteDatabase db;
		
		ContentValues values = carregarContentValues(email, senha, nome, avatar);
		db = dbHelper.getWritableDatabase();
		
		try {
			db.insert(NOME_TABELA, "", values);
		} catch (Exception e) {
			System.out.println("Erro !");
		}
	}
	
}

