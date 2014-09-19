package com.mooncascade.superapp2.activity;

import android.app.Activity;
import android.os.Bundle;

import com.mooncascade.superapp2.R;
import com.mooncascade.superapp2.gcm.GcmRegistrator;

/**
 * Created by toomas on 18.09.2014.
 */
public class FunAcitivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fun);
        GcmRegistrator.register(this);
    }
}
