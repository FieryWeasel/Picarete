package io.picarete.picarete.model;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by iem on 12/01/15.
 */
public class CustomFontTextView extends TextView {

    public CustomFontTextView(Context context) {
        super(context);
        Typeface myTypeface = Typeface.createFromAsset(context.getAssets(), "trebuchetms.ttf");
        setTypeface(myTypeface);
    }

    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface myTypeface = Typeface.createFromAsset(context.getAssets(), "trebuchetms.ttf");
        setTypeface(myTypeface);
    }

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface myTypeface = Typeface.createFromAsset(context.getAssets(), "trebuchetms.ttf");
        setTypeface(myTypeface);
    }


}
