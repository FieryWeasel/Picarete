package io.picarete.picarete.model.container.tutorial;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import io.picarete.picarete.model.SGM.SGMTutorial.SGMAResult;

/**
 * Created by Simon on 23/02/2015.
 */
public class ResultDisplayMsg extends SGMAResult {
    TextView tv = null;
    String msg = null;

    public ResultDisplayMsg(TextView tv, String msg){
        this.tv = tv;
        this.msg = msg;
    }

    @Override
    public void sendAction(Bundle info) {
        tv.setText(msg);
    }
}
