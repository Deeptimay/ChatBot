package com.example.chatbot.offlineSyncUtility;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class WorkManagerUtils {

    public static void startWorkManager() {
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(SyncWorker.class)
                .setConstraints(constraints())
                .build();
        WorkManager.getInstance().enqueue(oneTimeWorkRequest);
    }

    private static Constraints constraints() {
        return new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
    }
}
