package wonjin.eum.narcansos;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import wonjin.eum.narcansos.databinding.ActivityMapsBinding;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private GoogleMap googleMap;
    private MarkerOptions options = new MarkerOptions();
    private ArrayList<LatLng> latlngs = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Button helpBtn = findViewById(R.id.button_to_request);
        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MapsActivity.this, Requesting.class);
                startActivity(it);
            }
        });

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        latlngs.add(new LatLng(12.334343, 33.43434)); //some latitude and logitude value
//        latlngs.add(new LatLng(12.356343, 33.56644));
//
//        for (LatLng point : latlngs) {
//            options.position(point);
//            options.title("someTitle");
//            options.snippet("someDesc");
//            googleMap.addMarker(options);
//        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(12.356343, 33.56644);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

        /*
           1. 36.5972° N, 121.8978° W
   2. 36.5972° N, 121.8944° W
   3. 36.5984° N, 121.8879° W
         */
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMarkerClickListener(this);
    }

    public boolean onMarkerClick(Marker marker){
        Toast.makeText(this, marker.getTitle() + "\n" + marker.getPosition(), Toast.LENGTH_LONG).show();
        return true;

    }
}