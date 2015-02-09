package io.picarete.picarete.model.container.userdata;

import java.util.LinkedList;
import java.util.List;

import io.picarete.picarete.model.Constants;

/**
 * Created by iem on 13/01/15.
 */
public class Level {
    public int id;
    public List<AUnlock> unlocks;

    public Level(LinkedList<AUnlock> aUnlocks) {
        this.unlocks = aUnlocks;
    }

}
