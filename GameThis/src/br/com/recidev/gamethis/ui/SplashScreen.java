package br.com.recidev.gamethis.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import br.com.recidev.gamethis.R;
 
public class SplashScreen extends Activity{
    
    private Thread mSplashThread; 
    private boolean mblnClicou = false;
 
    /** Evento chamado quando a activity � executada pela primeira vez */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.splashscreen);
    	
    	//thread para mostrar uma tela de Splash
    	mSplashThread = new Thread() {
    		@Override
    		public void run() {
    			try {
    				synchronized(this){
    					//Espera por 3 segundos or sai quando
    					//o usu�rio tocar na tela
    					wait(3000);
    					mblnClicou = true;
    				}
    			}
    			catch(InterruptedException ex){                    
    			}
    			
    			if (mblnClicou){
    				//fechar a tela de Splash
    				finish();
    				
    				//Carrega a Activity Principal                 
    				Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
    				startActivity(mainIntent);
    			}
    		}
    	};
    	
    	mSplashThread.start();
    }
     
    @Override
    public void onPause() {
        super.onPause();
        //garante que quando o usu�rio clicar no bot�o
        //"Voltar" o sistema deve finalizar a thread
        mSplashThread.interrupt();
    }
     
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
        	
            //o m�todo abaixo est� relacionado a thread de splash
        	synchronized(mSplashThread){
        		mblnClicou = true;
        		
        		//o m�todo abaixo finaliza o comando wait
        		//mesmo que ele n�o tenha terminado sua espera
        		mSplashThread.notifyAll();
            }            
        }
        return true;
    }
 
}