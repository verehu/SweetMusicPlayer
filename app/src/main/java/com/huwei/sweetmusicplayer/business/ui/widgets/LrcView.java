package com.huwei.sweetmusicplayer.business.ui.widgets;


import java.util.List;


//import com.huwei.sweetmusicplayer.business.datamanager.MusicManager;
import com.huwei.sweetmusicplayer.R;
import com.huwei.sweetmusicplayer.contains.ILrcStateContain;
import com.huwei.sweetmusicplayer.business.core.MusicManager;
import com.huwei.sweetmusicplayer.business.models.LrcContent;
import com.huwei.sweetmusicplayer.business.ui.listeners.OnLrcSearchClickListener;
import com.huwei.sweetmusicplayer.util.DisplayUtil;
import com.huwei.sweetmusicplayer.util.TimeUtil;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.ScrollView;
import android.widget.TextView;
import android.view.View.OnTouchListener;

public class LrcView extends ScrollView implements OnScrollChangedListener, OnTouchListener, ILrcStateContain {
    private float width;    //歌词视图宽度
    private float height;    //歌词视图高度
    private Paint currentPaint;        //当前画笔对象
    private Paint notCurrentPaint;        //非当前画笔对象
    private Paint tipsPaint;  //提示信息画笔

    private float lightTextSize;        //高亮文本大小
    private float norTextSize;        //非高亮文本大小
    private float tipsTextSize;    //提示文本大小
    private float textHeight;    //文本高度
    private int index;    //歌词list集合下标

    private int lrcState = -1;
    private LrcTextView lrcTextView;
    private List<LrcContent> lrcLists;

    private int scrollY;
    private boolean canDrawLine = false;
    private int pos = -1; //手指按下后歌词要到的位置
    private Paint linePaint;

    private boolean canTouchLrc = true;        //是否可以触摸并调整歌词


    private int count = 0;  //绘制加载点的次数

    private Context mContext;

    private OnLrcSearchClickListener onLrcSearchClickListener;

    public LrcView(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public LrcView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODO Auto-generated constructor stub
    }

    public LrcView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub

        mContext = context;

        this.setOnTouchListener(this);

