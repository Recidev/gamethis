package br.com.recidev.gamethis.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class Util {
	
	private Util() {
	}
	
	
	/** 
	 * Checa se o usario esta conectado a wifi  3G
	 * @return
	 */
	public static boolean temConexao(Context context){

		boolean conectado = false;
		ConnectivityManager gerenciadorConectividade = (ConnectivityManager)context.
				getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo infoRede = null;
		if (gerenciadorConectividade != null) {
			infoRede = gerenciadorConectividade.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			conectado = infoRede.isConnected();
			if (!conectado) {
				infoRede = gerenciadorConectividade.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
				conectado = infoRede.isConnected();
			}
		}
		
		return conectado;
	}
	
	
	public static String stringToSha1(String texto){
		String sha1 = "";
		MessageDigest md;
		
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.update(texto.getBytes());
			byte[] hashSha1 = md.digest();
			sha1 = stringHexa(hashSha1);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return sha1;
	}
	
	
	private static String stringHexa(byte[] bytes) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			int parteAlta = ((bytes[i] >> 4) & 0xf) << 4;
			int parteBaixa = bytes[i] & 0xf;
			if (parteAlta == 0) s.append('0');
			s.append(Integer.toHexString(parteAlta | parteBaixa));
		}
		return s.toString();
	}


}
