package br.com.recidev.gamethis.repositorio;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.recidev.gamethis.dominio.Jogo;
import br.com.recidev.gamethis.util.SQLiteHelper;

public class RepositorioJogoSQLite {
	
	DateFormat dateFormatTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
	
	private static final String NOME_TABELA = "jogo";
	
	//Colunas
	private static final String DESCRICAO = "descricao";
	private static final String DATA_INICIO = "dt_inicio";
	private static final String DATA_TERMINO = "dt_termino";
	private static final String FLAG_ATIVADO = "fl_ativado";
	private static final String LOGIN_CRIADOR = "login_criador";
	private static final String NATURAL_ID = "natural_id";
	private static final String TIMESTAMP = "ts_jogo";
	private static final String SYNC_STATUS = "sync_sts";
	
	private static String[] colunas = new String[] {
		DESCRICAO, DATA_INICIO, DATA_TERMINO, FLAG_ATIVADO, LOGIN_CRIADOR, NATURAL_ID, TIMESTAMP, SYNC_STATUS};
	
	protected SQLiteDatabase db;
	protected Context context;
	
	
	public RepositorioJogoSQLite() {
	}
	
	
	private ContentValues carregarContentValues(Jogo jogo) {
		ContentValues valores = new ContentValues();
		valores.put(DESCRICAO, jogo.getDescricao());
		valores.put(DATA_INICIO, dateFormat.format(jogo.getDtInicial()));
		valores.put(DATA_TERMINO, dateFormat.format(jogo.getDtFinal()));
		valores.put(FLAG_ATIVADO, jogo.getAtivado());
		valores.put(LOGIN_CRIADOR, jogo.getLoginCriador());
		valores.put(TIMESTAMP, dateFormatTimestamp.format(jogo.getTimestamp()));
		valores.put(SYNC_STATUS, jogo.getSyncStatus());
		
		return valores;
	}

	
	public void inserirJogo(Jogo jogo, Context c) {
		try {
			context = c;
			SQLiteHelper dbHelper = SQLiteHelper.getInstance(context);
			
			ContentValues values = carregarContentValues(jogo);
			db = dbHelper.getWritableDatabase();
		
			db.insert(NOME_TABELA, "", values);
		} catch (Exception e) {
			System.out.println("Erro !");
		}
	}
	
	
	
	public void atualizarSyncStatusJogo(String descricao, int syncStatus, Context c) {
		try {
			context = c;
			SQLiteHelper dbHelper = SQLiteHelper.getInstance(context);
			
			String where = DESCRICAO + "=?";
			String whereArgs[] = new String[] {descricao + ""}; 
			
			ContentValues values = new ContentValues();
			values.put(SYNC_STATUS, syncStatus);
			
			db = dbHelper.getWritableDatabase();
		
			db.update(NOME_TABELA, values, where, whereArgs);
		} catch (Exception e) {
			System.out.println("Erro !");
		}
	}
	
	
	
	public ArrayList<Jogo> consultarJogosNaoAtualizados(Context c) {
		ArrayList<Jogo> retorno = new ArrayList<Jogo>();
		
		try {
			Cursor cursor = null;
			context = c;
			SQLiteHelper dbHelper = SQLiteHelper.getInstance(context);
			int syncStatus = 1;
			
			String where = SYNC_STATUS + "=?";
			String whereArgs[] = new String[] {syncStatus + ""};
			
			db = dbHelper.getWritableDatabase();
			cursor = db.query(NOME_TABELA, colunas, where, whereArgs, null, null, null, null);

			if (cursor.moveToFirst()) {
				do {
					retorno.add(carregaJogo(cursor));
				} while (cursor.moveToNext());
			}
			
		} catch (Exception e) {
			System.out.println("Erro !");
		}
		return retorno;
	}
	
	
	private Jogo carregaJogo(Cursor cursor) {
		Jogo jogo = new Jogo();
		try {
			jogo.setDescricao(cursor.getString(cursor.getColumnIndex(DESCRICAO)));
			jogo.setDtInicial(dateFormat.parse(cursor.getString(cursor.getColumnIndex(DATA_INICIO))));
			jogo.setDtFinal(dateFormat.parse(cursor.getString(cursor.getColumnIndex(DATA_TERMINO))));
			jogo.setAtivado(cursor.getInt(cursor.getColumnIndex(FLAG_ATIVADO)));
			jogo.setTimestamp(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(TIMESTAMP))));
			jogo.setSyncStatus(cursor.getInt(cursor.getColumnIndex(SYNC_STATUS)));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return jogo;
	}

}
