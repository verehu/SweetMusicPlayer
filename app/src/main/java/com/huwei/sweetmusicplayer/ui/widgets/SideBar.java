package com.huwei.sweetmusicplayer.ui.widgets;

import com.huwei.sweetmusicplayer.R;
import com.huwei.sweetmusicplayer.ui.listeners.OnTouchingLetterChangedListener;

 
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class SideBar extends View {
	 // 触摸事件  
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;  
    // 26个字母  
    public static String[] abc = { "A", "B", "C", "D", "E", "F", "G", "H", "I",  
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",  
            "W", "X", "Y", "Z", "#" };  
    private int choose = -1;// 选中  
    private Paint paint = new Paint();  
  
    private TextView mTextDialog;

    /** 
     * 为SideBar设置显示字母的TextView 
     * @param mTextDialog 
     */  
    public void setTextView(TextView mTextDialog) {  
        this.mTextDialog = mTextDialog;  
    } 
	
	public SideBar(Context context) {
		this(context,null);
		// TODO Auto-generated constructor stub
	}
	
	public SideBar(Context context, AttributeSet attrs) {
		this(context, attrs,0);
		// TODO Auto-generated constructor stub
	}
	
	public SideBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		int height=getHeight();
		int width=getWidth();
		int singleHeight=height/abc.length;
		
		Paint paint=new Paint();
		
		for(int i=0;i<abc.length;i++){
			paint.setColor(Color.rgb(33, 65, 98));  	//设置画笔颜色
			paint.setTypeface(Typeface.DEFAULT_BOLD);	//设置加粗
			
			paint.setAntiAlias(true);
			paint.setTextSize(20);
			
			if(i==choose){
				paint.setColor(Color.parseColor("#3399ff"));  
                paint.setFakeBoldText(true);  
			}
			
			//计算坐标
			float x=width/2-paint.measureText(abc[i])/2;
			float y=(i+1)*singleHeight;
			
			canvas.drawText(abc[i], x, y, paint);
		}
	 
		//重置画笔
		paint.reset();
	}

	   /** 
     * 向外公开的方法 
     *  
     * @param onTouchingLetterChangedListener 
     */  
    public void setOnTouchingLetterChangedListener(  
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {  
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;  
    }

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		float y=event.getY();
		int pos=(int) ((y/getHeight())*abc.length);
		final int oldchoose=choose;
		
		switch(event.getAction()){
		case MotionEvent.ACTION_UP:
			setBackgroundDrawable(new ColorDrawable(0x00000000));
			choose=-1;
			
			invalidate();
			if(mTextDialog!=null){
				mTextDialog.setVisibility(View.INVISIBLE);
			}
			break;
		default:
			 
			setBackgroundResource(R.drawable.sidebar_background);
			
			if(oldchoose!=pos){
				if(pos>=0&&pos<abc.length){
					if(onTouchingLetterChangedListener!=null){
						onTouchingLetterChangedListener.onTouchingLetrerChanged(abc[pos]);
					}
					
					if(mTextDialog!=null){
						mTextDialog.setText(abc[pos]);
						mTextDialog.setVisibility(View.VISIBLE);
					}
				}
				
				choose=pos;
				invalidate();
			}
			break;
		}
		
		return true;
	} 



}
