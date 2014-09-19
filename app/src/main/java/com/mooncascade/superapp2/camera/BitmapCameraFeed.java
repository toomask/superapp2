package com.mooncascade.superapp2.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;

import java.io.ByteArrayOutputStream;

/**
 * Created by toomas on 18.09.2014.
 */
public class BitmapCameraFeed implements CameraFeed.FrameReceiver {

    CameraFeed cameraFeed;
    FrameReceiver frameReceiver;

    public interface FrameReceiver {
        public void onFrame(Bitmap frame);
    }

    public BitmapCameraFeed(Context context, FrameReceiver frameReceiver) {
        this.frameReceiver = frameReceiver;
        cameraFeed = new CameraFeed(context, this);
    }

    public void startCamera() {
        cameraFeed.startCamera();
    }

    public void stopCamera() {
        cameraFeed.stopCamera();
    }

    @Override
    public void onFrame(byte[] bytes, Camera camera) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        YuvImage yuvImage = new YuvImage(bytes, camera.getParameters().getPreviewFormat(),
                CameraFeed.PIC_WIDTH, CameraFeed.PIC_HEIGHT, null);
        yuvImage.compressToJpeg(new Rect(0, 0, CameraFeed.PIC_WIDTH, CameraFeed.PIC_HEIGHT), 95, out);
        byte[] imageBytes = out.toByteArray();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        frameReceiver.onFrame(bitmap);
    }
}
