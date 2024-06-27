package wonjin.eum.narcansos;

import static android.Manifest.permission.CALL_PHONE;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Supplying extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supplying);

        String lat = getIntent().getStringExtra("curloclat");
        String log = getIntent().getStringExtra("curloclog");

        TextView tv = findViewById(R.id.editText);
        tv.setText(" location:  "+lat+","+log);

        Button call = findViewById(R.id.button_help);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "911";
                if (ContextCompat.checkSelfPermission(Supplying.this, CALL_PHONE)== PackageManager.PERMISSION_GRANTED) {
                    Intent it = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                    startActivity(it);
                } else {

                    Log.e("CALL", "call");
                    ActivityCompat.requestPermissions(
                            Supplying.this,
                            new String[]{CALL_PHONE},
                            123);
                }
            }
        });

    }
}
