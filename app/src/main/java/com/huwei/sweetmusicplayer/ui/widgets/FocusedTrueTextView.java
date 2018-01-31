package com.huwei.sweetmusicplayer.ui.widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;


/**
 * 默认返回Focused 为True的TextView
 * @author Jayce
 * @date 2015/6/12
 */
public class FocusedTrueTextView extends AppCompatTextView {

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
