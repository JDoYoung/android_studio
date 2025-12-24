package com.example.chap_10;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class ResultActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        setTitle("C089059_정도영 투표 결과");

        Intent intent = getIntent();
        int[] voteResult = intent.getIntArrayExtra("VoteCount");
        String[] imageName = intent.getStringArrayExtra("ImageName");

        TextView tv[] = new TextView[imageName.length];
        RatingBar rbar[] = new RatingBar[imageName.length];

        Integer tvID[] = {R.id.tv1,R.id.tv2,R.id.tv3,
                R.id.tv4,R.id.tv5,R.id.tv6,
                R.id.tv7,R.id.tv8,R.id.tv9};
        Integer rbarID[] = {R.id.rbar1,R.id.rbar2,R.id.rbar3,
                R.id.rbar4,R.id.rbar5,R.id.rbar6,
                R.id.rbar7,R.id.rbar8,R.id.rbar9};

        for (int x = 0; x < voteResult.length;x++){
            tv[x] = (TextView) findViewById(tvID[x]);
            rbar[x] = (RatingBar) findViewById(rbarID[x]);
        }

        for (int x=0;x< voteResult.length;x++){
            tv[x].setText(imageName[x]);
            rbar[x].setRating((float) voteResult[x]);
        }

        Button btnReturn = (Button) findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
