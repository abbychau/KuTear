package com.kutear.app.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.kutear.app.utils.L;

/**
 * Created by kutear.guo on 2015/10/28.
 */
public class CustomTextView extends TextView {
    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint mPaint1 = new Paint();
        Paint mPaint2 = new Paint();
        mPaint1.setColor(Color.BLUE);
        mPaint2.setColor(Color.BLACK);
        canvas.drawLine(0, 10, 1000, 10, mPaint1);
        canvas.save();
        canvas.translate(100,100);
        canvas.drawLine(0+10, 10+10, 1000+10, 10+10, mPaint2);
        canvas.restore();
        canvas.drawLine(0, 10, 1000, 10, mPaint1);
        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        L.v("TAG","function onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
