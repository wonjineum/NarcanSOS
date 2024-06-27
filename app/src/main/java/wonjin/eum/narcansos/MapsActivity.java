package wonjin.eum.narcansos;
import static android.Manifest.permission.POST_NOTIFICATIONS;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

import wonjin.eum.narcansos.databinding.ActivityMapsBinding;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    // Declare the launcher at the top of your Activity/Fragment:
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                } else {
                    // TODO: Inform user that that your app will not show notifications.
                }
            });

    private void askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(POST_NOTIFICATIONS);
            }
        }
    }


    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private GoogleMap googleMap;
    private MarkerOptions options = new MarkerOptions();
    private ArrayList<LatLng> latlngs = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
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

 //       FirebaseMessaging.getInstance().setAutoInitEnabled(true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng first = new LatLng(36.5972, 121.8978);
        mMap.addMarker(new MarkerOptions().position(first).title("First"));
        LatLng second = new LatLng(121.8944, 36.5972 );
        mMap.addMarker(new MarkerOptions().position(second).title("second"));
        LatLng third = new LatLng(121.8879, 36.5984 );
        mMap.addMarker(new MarkerOptions().position(third).title("third"));



/*
*    1. 36.5972° N, 121.8978° W
   2. 36.5972° N, 121.8944° W
   3. 36.5984° N, 121.8879° W
* */

        mMap.moveCamera(CameraUpdateFactory.newLatLng(first));
        mMap.setOnMarkerClickListener(this);
    }

    public boolean onMarkerClick(Marker marker){
        Toast.makeText(this, marker.getTitle() + "\n" + marker.getPosition(), Toast.LENGTH_LONG).show();
        Intent it = new Intent(MapsActivity.this, Supplying.class);
        String lat = String.valueOf(marker.getPosition().latitude);
        String log = String.valueOf(marker.getPosition().longitude);
        it.putExtra("curloclat",lat);
        it.putExtra("curloclog",log);
        startActivity(it);
        return true;

    }
}