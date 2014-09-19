package com.mooncascade.superapp2.util;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Uploader {

    public static void upload(byte[] bytes) {
        upload(new ByteArrayInputStream(bytes));
    }

    public static void upload(final InputStream inputStream) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                uploadBytesToServer(inputStream);
            }
        }).start();
    }

    static final String SERVER_URL = "http://x-mega.com/superapp2_server/send_gif.php";

    static final String end = "\r\n";
    static final String twoHyphens = "--";
    static final String boundary = "*****++++++************++++++++++++";

    private static void uploadBytesToServer(InputStream inputStream) {

        try {
            URL url = new URL(SERVER_URL);

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");

            /* setRequestProperty */
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+ boundary);

            DataOutputStream ds = new DataOutputStream(conn.getOutputStream());
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\"" + "fun.gif" +"\"" + end);
            ds.writeBytes(end);

            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = -1;
            while((length = inputStream.read(buffer)) != -1) {
                ds.write(buffer, 0, length);
            }
            ds.writeBytes(end);
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
            /* close streams */
            inputStream.close();
            ds.flush();
            ds.close();

            StringBuffer stringBuffer = new StringBuffer();
            InputStream is = conn.getInputStream();
            byte[] data = new byte[bufferSize];
            int leng = -1;
            while((leng = is.read(data)) != -1) {
                stringBuffer.append(new String(data, 0, leng));
            }
            String result = stringBuffer.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}