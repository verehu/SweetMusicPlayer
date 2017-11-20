package com.huwei.sweetmusicplayer.business.ui.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author jayce
 * @date 2015/1/23
 */
public class GradientTextProgress extends TextView {
    private String mText;
    private int maxValue;
    private int curValue;
    private Paint bgPaint,paint;
    private int mWidth, mHeight, baseline;
    private int bgColorInt= Color.BLACK;
    private int proColorInt= Color.BLUE;
    
    private LinearGradient lg;
    private boolean hasGradient;
    private int[] color;
    private float[] position;
    private Shader.TileMode mode;

    public GradientTextProgress(Context context) {
        this(context, null);
    }

    public GradientTextProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientTextProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();

        mText = getText().toString();
    }

    private void initPaint() {
        bgPaint = getPaint();
        bgPaint.setAntiAlias(true);
        bgPaint.setTextSize(getTextSize());
        
        paint=new Paint();
        paint.set(bgPaint);
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
        invalidate();
    }

    public int getProgress() {
        return curValue;
    }

    public void setLinearGradient(int color[],float position[],Shader.TileMode mode) {
        hasGradient=true;
        this.color=color;
        this.position=position;
        this.mode=mode;
    }

    public void setProgress(int curValue) {
        this.curValue = curValue;
        invalidate();
    }
    

    public void setProColorInt(int proColorInt) {
        this.proColorInt = proColorInt;
    }

    public void setBgColorInt(int bgColorInt) {
        this.bgColorInt = bgColorInt;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        baseline =  (mHeight - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        
 
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (null == mText || "".equals(mText)) return;


        bgPaint.setColor(bgColorInt);
        canvas.drawText(mText, 0, baseline, bgPaint);


        float section = (float) curValue / maxValue;
        Rect proRect = new Rect(0, 0, (int) (section * mWidth), mHeight);
        if(!hasGradient){
            paint.setColor(proColorInt);
        }else{
            lg=new LinearGradient(0,0,(int) (section * mWidth),mHeight,color,position, mode);
            paint.setShader(lg);
        }
        
        canvas.save();
        canvas.clipRect(proRect);
        canvas.drawText(mText, 0, baseline, paint);

        canvas.restore();
    }
}
