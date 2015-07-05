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
		
		View viewAvatar = inflater.inflate(R.layout.list_pesquisar_jogador, parent, false);
		TextView textView = (TextView) viewAvatar.findViewById(R.id.textoEmailJogadorLinha);
		ImageView imageView = (ImageView) viewAvatar.findViewById(R.id.imagemJogadorLinha);
		
		textView.setText(listaJogadores.get(position).getEmail());
		int tipoAvatar = listaJogadores.get(position).getAvatar();
		
		if(tipoAvatar == 0){
			imageView.setImageResource(R.drawable.warcraft_undead_hero);
		} else if(tipoAvatar == 1){
			imageView.setImageResource(R.drawable.warcraft_elf_hero);
		} else if(tipoAvatar == 2){
			imageView.setImageResource(R.drawable.warcraft_hero);
		} else {
			imageView.setImageResource(R.drawable.ic_launcher);
		}
		
		return viewAvatar;
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
