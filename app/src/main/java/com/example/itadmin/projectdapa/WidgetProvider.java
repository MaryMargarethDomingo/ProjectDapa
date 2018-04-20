package com.example.itadmin.projectdapa;

import android.app.PendingIntent;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Created by Michael on 19 Apr 2018.
 */

public class WidgetProvider extends AppWidgetProvider {

    // Retrieve the widget’s layout//
    //RemoteViews views = new RemoteViews(MainActivity.getContextOfApplication().getPackageName(), R.layout.appwidget);
    private static final String onClickTag = "onClickTag";

    @Override
    public void onReceive(Context context, Intent intent) {

    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }
}
