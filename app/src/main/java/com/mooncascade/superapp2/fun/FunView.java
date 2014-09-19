package com.mooncascade.superapp2.fun;

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
import com.mooncascade.superapp2.network_stuffs.Uploader;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Created by toomas on 18.09.2014.
 */
public class FunView extends View implements BitmapCameraFeed.FrameReceiver {

    public FunView(Context context) {
        super(context);
        init();
    }

    public FunView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FunView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    BitmapCameraFeed cameraFeed;

    private void init() {
        cameraFeed = new BitmapCameraFeed(getContext(), this);

        renderBitmap = Bitmap.createBitmap(
                CameraFeed.PIC_WIDTH, CameraFeed.PIC_HEIGHT,
                Bitmap.Config.ARGB_4444);

        renderCanvas = new Canvas(renderBitmap);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        cameraFeed.startCamera();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cameraFeed.stopCamera();
    }

    Bitmap renderBitmap;
    Canvas renderCanvas;
    ArrayList<Bitmap> bitmaps;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            bitmaps = new ArrayList<Bitmap>();
        } else if (event.getAction() == MotionEvent.ACTION_CANCEL
                || event.getAction() == MotionEvent.ACTION_UP) {

            Uploader.upload( new ByteArrayInputStream( Gif.encodeGif(bitmaps) ) );

            bitmaps = null;
        }

        return true;
    }

    @Override
    public void onFrame(Bitmap frame) {
        if (bitmaps != null) {
            bitmaps.add(frame);
        }


        Paint paint = new Paint();
        paint.setAlpha(50);
        renderCanvas.drawBitmap(frame, 0, 0, paint);

        paint.setColor(ColorFun.randomColor());
        paint.setAlpha(50);

        renderCanvas.drawRect(0, 0, CameraFeed.PIC_WIDTH, CameraFeed.PIC_HEIGHT, paint);

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            Paint paint = new Paint();
       //     paint.setAlpha(1);
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
