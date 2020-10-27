package de.tukl.programmierpraktikum2020.p2.a1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {

    @Test
    public void graphGetEmptyNodeDataTest(){
        Graph<Integer, Integer> graph = new GraphImpl<>();
        Assertions.assertThrows(InvalidNodeException.class , () -> graph.getData(0));
    }

    @Test
    public void graphGetDataTest() throws GraphException{
        Graph<Integer, Integer> graph = new GraphImpl<>();
        int nodeIdentifier = graph.addNode(0);
        assertEquals(0, graph.getData(nodeIdentifier));
    }

    @Test
    public void graphInitializeMultipleNodesTest() throws GraphException{
        Graph<Integer, Integer> graph = new GraphImpl<>();
        for(int i = 0; i < 10; i++){
            int nodeIdentifier = graph.addNode(i);
            assertEquals(i, graph.getData(nodeIdentifier));
        }
    }

    @Test
    public void graphSetDataTest() throws GraphException{
        Graph<Integer, Integer> graph = new GraphImpl<>();
        for(int i = 0; i < 10; i++){
            int nodeIdentifier = graph.addNode(i);
            assertEquals(i, graph.getData(nodeIdentifier));
            graph.setData(nodeIdentifier, 1);
            assertEquals(1, graph.getData(nodeIdentifier));
        }
    }

    @Test
    public void graphSetEmptyNodeDataTest(){
        Graph<Integer, Integer> graph = new GraphImpl<>();
        Assertions.assertThrows(InvalidNodeException.class , () -> graph.setData(0,5));
    }

    @Test
    public void graphAddInvalidNodeEdgeFromIDTest(){
        Graph<Integer, Integer> graph = new GraphImpl<>();
        int nodeID = graph.addNode(0);
        Assertions.assertThrows(InvalidNodeException.class , () -> graph.addEdge(nodeID + 1, nodeID,42));
    }

    @Test
    public void graphAddInvalidNodeEdgeToIDTest(){
        Graph<Integer, Integer> graph = new GraphImpl<>();
        int nodeID = graph.addNode(0);
        Assertions.assertThrows(InvalidNodeException.class , () -> graph.addEdge(nodeID,nodeID + 1,42));
    }

    @Test
    public void graphAddDuplicateEdgeTest() throws GraphException{
        Graph<Integer, Integer> graph = new GraphImpl<>();
        int nodeID1 = graph.addNode(0);
        int nodeID2 = graph.addNode(1);
        graph.addEdge(nodeID1,nodeID2,7);
        Assertions.assertThrows(DuplicateEdgeException.class , () -> graph.addEdge(nodeID1,nodeID2,7));
        Assertions.assertThrows(DuplicateEdgeException.class , () -> graph.addEdge(nodeID1,nodeID2,8));
    }

    @Test
    public void graphGetInvalidEdgeWeightTest(){
        Graph<Integer, Integer> graph = new GraphImpl<>();
        int nodeID1 = graph.addNode(0);
        int nodeID2 = graph.addNode(1);
        Assertions.assertThrows(InvalidEdgeException.class , () -> graph.getWeight(nodeID1,nodeID2));
    }

    @Test
    public void graphSetInvalidEdgeWeightTest(){
        Graph<Integer, Integer> graph = new GraphImpl<>();
        int nodeID1 = graph.addNode(0);
        int nodeID2 = graph.addNode(1);
        Assertions.assertThrows(InvalidEdgeException.class , () -> graph.setWeight(nodeID1,nodeID2,42));
    }

    @Test
    public void graphGetInvalidEdgeNodeWeightTest(){
        Graph<Integer, Integer> graph = new GraphImpl<>();
        int nodeID1 = graph.addNode(0);
        Assertions.assertThrows(InvalidEdgeException.class , () -> graph.getWeight(nodeID1,nodeID1+1));
        Assertions.assertThrows(InvalidEdgeException.class , () -> graph.getWeight(nodeID1+1,nodeID1));
    }

    @Test
    public void graphSetInvalidEdgeNodeWeightTest(){
        Graph<Integer, Integer> graph = new GraphImpl<>();
        int nodeID1 = graph.addNode(0);
        Assertions.assertThrows(InvalidEdgeException.class , () -> graph.setWeight(nodeID1,nodeID1+1,42));
        Assertions.assertThrows(InvalidEdgeException.class , () -> graph.setWeight(nodeID1+1,nodeID1,42));
    }

    @Test
    public void graphGetAndSetWeightTest() throws GraphException{
        Graph<Integer, Integer> graph = new GraphImpl<>();
        int nodeID1 = graph.addNode(0);
        for(int i = 1; i<10;i++){
            int nodeID2 = graph.addNode(i);
            graph.addEdge(nodeID1,nodeID2,i+100);
            graph.addEdge(nodeID2,nodeID1,i+200);
            assertEquals(i+100, graph.getWeight(nodeID1,nodeID2));
            assertEquals(i+200, graph.getWeight(nodeID2,nodeID1));
            graph.setData(nodeID2,0); //unnötig, aber prüft, ob das nicht vielleicht doch irgendwas manipuliert.
            graph.setWeight(nodeID1,nodeID2,i+50);
            graph.setWeight(nodeID2,nodeID1,i+10);
            assertEquals(i+50, graph.getWeight(nodeID1,nodeID2));
            assertEquals(i+10, graph.getWeight(nodeID2,nodeID1));
        }
    }

    @Test
    public void graphGetNodeIDsTest(){
        Graph<Integer, Integer> graph = new GraphImpl<>();
        assertEquals(new HashSet<>(),graph.getNodeIds());
        Set<Integer> expectedNodeIDs = new HashSet<>();
        for(int i = 0; i<10;i++){
            int nodeID = graph.addNode(i);
            expectedNodeIDs.add(nodeID);
        }
        Set<Integer> actualNodeIDs = graph.getNodeIds();
        assertEquals(expectedNodeIDs,actualNodeIDs);
    }

    @Test
    public void graphGetIncomingNeighboursTest() throws GraphException{
        Graph<Integer, Integer> graph = new GraphImpl<>();
        Set<Integer> expectedIncomingNeighbours = new HashSet<>();
        int nodeID1 = graph.addNode(0);
        graph.addEdge(nodeID1,nodeID1,42); // Test Kante zu sich selbst
        expectedIncomingNeighbours.add(nodeID1);
        for(int i = 1; i<10;i++){
            int nodeID2 = graph.addNode(i);
            graph.addEdge(nodeID2,nodeID1,i+100);
            expectedIncomingNeighbours.add(nodeID2);
        }
        int nodeID3 = graph.addNode(22);
        graph.addEdge(nodeID1,nodeID3,23);
        assertEquals(expectedIncomingNeighbours,graph.getIncomingNeighbors(nodeID1));
        assertEquals(new HashSet<>(),graph.getOutgoingNeighbors(nodeID3));
    }

    @Test
    public void graphGetOutgoingNeighboursTest() throws GraphException{
        Graph<Integer, Integer> graph = new GraphImpl<>();
        Set<Integer> expectedOutgoingNeighbours = new HashSet<>();
        int nodeID1 = graph.addNode(0);
        graph.addEdge(nodeID1,nodeID1,42); // Test Kante zu sich selbst
        expectedOutgoingNeighbours.add(nodeID1);
        for(int i = 1; i<10;i++){
            int nodeID2 = graph.addNode(i);
            graph.addEdge(nodeID1,nodeID2,i+100);
            expectedOutgoingNeighbours.add(nodeID2);
        }
        int nodeID3 = graph.addNode(22);
        graph.addEdge(nodeID3,nodeID1,23);
        assertEquals(expectedOutgoingNeighbours,graph.getOutgoingNeighbors(nodeID1));
        assertEquals(new HashSet<>(),graph.getIncomingNeighbors(nodeID3));
    }

    @Test
    public void graphGetInvalidNeighboursTest(){
        Graph<Integer, Integer> graph = new GraphImpl<>();
        Assertions.assertThrows(InvalidNodeException.class , () -> graph.getIncomingNeighbors(0));
        Assertions.assertThrows(InvalidNodeException.class , () -> graph.getOutgoingNeighbors(0));
    }
}
