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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Requesting extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();
    public LocationTrack locationTrack;
    public double longitude;
    public double latitude;
    public FirebaseDatabase mDatabase;
    public DatabaseReference myref;
    public DatabaseReference myref1;
    public  ValueEventListener mValueEventListener;
    private final static int ALL_PERMISSIONS_RESULT = 101;
    public  Counter c;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requesting);
        EditText edt = findViewById(R.id.editText);
       // FirebaseApp.initializeApp(Requesting.this);

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


             longitude = locationTrack.getLongitude();
             latitude = locationTrack.getLatitude();

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
                String number = "89911";
                if ( ContextCompat.checkSelfPermission(Requesting.this, CALL_PHONE)== PackageManager.PERMISSION_GRANTED) {
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

                 mDatabase = FirebaseDatabase.getInstance("https://narcansos-default-rtdb.firebaseio.com/");
                 myref = mDatabase.getReference();

                //선언


//초기화
                 mValueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                         c  = postSnapshot.getValue(Counter.class);
                         Log.e("CNT",String.valueOf( c.getCnt()));
                            Loc loc = new Loc(longitude,latitude);

                            myref.child("requests").child(String.valueOf(c.getCnt()+1)).child("location").setValue(loc.print());

                            myref.child("cnt").setValue(c.getCnt()+1);
                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                myref1 = mDatabase.getReference("cnt");
                myref1.addValueEventListener(new ValueEventListener(){
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                            c  = postSnapshot.getValue(Counter.class);
                            Log.e("CNT",String.valueOf( c.getCnt()));
                            Loc loc = new Loc(longitude,latitude);

                            myref.child("requests").child(String.valueOf(c.getCnt()+1)).child("location").setValue(loc.print());

                            myref.child("cnt").setValue(c.getCnt()+1);
                        }
                    }//onDataChange
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }//onCancelled
                });//addValueEventListener
;

// Add a new document with a generated ID
                /*
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
                        });

                 */

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
