package wonjin.eum.narcansos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationCompat.Builder mBilder =
                new NotificationCompat.Builder(MainActivity.this, "0")
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("Request!")
                        .setContentText("new request!")
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true)
                ;

        NotificationManager mnm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mnm.notify(0, mBilder.build());

        Button mapbtn = findViewById(R.id.button_to_map);
        mapbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(it);
            }
        });
        Button reqbtn = findViewById(R.id.button_to_request1);
        reqbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, Requesting.class);
                startActivity(it);
            }
        });
        TextView cont = findViewById(R.id.contact);

    }
}