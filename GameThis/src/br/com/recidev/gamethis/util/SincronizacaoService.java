package br.com.recidev.gamethis.util;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SincronizacaoService extends Service {

	
	 @Override
	   public void onStart(Intent intent, int startId){ 
		 
		 String path = intent.getStringExtra("path");
		 String json = intent.getStringExtra("json");
		 
		HttpSincronizacaoClient httpClient = new HttpSincronizacaoClient();
			
		try {
			String result = httpClient.post(path, json);
			System.out.println(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
		 //new SincronizacaoTask().execute(path, json);
		 
		 
		 //Codigo para notificao
//		 NotificationManager gerenciadorNotificacao = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//		 Intent intentDestino = new Intent(this.getApplicationContext(), HomeActivity.class);
//		 PendingIntent pendingIntentDestino = PendingIntent.getActivity(this.getApplicationContext(), 0, intentDestino, 
//				 PendingIntent.FLAG_UPDATE_CURRENT);
//		 NotificationCompat.Builder notificacao;
//		 notificacao = new NotificationCompat.Builder(this).
//				 setContentTitle(intent.getStringExtra("titulo")).
//				 setContentText(intent.getStringExtra("conteudo")).
//				 setSmallIcon(R.drawable.game_this_2);
//		 notificacao.setContentIntent(pendingIntentDestino);
//		 
//		 int defaults = 0;
//		 defaults = defaults | Notification.DEFAULT_LIGHTS;
//		 defaults = defaults | Notification.DEFAULT_VIBRATE;
//		 defaults = defaults | Notification.DEFAULT_SOUND;
//		 notificacao.setDefaults(defaults);
//		 notificacao.setAutoCancel(true);
//		 gerenciadorNotificacao.notify(0, notificacao.build());
	 }
	 
	 
	 @Override
	 public IBinder onBind(Intent intent) {
		 // TODO Auto-generated method stub
		 return null;
	 }
	 
	 @Override
	 public void onCreate() {
		 // TODO Auto-generated method stub  
	 }
	 
	 @Override
	 public void onDestroy() {
		 // TODO Auto-generated method stub
		 super.onDestroy();
	 }
	 
	 
}
