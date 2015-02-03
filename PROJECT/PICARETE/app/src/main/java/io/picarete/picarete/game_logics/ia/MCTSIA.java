package io.picarete.picarete.game_logics.ia;

import android.content.Context;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import io.picarete.picarete.game_logics.Game;
import io.picarete.picarete.game_logics.gameplay.ETileSide;
import io.picarete.picarete.game_logics.gameplay.Edge;
import io.picarete.picarete.game_logics.gameplay.Tile;
import io.picarete.picarete.model.NoDuplicatesList;

/**
 * Created by root on 1/7/15.
 */
public class MCTSIA extends AIA {

    @Override
    protected Edge findEdge(int height, int width, Game game, List<Edge> previousEdgesPlayed) {
        Edge bestEdge = null;

        Log.d(this.getName(), "MCTS");

        TreeNodeMCTS nodeRoot = new TreeNodeMCTS(previousEdgesPlayed.get(previousEdgesPlayed.size()-1).id);
        Game gameCopied = copyGame(game);
        for(int i = 0; i < 100000000; i++)
            nodeRoot.selectAction(gameCopied);

        TreeNodeMCTS bestNode = nodeRoot.getBestNode();
        for (Tile t : game.getTiles()){
            for (Edge e : t.getEdges().values()){
                if(e.id == bestNode.idEdge)
                    bestEdge = e;
            }
        }

        return bestEdge;
    }

    private Game copyGame(Game game) {
        Game gameCopied = new Game(game.context, game.gameMode, game.height, game.width, 0);

        gameCopied.scores.put(0, game.getScores().get(0));
        gameCopied.scores.put(1, game.getScores().get(1));

        for (Tile t : game.getTiles()){
            gameCopied.tiles.add(copyTile(t, game.getTiles(), gameCopied));
        }

        return gameCopied;
    }

    private Tile copyTile(Tile tile, List<Tile> tiles, Game game) {
        Edge edgeLeft = new Edge(tile.getEdges().get(ETileSide.LEFT).id);
        Edge edgeTop = new Edge(tile.getEdges().get(ETileSide.TOP).id);
        Edge edgeRight = new Edge(tile.getEdges().get(ETileSide.RIGHT).id);
        Edge edgeBottom = new Edge(tile.getEdges().get(ETileSide.BOTTOM).id);

        for(Tile t : tiles){
            for (Edge e : t.getEdges().values()){
                if(e.id == edgeLeft.id)
                    edgeLeft = e;
                if(e.id == edgeTop.id)
                    edgeTop = e;
                if(e.id == edgeRight.id)
                    edgeRight = e;
                if(e.id == edgeBottom.id)
                    edgeBottom = e;
            }
        }

        Tile tileCopied = new Tile(tile.id, edgeLeft, edgeTop, edgeRight, edgeBottom);
        tileCopied.setEventListener(game);

        return tileCopied;
    }

    public class TreeNodeMCTS {
        int idEdge = -1;
        List<TreeNodeMCTS> children = new NoDuplicatesList<>();
        Random r = new Random();
        double epsilon = 1e-6;

        double nVisits = 0, totValue = 0;

        public TreeNodeMCTS(int idEdge){
            this.idEdge = idEdge;
        }

        public int selectAction(Game game) {
            int score;
            if(this.isLeaf()){
                this.expand(game);
                TreeNodeMCTS e = selectEdgeToPlay();
                score = e.play(game);
            } else {
                TreeNodeMCTS e = selectBestEdge();
                score = e.selectAction(game);
            }

            updateStats(score);

            return score;
        }

        private int play(Game game) {
            int score = -1;

            game = playEdge(game, idEdge);

            if(!isFinish(game)){
                this.expand(game);
                TreeNodeMCTS e = selectEdgeToPlay();
                score = e.play(game);
            } else {
                int scoreP1 = game.getScores().get(0);
                int scoreP2 = game.getScores().get(1);

                if(scoreP1 >= scoreP2) score = 0;
                else score = 1;
            }

            updateStats(score);

            return score;
        }

        private Game playEdge(Game game, int idEdge){
            for (Tile t : game.getTiles()){
                for (Edge e : t.getEdges().values()){
                    if(e.id == idEdge){
                        t.onClick(e);
                        return game;
                    }
                }
            }

            return game;
        }

        public TreeNodeMCTS getBestNode(){
            double bestScore = -1;
            TreeNodeMCTS bestNode = null;

            for (TreeNodeMCTS n : children){
                if(totValue / nVisits > bestScore){
                    bestNode = n;
                    bestScore = totValue / nVisits;
                }
            }

            return bestNode;
        }

        private boolean isFinish(Game game) {
            boolean isFinished = true;

            for (Tile t : game.getTiles())
                if(!t.isComplete())
                    isFinished = false;

            return isFinished;
        }

        private void expand(Game game) {
            for (int i = 0; i < game.getTiles().size(); i++) {
                for (Edge e : game.getTiles().get(i).getEdges().values()){

                    if(!e.isChosen()){
                        boolean isAlready = false;
                        for (TreeNodeMCTS n : children){
                            if(n.idEdge == e.id )
                                isAlready = true;
                        }
                        if(!isAlready)
                            children.add(new TreeNodeMCTS(e.id));
                    }
                }
            }
        }

        private void updateStats(double value) {
            nVisits++;
            totValue += value;
        }

        private boolean isLeaf() {
            return (children.size() == 0 ? true : false);
        }

        private TreeNodeMCTS selectBestEdge() {
            TreeNodeMCTS selected = null;
            double bestValue = Double.MIN_VALUE;
            for (TreeNodeMCTS c : children) {
                double uctValue = c.totValue / (c.nVisits + epsilon) +
                        Math.sqrt(Math.log(nVisits+1) / (c.nVisits + epsilon)) +
                        r.nextDouble() * epsilon;
                // small random number to break ties randomly in unexpanded nodes
                if (uctValue > bestValue) {
                    selected = c;
                    bestValue = uctValue;
                }
            }
            return selected;
        }

        private TreeNodeMCTS selectEdgeToPlay() {
            return children.get(r.nextInt(children.size()));
        }

    }
}