        init();
    }

    public void setOnLrcSearchClickListener(OnLrcSearchClickListener onLrcSearchClickListener) {
        this.onLrcSearchClickListener = onLrcSearchClickListener;
    }

    public List<LrcContent> getLrcLists() {
        return lrcLists;
    }

    public void setLrcLists(List<LrcContent> lrcLists) {
        this.lrcLists = lrcLists;

        //判断歌词界面是否可以触摸
//		if(lrcLists==null||lrcLists.size()==0)	canTouchLrc=false;
//		else canTouchLrc=true;
        //设置index=-1
        this.index = -1;

        LayoutParams params1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lrcTextView = new LrcTextView(this.getContext());
        lrcTextView.setLayoutParams(params1);


        this.removeAllViews();
        this.addView(lrcTextView);
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        //歌曲位置发生变化,而且手指不是调整歌词位置的状态
        if (this.index != index && pos == -1) {
            this.scrollTo(0, (int) (index * textHeight));
        }

        this.index = index;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // TODO Auto-generated method stub
        super.onSizeChanged(w, h, oldw, oldh);

        this.width = w;
        this.height = h;
    }

    public int getIndexByLrcTime(int currentTime) {
        if (lrcLists == null) {
            return 0;
        }

        for (int i = 0; i < lrcLists.size(); i++) {
            if (currentTime < lrcLists.get(i).getLrcTime()) {
                return i - 1;
            }
        }
        return lrcLists.size() - 1;
    }

    public void clear() {
        lrcLists = null;
    }


    public void setLrcState(int lrcState) {
        this.lrcState = lrcState;
        invalidate();
    }


    class LrcTextView extends TextView {


        public LrcTextView(Context context) {
            this(context, null);
            // TODO Auto-generated constructor stub
        }

        public LrcTextView(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
            // TODO Auto-generated constructor stub
        }

        public LrcTextView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            // TODO Auto-generated constructor stub
            this.setWillNotDraw(false);
        }

        //绘制歌词
        @Override
        protected void onDraw(Canvas canvas) {
            // TODO Auto-generated method stub
            super.onDraw(canvas);

            Log.i("LrcTextView onDraw", "LrcTextView onDraw");

            if (canvas == null) return;

            int tempY = (int) height / 2;


            switch (lrcState) {
                case READ_LOC_FAIL:
                    tipsPaint.setUnderlineText(true);
                    canvas.drawText("暂无歌词,点击开始搜索", width / 2, tempY, tipsPaint);
                    break;
                case QUERY_ONLINE:
                    tipsPaint.setUnderlineText(false);
                    String drawContentStr = "在线匹配歌词";
                    for (int i = 0; i < count; i++) {
                        drawContentStr += ".";
                    }

                    count++;
                    if (count >= 6) count = 0;

                    canvas.drawText(drawContentStr, width / 2, tempY, tipsPaint);

                    handler.sendEmptyMessageDelayed(1, 500);
                    break;
                case QUERY_ONLINE_OK:
                case READ_LOC_OK:
                    //绘制歌词
                    for (int i = 0; i < lrcLists.size(); i++, tempY += textHeight) {
                        if (i == index) {
                            canvas.drawText(lrcLists.get(i).getLrcStr(), width / 2, tempY, currentPaint);
                        } else if (i == pos) {
                            canvas.drawText(lrcLists.get(i).getLrcStr(), width / 2, tempY, linePaint);
                        } else {
                            canvas.drawText(lrcLists.get(i).getLrcStr(), width / 2, tempY, notCurrentPaint);
                        }
                    }
                    break;
                case QUERY_ONLINE_FAIL:
                    tipsPaint.setUnderlineText(true);
                    canvas.drawText("搜索失败，请重试", width / 2, tempY, tipsPaint);
                    break;
                case QUERY_ONLINE_NULL:
                    tipsPaint.setUnderlineText(false);
                    canvas.drawText("网络无匹配歌词", width / 2, tempY, tipsPaint);
                    break;
            }

//		  	if(MusicManager.OperateState.READLRC_LISTNULL.equals(lrcState)){
//		    	canvas.drawText("歌词内容为空", width/2, tempY, notCurrentPaint);
//		    	return;
//		    }else if(MusicManager.OperateState.READLRCFILE_FAIL.equals(lrcState)){
//		    	canvas.drawText("暂无歌词", width/2, tempY, notCurrentPaint);
//		    	return;
//		    }
//		    else if(MusicManager.OperateState.READLRC_SUCCESS.equals(lrcState)){
//
//			    //绘制歌词
//			    for(int i=0;i<lrcLists.size();i++,tempY+=textHeight){
//			    	if(i==index){
//			    		canvas.drawText(lrcLists.get(i).getLrcStr(), width/2, tempY, currentPaint);
//			    	}else if(i==pos){
//			    		canvas.drawText(lrcLists.get(i).getLrcStr(), width/2, tempY, linePaint);
//			    	}else{
//			    		canvas.drawText(lrcLists.get(i).getLrcStr(), width/2, tempY, notCurrentPaint);
//			    	}
//			    }
//
//		    	return;
//		    }else if(MusicManager.OperateState.READLRC_ONLINE.equals(lrcState)){
//		    	String drawContentStr="在线匹配歌词";
//
//		    	for(int i=0;i<count;i++){
//		    		drawContentStr+=".";
//		    	}
//
//		    	count++;
//		    	if(count>=6) count=0;
//
//		    	canvas.drawText(drawContentStr, width/2, tempY, notCurrentPaint);
//
//
//		    	handler.sendEmptyMessageDelayed(1, 500);
//		    	return;
//		    }else if(MusicManager.OperateState.READLRCONLINE_FAIL.equals(lrcState)){
//		    	canvas.drawText("从网络加载歌词失败", width/2, tempY, notCurrentPaint);
//		    	return;
//		    }
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            // TODO Auto-generated method stub
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);


            heightMeasureSpec = (int) (height + textHeight * (lrcLists.size() - 1));
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        }


    }

    ;


    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        if (canDrawLine) {
            canvas.drawLine(0, scrollY + height / 2, width, scrollY + height / 2, linePaint);
            canvas.drawText(TimeUtil.mill2mmss(lrcLists.get(pos).getLrcTime()), 42, scrollY + height / 2 - 2, linePaint);
        }
    }

    private void init() {
        setFocusable(true);    //设置该控件可以有焦点
        this.setWillNotDraw(false);

        norTextSize = DisplayUtil.sp2px(mContext, 16);
        lightTextSize = DisplayUtil.sp2px(mContext, 18);
        tipsTextSize = DisplayUtil.sp2px(mContext, 20);
        textHeight = norTextSize+DisplayUtil.dip2px(mContext,10);

        //高亮歌词部分
        currentPaint = new Paint();
        currentPaint.setAntiAlias(true);    //设置抗锯齿
        currentPaint.setTextAlign(Paint.Align.CENTER);    //设置文本居中

        //非高亮歌词部分
        notCurrentPaint = new Paint();
        notCurrentPaint.setAntiAlias(true);
        notCurrentPaint.setTextAlign(Paint.Align.CENTER);

        //提示信息画笔
        tipsPaint = new Paint();
        tipsPaint.setAntiAlias(true);
        tipsPaint.setTextAlign(Paint.Align.CENTER);

        //
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setTextAlign(Paint.Align.CENTER);

        //设置画笔颜色
        currentPaint.setColor(getResources().getColor(R.color.primary));
        notCurrentPaint.setColor(Color.argb(140, 255, 255, 255));
        tipsPaint.setColor(Color.WHITE);
        linePaint.setColor(Color.RED);


        //设置字体
        currentPaint.setTextSize(lightTextSize);
        currentPaint.setTypeface(Typeface.SERIF);

        notCurrentPaint.setTextSize(norTextSize);
        notCurrentPaint.setTypeface(Typeface.DEFAULT);

        tipsPaint.setTextSize(tipsTextSize);
        tipsPaint.setTypeface(Typeface.DEFAULT);

        linePaint.setTextSize(lightTextSize);
        linePaint.setTypeface(Typeface.SERIF);

    }

    @Override
    public void invalidate() {
        // TODO Auto-generated method stub
        super.invalidate();

        lrcTextView.invalidate();
    }

    @Override
    public void onScrollChanged() {
        // TODO Auto-generated method stub


    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub

        //界面不能被触摸
        if (!canTouchLrc) return true;

        switch (lrcState) {
            case READ_LOC_FAIL:
            case QUERY_ONLINE_FAIL:
                return handleTouchLrcFail(event.getAction());
            case READ_LOC_OK:
            case QUERY_ONLINE_OK:
                return handleTouchLrcOK(event.getAction());

        }


        return false;
    }

    boolean handleTouchLrcOK(int action) {

        switch (action) {
            case MotionEvent.ACTION_MOVE:
                scrollY = this.getScrollY();
                pos = (int) (this.getScrollY() / textHeight);

                canDrawLine = true;
                this.invalidate();

                Log.i("LrcStateView", "ACTION_DOWN");
                break;
            case MotionEvent.ACTION_UP:
                if(pos!=-1)
                    MusicManager.getInstance().seekTo(lrcLists.get(pos).getLrcTime());
                canDrawLine = false;
                pos =-1;
                this.invalidate();
                break;
        }
        return false;
    }

    boolean handleTouchLrcFail(int action) {
        switch (action) {
            case MotionEvent.ACTION_UP:
                if (onLrcSearchClickListener != null) {
                    onLrcSearchClickListener.onLrcSearchClicked(this);
                }
                break;
        }
        return true;
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            invalidate();
        }

    };
}	
