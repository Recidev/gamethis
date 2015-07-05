package br.com.recidev.gamethis.dominio;

import java.sql.Timestamp;

public class Usuario implements java.io.Serializable{

	private static final long serialVersionUID = -7336715932324205974L;
	private String email;
	private String senha;
	private String nome;
	private int avatar;
	private Timestamp ts_usuario;
	private int sync_sts;
	private String gcm_id;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getAvatar() {
		return avatar;
	}
	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}
	
	
	
	public String getGcm_id() {
		return gcm_id;
	}
	public void setGcm_id(String gcm_id) {
		this.gcm_id = gcm_id;
	}
	public Timestamp getTs_usuario() {
		return ts_usuario;
	}
	public void setTs_usuario(Timestamp ts_usuario) {
		this.ts_usuario = ts_usuario;
	}
	public int getSync_sts() {
		return sync_sts;
	}
	public void setSync_sts(int sync_sts) {
		this.sync_sts = sync_sts;
	}
	
}
