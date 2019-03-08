package com.mtp.Activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mtp.MainActivity;
import com.mtp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.mtp.MainActivity.hideSoftKeyboard;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.editText_password)
    EditText et;

    @Override
//    protected void onCreate(Bundle savedInstanceState) {
    protected void setupUI() {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//        ButterKnife.bind(RegisterActivity.this);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Close keyboard on background click
        ConstraintLayout constrainLayout = findViewById(R.id.backgroundWallpaper);
        constrainLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                hideSoftKeyboard(RegisterActivity.this);
            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_register;
    }


//    @OnClick(R.id.bt_signUp)
//    public void signUp(View v){
//        Toast.makeText(getApplicationContext(), "signUp", Toast.LENGTH_SHORT).show();
//    }

}
