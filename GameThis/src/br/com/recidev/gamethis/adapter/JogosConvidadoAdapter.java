package br.com.recidev.gamethis.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.recidev.gamethis.R;
import br.com.recidev.gamethis.dominio.Jogo;

public class JogosConvidadoAdapter extends BaseAdapter{
	
	Context contexto;
	ArrayList<Jogo> listaJogosConvidado;
	Jogo jogoConvidadoLista;
	
	public JogosConvidadoAdapter(Context context,  ArrayList<Jogo> jogosConvidado) {
		contexto = context;
		listaJogosConvidado = jogosConvidado; 
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		
		View viewJogosConvidado = inflater.inflate(R.layout.list_jogos_convidado, parent, false);
		TextView textViewDescricaoJogoConvidado = (TextView) viewJogosConvidado.findViewById(R.id.textoDescricaoJogoConvidado);
		textViewDescricaoJogoConvidado.setText(listaJogosConvidado.get(position).getDescricao());
		
		return viewJogosConvidado;
	}
	

	@Override
	public int getCount() {
		return listaJogosConvidado.size();
	}

	@Override
	public Object getItem(int position) {
		return listaJogosConvidado.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
