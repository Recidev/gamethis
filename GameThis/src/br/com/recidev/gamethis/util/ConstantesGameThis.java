package br.com.recidev.gamethis.util;

public final class ConstantesGameThis {

	public static final String SENDER_ID = "481824738264";
	public static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	//Paths REST - Usuario
	public static final String PATH_INDEX = "/usuarios";
	public static final String PATH_SHOW = "/usuarios/";
	public static final String PATH_CREATE = "/usuarios/create";
	public static final String PATH_UPDATE = "/usuarios/:id"; 
	public static final String PATH_DELETE = "/usuarios/:id";
	public static final String PATH_LOGIN = "/usuarios";
	
	//Paths REST - Jogos
	public static final String PATH_JOGO_INDEX = "/jogos";
	public static final String PATH_JOGO_SHOW = "/jogos/";
	public static final String PATH_JOGO_USUARIO_CRIADOR_SHOW = "/jogos/usuario/";
	public static final String PATH_JOGO_CREATE = "/jogos/create";
	public static final String PATH_JOGO_UPDATE = "/jogos/:id"; 
	public static final String PATH_JOGO_DELETE = "/jogos/:id";
	
	//Paths REST - Atividades
	public static final String PATH_ATIVIDADE_CREATE = "/atividades/create";
	public static final String PATH_ATIVIDADE_SHOW_ID_JOGO = "/atividades/";
	public static final String PATH_ATIVIDADE_UPDATE = "/atividades/update";
	
	//Paths REST - Jogo-Jogador
	public static final String PATH_JOGO_JOGADOR_CREATE = "/jogo_jogador/create";
	public static final String PATH_JOGO_JOGADOR_SHOW = "/jogo_jogador/";
	
	//Paths REST - Sincronização do App
	public static final String PATH_SYNC_JOGO = "/sync/sqliteToService";
}