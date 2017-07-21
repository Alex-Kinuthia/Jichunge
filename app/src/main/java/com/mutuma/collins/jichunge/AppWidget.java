package com.mutuma.collins.jichunge;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link AppWidgetConfigureActivity AppWidgetConfigureActivity}
 */
public class AppWidget extends AppWidgetProvider implements View.OnClickListener {

    private static String TAG = AppWidget.class.getSimpleName();
    public static final String SEND_ALERT_MESSAGE = "SEND_ALERT_MESSAGE";

    TextView textView;

    private DatabaseReference databaseRef;
  //    private FirebaseDatabase database;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = AppWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
            Intent intent = new Intent(context, AppWidget.class);
            intent.setAction(SEND_ALERT_MESSAGE);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

            views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
            views.getClass();

            appWidgetManager.updateAppWidget(appWidgetId, views);
            //            call get contacts method
//            getContacts();
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(SEND_ALERT_MESSAGE)) {
//            getContacts();
            sendSms();
            Toast.makeText(context, "message sent", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            AppWidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created


    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private void getContacts() {
        // set the firebase reference
        databaseRef = FirebaseDatabase.getInstance().getReference("contacts");

        // seting a firebase listener to the contacts node
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // capture all the contacts from the firebase snapshot
                String cont1 = dataSnapshot.child("contact1").getValue().toString();
                String cont2 = dataSnapshot.child("contact2").getValue().toString();

//                Toast.makeText(AppWidget.class, ">>>>" + cont, Toast.LENGTH_SHORT).show();
                Log.d(TAG, ">>> 12222" + cont1);
                Log.d(TAG, ">>> 12222" + cont2);

                // call send sms method

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "HEEEEE");
        getContacts();
    }

    // a method to send the sms
    private void sendSms() {

        SmsManager manager = SmsManager.getDefault();
        String smsContext = "Help!!! I'm in danger.I am at Nairobi, Ngong Lane";
        String[] contacts = {"+254716007495", "+254701928677", "+254722212132"};
//                {"+254716007495", "+254701928677", "+254722212132",};

//        loop

        for (String num: contacts) {
            manager.sendTextMessage(num, null, smsContext, null, null);
        }
    }


}

