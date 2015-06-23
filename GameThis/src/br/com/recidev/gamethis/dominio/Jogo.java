package br.com.recidev.gamethis.dominio;

import java.sql.Date;

public class Jogo {

	private String descricao;
	private Date dtInicial;
	private Date dtFinal;
	private int criador;
	private int ativado;
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Date getDtInicial() {
		return dtInicial;
	}
	public void setDtInicial(Date dtInicial) {
		this.dtInicial = dtInicial;
	}
	public Date getDtFinal() {
		return dtFinal;
	}
	public void setDtFinal(Date dtFinal) {
		this.dtFinal = dtFinal;
	}
	public int getCriador() {
		return criador;
	}
	public void setCriador(int criador) {
		this.criador = criador;
	}
	public int getAtivado() {
		return ativado;
	}
	public void setAtivado(int ativado) {
		this.ativado = ativado;
	}
}
