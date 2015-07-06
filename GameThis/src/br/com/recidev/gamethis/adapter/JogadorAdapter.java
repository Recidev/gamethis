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
import br.com.recidev.gamethis.dominio.Usuario;

public class JogadorAdapter extends BaseAdapter{

	Context contexto;
	ArrayList<Usuario> listaJogadores;
	Usuario jogadorLista;

	public JogadorAdapter(Context context,  ArrayList<Usuario> jogadores) {
		contexto = context;
		listaJogadores = jogadores; 
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		
		View viewJogador = inflater.inflate(R.layout.list_pesquisar_jogador, parent, false);
		TextView textViewEmailJogador = (TextView) viewJogador.findViewById(R.id.textoEmailJogadorLinha);
		ImageView imageViewJogador = (ImageView) viewJogador.findViewById(R.id.imagemJogadorLinha);
		
		textViewEmailJogador.setText(listaJogadores.get(position).getEmail());
		int tipoAvatar = listaJogadores.get(position).getAvatar();
		
		if(tipoAvatar == 0){
			imageViewJogador.setImageResource(R.drawable.warcraft_undead_hero);
		} else if(tipoAvatar == 1){
			imageViewJogador.setImageResource(R.drawable.warcraft_elf_hero);
		} else if(tipoAvatar == 2){
			imageViewJogador.setImageResource(R.drawable.warcraft_hero);
		} else {
			imageViewJogador.setImageResource(R.drawable.ic_launcher);
		}
		
		return viewJogador;
	}


	@Override
	public int getCount() {
		return listaJogadores.size();
	}

	@Override
	public Object getItem(int position) {
		return listaJogadores.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
