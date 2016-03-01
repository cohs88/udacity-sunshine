package com.example.sunshine2;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by servando on 2/29/2016.
 */
public class MyView extends View {
    public MyView(Context context)
    {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defaultStyle)
    {
        super(context, attrs, defaultStyle);
    }

    protected void onMeasure(int wMeasureSpec, int hMeasureSpec)
    {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
