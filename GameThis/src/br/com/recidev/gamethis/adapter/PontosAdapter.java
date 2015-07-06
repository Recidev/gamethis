package br.com.recidev.gamethis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.recidev.gamethis.R;

public class PontosAdapter extends ArrayAdapter<String>{
	
	Context contexto;
	private String[] pontosAtividade;
	

	public PontosAdapter(Context context,  String[] pontos) {
		super(context, R.layout.list_selecionar_pontuacao, pontos);
		contexto = context;
		pontosAtividade = pontos;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		
		View viewPontos = inflater.inflate(R.layout.list_selecionar_pontuacao, parent, false);
		TextView textView = (TextView) viewPontos.findViewById(R.id.textoLinhaPontuacao);
		textView.setText(pontosAtividade[position]);
		return viewPontos;
	}

}
