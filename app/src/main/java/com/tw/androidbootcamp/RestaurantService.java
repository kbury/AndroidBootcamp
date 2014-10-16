package com.tw.androidbootcamp;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class RestaurantService extends Service {
    public RestaurantService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this
                .getApplicationContext());

        if(intent==null)
            return super.onStartCommand(intent, flags, startId);

        final int appWidgetId = intent
                .getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,0);

        final Context context = this.getApplicationContext();

        Timer time = new Timer("update");

        final Intent intentTimer = new Intent(context, MainActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentTimer, PendingIntent.FLAG_UPDATE_CURRENT);

        intentTimer.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intentTimer.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                appWidgetId);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Date date = new Date();
                CharSequence widgetText = "Hello World";
                Long time = new Long(date.getTime() );
                // Construct the RemoteViews object
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.restaurants_widget);
                views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
                views.setTextViewText(R.id.appwidget_text, time.toString());

                // Instruct the widget manager to update the widget
                appWidgetManager.updateAppWidget(appWidgetId, views);
            }
        };

        time.scheduleAtFixedRate(task, 0, 10);

        stopSelf();
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
