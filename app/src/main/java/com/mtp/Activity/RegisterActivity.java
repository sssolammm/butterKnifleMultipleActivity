package com.mtp.Activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mtp.R;

import static com.mtp.MainActivity.hideSoftKeyboard;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Close keyboard on background click
        ConstraintLayout constrainLayout= (ConstraintLayout) findViewById(R.id.backgroundWallpaper);
        constrainLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                hideSoftKeyboard(RegisterActivity.this);
            }
        });
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }


}
