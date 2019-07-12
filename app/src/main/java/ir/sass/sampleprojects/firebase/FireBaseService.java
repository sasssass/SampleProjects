package ir.sass.sampleprojects.firebase;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FireBaseService extends FirebaseMessagingService
{
    public final String TAG = "ALISASS FireBaseService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        super.onMessageReceived(remoteMessage);
        Log.i(TAG,"notif comes -> "+remoteMessage.toString());

        SQLiteDatabaseHandler db = new SQLiteDatabaseHandler(getBaseContext());
        db.addNotif(new MessageFireBase(
                "null",
                remoteMessage.getData().get("title"),
                remoteMessage.getData().get("body")));
    }
}
