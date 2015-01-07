package io.picarete.picarete.game_logics;

/**
 * Created by root on 1/6/15.
 */

public class Edge {
    private int idPlayer = -1;
    private boolean isChosen = false;

    public boolean isChosen() {
        return isChosen;
    }

    public void setChosen(boolean isChosen) {
        this.isChosen = isChosen;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }
}
