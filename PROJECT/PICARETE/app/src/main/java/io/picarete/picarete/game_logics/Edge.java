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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;

        Edge edge = (Edge) o;

        if (idPlayer != edge.idPlayer) return false;
        if (isChosen != edge.isChosen) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPlayer;
        result = 31 * result + (isChosen ? 1 : 0);
        return result;
    }
}
