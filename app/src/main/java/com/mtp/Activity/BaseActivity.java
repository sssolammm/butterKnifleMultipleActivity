package com.mtp.Activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mtp.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        ButterKnife.bind(this);

        setupUI();
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    //Nomes es fa servir per els fragments

//    @Override
//    protected  void onDestroy() {
//        super.onDestroy();
//        unbinder.unbind();
//    }

    protected abstract int getLayoutResource();


    //Si es fa el create en aquesta clase no cal fer el create a  cada activity
    protected abstract void setupUI();
}
