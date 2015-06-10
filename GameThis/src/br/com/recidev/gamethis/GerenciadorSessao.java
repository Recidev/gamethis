package br.com.recidev.gamethis;


import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
 
public class GerenciadorSessao {
    SharedPreferences preferencias;
    Editor editor;
    Context contexto;
    int PRIVATE_MODE = 0;
    
    private static final String PREF_NAME = "GameThisPrefs";
    private static final String ESTA_LOGADO = "estaLogado";
    public static final String EMAIL_KEY = "emailkey";
    public static final String SENHA_KEY = "senhakey";
    public static final String NOME_KEY = "nomekey";
    public static final String AVATAR_KEY = "avatarkey";
    public static final int[] TIPOS_AVATAR = new int[] { 
    	R.drawable.warcraft_undead_hero, 
    	R.drawable.warcraft_elf_hero, 
    	R.drawable.warcraft_hero};

     
    public GerenciadorSessao(Context context){
        this.contexto = context;
        preferencias = contexto.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preferencias.edit();
    }
     
    /**
     * 
     * */
    public void criarSessaoLogin(String email, String senha, String nome, int avatar){
        editor.putBoolean(ESTA_LOGADO, true);
        editor.putString(EMAIL_KEY, email);
        editor.putString(SENHA_KEY, senha);
        editor.putString(NOME_KEY, nome);
        editor.putInt(AVATAR_KEY, avatar);
        editor.commit();
    }   
     

    /**
     *
     * */
    public HashMap<String, String> getDetalhesUsuario(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(EMAIL_KEY, preferencias.getString(EMAIL_KEY, null));
        user.put(SENHA_KEY, preferencias.getString(SENHA_KEY, null));
        return user;
    }
     
    /**
     * 
     * */
    public void logoutUsuario(){
        editor.clear();
        editor.commit();
    }
     
    /**
     * 
     * **/
    public boolean estaLogado(){
        return preferencias.getBoolean(ESTA_LOGADO, false);
    }
}