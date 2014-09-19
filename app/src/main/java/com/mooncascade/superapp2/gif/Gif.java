package com.mooncascade.superapp2.gif;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by toomas on 18.09.2014.
 */
public class Gif {

    public static void  encodeGifAndSaveToSdCard(List<Bitmap> bitmaps) {
        saveGifToFile( encodeGif( bitmaps ) , "/sdcard/" + generateFileName() );
    }

    public static byte[] encodeGif(List<Bitmap> bitmaps) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        AnimatedGifEncoder encoder = new AnimatedGifEncoder();
        encoder.start(bos);
        for (Bitmap bitmap : bitmaps) {
            encoder.addFrame(bitmap);
        }
        encoder.finish();
        return bos.toByteArray();
    }

    private static void saveGifToFile(byte[] gif, String path) {
        FileOutputStream outStream = null;
        try{
            outStream = new FileOutputStream(path);
            outStream.write(gif);
            outStream.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static String generateFileName() {
        return "gif_" + System.currentTimeMillis() + ".gif";
    }
}
