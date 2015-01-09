package com.android.step.bl;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.android.step.R;
import com.android.step.StepApplication;
import com.android.step.ui.MainActivity;

/**
 * Created by Geri on 2014.11.06..
 */
public class NotificationManager {

    private static android.app.NotificationManager mNotificationManager;
    private static final int mNotificationID = 2139;

    private String mNotificationHint, mNotificationDescription;
    private int mNotificationResId;

    private Context mContext;
    private boolean isVisible = false;

    private static NotificationManager mInstance = null;

    public static NotificationManager getInstance() {
        if (mInstance == null) {
            mInstance = new NotificationManager();
        }
        return mInstance;
    }

    private NotificationManager() {
        mContext = StepApplication.getContext();
    }

    public void showNotification(String hint, String description, int iconResId) {
        this.mNotificationHint = hint;
        this.mNotificationDescription = description;
        mNotificationResId = iconResId;

        Notification not = new Notification(mNotificationResId, mNotificationHint, System.currentTimeMillis());
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
        not.flags = Notification.FLAG_ONGOING_EVENT;

        String app_name = mContext.getString(R.string.app_name);
        not.setLatestEventInfo(mContext, app_name, mNotificationDescription, contentIntent);
        mNotificationManager = (android.app.NotificationManager)mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
        mNotificationManager.notify(mNotificationID, not);
        isVisible = true;
    }

    public void hideNotification() {
        if (isVisible) {
            mNotificationManager.cancel(mNotificationID);
            isVisible = false;
        }
    }
}
