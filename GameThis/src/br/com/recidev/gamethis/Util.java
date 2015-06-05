package br.com.recidev.gamethis;

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


}
