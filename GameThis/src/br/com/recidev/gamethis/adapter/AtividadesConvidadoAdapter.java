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

public class AtividadesConvidadoAdapter  extends BaseAdapter{
	
	Context contexto;
	ArrayList<Atividade> listaAtividadesJogoConvidado;
	Atividade atividadeLista;
	
	
	public AtividadesConvidadoAdapter(Context context,  ArrayList<Atividade> atividades) {
		contexto = context;
		listaAtividadesJogoConvidado = atividades; 
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		
		View viewAtividade = inflater.inflate(R.layout.list_atividades_convidado, parent, false);
		TextView textViewPontosAtividadeConvidado = (TextView) viewAtividade.findViewById(R.id.textoPontosAtividadeConvidado);
		TextView textViewDescricaoAtividadeConvidado = (TextView) viewAtividade.findViewById(R.id.textoDescricaoAtividadeConvidado);
		TextView textViewDuracaoAtividadeConvidado = (TextView) viewAtividade.findViewById(R.id.textoDuracaoAtividadeConvidado);
		
		
		textViewPontosAtividadeConvidado.setText(
			textViewPontosAtividadeConvidado.getText().toString() + " " + String.valueOf(listaAtividadesJogoConvidado.get(position).getPontos()));
		textViewDescricaoAtividadeConvidado.setText(listaAtividadesJogoConvidado.get(position).getDescricao());
		textViewDuracaoAtividadeConvidado.setText(String.valueOf(listaAtividadesJogoConvidado.get(position).getDuracao()));
		
		return viewAtividade;
	}

	@Override
	public int getCount() {
		return listaAtividadesJogoConvidado.size();
	}

	@Override
	public Object getItem(int position) {
		return listaAtividadesJogoConvidado.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
