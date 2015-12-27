package com.jd.app.android.hotspot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.RemoteViews;

/**
 * HotspotIntentReceiver
 * <p/>
 * Created by Jayshil Dave
 * 25/09/15
 * <p/>
 */
public class HotspotIntentReceiver extends BroadcastReceiver {

    private static final long UI_REFERSH_WAIT_TIME = 1500;

    @Override
    public void onReceive(final Context context, Intent intent) {
        if(intent.getAction().equals("jd.app.android.hotspot.intent.action.CHANGE_HOTSPOT")){
            ApManager.configApState(context);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateUI(context);
                }
            }, UI_REFERSH_WAIT_TIME);
        }
    }

    private void updateUI(Context context) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_hotspot_switch);
        boolean isApOn = ApManager.isApOn(context);
        String text = context.getString(R.string.hotspot_on);
        if (isApOn) {
            text  = context.getString(R.string.hotspot_off);
        }
        remoteViews.setCharSequence(R.id.hotspot_switch, "setText", text);
        //REMEMBER TO ALWAYS REFRESH YOUR BUTTON CLICK LISTENERS!!!
        remoteViews.setOnClickPendingIntent(R.id.hotspot_switch, HotspotWidgetProvider.buildButtonPendingIntent(context));

        HotspotWidgetProvider.pushWidgetUpdate(context.getApplicationContext(), remoteViews);
    }
}