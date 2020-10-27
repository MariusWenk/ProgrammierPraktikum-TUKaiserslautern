package de.tukl.programmierpraktikum2020.p2.a1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GraphImpl<D, W> implements Graph<D, W> {
    private final ArrayList<GraphNode<D, W>> nodes;

    public GraphImpl() {
        this.nodes = new ArrayList<>();
    }

    @Override
    public int addNode(D data) {
        int id_res = this.nodes.size();
        this.nodes.add(new GraphNode<>(data));
        return id_res;
    }

    private boolean isInvalidNode(int nodeID) {
        return this.nodes.size() <= nodeID;
    }

    @Override
    public D getData(int nodeId) throws InvalidNodeException {
        if (this.isInvalidNode(nodeId)) {
            throw new InvalidNodeException(nodeId);
        }
        return this.nodes.get(nodeId).data;
    }

    @Override
    public void setData(int nodeId, D data) throws InvalidNodeException {
        if (this.isInvalidNode(nodeId)) {
            throw new InvalidNodeException(nodeId);
        }
        this.nodes.get(nodeId).data = data;
    }

    @Override
    public void addEdge(int fromId, int toId, W weight) throws InvalidNodeException, DuplicateEdgeException {
        if (this.isInvalidNode(fromId)) {
            throw new InvalidNodeException(fromId);
        }
        if (this.isInvalidNode(toId)) {
            throw new InvalidNodeException(fromId);
        }
        GraphNode<D, W> from = this.nodes.get(fromId);
        GraphNode<D, W> to = this.nodes.get(toId);
        if (from.outgoing_edges.containsKey(toId)) {
            throw new DuplicateEdgeException(fromId, toId);
        }
        //If from.outgoing_edges does not contain toId we are sure that to.ingoing_edges does not contain fromId, see construction at setWeight
        from.outgoing_edges.put(toId, weight);
        to.ingoing_edges.put(fromId, weight);
    }

    @Override
    public W getWeight(int fromId, int toId) throws InvalidEdgeException {
        if (this.isInvalidNode(fromId) || this.isInvalidNode(toId)) {
            throw new InvalidEdgeException(fromId, toId);
        }
        GraphNode<D, W> node = this.nodes.get(fromId);
        W w = node.outgoing_edges.getOrDefault(toId, null);
        if (w == null) {
            throw new InvalidEdgeException(fromId, toId);
        }
        return w;
    }

    @Override
    public void setWeight(int fromId, int toId, W weight) throws InvalidEdgeException {
        if (this.isInvalidNode(fromId) || this.isInvalidNode(toId)) {
            throw new InvalidEdgeException(fromId, toId);
        }
        GraphNode<D, W> from = this.nodes.get(fromId);
        GraphNode<D, W> to = this.nodes.get(toId);
        if (!from.outgoing_edges.containsKey(toId)) {
            throw new InvalidEdgeException(fromId, toId);
        }
        //If from.outgoing_edges contains toId we are sure that to.ingoing_edges contains fromId, see construction at addEdge
        from.outgoing_edges.put(toId, weight);
        to.ingoing_edges.put(fromId, weight);
    }

    @Override
    public Set<Integer> getNodeIds() {
        Set<Integer> res = new HashSet<>();
        for (int i = 0; i < this.nodes.size(); i++) {
            res.add(i);
        }
        return res;
    }

    @Override
    public Set<Integer> getIncomingNeighbors(int nodeId) throws InvalidNodeException {
        if (this.isInvalidNode(nodeId)) {
            throw new InvalidNodeException(nodeId);
        }
        GraphNode<D, W> node = this.nodes.get(nodeId);
        return node.ingoing_edges.keySet();
    }

    @Override
    public Set<Integer> getOutgoingNeighbors(int nodeId) throws InvalidNodeException {
        if (this.isInvalidNode(nodeId)) {
            throw new InvalidNodeException(nodeId);
        }
        GraphNode<D, W> node = this.nodes.get(nodeId);
        return node.outgoing_edges.keySet();
    }
}

class GraphNode<D, W> {
    D data;
    HashMap<Integer, W> outgoing_edges;
    HashMap<Integer, W> ingoing_edges;

    public GraphNode(D data) {
        this.data = data;
        this.outgoing_edges = new HashMap<>();
        this.ingoing_edges = new HashMap<>();
    }

}