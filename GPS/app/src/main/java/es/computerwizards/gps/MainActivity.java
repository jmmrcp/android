package es.computerwizards.gps;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private TextView lblatitud;
    private TextView lbongitud;
    private ToggleButton btnActualizar;

    GoogleApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblatitud = (TextView) findViewById(R.id.lblatitud);
        lbongitud = (TextView) findViewById(R.id.lblongitud);
        btnActualizar = (ToggleButton) findViewById(R.id.btnActualizar);

        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }
    
    private void updateUI(Location location) {
        if (location != null) {
            lblatitud.setText("Latitud: " + String.valueOf(location.getLatitude()));
            lbongitud.setText("Longitud: " + String.valueOf(location.getLongitude()));
        } else {
            lblatitud.setText("Latitud: desconocida");
            lbongitud.setText("Longitud: Desconocida");
        }
    }

}
