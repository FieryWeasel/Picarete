package io.picarete.picarete.model;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by iem on 12/01/15.
 */
public class CustomFontButton extends Button {

    public CustomFontButton(Context context) {
        super(context);
        Typeface myTypeface = Typeface.createFromAsset(context.getAssets(), "trebuchetms.ttf");
        setTypeface(myTypeface);
    }

    public CustomFontButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface myTypeface = Typeface.createFromAsset(context.getAssets(), "trebuchetms.ttf");
        setTypeface(myTypeface);
    }

    public CustomFontButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface myTypeface = Typeface.createFromAsset(context.getAssets(), "trebuchetms.ttf");
        setTypeface(myTypeface);
    }
}
