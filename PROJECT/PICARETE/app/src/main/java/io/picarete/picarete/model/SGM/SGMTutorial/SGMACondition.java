package io.picarete.picarete.model.SGM.SGMTutorial;

import android.os.AsyncTask;
import android.os.Bundle;

/**
 * Created by Simon on 17/02/2015.
 */
public abstract class SGMACondition<Params>{
    private boolean isValidate = false;

    public boolean isValidate() {
        return isValidate;
    }

    public void setValidate(boolean isValidate) {
        this.isValidate = isValidate;
    }

    public abstract void sendAction(Params... params);
}
