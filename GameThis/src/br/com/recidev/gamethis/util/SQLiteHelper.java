package br.com.recidev.gamethis.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
	
	private static SQLiteHelper instance;
	private static final String NOME_BANCO = "gamethis";
	private static final int VERSAO_BANCO = 1;
	
	/**
	 * Retorna a instancia estatica 
	 * 
	 * @param context Contexto da aplicacao
	 * @return Instancia
	 */
	public static SQLiteHelper getInstance(Context context){
		if(instance == null){
			instance = new SQLiteHelper(context);
		}
		return instance;
	}

	public SQLiteHelper(Context context) {
		super(context, NOME_BANCO, null, VERSAO_BANCO);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		StringBuffer query = new StringBuffer();
		query.append("CREATE TABLE usuario ( ");
		query.append(" id INTEGER PRIMARY KEY NOT NULL, ");
		query.append(" email VARCHAR(100), ");
		query.append(" senha VARCHAR(100), ");
		query.append(" nome VARCHAR(100), ");
		query.append(" avatar INTEGER, ");
		query.append(" ts_usuario DATETIME, ");
		query.append(" sync_sts INTEGER(11) NOT NULL DEFAULT 0 "); 
		query.append(")");
		db.execSQL(query.toString());
		
		query = new StringBuffer();
		query.append("CREATE UNIQUE INDEX email_UNIQUE ON usuario (email)");
		db.execSQL(query.toString());
		
		
		query = new StringBuffer();
		query.append("CREATE TABLE atividade ( ");
		query.append(" id INTEGER PRIMARY KEY NOT NULL, ");
		query.append(" natural_id VARCHAR(100) NOT NULL, ");
		query.append(" descricao VARCHAR(100), ");
		query.append(" duracao INTEGER, ");
		query.append(" pontos INTEGER, ");
		query.append(" login_jogador VARCHAR(100), ");
		query.append(" flag_concluida INTEGER(11) NOT NULL DEFAULT 0, ");
		query.append(" sync_sts INTEGER(11) NOT NULL DEFAULT 0, ");
		query.append(" FOREIGN KEY (login_jogador) REFERENCES usuario(email) ");
		query.append(")");
		db.execSQL(query.toString());
		
		query = new StringBuffer();
		query.append("CREATE TABLE jogo ( ");
		query.append(" id INTEGER PRIMARY KEY  NOT NULL, ");
		query.append(" natural_id VARCHAR(100) NOT NULL, ");
		query.append(" descricao VARCHAR(100), ");
		query.append(" dt_inicio DATETIME, ");
		query.append(" dt_termino DATETIME, ");
		query.append(" fl_ativado INTEGER, ");
		query.append(" login_criador VARCHAR(100), ");
		query.append(" ts_jogo DATETIME, ");
		query.append(" sync_sts INTEGER(11) NOT NULL DEFAULT 0, ");
		query.append(" FOREIGN KEY (login_criador) REFERENCES usuario(email) ");
		query.append(")");
		db.execSQL(query.toString());
		
		query = new StringBuffer();
		query.append("CREATE TABLE jogo_usuario ( ");
		query.append(" id_jogo varchar(100) NOT NULL, ");
		query.append(" login_jogador VARCHAR(100), ");
		query.append(" sync_sts INTEGER(11) NOT NULL DEFAULT 0 ");
		query.append(")");
		db.execSQL(query.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
}
