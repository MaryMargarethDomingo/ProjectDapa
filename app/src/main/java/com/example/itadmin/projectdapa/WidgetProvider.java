package com.example.itadmin.projectdapa;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Michael on 19 Apr 2018.
 */

public class WidgetProvider extends AppWidgetProvider {

    private static final String onClickTag = "emergencyCallButton";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.appwidget);
        ComponentName thisWidget = new ComponentName(context, WidgetProvider.class);
        remoteViews.setOnClickPendingIntent(R.id.emergencyButton,
                getPendingSelfIntent(context, onClickTag));

        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.w("Widget", "Clicked button1 outside");
        Log.w("Widget", "Action: " + intent.getAction());
        Log.w("Widget", "Tag: " + onClickTag);
        if (onClickTag.equals(intent.getAction())){
            Log.w("Widget", "Clicked button1");
            Toast.makeText(context, "onclick called", Toast.LENGTH_LONG).show();
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:911"));

            if (ActivityCompat.checkSelfPermission(MainActivity.getContextOfApplication(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            context.startActivity(callIntent);
        }
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }
}
