package wonjin.eum.narcansos;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Requesting extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();
    LocationTrack locationTrack;


    private final static int ALL_PERMISSIONS_RESULT = 101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requesting);
        EditText edt = findViewById(R.id.editText);


        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions((String[]) permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }

        locationTrack = new LocationTrack(Requesting.this);


        if (locationTrack.canGetLocation()) {


            double longitude = locationTrack.getLongitude();
            double latitude = locationTrack.getLatitude();

            Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
            edt.setText("location:  \n"+Double.toString(longitude)+" , "+Double.toString(latitude));
        } else {

            locationTrack.showSettingsAlert();
        }

        Button todb = findViewById(R.id.button_help);
        Button call = findViewById(R.id.button_help2);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "911";
                if (ContextCompat.checkSelfPermission(Requesting.this, CALL_PHONE)== PackageManager.PERMISSION_GRANTED) {
                    Intent it = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                    startActivity(it);
                } else {

                    Log.e("CALL", "call");
                    ActivityCompat.requestPermissions(
                            Requesting.this,
                            new String[]{CALL_PHONE},
                            123);
                }
            }
        });

        /*
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Toast.makeText(Requesting.this, "Location: "+location.toString(),Toast.LENGTH_LONG ).show();
                        Log.e("LOC: ", location.toString());
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            Log.e("LOC: ", "NO");
                        }
                    }
                });


         */

        todb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  FirebaseApp.initializeApp(Requesting.this);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> user = new HashMap<>();
                user.put("first", "Ada");
                user.put("last", "Lovelace");
                user.put("born", 1815);

// Add a new document with a generated ID
                db.collection("location")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("good: ", "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("bad: ", "Error adding document", e);
                            }
                        });*/

            }
        });
    }


    private ArrayList findUnAskedPermissions(ArrayList wanted) {
        ArrayList result = new ArrayList();

        for (Object perm : wanted) {
            if (!hasPermission((String) perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }
}
