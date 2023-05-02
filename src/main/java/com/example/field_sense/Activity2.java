package com.example.field_sense;


import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class Activity2 extends AppCompatActivity {

    private final static int REQUEST_CODE = 100;
    private TextView  textLatLong ;
    private ProgressBar progressBar;

    final static String ACCESS_FINE_LOCATION = "android.permission.ACCESS_FINE_LOCATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        textLatLong = findViewById(R.id.textLatLong);
        progressBar = findViewById(R.id.progressbar);
        findViewById(R.id.get_location_btn).setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Activity2.this, new String[]{ACCESS_FINE_LOCATION}, REQUEST_CODE);
            } else {
                getCurrentLocation();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == REQUEST_CODE) {
            getCurrentLocation();

        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void getCurrentLocation() {
        progressBar.setVisibility(View.VISIBLE);
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(Activity2.this).requestLocationUpdates(locationRequest, new LocationCallback() {

            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                LocationServices.getFusedLocationProviderClient(Activity2.this).removeLocationUpdates(this);
                Locationhelper helper = new Locationhelper(
                        locationResult.getLastLocation().getLatitude(),
                        locationResult.getLastLocation().getLongitude()
                );
                FirebaseDatabase.getInstance().getReference("LOCATION").setValue(helper).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(Activity2.this, "Location Saved", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(Activity2.this, "Location Not saved", Toast.LENGTH_SHORT).show();
                    }
                });


                if (locationResult.getLocations().size()>0)
                {
                    int latestLocationIndex = locationResult.getLocations().size()-1;
                    double latitude =
                            locationResult.getLocations().get(latestLocationIndex).getLatitude();
                    double longitude =
                            locationResult.getLocations().get(latestLocationIndex).getLongitude();
                    textLatLong.setText(
                            String.format(
                                "Latitude : %s\n longitude : %s ",
                                    latitude,
                                    longitude
                            )
                    );

                }
                progressBar.setVisibility(View.GONE);
            }
        }, Looper.getMainLooper());
}
}
