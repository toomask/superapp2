package com.mooncascade.superapp2.activity;

import android.app.Activity;
import android.os.Bundle;

import com.mooncascade.superapp2.R;
import com.mooncascade.superapp2.gcm.GcmRegistrator;
import com.mooncascade.superapp2.view.RecorderView;

/**
 * Created by toomas on 18.09.2014.
 */
public class FunAcitivity extends Activity {

    RecorderView gifRecorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fun);
        gifRecorder = (RecorderView) findViewById(R.id.gif_recorder);
        GcmRegistrator.register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gifRecorder.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gifRecorder.stopCamera();
    }
}
