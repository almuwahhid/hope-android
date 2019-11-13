package id.co.hope.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import uk.co.chrisjenx.calligraphy.CalligraphyUtils;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;


public class BaseToolbar extends Toolbar {
    public BaseToolbar(Context context) {
        super(context);
        setToolBarFont(context);
    }

    public BaseToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setToolBarFont(context);
    }

    public BaseToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setToolBarFont(context);
    }

    private void setToolBarFont(Context context) {
        for (int i = 0; i < this.getChildCount(); i++) {
            final View child = this.getChildAt(i);
            if (child instanceof TextView) {
                final TextView textView = (TextView) child;
                CalligraphyUtils.applyFontToTextView(textView, TypefaceUtils.load(context.getAssets(), "fonts/SanFransisco-Regular.otf"));
            }
        }
    }
}
