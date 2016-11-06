package es.computerwizards.notification;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

public class Localizacion implements LocationListener {
    MainActivity mainActivity;

    public void setMainActivity (MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    @Override
    public void onLocationChanged(Location location) {
        location.getLatitude();
        location.getLongitude();

        String Text = "Mi Localizacion actual es: " + "\n Lat = " + location.getLatitude()
                + "\n Lomg = " + location.getLongitude();
        mainActivity.messageTextView.setText(Text);
        this.mainActivity.setLocation(location);
    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(mainActivity, "GPS Desactivado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String s) {
        Toast.makeText(mainActivity, "GPS Activado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

}
