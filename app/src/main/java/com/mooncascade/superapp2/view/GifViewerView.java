package com.mooncascade.superapp2.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.mooncascade.superapp2.R;

/**
 * Created by toomas on 19.09.2014.
 */
public class GifViewerView extends FrameLayout {

    public static GifViewerView create(Context context) {
        GifViewerView mView = new GifViewerView(context);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.RIGHT | Gravity.TOP;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.addView(mView, params);
        return mView;
    }

    public GifViewerView(Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_gif_viewer, this, true);

        startReleaseTimer();
    }

    private void startReleaseTimer() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                release();
            }
        }, 5000);
    }

    public void release() {
        ((WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE)).removeView(this);
    }
}
