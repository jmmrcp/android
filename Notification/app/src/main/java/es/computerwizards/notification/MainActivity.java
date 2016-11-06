package es.computerwizards.notification;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    int numMessages = 0;
    TextView messageTextView;
    TextView messageTextView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageTextView = (TextView) findViewById(R.id.message_id);
        messageTextView2 = (TextView) findViewById(R.id.message_id2);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion localizacion = new Localizacion();
        localizacion.setMainActivity(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) localizacion);

        messageTextView.setText("LocationListener Agregado");
        messageTextView2.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_5:
                Toast.makeText(this, "5 Seconds Delay", Toast.LENGTH_SHORT).show();
                scheduleNotificacion(getNotification("5 Seconds Delay"), 5000);
                return true;
            case R.id.action_10:
                Toast.makeText(this, "10 Seconds Delay", Toast.LENGTH_SHORT).show();
                scheduleNotificacion(getNotification("10 Seconds Delay"), 10000);
                return true;
            case R.id.action_30:
                Toast.makeText(this, "30 Seconds Delay", Toast.LENGTH_SHORT).show();
                scheduleNotificacion(getNotification("30 Seconds Delay"), 30000);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void scheduleNotificacion(Notification notification, int delay) {

        Intent intent = new Intent(this, NotificacionPublisher.class);
        intent.putExtra(NotificacionPublisher.NOTIFICATION_ID, 1);
        intent.putExtra(NotificacionPublisher.NOTIFICATION, notification);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futuro = SystemClock.elapsedRealtime() + delay;

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futuro, pendingIntent);
    }

    private Notification getNotification(String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setContentTitle("Notificacion");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.notificacion);
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        builder.setNumber(++numMessages);
        builder.setSubText("Esto es un subtexto en la notificacion");

        return builder.build();
    }

    public void setLocation(Location location) {
        if (location.getLatitude() != 0.0 && location.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        location.getLatitude(), location.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address address = list.get(0);
                    messageTextView2.setText("Mi direccion es: \n" +
                            address.getAddressLine(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
