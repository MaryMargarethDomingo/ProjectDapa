package com.example.itadmin.projectdapa.session.utility;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import com.example.itadmin.projectdapa.MainActivity;
import com.example.itadmin.projectdapa.R;
import com.example.itadmin.projectdapa.session.controller.ProfileFragment;

public class NotificationService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        Intent intent1 = new Intent(context, MainActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,100, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        //get weather data
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        if(preferences.getBoolean("willRain", false)){
            Notification.Builder builder = new Notification.Builder(context).
                    setSmallIcon(R.mipmap.ic_launcher).
                    setContentIntent(pendingIntent).
                    setContentText("Hi " + ProfileFragment.user.getDisplayName().split(" ")[0] + "! Looks like there will be no rain today! Stay Safe!").
                    setContentTitle("Project DAPA").
                    setStyle(new Notification.BigTextStyle().bigText("Hi " + ProfileFragment.user.getDisplayName().split(" ")[0] + "! Looks like there will be no rain today! Stay Safe!")).
                    setSound(alarmSound).setAutoCancel(true);

            notificationManager.notify(100,builder.build());
        }else{
            Notification.Builder builder = new Notification.Builder(context).
                    setSmallIcon(R.mipmap.ic_launcher).
                    setContentIntent(pendingIntent).
                    setContentText("Hi " + ProfileFragment.user.getDisplayName().split(" ")[0] + "! Looks like there will be " + preferences.getString("rainString", "rain") + " today. Stay safe!").
                    setContentTitle("Project DAPA").
                    setStyle(new Notification.BigTextStyle().bigText("Hi " + ProfileFragment.user.getDisplayName().split(" ")[0] + "! Looks like there will be " + preferences.getString("rainString", "rain") + " today. Stay safe!")).
                    setSound(alarmSound).setAutoCancel(true).setShowWhen(true);

            notificationManager.notify(100,builder.build());
        }
    }
}
