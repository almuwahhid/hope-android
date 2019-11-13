package id.co.hope.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import id.co.hope.R;
import id.co.hope.app.main.MainActivity;


public class NotificationUtil {
    private Context context;
    private static NotificationUtil instance;
    private PendingIntent pendingIntent;
    NotificationManager notificationManager;
    NotificationCompat.Builder notificationBuilder;

    public NotificationUtil(Context context) {
        this.context = context;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void cancelNotif(int id){
        notificationManager.cancel(id);
    }


    public void showPersonChatNotif(){
        String NOTIFICATION_CHANNEL_ID = "112";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            // Configure the notification channel.
            notificationChannel.setDescription("RIDE");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(ContextCompat.getColor(context, R.color.colorPrimary));
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(R.drawable.logo_24)
                .setTicker("RIDE")
                //     .setPriority(Notification.PRIORITY_MAX)
                .setContentTitle("Pengingat Tugas")
                .setContentText("Ada tugas yang harus kamu kerjakan, lihat sekarang")
                .setContentInfo("REDE")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Ada tugas yang harus kamu kerjakan, lihat sekarang"));

        String replyLabel = "Enter your reply here";

        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 112, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(112,
                notificationBuilder.build());
    }
}
