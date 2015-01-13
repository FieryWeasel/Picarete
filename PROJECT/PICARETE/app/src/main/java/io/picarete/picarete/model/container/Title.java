package io.picarete.picarete.model.container;

import java.util.List;

/**
 * Created by iem on 13/01/15.
 */
public class Title {
    public String title = "";
    public List<Condition> conditions = null;

    public Title(String title, List<Condition> conditions) {
        this.title = title;
        this.conditions = conditions;
    }

    public boolean isUnlocked(){
        boolean isUnlocked = true;

        if(conditions != null){
            for (Condition c : conditions){
                if (!c.isConditionValidated())
                    isUnlocked = false;
            }
        }

        return isUnlocked;
    }
}
