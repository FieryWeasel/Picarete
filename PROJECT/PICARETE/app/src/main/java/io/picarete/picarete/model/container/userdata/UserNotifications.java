package io.picarete.picarete.model.container.userdata;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

import io.picarete.picarete.R;
import io.picarete.picarete.model.container.ColorCustom;

/**
 * Created by iem on 16/01/15.
 */
public class UserNotifications implements User.UserEventListener {
    public static int counter = 0;
    private Context context;

    public UserNotifications(Context context){
        this.context = context;
    }

    @Override
    public void gainALevel(int level) {
        Notification n  = new Notification.Builder(context)
                .setContentTitle("You reach the level " + level)
                .setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(++counter, n);
    }

    @Override
    public void gainAGameMode() {
        Notification n  = new Notification.Builder(context)
                .setContentTitle("You unlock a new game mode")
                .setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(++counter, n);
    }

    @Override
    public void gainARow(int row) {
        Notification n  = new Notification.Builder(context)
                .setContentTitle("You unlock a row size")
                .setContentText(String.format("You can now play with %d rows", row))
                .setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(++counter, n);
    }

    @Override
    public void gainAColumn(int column) {
        Notification n  = new Notification.Builder(context)
                .setContentTitle("You unlock a column size")
                .setContentText(String.format("You can now play with %d columns", column))
                .setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(++counter, n);
    }

    @Override
    public void gainAColor(ColorCustom color) {
        Notification n  = new Notification.Builder(context)
                .setContentTitle("You unlock a new color")
                .setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(++counter, n);
    }

    @Override
    public void gainAnIA() {
        Notification n  = new Notification.Builder(context)
                .setContentTitle("You unlock a new difficulty level")
                .setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(++counter, n);
    }

    @Override
    public void gainATitle(String title) {
        Notification n  = new Notification.Builder(context)
                .setContentTitle("You unlock a new title")
                .setContentText(String.format("You have unlock the title %s, set it on your Profile page", title))
                .setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(++counter, n);
    }
}
