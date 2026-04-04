package com.winner.client.hubservice.hub.domain.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.UUID;

public class DijkstraPathFinder {

    public Result findShortestPath(HubGraph graph, UUID start, UUID end) {

        if (start.equals(end)) {
            return new Result(List.of(start), 0.0);
        }

        Map<UUID, Double> timeMap = new HashMap<>();
        Map<UUID, UUID> prev = new HashMap<>();

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(n -> n.dist));

        timeMap.put(start, 0.0);
        pq.offer(new Node(start, 0.0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            if (current.dist > timeMap.getOrDefault(current.id, Double.MAX_VALUE)) {
                continue;
            }

            if (current.id.equals(end)) {
                break;
            }

            for (HubEdge edge : graph.getEdges(current.id)) {
                double newDist = current.dist + edge.getTime();

                if (newDist < timeMap.getOrDefault(edge.getTo(), Double.MAX_VALUE)) {
                    timeMap.put(edge.getTo(), newDist);
                    prev.put(edge.getTo(), current.id);
                    pq.offer(new Node(edge.getTo(), newDist));
                }
            }
        }

        if (!timeMap.containsKey(end)) {
            return new Result(Collections.emptyList(), -1.0);
        }

        List<UUID> path = new ArrayList<>();
        UUID cur = end;

        while (cur != null) {
            path.add(cur);
            cur = prev.get(cur);
        }

        Collections.reverse(path);

        return new Result(path, timeMap.get(end));
    }

    public static class Result {

        public final List<UUID> path;
        public final double totalTime;

        public Result(List<UUID> path, double totalTime) {
            this.path = path;
            this.totalTime = totalTime;
        }
    }

    static class Node {
        UUID id;
        double dist;

        public Node(UUID id, double dist) {
            this.id = id;
            this.dist = dist;
        }
    }
}
