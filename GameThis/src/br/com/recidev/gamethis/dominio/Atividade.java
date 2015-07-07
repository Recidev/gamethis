package br.com.recidev.gamethis.dominio;


public class Atividade implements java.io.Serializable{

	private static final long serialVersionUID = -3809318758592231779L;
	private String descricao;
	private int pontos;
	private int duracao;
	private String loginJogador;
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
	public int getSync_sts() {
		return sync_sts;
	}
	public void setSync_sts(int sync_sts) {
		this.sync_sts = sync_sts;
	}
}
