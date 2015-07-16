package br.com.recidev.gamethis.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.recidev.gamethis.R;
import br.com.recidev.gamethis.dominio.Ranking;
import br.com.recidev.gamethis.dominio.Usuario;

public class RankingAdapter extends BaseAdapter{

	Context contexto;
	ArrayList<Ranking> listaRanking;
	Usuario jogadorLista;

	public RankingAdapter(Context context,  ArrayList<Ranking> arrayRanking) {
		contexto = context;
		listaRanking = arrayRanking; 
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		
		View viewRanking = inflater.inflate(R.layout.list_ranking, parent, false);
		TextView textoPosicaoRankingValor = (TextView) viewRanking.findViewById(R.id.textoPosicaoRankingValor);
		TextView textoLoginJogadorRankingLinha  = (TextView) viewRanking.findViewById(R.id.textoLoginJogadorRankingLinha);
		TextView textoPontuacaoRankingLinha  = (TextView) viewRanking.findViewById(R.id.textoPontuacaoRankingLinha);
		
		int posicaoRanking = position + 1;
		textoPosicaoRankingValor.setText(textoPosicaoRankingValor.getText() +
				" " + String.valueOf(posicaoRanking));
		textoLoginJogadorRankingLinha.setText(listaRanking.get(position).getLoginJogador());
		textoPontuacaoRankingLinha.setText(
				" " + String.valueOf(listaRanking.get(position).getPontuacao()));
		
		//ImageView imageViewJogador = (ImageView) viewRanking.findViewById(R.id.imagemJogadorLinha);
		
		//int tipoAvatar = 0;
		
//		if(tipoAvatar == 0){
//			imageViewJogador.setImageResource(R.drawable.warcraft_undead_hero);
//		} else if(tipoAvatar == 1){
//			imageViewJogador.setImageResource(R.drawable.warcraft_elf_hero);
//		} else if(tipoAvatar == 2){
//			imageViewJogador.setImageResource(R.drawable.warcraft_hero);
//		} else {
//			imageViewJogador.setImageResource(R.drawable.ic_launcher);
//		}
		
		return viewRanking;
	}


	@Override
	public int getCount() {
		return listaRanking.size();
	}

	@Override
	public Object getItem(int position) {
		return listaRanking.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
