package com.example.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CallerInfo extends AppCompatActivity {

    Button b3;
    EditText b4;
    EditText b5;
    DatabaseHelper2 DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caller_info);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);

        b3=(Button)findViewById(R.id.save);
        b4=(EditText)findViewById(R.id.cname);
        b5=(EditText)findViewById(R.id.phno);
        DB = new DatabaseHelper2(this);

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cn = b4.getText().toString();
                String ccontact = b5.getText().toString();

                if (!b5.getText().toString().isEmpty()) {
                    Boolean check = DB.insertcdata(cn, ccontact);
                    if(check ==true)
                        Toast.makeText(CallerInfo.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(CallerInfo.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(CallerInfo.this, "Please fill the number field atleast", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}