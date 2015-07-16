package br.com.recidev.gamethis.dominio;

public class Ranking implements java.io.Serializable{

	private static final long serialVersionUID = 3171691823563026957L;
	private String id_jogo;
	private String loginJogador;
	private int pontuacao;
	private int sync_sts;
	
	
	public String getId_jogo() {
		return id_jogo;
	}
	public void setId_jogo(String id_jogo) {
		this.id_jogo = id_jogo;
	}
	public String getLoginJogador() {
		return loginJogador;
	}
	public void setLoginJogador(String loginJogador) {
		this.loginJogador = loginJogador;
	}
	public int getPontuacao() {
		return pontuacao;
	}
	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}
	public int getSync_sts() {
		return sync_sts;
	}
	public void setSync_sts(int sync_sts) {
		this.sync_sts = sync_sts;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
