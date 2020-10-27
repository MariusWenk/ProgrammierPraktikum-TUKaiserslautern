package de.tukl.programmierpraktikum2020.p2.a2;

import de.tukl.programmierpraktikum2020.p2.a1.DuplicateEdgeException;
import de.tukl.programmierpraktikum2020.p2.a1.Graph;
import de.tukl.programmierpraktikum2020.p2.a1.InvalidEdgeException;
import de.tukl.programmierpraktikum2020.p2.a1.InvalidNodeException;

import java.util.*;

public class Graph7<Data, Weight> implements Graph<Data,Weight> {
    ArrayList<ArrayList<Weight>> adjMatrix = new ArrayList<>();
    ArrayList<Data> dataList = new ArrayList<>();
    public Graph7(){
        for (int i = 0; i < 7; i++) {
            ArrayList<Weight> a= new ArrayList<>(7);
            adjMatrix.add(a);
            for (int j = 0; j < 7; j++) {
                a.add(null);
            }
        }
    }

    @Override
    public int addNode(Data data) {
        dataList.add(data);
        return  dataList.size()-1;
    }

    @Override
    public Data getData(int nodeId) throws InvalidNodeException {
        if(dataList.size() <= nodeId) throw new InvalidNodeException(nodeId);
        return dataList.get(nodeId);
    }

    @Override
    public void setData(int nodeId, Data data) throws InvalidNodeException {
        if(dataList.size() <= nodeId) throw new InvalidNodeException(nodeId);
        dataList.set(nodeId,data);
    }

    @Override
    public void addEdge(int fromId, int toId, Weight weight) throws InvalidNodeException, DuplicateEdgeException {
        if (adjMatrix.get(fromId).get(toId) != null) throw new DuplicateEdgeException(fromId,toId);
        if(dataList.size() <= fromId) throw new InvalidNodeException(fromId);
        if(dataList.size() <= toId) throw new InvalidNodeException(toId);
        adjMatrix.get(fromId).set(toId,weight);
    }

    @Override
    public Weight getWeight(int fromId, int toId) throws InvalidEdgeException {
        if (adjMatrix.get(fromId).get(toId) == null) throw new InvalidEdgeException(fromId,toId);
        return adjMatrix.get(fromId).get(toId);
    }

    @Override
    public void setWeight(int fromId, int toId, Weight weight) throws InvalidEdgeException {
        if (adjMatrix.get(fromId).get(toId) == null) throw new InvalidEdgeException(fromId,toId);
        adjMatrix.get(fromId).set(toId,weight);
    }

    @Override
    public Set<Integer> getNodeIds() {
        var h = new HashSet<Integer>();
        for (int i = 0; i < dataList.size(); i++) {
            h.add(i);
        }
        return  h;
    }

    @Override
    public Set<Integer> getIncomingNeighbors(int nodeId) throws InvalidNodeException {
        if(dataList.size() <= nodeId) throw new InvalidNodeException(nodeId);
        var h = new HashSet<Integer>();
        for (int i = 0; i < dataList.size(); i++) {
            if(adjMatrix.get(i).get(nodeId)!= null)
                h.add(i);
        }
        return h;
    }

    @Override
    public Set<Integer> getOutgoingNeighbors(int nodeId) throws InvalidNodeException {
        if(dataList.size() <= nodeId) throw new InvalidNodeException(nodeId);
        if(dataList.size() <= nodeId) throw new InvalidNodeException(nodeId);
        var h = new HashSet<Integer>();
        for (int i = 0; i < dataList.size(); i++) {
            if(adjMatrix.get(nodeId).get(i)!= null)
                h.add(i);
        }
        return h;
    }
}
