/*
 * //
 * //                       _oo0oo_
 * //                      o8888888o
 * //                      88" . "88
 * //                      (| -_- |)
 * //                      0\  =  /0
 * //                    ___/`---'\___
 * //                  .' \\|     |// '.
 * //                 / \\|||  :  |||// \
 * //                / _||||| -:- |||||- \
 * //               |   | \\\  -  /// |   |
 * //               | \_|  ''\---/''  |_/ |
 * //               \  .-\__  '-'  ___/-. /
 * //             ___'. .'  /--.--\  `. .'___
 * //          ."" '<  `.___\_<|>_/___.' >' "".
 * //         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 * //         \  \ `_.   \_ __\ /__ _/   .-` /  /
 * //     =====`-.____`.___ \_____/___.-`___.-'=====
 * //                       `=---='
 * //
 * //
 * //     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * //
 * //               佛祖保佑         永无BUG
 * //
 */

package com.kutear.app.netutils;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.text.Html.ImageGetter;
import android.widget.TextView;

import com.kutear.app.R;
import com.kutear.app.utils.DeviceInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class UrlImageParser implements ImageGetter {
    Context c;
    TextView container;
    ArrayList<UrlDrawable> drawables = new ArrayList<>();

    /***
     * Construct the URLImageParser which will execute AsyncTask and refresh the container
     *
     * @param t
     * @param c
     */
    public UrlImageParser(TextView t, Context c) {
        this.c = c;
        this.container = t;
    }

    /**
     * 在 {@link Activity#onDestroy()}或{@link Fragment#onDestroy()}中释放
     */
    public void destoryDrawable() {
        for (UrlDrawable drawable : drawables) {
            Bitmap bmp = drawable.getBitmap();
            if (bmp != null && bmp.isRecycled()) {
                bmp.recycle();
            }
        }
    }

    public Drawable getDrawable(String source) {
        UrlDrawable urlDrawable = new UrlDrawable();
        drawables.add(urlDrawable);
        // get the actual source
        ImageGetterAsyncTask asyncTask =
                new ImageGetterAsyncTask(urlDrawable);

        asyncTask.execute(source);

        // return reference to URLDrawable where I will change with actual image from
        // the src tag
        return urlDrawable;
    }

    public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable> {
        UrlDrawable urlDrawable;

        public ImageGetterAsyncTask(UrlDrawable d) {
            this.urlDrawable = d;
        }

        @Override
        protected Drawable doInBackground(String... params) {
            String source = params[0];
            return fetchDrawable(source);
        }

        @Override
        protected void onPostExecute(Drawable result) {
            if (result == null) {
                Snackbar.make(container, R.string.failed_to_load_image, Snackbar.LENGTH_SHORT).show();
                return;
            }
            float multiplier = (float) (DeviceInfo.getScreenWidth(c) * 0.8) / (float) result.getIntrinsicWidth();
            int width = (int) (result.getIntrinsicWidth() * multiplier);
            int height = (int) (result.getIntrinsicHeight() * multiplier);
            urlDrawable.setBounds((int) (DeviceInfo.getScreenWidth(c) * 0.1), 0, width, height);
            // set the correct bound according to the result from HTTP call
//            urlDrawable.setBounds(0, 0, result.getIntrinsicWidth(), result.getIntrinsicHeight());
            // change the reference of the current drawable to the result
            // from the HTTP call
            urlDrawable.drawable = result;

            // redraw the image by invalidating the container
//            UrlImageParser.this.container.invalidate();

            UrlImageParser.this.container.setHeight((UrlImageParser.this.container.getHeight()
                    + height));

            // Pre ICS
            UrlImageParser.this.container.setEllipsize(null);
        }

        /***
         * Get the Drawable from URL
         *
         * @param urlString
         * @return
         */
        public Drawable fetchDrawable(String urlString) {
            try {
                InputStream is = fetch(urlString);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                Drawable drawable = Drawable.createFromResourceStream(null, null, is, "src", options);
                float multiplier = (float) (DeviceInfo.getScreenWidth(c) * 0.8) / (float) drawable.getIntrinsicWidth();
                int width = (int) (drawable.getIntrinsicWidth() * multiplier);
                int height = (int) (drawable.getIntrinsicHeight() * multiplier);
                drawable.setBounds((int) (DeviceInfo.getScreenWidth(c) * 0.1), 0, width, height);
                return drawable;
            } catch (Exception e) {
                return null;
            }

        }

        private InputStream fetch(String urlString) throws MalformedURLException, IOException {
//            DefaultHttpClient httpClient = new DefaultHttpClient();
//            HttpGet request = new HttpGet(urlString);
//            HttpResponse response = httpClient.execute(request);
//            return response.getEntity().getContent();
            URL http_url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) http_url.openConnection();
            connection.setConnectTimeout(5 * 1000);//设置连接超时
            if (connection.getResponseCode() == 200) {
                return connection.getInputStream();//得到网络返回的输入流
            }
            return null;
        }
    }

    @SuppressWarnings("deprecation")
    public class UrlDrawable extends BitmapDrawable {
        // the drawable that you need to set, you could set the initial drawing
        // with the loading image if you need to
        protected Drawable drawable = c.getResources().getDrawable(R.drawable.kutear_logo);

        @Override
        public void draw(Canvas canvas) {
            // override the draw to facilitate refresh function later
            if (drawable != null) {
                drawable.draw(canvas);
            }
        }
    }
}

