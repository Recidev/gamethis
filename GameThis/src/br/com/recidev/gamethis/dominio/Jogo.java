package br.com.recidev.gamethis.dominio;

import java.sql.Timestamp;
import java.util.Date;

public class Jogo implements java.io.Serializable{

	private static final long serialVersionUID = 7545195133894465381L;
	private String descricao;
	private Date dtInicial;
	private Date dtFinal;
	private String loginCriador;
	private String naturalId;
	private int ativado;
	private Timestamp timestamp;
	private int syncStatus;
	
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
	public String getLoginCriador() {
		return loginCriador;
	}
	public void setLoginCriador(String loginCriador) {
		this.loginCriador = loginCriador;
	}
	public int getAtivado() {
		return ativado;
	}
	public void setAtivado(int ativado) {
		this.ativado = ativado;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public int getSyncStatus() {
		return syncStatus;
	}
	public void setSyncStatus(int syncStatus) {
		this.syncStatus = syncStatus;
	}
	public String getNaturalId() {
		return naturalId;
	}
	public void setNaturalId(String naturalId) {
		this.naturalId = naturalId;
	}
}
