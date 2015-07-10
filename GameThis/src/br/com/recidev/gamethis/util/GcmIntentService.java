/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.recidev.gamethis.util;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import br.com.recidev.gamethis.R;
import br.com.recidev.gamethis.dominio.Usuario;
import br.com.recidev.gamethis.ui.HomeActivity;

import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * This {@code IntentService} does the actual handling of the GCM message.
 * {@code GcmBroadcastReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class GcmIntentService extends IntentService {

    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    public GcmIntentService() {
        super("GcmIntentService");
    }
    public static final String TAG = "GCM Demo";

    @Override
    protected void onHandleIntent(Intent intent) {

        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM will be
             * extended in the future with new message types, just ignore any message types you're
             * not interested in, or that you don't recognize.
             */
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                sendNotification("Send error: " + extras.toString(), "", null);
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                sendNotification("Deleted messages on server: " + extras.toString(), "", null);
            // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
            	String tipoNotificacao = intent.getStringExtra("collapse_key");
            	if(tipoNotificacao != null && !tipoNotificacao.equals("")){
            		
            		if(tipoNotificacao.equals("Novo Usuario")){
            			sendNotification("Novo usu�rio criado com sucesso!", tipoNotificacao, extras);
            		}
            		
            		if(tipoNotificacao.equals("Novo Jogo")){
            			sendNotification("Voc� foi adicionado em um Novo Jogo!", tipoNotificacao, extras);
            		}
            		
            	}
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(String msg, String tipoNotificacao, Bundle extras) {

        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = null;
        if(tipoNotificacao.equals("Novo Usuario")){
        	Usuario usuario = new Usuario();
        	usuario.setNome(extras.getString("nome"));
        	usuario.setEmail(extras.getString("email"));
        	usuario.setSenha(extras.getString("senha"));
        	usuario.setAvatar(Integer.parseInt(extras.getString("avatar")));
        	usuario.setNome(extras.getString("syncStatus"));
        	usuario.setGcm_id(extras.getString("gcm_id"));
        	
        	contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, HomeActivity.class), 0);
        }
        

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.game_this_2)
        		.setContentTitle("GameThis")
        		.setStyle(new NotificationCompat.BigTextStyle()
        		.bigText(msg))
        		.setContentText(msg);
        mBuilder.setContentIntent(contentIntent);
        Uri sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.toasty);
        mBuilder.setSound(sound);
        
		 int defaults = 0;
		 defaults = defaults | Notification.DEFAULT_LIGHTS;
		 defaults = defaults | Notification.DEFAULT_VIBRATE;
		 //defaults = defaults | Notification.DEFAULT_SOUND;
		 mBuilder.setDefaults(defaults);
		 mBuilder.setAutoCancel(true);
        
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());        
    }
}
