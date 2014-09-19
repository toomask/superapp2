package com.mooncascade.superapp2.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.mooncascade.superapp2.camera.BitmapCameraFeed;
import com.mooncascade.superapp2.camera.CameraFeed;
import com.mooncascade.superapp2.gif.Gif;
import com.mooncascade.superapp2.util.ColorFun;
import com.mooncascade.superapp2.util.Uploader;

import java.util.ArrayList;

/**
 * Created by toomas on 18.09.2014.
 */
public class RecorderView extends View implements BitmapCameraFeed.FrameReceiver {

    public RecorderView(Context context) {
        super(context);
        init();
    }

    public RecorderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RecorderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    BitmapCameraFeed cameraFeed;

    Bitmap renderBitmap;
    Canvas renderCanvas;
    ArrayList<Bitmap> gifFrames;

    Bitmap overlayBitmap;
    Canvas overlayCanvas;

    private void init() {
        cameraFeed = new BitmapCameraFeed(getContext(), this);

        renderBitmap = Bitmap.createBitmap(
                CameraFeed.PIC_WIDTH, CameraFeed.PIC_HEIGHT,
                Bitmap.Config.ARGB_4444);
        renderCanvas = new Canvas(renderBitmap);

        overlayBitmap = Bitmap.createBitmap(
                CameraFeed.PIC_WIDTH, CameraFeed.PIC_HEIGHT,
                Bitmap.Config.ARGB_4444);
        overlayCanvas = new Canvas(overlayBitmap);
    }

    public void startCamera() {
        cameraFeed.startCamera();
    }

    public void stopCamera() {
        cameraFeed.stopCamera();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            gifFrames = new ArrayList<Bitmap>();
        } else if (event.getAction() == MotionEvent.ACTION_CANCEL
                || event.getAction() == MotionEvent.ACTION_UP) {
            encodeGifAndUpload();
            gifFrames = null;
        }

        Paint overlayPain = new Paint();
        overlayPain.setColor(ColorFun.randomColor());
        for (int i = 0; i < event.getPointerCount(); i++) {
            overlayCanvas.drawCircle(
                    calculateXPositionOnOverlayBitmap(event.getX(i)),
                    calculateYPositionOnOverlayBitmap(event.getY(i)),
                    20.0f,
                    overlayPain
            );
        }

        return true;
    }

    private float calculateXPositionOnOverlayBitmap(float motionEventX) {
        return motionEventX * CameraFeed.PIC_WIDTH / getWidth();
    }

    private float calculateYPositionOnOverlayBitmap(float motionEventY) {
        return motionEventY * CameraFeed.PIC_HEIGHT / getHeight();
    }

    private void encodeGifAndUpload() {
        Uploader.upload( Gif.encodeGif(gifFrames) );
    }

    @Override
    public void onFrame(Bitmap frame) {

        Paint paint = new Paint();
//        paint.setAlpha(200);
        renderCanvas.drawBitmap(frame, 0, 0, paint);

        renderCanvas.drawBitmap(overlayBitmap, 0, 0, paint);

        if (gifFrames != null) {
            gifFrames.add(renderBitmap.copy(Bitmap.Config.ARGB_4444, false));
        }

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            Paint paint = new Paint();

            canvas.drawBitmap(renderBitmap,
                    new Rect(0, 0, CameraFeed.PIC_WIDTH, CameraFeed.PIC_HEIGHT),
                    new Rect(0, 0, getWidth(), getHeight()),
                    paint);

        } catch (Exception e) {
            Paint paint = new Paint();
            paint.setColor(Color.parseColor("#CC0066"));
            canvas.drawText(e.toString(), 0, 0, paint);
        }
    }

}
