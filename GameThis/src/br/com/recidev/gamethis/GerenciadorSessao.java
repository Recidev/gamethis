package br.com.recidev.gamethis;


import java.util.HashMap;
 
import android.content.Context;
import android.content.Intent;
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
    public static final String PASSWORD_KEY = "passwordkey";
     
    public GerenciadorSessao(Context context){
        this.contexto = context;
        preferencias = contexto.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preferencias.edit();
    }
     
    /**
     * 
     * */
    public void criarSessaoLogin(String email, String password){
        editor.putBoolean(ESTA_LOGADO, true);
        editor.putString(EMAIL_KEY, email);
        editor.putString(PASSWORD_KEY, password);
        editor.commit();
    }   
     
//    public void checaLogin(){
//        if(!this.estaLogado()){
//            Intent intent = new Intent(contexto, LoginActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            contexto.startActivity(intent);
//        }
//    }
     
    /**
     *
     * */
    public HashMap<String, String> getDetalhesUsuario(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(EMAIL_KEY, preferencias.getString(EMAIL_KEY, null));
        user.put(PASSWORD_KEY, preferencias.getString(PASSWORD_KEY, null));
        return user;
    }
     
    /**
     * 
     * */
    public void logoutUsuario(){
        editor.clear();
        editor.commit();
         
        Intent intent = new Intent(contexto, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        contexto.startActivity(intent);
    }
     
    /**
     * 
     * **/
    public boolean estaLogado(){
        return preferencias.getBoolean(ESTA_LOGADO, false);
    }
}