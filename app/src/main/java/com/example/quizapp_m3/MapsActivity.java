package com.example.quizapp_m3;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.quizapp_m3.Model.LocationHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    Location currentLocation;
    Button btnSuivantPlay;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE=612; // l'identifiant de l'appel de l'autorisation

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        btnSuivantPlay =findViewById(R.id.btnSuivantPlay);
     //Fournisseur d'emplacement fusionné pour récupérer le dernier emplacement connu de l'appareil
        // API de localisation des services Google Play
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLocation();
        // clicker suivant
        btnSuivantPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationHelper helper = new LocationHelper(
                        FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                        currentLocation.getLatitude(),
                        currentLocation.getLongitude()
                );
// enregister dans la base de données
                  FirebaseDatabase.getInstance().getReference("Current Location")
                        .setValue(helper).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Location Saved !", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Location Not Saved", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                Intent intent = new Intent(MapsActivity.this, Playing.class);

                startActivity(intent);

            }
        });
    }

    private void fetchLocation() {
        // Vérifier est ce que l'application est autorisé à acceder a la localisation de l'appareil
        if(ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&ActivityCompat.checkSelfPermission(
                this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
         // Demande d'autorisation , un pop up s'affiche pour accepter ou refuser la demande d'autorisation
         // le résultat de cette demande est renvoyé à la méthode onRequestPermissionResult qui se chargera de la suite
         ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
         return;
        }
        // Autorisation déja accordée, on obtient le dernier emplacement
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    String msg = "Location : "+Double.toString(currentLocation.getLatitude())+" , "
                            +Double.toString(currentLocation.getLongitude());


                //Afficher la lattitude et la longitude avec un Toast
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                assert supportMapFragment != null;
                supportMapFragment.getMapAsync(MapsActivity.this);


            }
            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        // créér un objet latLng qui stocke la latitude et la longitude de la localisation actuelle
        LatLng latLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());

        // Ajouter un marker de la localisation actuelle dans la carte
       MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Vous êtes là !");
       googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
       googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
       googleMap.addMarker(markerOptions);

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

           @Override
           public boolean onMarkerClick(Marker marker) {

               return false;
           }

       });
     //   Toast.makeText(this,"Localisation saved",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            // Dans le cas lorsque vous cliquez sur le bouton autoriser du pop up,
            // il y aura un deuxiee appel de la methode
            // fetchLocation() pour obtenir la derniere localisation
            case REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    fetchLocation();
                }
                break;
        }

    }

}
