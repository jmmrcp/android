package es.computerwizards.gps2010;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

public class MiLocationListener implements LocationListener {
    @Override
    public void onLocationChanged(Location location) {
        location.getLatitude();
        location.getLongitude();

        String coordenadas = "Mis coordenadas son: " + "Latitud= " + location.getLatitude()
                + "Longitud= " + location.getLongitude();

        Toast.makeText(MainActivity.getAppContext(), coordenadas, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
        Toast.makeText(MainActivity.getAppContext(), "Gps Activo", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(MainActivity.getAppContext(), "Gps Inactivo", Toast.LENGTH_SHORT).show();
    }
}
