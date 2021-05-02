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

    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();
    private final static int ALL_PERMISSIONS_RESULT = 101;
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

        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);
        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (permissionsToRequest.size() > 0)
                requestPermissions((String[]) permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
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
                location();
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

    private void location()
    {
        int permChk= ContextCompat.checkSelfPermission(MainActivity2.this, Manifest.permission.SEND_SMS);

        if(permChk== PackageManager.PERMISSION_GRANTED)
        {
            locationTrack = new LocationTrack(MainActivity2.this);
            if (locationTrack.canGetLocation()) {
                longitude = locationTrack.getLongitude();
                latitude = locationTrack.getLatitude();

            } else {

                locationTrack.showSettingsAlert();
            }
            msgperm(latitude,longitude);
        }
        else
        {
            ActivityCompat.requestPermissions(MainActivity2.this,new String[]{Manifest.permission.SEND_SMS},0);
        }
    }


    private void msgperm(Double latitude,Double longitude)
    {
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


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (Object perms : permissionsToRequest) {
                    if (!hasPermission((String) perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale((String) permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions((String[]) permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity2.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    @Override
    public void onBackPressed (){
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationTrack.stopListener();
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
            location();
            return true;
        }
        return super.onKeyLongPress( keyCode, event );
    }
}