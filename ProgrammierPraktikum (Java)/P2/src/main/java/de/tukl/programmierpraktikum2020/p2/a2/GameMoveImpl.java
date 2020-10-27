package de.tukl.programmierpraktikum2020.p2.a2;

import de.tukl.programmierpraktikum2020.p2.a1.Graph;
import de.tukl.programmierpraktikum2020.p2.a1.GraphException;
import de.tukl.programmierpraktikum2020.p2.a1.InvalidEdgeException;
import de.tukl.programmierpraktikum2020.p2.a1.InvalidNodeException;

public class GameMoveImpl implements  GameMove {
    Graph<Color,Integer> graph;
    public GameMoveImpl(Graph<Color,Integer> graph){
        this.graph=graph;
    }

    @Override
    public void setColor(int nodeId, Color color) throws GraphException, ForcedColorException {
        if(color == Color.WHITE) throw new GraphException("Cant paint in white");
        for(Color c : Color.values()){
            if(checkWeight(c, nodeId,true)) throw new ForcedColorException(nodeId, color);
        }
        graph.setData(nodeId,color);
        for (int id : graph.getOutgoingNeighbors(nodeId)){
            checkWeight(color, id, false);
        }
    }

    private boolean checkWeight(Color color, int nodeId, boolean check) throws InvalidNodeException, InvalidEdgeException {

        if(color==Color.WHITE) return false;
        if(!check && color == graph.getData(nodeId)){
            return false;
        }
        int wTotal = 0;
        int wC = 0;
        for (int ids : graph.getIncomingNeighbors(nodeId)){
            int weight = graph.getWeight(ids,nodeId);
            wTotal += weight;
            if(graph.getData(ids)==color){
                wC += weight;
            }
        }
        if(wC > wTotal/2){
            graph.setData(nodeId,color);
            for (int ids : graph.getOutgoingNeighbors(nodeId)){
                if(ids != nodeId){
                    checkWeight(color,ids, false);
                }
            }

            return true;
        }
        return false;
    }

    @Override
    public void increaseWeight(int fromId, int toId) throws GraphException {
        System.out.println(graph.getData(1));
        int weight=graph.getWeight(fromId, toId);
        graph.setWeight(fromId,toId, weight+1);
        checkWeight(graph.getData(fromId),toId, false);
    }

    @Override
    public void decreaseWeight(int fromId, int toId) throws GraphException, NegativeWeightException {
        int weight=graph.getWeight(fromId, toId);
        if(weight==0) throw new NegativeWeightException(fromId, toId);
        graph.setWeight(fromId,toId, weight-1);
        Color color = graph.getData(fromId);
        for (Color c : Color.values()){
            if(c == color) continue;
            checkWeight(c,toId, false);
        }
    }
}
