package br.com.recidev.gamethis.util;


public class GCMMensagem {
	
	private String[] registration_ids;
	private Object data;
	private String collapse_key;
	
	
	public String[] getRegistration_ids() {
		return registration_ids;
	}
	public void setRegistration_ids(String[] registration_ids) {
		this.registration_ids = registration_ids;
	}

	public String getCollapse_key() {
		return collapse_key;
	}
	public void setCollapse_key(String collapse_key) {
		this.collapse_key = collapse_key;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}


}
