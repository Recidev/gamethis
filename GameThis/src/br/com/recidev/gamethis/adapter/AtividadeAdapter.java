package br.com.recidev.gamethis.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.recidev.gamethis.R;
import br.com.recidev.gamethis.dominio.Atividade;

public class AtividadeAdapter extends BaseAdapter{
	
	Context contexto;
	ArrayList<Atividade> listaAtividades;
	Atividade atividadeLista;
	
	public AtividadeAdapter(Context context,  ArrayList<Atividade> atividades) {
		contexto = context;
		listaAtividades = atividades; 
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		
		View viewAtividade = inflater.inflate(R.layout.list_atividades_cadastradas, parent, false);
		TextView textViewPontosAtividade = (TextView) viewAtividade.findViewById(R.id.textoPontosAtividade);
		TextView textViewDescricaoAtividade = (TextView) viewAtividade.findViewById(R.id.textoDescricaoAtividade);
		TextView textViewDuracaoAtividade = (TextView) viewAtividade.findViewById(R.id.textoDuracaoAtividade);
		
		textViewPontosAtividade.setText(String.valueOf(listaAtividades.get(position).getPontos()));
		textViewDescricaoAtividade.setText(listaAtividades.get(position).getDescricao());
		textViewDuracaoAtividade.setText(String.valueOf(listaAtividades.get(position).getDuracao()));
		
		return viewAtividade;
	}
	

	@Override
	public int getCount() {
		return listaAtividades.size();
	}

	@Override
	public Object getItem(int position) {
		return listaAtividades.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
