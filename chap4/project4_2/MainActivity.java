package com.example.project4_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView title, text1, text2;
    CheckBox chkAgree;
    RadioGroup rGroup1;
    RadioButton rdoCpp, rdoC, rdoRust;
    Button btnOK;
    ImageView imgLang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("언어 사진 보기");
        title = (TextView) findViewById(R.id.Title1);
        text1 = (TextView) findViewById(R.id.Text1);
        chkAgree = (CheckBox) findViewById(R.id.ChkAgree);

        text2 = (TextView) findViewById(R.id.Text2);
        rGroup1 = (RadioGroup) findViewById(R.id.Rgroup1);
        rdoCpp = (RadioButton) findViewById(R.id.RdoCpp);
        rdoC = (RadioButton) findViewById(R.id.RdoC);
        rdoRust = (RadioButton) findViewById(R.id.RdoRust);

        btnOK = (Button) findViewById(R.id.BtnOK);
        imgLang = (ImageView) findViewById(R.id.ImgLang);

        chkAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (chkAgree.isChecked() == true) {
                    text2.setVisibility(View.VISIBLE);
                    rGroup1.setVisibility(View.VISIBLE);
                    btnOK.setVisibility(View.VISIBLE);
                    imgLang.setVisibility(View.VISIBLE);
                } else {
                    text2.setVisibility(View.INVISIBLE);
                    rGroup1.setVisibility(View.INVISIBLE);
                    btnOK.setVisibility(View.INVISIBLE);
                    imgLang.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkedRadio = rGroup1.getCheckedRadioButtonId();
                if (checkedRadio == R.id.RdoCpp)
                    imgLang.setImageResource(R.drawable.cpp);
                else if (checkedRadio == R.id.RdoC)
                    imgLang.setImageResource(R.drawable.c);
                else if (checkedRadio == R.id.RdoRust)
                    imgLang.setImageResource(R.drawable.rust);
                else
                    Toast.makeText(getApplicationContext(), "언어 먼저 선택하세요",
                            Toast.LENGTH_SHORT).show();
            }
        });
    }
}