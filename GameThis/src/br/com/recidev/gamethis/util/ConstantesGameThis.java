package br.com.recidev.gamethis.util;

public final class ConstantesGameThis {

	public static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	//Paths REST
	public static final String PATH_INDEX = "/usuarios";
	public static final String PATH_SHOW = "/usuarios/";
	public static final String PATH_CREATE = "/usuarios/create";
	public static final String PATH_UPDATE = "/usuarios/:id"; 
	public static final String PATH_DELETE = "/usuarios/:id";
	public static final String PATH_LOGIN = "/usuarios";
	
	//Paths REST
	public static final String PATH_JOGO_INDEX = "/jogos";
	public static final String PATH_JOGO_SHOW = "/jogos/";
	public static final String PATH_JOGO_CREATE = "/jogos/create";
	public static final String PATH_JOGO_UPDATE = "/jogos/:id"; 
	public static final String PATH_JOGO_DELETE = "/jogos/:id";
	
	public static final String PATH_SYNC_JOGO = "/sync/sqliteToService";
}