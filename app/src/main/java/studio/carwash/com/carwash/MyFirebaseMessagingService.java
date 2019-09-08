package studio.carwash.com.carwash;

import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Dell on 8/18/2019.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService{

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("Logs", "From: " + remoteMessage.getFrom());
        String title = "Order Status";
        String body = "Your recent order status is changed.. click to see details.";
        String sound = "default";
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d("Logs", "Message data payload: " + remoteMessage.getData());
            title = remoteMessage.getData().get("title");
            body = remoteMessage.getData().get("body");
            sound = remoteMessage.getData().get("sound");
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d("Kigs", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        notifyUser(title,body,sound);
    }

    public void notifyUser(String from, String notification, String sound){
        MyNotificationManager manager = new MyNotificationManager(this);
        manager.showSmallNotification(from,notification,sound, new Intent(this, ConsumerActivity.class));

    }
}
