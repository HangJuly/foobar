package excape_pods;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class Solution {
    private static final int INF = Integer.MAX_VALUE;

    private static int[][] transform(int[] sources, int[] sinks, int[][] network) {
        int length = network.length;
        int newLength = length + 2;
        int[][] newNetwork = new int[newLength][newLength];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                newNetwork[i + 1][j + 1] = network[i][j];
            }
        }
        for (int entrance : sources) {
            newNetwork[0][entrance + 1] = INF;
        }
        for (int exit : sinks) {
            newNetwork[exit + 1][newLength - 1] = INF;
        }
        return newNetwork;
    }

    private static List<Integer> bfs(int[][] residual_network) {
        int[] parents = new int[residual_network.length];
        Arrays.fill(parents, -1);
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(0);
        int u;
        for (; !queue.isEmpty() && parents[parents.length - 1] == -1; ) {
            u = queue.remove();
            for (int v = 0; v < parents.length; v++) {
                if (residual_network[u][v] > 0 && parents[v] == -1) {
                    queue.add(v);
                    parents[v] = u;
                }
            }
        }
        List<Integer> path = new ArrayList<>();
        u = parents[parents.length - 1];
        while (u != 0) {
            if (u == -1) return null;
            path.add(u);
            u = parents[u];
        }
        Collections.reverse(path);
        return path;
    }

    private static int solveWithFordFulkerson(int[][] residual_network) {
        int max_flow = 0;
        List<Integer> path;
        while ((path = bfs(residual_network)) != null) {
            // calculate residual capacity c_f(p)
            int residual_capacity = INF;
            int u = 0;
            for (int v : path) {
                residual_capacity = Math.min(residual_capacity, residual_network[u][v]);
                u = v;
            }
            // increment max flow
            max_flow += residual_capacity;
            u = 0;
            // update residual network
            for (int v : path) {
                residual_network[u][v] -= residual_capacity;
                residual_network[v][u] += residual_capacity;
                u = v;
            }
        }
        return max_flow;
    }
    public static void main(String[] arg){
        int[] entrances={0};
        int[] exits={3};
        int[][] path={{0, 7, 0, 0}, {0, 0, 6, 0}, {0, 0, 0, 8}, {9, 0, 0, 0}};
        // int res=solution(entrances, exits, path);
        int res = solveWithFordFulkerson(transform(entrances, exits, path));
        System.out.println("res: "+res);
    }
}