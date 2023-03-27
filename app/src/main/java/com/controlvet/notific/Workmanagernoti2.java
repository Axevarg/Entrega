package com.controlvet.notific;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Workmanagernoti2 extends Worker {
    public Workmanagernoti2(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    public static void GuardarNoti2(long duracion, Data data, String tag){
        OneTimeWorkRequest noti = new OneTimeWorkRequest.Builder(Workmanagernoti2.class).setInitialDelay(duracion, TimeUnit.MILLISECONDS).addTag(tag).setInputData(data).build();

        WorkManager instance = WorkManager.getInstance();
        instance.enqueue(noti);
    }
    @NonNull
    @Override
    public Result doWork() {

        String titulo = getInputData().getString("titulo");
        String detalle = getInputData().getString("detalle");
        int id = (int) getInputData().getLong("idnoti",0);

        oreo(titulo,detalle);

        return Result.success();
    }
    private void oreo(String t, String d){
        String id = "message";
        NotificationManager nm = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),id);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel nc = new NotificationChannel(id, "nuevo", NotificationManager.IMPORTANCE_HIGH);
            nc.setDescription("Notificacion FCM");
            nc.setShowBadge(true);
            assert nm != null;
            nm.createNotificationChannel(nc);
        }



        builder.setAutoCancel(true).setWhen(System.currentTimeMillis())
                .setContentTitle(t)
                .setTicker("Nueva Notificacion")

                .setContentText(d)

                .setContentInfo("nuevo");
        Random random = new Random();
        int idNotifify = random.nextInt(8000);
        assert nm != null;
        nm.notify(idNotifify,builder.build());
    }
}