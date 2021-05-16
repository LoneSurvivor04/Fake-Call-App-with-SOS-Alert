package com.example.app;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity2 extends AppCompatActivity {

    Button b1,b2,sos,tb,cn,sch;
    DatabaseHelper DB;
    double longitude,latitude;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0;
    LocationTrack gpsTracker;
    LocationTrack locationTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        b1=findViewById(R.id.b1);
        b2=findViewById(R.id.sch);
        sos=findViewById(R.id.sosbut);
        tb=findViewById(R.id.trust);
        cn=findViewById(R.id.callnow);
        sch=findViewById(R.id.sch);

        DB = new DatabaseHelper(this);


        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, CallerInfo.class);
                startActivity(intent);
            }
        });

        tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, TrustedContacts.class);
                startActivity(intent);
            }
        });

        sch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, Schedule.class);
                startActivity(intent);
            }
        });

        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

        cn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent callEvent = new Intent(MainActivity2.this,Caller.class);

                startActivity(callEvent);
            }

        });
    }

    public void getLocation(){
        gpsTracker = new LocationTrack(MainActivity2.this);
        if(gpsTracker.canGetLocation()){
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
            msgperm();;
        }else{
            gpsTracker.showSettingsAlert();
        }
    }


    private void msgperm()
    {
        if (ContextCompat.checkSelfPermission(this,
            Manifest.permission.SEND_SMS)
            != PackageManager.PERMISSION_GRANTED) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.SEND_SMS)) {

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
                }
        }
        Cursor res = DB.sndsos();
        SmsManager sms=SmsManager.getDefault();
        if(res.getCount()==0){
            Toast.makeText(MainActivity2.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
            return;
        }
        while(res.moveToNext()){

            sms.sendTextMessage(res.getString(1),null,"Alert Im in danger\nhttps://maps.google.com/?q="+Double.toString(latitude)+","+Double.toString(longitude),null,null);
            Toast.makeText(MainActivity2.this, "Alert Message Sent", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationTrack.stopUsingGPS();
    }

    @Override
    public boolean onKeyDown( int keyCode, KeyEvent event ) {
        if( keyCode == KeyEvent.KEYCODE_VOLUME_DOWN ) {
            event.startTracking();
            return true;
        }
        return super.onKeyDown( keyCode, event );
    }

    @Override
    public boolean onKeyLongPress( int keyCode, KeyEvent event ) {
        if( keyCode == KeyEvent.KEYCODE_VOLUME_DOWN ) {
            getLocation();
            return true;
        }
        return super.onKeyLongPress( keyCode, event );
    }

    @Override
    public void onBackPressed (){
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }
}