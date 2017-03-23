package it.unibo.studio.savini.gianni.lab04es2;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private final static int REQUEST_CODE = 101;

    private TextView txvProvider;
    private TextView txvLatitude;
    private TextView txvLongitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txvProvider = (TextView) findViewById(R.id.txv_provider);
        txvLatitude = (TextView) findViewById(R.id.txv_latitude);
        txvLongitude = (TextView) findViewById(R.id.txv_longitude);

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            register();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }

    }

    private void register() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //Per fare ciò serve un permesso particolare
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, MainActivity.this);
    }

    @Override
    public void onLocationChanged(Location location) {
        //Il warning è dato dalla gestione non efficace delle stringhe
        txvProvider.setText("Provider: " + location.getProvider());
        txvLatitude.setText(String.format("Latitudine: %f", location.getLatitude()));
        txvLongitude.setText(String.format("Longitudine: %f", location.getLongitude()));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(MainActivity.this, provider + "abilitato", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(MainActivity.this, provider + "disabilitato", Toast.LENGTH_SHORT).show();
    }
}
