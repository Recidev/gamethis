package br.com.recidev.gamethis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AvatarAdapter extends ArrayAdapter<String>{

	Context contexto;
	String[] nomesAvatar;

	public AvatarAdapter(Context context,  String[] nomes) {
		super(context, R.layout.list_selecionar_avatar, nomes);
		contexto = context;
		nomesAvatar = nomes;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		
		View viewAvatar = inflater.inflate(R.layout.list_selecionar_avatar, parent, false);
		TextView textView = (TextView) viewAvatar.findViewById(R.id.textoLinha);
		ImageView imageView = (ImageView) viewAvatar.findViewById(R.id.imagemLinha);
		textView.setText(nomesAvatar[position]);
		
		String tipoAvatar = nomesAvatar[position];
		
		
		if(tipoAvatar.equals("Warior")){
			imageView.setImageResource(R.drawable.warcraft_undead_hero);
		} else if(tipoAvatar.equals("Mage")){
			imageView.setImageResource(R.drawable.warcraft_elf_hero);
		} else if(tipoAvatar.equals("Thiev")){
			imageView.setImageResource(R.drawable.warcraft_hero);
		} else {
			imageView.setImageResource(R.drawable.ic_launcher);
		}
		
		return viewAvatar;
	}

}
