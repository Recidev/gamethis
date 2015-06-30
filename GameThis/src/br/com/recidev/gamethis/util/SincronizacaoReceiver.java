package br.com.recidev.gamethis.util;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import br.com.recidev.gamethis.dominio.Jogo;
import br.com.recidev.gamethis.repositorio.RepositorioJogoSQLite;

import com.google.gson.Gson;

public class SincronizacaoReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		RepositorioJogoSQLite repJogo = new RepositorioJogoSQLite();
		
		//Atualiza dados do SQLite para o banco remoto
		String path = ConstantesGameThis.PATH_SYNC_JOGO;
		ArrayList<Jogo> jogosNaoAtualizados = new ArrayList<Jogo>();
		jogosNaoAtualizados = repJogo.consultarJogosNaoAtualizados(context);
		
		Gson gson = new Gson();
		String convertedJsonJogos = gson.toJson(jogosNaoAtualizados);
		String jsonFinal = "{\"jogos\":" + convertedJsonJogos +"}";
		
		Intent sincService = new Intent(context, SincronizacaoService.class);
		sincService.putExtra("path", path);
		sincService.putExtra("json", jsonFinal);
		context.startService(sincService);
		
		
		//Atualiza dados do banco remoto para o SQLite
		boolean existeDadosParaAtualizar = false;
		
		if(existeDadosParaAtualizar){
			sincService = new Intent(context, SincronizacaoService.class);
			sincService.putExtra("titulo", "Sincronização de Jogos");
			sincService.putExtra("conteudo", "Jogo X foi atualizado");
			context.startService(sincService);
		}
	}
	
	

}
