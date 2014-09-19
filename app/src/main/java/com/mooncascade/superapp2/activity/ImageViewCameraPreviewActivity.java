package com.mooncascade.superapp2.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.mooncascade.superapp2.R;
import com.mooncascade.superapp2.camera.BitmapCameraFeed;


public class ImageViewCameraPreviewActivity extends Activity implements BitmapCameraFeed.FrameReceiver{

    ImageView imageView;
    BitmapCameraFeed cameraFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view_camera_preview);
        imageView = (ImageView) findViewById(R.id.imageView);
        cameraFeed = new BitmapCameraFeed(this, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraFeed.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraFeed.stopCamera();
    }

    @Override
    public void onFrame(Bitmap frame) {
        imageView.setImageBitmap(frame);
    }
}
