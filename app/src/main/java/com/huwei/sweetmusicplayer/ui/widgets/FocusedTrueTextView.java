package com.huwei.sweetmusicplayer.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;



/**
 * 默认返回Focused 为True的TextView
 * @author Jayce
 * @date 2015/6/12
 */
public class FocusedTrueTextView extends TextView {

    public FocusedTrueTextView(Context context) {
        super(context);
    }

    public FocusedTrueTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FocusedTrueTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
