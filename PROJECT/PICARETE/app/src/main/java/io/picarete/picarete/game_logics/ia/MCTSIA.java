package io.picarete.picarete.game_logics.ia;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import io.picarete.picarete.game_logics.gameplay.Edge;
import io.picarete.picarete.game_logics.gameplay.Tile;

/**
 * Created by root on 1/7/15.
 */
public class MCTSIA extends AIA {
    // Todo rework on this IA
    @Override
    protected Edge findEdge(int height, int width, List<Tile> game, List<Edge> previousEdgesPlayed) {
        Edge bestEdge = null;

        return bestEdge;
    }

    public class TreeNode {
        Random r = new Random();
        int nActions = 5;
        double epsilon = 1e-6;

        TreeNode[] children;
        double nVisits, totValue;

        public void selectAction() {
            List<TreeNode> visited = new LinkedList<TreeNode>();
            TreeNode cur = this;
            visited.add(this);
            while (!cur.isLeaf()) {
                cur = cur.select();
                visited.add(cur);
            }
            cur.expand();
            TreeNode newNode = cur.select();
            visited.add(newNode);
            double value = rollOut(newNode);
            for (TreeNode node : visited) {
                // would need extra logic for n-player game
                node.updateStats(value);
            }
        }

        public void expand() {
            children = new TreeNode[nActions];
            for (int i=0; i<nActions; i++) {
                children[i] = new TreeNode();
            }
        }

        private TreeNode select() {
            TreeNode selected = null;
            double bestValue = Double.MIN_VALUE;
            for (TreeNode c : children) {
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

        public boolean isLeaf() {
            return children == null;
        }

        public double rollOut(TreeNode tn) {
            // ultimately a roll out will end in some value
            // assume for now that it ends in a win or a loss
            // and just return this at random
            return r.nextInt(2);
        }

        public void updateStats(double value) {
            nVisits++;
            totValue += value;
        }

        public int arity() {
            return children == null ? 0 : children.length;
        }
    }
}