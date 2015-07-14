package br.com.recidev.gamethis.dominio;


public class Atividade implements java.io.Serializable{

	private static final long serialVersionUID = -3809318758592231779L;
	private String naturalId;
	private String descricao;
	private int pontos;
	private int duracao;
	private String loginJogador;
	private String id_jogo;
	private int sync_sts;
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getPontos() {
		return pontos;
	}
	public void setPontos(int pontos) {
		this.pontos = pontos;
	}
	public int getDuracao() {
		return duracao;
	}
	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}
	public String getLoginJogador() {
		return loginJogador;
	}
	public void setLoginJogador(String loginJogador) {
		this.loginJogador = loginJogador;
	}
	public String getId_jogo() {
		return id_jogo;
	}
	public void setId_jogo(String id_jogo) {
		this.id_jogo = id_jogo;
	}
	public int getSync_sts() {
		return sync_sts;
	}
	public void setSync_sts(int sync_sts) {
		this.sync_sts = sync_sts;
	}
	public String getNaturalId() {
		return naturalId;
	}
	public void setNaturalId(String naturalId) {
		this.naturalId = naturalId;
	}
}
