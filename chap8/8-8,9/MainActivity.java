package com.example.chap_8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnRead;
        final EditText edtRaw;
        btnRead = (Button) findViewById(R.id.btnRead);
        edtRaw = (EditText) findViewById(R.id.edtRaw);

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    InputStream inputS = getResources().openRawResource(R.raw.raw_test);
                    byte[] txt = new byte[inputS.available()];
                    inputS.read(txt);
                    edtRaw.setText(new String(txt));
                    inputS.close();
                } catch (IOException e) {}
            }
        });

    }
}