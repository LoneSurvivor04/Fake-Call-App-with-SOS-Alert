package com.example.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class Caller extends AppCompatActivity {

    public MediaPlayer player;
    ImageButton b1;
    ImageButton b2;
    TextView b3,b4;
    DatabaseHelper2 DB1;
    Button b5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caller);

        DB1 = new DatabaseHelper2(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);

        b1 = findViewById(R.id.rcv);
        b2 = findViewById(R.id.rjt);
        b3 = findViewById(R.id.ccname);
        b4 = findViewById(R.id.cpno);
        b5 = findViewById(R.id.reply);

        if(DatabaseHelper2.ct.getString("key")==null && DatabaseHelper2.nm.getString("key")==null )
        {
            b3.setText("Dad");
            b4.setText("9241613821");
        }
        else
        {
            b3.setText(DatabaseHelper2.nm.getString("key"));
            b4.setText(DatabaseHelper2.ct.getString("lock"));
        }

        player  = MediaPlayer.create(Caller.this, Settings.System.DEFAULT_RINGTONE_URI);
        player.setLooping(true);
        player.start();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.stop();
                finish();
                Intent acceptCall = new Intent(Caller.this, Dial.class);
                startActivity(acceptCall);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.stop();
                Intent intent1 = new Intent(Caller.this, MainActivity2.class);
                startActivity(intent1);
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.stop();
                Intent intent1 = new Intent(Caller.this, MainActivity2.class);
                startActivity(intent1);

                Uri sms_uri = Uri.parse("smsto:"+b4.getText());
                Intent sms_intent = new Intent(Intent.ACTION_SENDTO, sms_uri);
                sms_intent.putExtra("sms_body", "");
                startActivity(sms_intent);
            }
        });
    }
}

