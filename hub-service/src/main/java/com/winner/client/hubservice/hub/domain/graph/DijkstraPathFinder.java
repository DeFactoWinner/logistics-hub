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

        Map<UUID, Double> distance = new HashMap<>();
        Map<UUID, UUID> prev = new HashMap<>();

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(n -> n.dist));

        distance.put(start, 0.0);
        pq.offer(new Node(start, 0.0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            if (current.dist > distance.getOrDefault(current.id, Double.MAX_VALUE)) {
                continue;
            }

            if (current.id.equals(end)) {
                break;
            }

            for (HubEdge edge : graph.getEdges(current.id)) {
                double newDist = current.dist + edge.getDistance();

                if (newDist < distance.getOrDefault(edge.getTo(), Double.MAX_VALUE)) {
                    distance.put(edge.getTo(), newDist);
                    prev.put(edge.getTo(), current.id);
                    pq.offer(new Node(edge.getTo(), newDist));
                }
            }
        }

        List<UUID> path = new ArrayList<>();
        UUID cur = end;

        while (cur != null) {
            path.add(cur);
            cur = prev.get(cur);
        }

        Collections.reverse(path);

        return new Result(path, distance.getOrDefault(end, -1.0));
    }

    public static class Result {

        public final List<UUID> path;
        public final double distance;

        public Result(List<UUID> path, double distance) {
            this.path = path;
            this.distance = distance;
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
