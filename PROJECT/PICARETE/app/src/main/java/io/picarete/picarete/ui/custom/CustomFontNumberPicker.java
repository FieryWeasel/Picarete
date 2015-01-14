package io.picarete.picarete.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.NumberPicker;

/**
 * Created by iem on 12/01/15.
 */
public class CustomFontNumberPicker extends NumberPicker{

    //TODO Font
    private Context mContext;

    public CustomFontNumberPicker(Context context) {
        super(context);
        this.mContext = context;
        setNumberPickerTextFont(context);
    }

    public CustomFontNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setNumberPickerTextFont(context);
    }

    public CustomFontNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        setNumberPickerTextFont(context);
    }

    public boolean setNumberPickerTextFont(Context context)
    {
        if(isInEditMode()){
            Typeface myTypeface = Typeface.createFromAsset(context.getAssets(), "trebuchetms.ttf");

            final int count = getChildCount();
            for(int i = 0; i < count; i++){
                View child = getChildAt(i);
                if(child instanceof EditText){
                    try{
                        ((EditText)child).setTypeface(myTypeface);
                        invalidate();
                        return true;
                    } catch(IllegalArgumentException e){
                        Log.w("setNumberPickerTextColor", e);
                    }
                }
            }
        }
        return false;
    }
}
