package com.winner.client.hubservice.hub.domain.graph;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.UUID;

public class DijkstraPathFinder {

    public Map<UUID, Double> findShortestDistances(HubGraph graph, UUID start) {

        Map<UUID, Double> distance = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(n -> n.dist));

        distance.put(start, 0.0);
        pq.offer(new Node(start, 0.0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            if (current.dist > distance.getOrDefault(current.id, Double.MAX_VALUE)) {
                continue;
            }

            for (HubEdge edge : graph.getEdges(current.id)) {
                double newDist = current.dist + edge.getDistance();

                if (newDist < distance.getOrDefault(edge.getTo(), Double.MAX_VALUE)) {
                    distance.put(edge.getTo(), newDist);
                    pq.offer(new Node(edge.getTo(), newDist));
                }
            }
        }

        return distance;
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
