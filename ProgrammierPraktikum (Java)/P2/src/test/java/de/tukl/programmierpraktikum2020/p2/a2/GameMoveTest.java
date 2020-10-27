package de.tukl.programmierpraktikum2020.p2.a2;

import de.tukl.programmierpraktikum2020.p2.a1.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Constructor;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class GameMoveTest {
    private Graph<Color,Integer> getGraph(int n, boolean b){
        Graph<Color,Integer> graph = b?new GraphImpl<>():new Graph7<>();
        for (int i = 0; i < n; i++) {
            graph.addNode(Color.WHITE);
        }
        return graph;
    }

    private Graph<Color,Integer> graph1(boolean b){
        Graph<Color,Integer> graph = getGraph(5,b);
        try {
            graph.addEdge(0,1,1);
            graph.addEdge(1,2,1);
            graph.addEdge(0,3,1);
            graph.addEdge(3,4,1);
        } catch (InvalidNodeException | DuplicateEdgeException e) {
            e.printStackTrace();
        }
        return graph;
    }
    private Graph<Color, Integer> graph2(boolean b){
        Graph<Color,Integer> graph = getGraph(7,b);
        try {
            graph.addEdge(1,0,3);
            graph.addEdge(2,0,1);
            graph.addEdge(3,0,1);
            graph.addEdge(4,0,1);
            graph.addEdge(0,5,1);
            graph.addEdge(5,6,1);
            graph.setData(1, Color.BLUE);
            graph.setData(2, Color.BLUE);
            graph.setData(3, Color.RED);
            graph.setData(4, Color.RED);
        } catch (InvalidNodeException | DuplicateEdgeException e) {
            e.printStackTrace();
        }
        return graph;
    }
    private Graph<Color, Integer> graph3(boolean b){
        Graph<Color,Integer> graph = getGraph(7,b);
        try {
            graph.addEdge(1,0,3);
            graph.addEdge(2,0,1);
            graph.addEdge(3,0,1);
            graph.addEdge(4,0,1);
            graph.addEdge(0,5,1);
            graph.addEdge(0,6,1);
            graph.setData(1, Color.BLUE);
            graph.setData(2, Color.BLUE);
            graph.setData(3, Color.RED);
            graph.setData(4, Color.RED);
        } catch (InvalidNodeException | DuplicateEdgeException e) {
            e.printStackTrace();
        }
        return graph;
    }
    private Graph<Color, Integer> circuitGraph(boolean b){
        Graph<Color,Integer> graph = getGraph(3,b);
        try {
            graph.addEdge(0,1,1);
            graph.addEdge(1,2,1);
            graph.addEdge(2,0,1);
        } catch (InvalidNodeException | DuplicateEdgeException e) {
            e.printStackTrace();
        }
        return graph;
    }
    private Graph<Color, Integer> selfGraph(boolean b){
        Graph<Color,Integer> graph = getGraph(1,b);
        try {
            graph.addEdge(0,0,1);
        } catch (InvalidNodeException | DuplicateEdgeException e) {
            e.printStackTrace();
        }
        return graph;
    }

    @Test
    public void GraphExample1() throws Exception {
        Graph<Color, Integer> graph = new GraphImpl<>();
        graph.addNode(Color.WHITE); //0
        graph.addNode(Color.WHITE); //1
        graph.addNode(Color.WHITE); //2
        graph.addEdge(0,1,2);
        graph.addEdge(1,0,3);
        graph.addEdge(2,1,2);
        graph.addEdge(2,0,1);


        GameMoveImpl board = new GameMoveImpl(graph);

        board.setColor(2,Color.RED);

        assertEquals(Color.RED,graph.getData(2));
        assertEquals(Color.WHITE,graph.getData(1));
        assertEquals(Color.WHITE,graph.getData(0));

        board.increaseWeight(2,1);

        assertEquals(3,graph.getWeight(2,1));
        assertEquals(Color.RED,graph.getData(2));
        assertEquals(Color.RED,graph.getData(1));
        assertEquals(Color.RED,graph.getData(0));

        board.setColor(2,Color.RED);

        assertEquals(Color.RED,graph.getData(2));
        assertEquals(Color.RED,graph.getData(1));
        assertEquals(Color.RED,graph.getData(0));

        board.setColor(2,Color.GREEN);

        assertEquals(Color.GREEN,graph.getData(2));
        assertEquals(Color.GREEN,graph.getData(1));
        assertEquals(Color.GREEN,graph.getData(0));

    }
    @Test
    public void GraphExample2() throws Exception{
        Graph<Color, Integer> graph = new GraphImpl<>();
        graph.addNode(Color.WHITE); //0
        graph.addNode(Color.WHITE); //1
        graph.addNode(Color.WHITE); //2
        graph.addNode(Color.WHITE); //3

        graph.addEdge(0,1,1);
        graph.addEdge(0,2,1);
        graph.addEdge(2,1,3);
        graph.addEdge(3,1,1);
        graph.addEdge(2,2,3);
        graph.addEdge(3,2,1);

        GameMoveImpl board = new GameMoveImpl(graph);

        board.setColor(0,Color.RED);
        assertEquals(graph.getData(0),Color.RED);
        assertEquals(graph.getData(1),Color.WHITE);
        assertEquals(graph.getData(2),Color.WHITE);
        assertEquals(graph.getData(3),Color.WHITE);

        board.setColor(2,Color.GREEN);
        assertEquals(graph.getData(0),Color.RED);
        assertEquals(graph.getData(1),Color.GREEN);
        assertEquals(graph.getData(2),Color.GREEN);
        assertEquals(graph.getData(3),Color.WHITE);

        board.setColor(3,Color.RED);
        assertEquals(graph.getData(0),Color.RED);
        assertEquals(graph.getData(1),Color.GREEN);
        assertEquals(graph.getData(2),Color.GREEN);
        assertEquals(graph.getData(3),Color.RED);

        board.decreaseWeight(2,1);
        assertEquals(graph.getData(0),Color.RED);
        assertEquals(graph.getData(1),Color.GREEN);
        assertEquals(graph.getData(2),Color.GREEN);
        assertEquals(graph.getData(3),Color.RED);
        assertEquals(2,graph.getWeight(2,1));

        board.decreaseWeight(2,1);
        assertEquals(graph.getData(0),Color.RED);
        assertEquals(graph.getData(1),Color.RED);
        assertEquals(graph.getData(2),Color.GREEN);
        assertEquals(graph.getData(3),Color.RED);
        assertEquals(1,graph.getWeight(2,1));
    }

    @Test
    public void GraphExample3() throws Exception{
        Graph<Color, Integer> graph = new GraphImpl<>();
        graph.addNode(Color.WHITE); //0
        graph.addNode(Color.WHITE); //1
        graph.addNode(Color.WHITE); //2
        graph.addNode(Color.WHITE); //3
        graph.addEdge(0,1,1);
        graph.addEdge(0,2,1);
        graph.addEdge(2,2,2);
        graph.addEdge(2,1,2);
        graph.addEdge(3,2,1);
        graph.addEdge(3,1,2);


        GameMoveImpl board = new GameMoveImpl(graph);
        board.setColor(3,Color.RED);

        assertEquals(Color.RED,graph.getData(3));
        assertEquals(Color.WHITE,graph.getData(1));
        assertEquals(Color.WHITE,graph.getData(0));
        assertEquals(Color.WHITE,graph.getData(2));

        board.setColor(2,Color.GREEN);

        assertEquals(Color.RED,graph.getData(3));
        assertEquals(Color.WHITE,graph.getData(1));
        assertEquals(Color.WHITE,graph.getData(0));
        assertEquals(Color.GREEN,graph.getData(2));

        board.setColor(1,Color.BLUE);

        assertEquals(Color.RED,graph.getData(3));
        assertEquals(Color.BLUE,graph.getData(1));
        assertEquals(Color.WHITE,graph.getData(0));
        assertEquals(Color.GREEN,graph.getData(2));

        board.setColor(1,Color.YELLOW);

        assertEquals(Color.RED,graph.getData(3));
        assertEquals(Color.YELLOW,graph.getData(1));
        assertEquals(Color.WHITE,graph.getData(0));
        assertEquals(Color.GREEN,graph.getData(2));


        board.setColor(0,Color.RED);

        assertEquals(Color.RED,graph.getData(3));
        assertEquals(Color.RED,graph.getData(1));
        assertEquals(Color.RED,graph.getData(0));
        assertEquals(Color.GREEN,graph.getData(2));


        board.increaseWeight(2,2);

        assertEquals(3,graph.getWeight(2,2));
        assertEquals(Color.RED,graph.getData(3));
        assertEquals(Color.RED,graph.getData(1));
        assertEquals(Color.RED,graph.getData(0));
        assertEquals(Color.GREEN,graph.getData(2));

        board.setColor(3,Color.BLUE);

        assertEquals(Color.BLUE,graph.getData(3));
        assertEquals(Color.RED,graph.getData(1));
        assertEquals(Color.RED,graph.getData(0));
        assertEquals(Color.GREEN,graph.getData(2));

        board.setColor(0,Color.YELLOW);

        assertEquals(Color.BLUE,graph.getData(3));
        assertEquals(Color.RED,graph.getData(1));
        assertEquals(Color.YELLOW,graph.getData(0));
        assertEquals(Color.GREEN,graph.getData(2));

        board.setColor(3,Color.RED);

        assertEquals(Color.RED,graph.getData(3));
        assertEquals(Color.RED,graph.getData(1));
        assertEquals(Color.YELLOW,graph.getData(0));
        assertEquals(Color.GREEN,graph.getData(2));

        board.increaseWeight(2,1);

        assertEquals(3,graph.getWeight(2,1));
        assertEquals(Color.RED,graph.getData(3));
        assertEquals(Color.RED,graph.getData(1));
        assertEquals(Color.YELLOW,graph.getData(0));
        assertEquals(Color.GREEN,graph.getData(2));

        board.setColor(1,Color.BLUE);

        assertEquals(Color.RED,graph.getData(3));
        assertEquals(Color.BLUE,graph.getData(1));
        assertEquals(Color.YELLOW,graph.getData(0));
        assertEquals(Color.GREEN,graph.getData(2));

        board.setColor(3,Color.YELLOW);

        assertEquals(Color.YELLOW,graph.getData(3));
        assertEquals(Color.BLUE,graph.getData(1));
        assertEquals(Color.YELLOW,graph.getData(0));
        assertEquals(Color.GREEN,graph.getData(2));

        board.setColor(3,Color.RED);

        assertEquals(Color.RED,graph.getData(3));
        assertEquals(Color.BLUE,graph.getData(1));
        assertEquals(Color.YELLOW,graph.getData(0));
        assertEquals(Color.GREEN,graph.getData(2));

        board.increaseWeight(2,1);

        assertEquals(4,graph.getWeight(2,1));
        assertEquals(Color.RED,graph.getData(3));
        assertEquals(Color.GREEN,graph.getData(1));
        assertEquals(Color.YELLOW,graph.getData(0));
        assertEquals(Color.GREEN,graph.getData(2));
    }

    @Test
    public void GraphExample4()throws Exception{
        Graph<Color, Integer> graph = new GraphImpl<>();
        graph.addNode(Color.WHITE); //0
        graph.addNode(Color.WHITE); //1
        graph.addEdge(0,1,1);
        GameMoveImpl board = new GameMoveImpl(graph);

        board.setColor(0,Color.RED);

        assertEquals(Color.RED,graph.getData(0));
        assertEquals(Color.RED,graph.getData(1));

        assertThrows(ForcedColorException.class,()->board.setColor(1,Color.GREEN));

    }
    @Test
    public void GraphExample5()throws Exception{
        Graph<Color, Integer> graph = new GraphImpl<>();
        graph.addNode(Color.WHITE); //0

        graph.addEdge(0,0,1);
        GameMoveImpl board = new GameMoveImpl(graph);

        board.setColor(0,Color.RED);

        assertEquals(Color.RED,graph.getData(0));


        assertThrows(ForcedColorException.class,()->board.setColor(0,Color.GREEN));

    }
    @Test
    public void GraphExample6()throws Exception {
        Graph<Color, Integer> graph = new GraphImpl<>();
        graph.addNode(Color.WHITE); //0

        graph.addEdge(0, 0, 1);
        GameMoveImpl board = new GameMoveImpl(graph);

        board.setColor(0,Color.RED);
        assertEquals(Color.RED,graph.getData(0));

        board.decreaseWeight(0,0);
        assertEquals(0,graph.getWeight(0,0));

        board.setColor(0,Color.RED);
        assertEquals(Color.RED,graph.getData(0));

        board.setColor(0,Color.GREEN);
        assertEquals(Color.GREEN,graph.getData(0));


    }
    @Test
    public void GraphExample7()throws Exception{
        Graph<Color, Integer> graph = new GraphImpl<>();
        graph.addNode(Color.WHITE); //0

        graph.addEdge(0,0,1);
        GameMoveImpl board = new GameMoveImpl(graph);

        board.decreaseWeight(0,0);

        assertEquals(0,graph.getWeight(0,0));


        assertThrows(NegativeWeightException.class,()->board.decreaseWeight(0,0));

    }

    @Test
    public void normalChangeColor(){
        Graph<Color,Integer> graph = graph1(true);
        GameMoveImpl gameMove = new GameMoveImpl(graph);
        try {
            gameMove.setColor(0 , Color.BLUE);
            assertEquals(graph.getData(0), Color.BLUE);
        } catch (GraphException | ForcedColorException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void forcedChangeColor(){
        Graph<Color,Integer> graph = graph1(true);
        GameMoveImpl gameMove = new GameMoveImpl(graph);
        try {
            gameMove.setColor(0 , Color.BLUE);
            gameMove.setColor(1 , Color.BLUE);
            Assertions.assertThrows(ForcedColorException.class,() ->gameMove.setColor(1,Color.GREEN));
        } catch (GraphException | ForcedColorException e) {
            e.printStackTrace();
        }
    }
    @Test void multipleChainPropagateIncrease(){
        var graph = graph2(true);
        GameMoveImpl gameMove = new GameMoveImpl(graph);
        try {
            gameMove.increaseWeight(1,0);
            assertEquals(Color.BLUE,graph.getData(0));
            assertEquals(Color.BLUE,graph.getData(1));
            assertEquals(Color.BLUE ,graph.getData(2));
            assertEquals(Color.RED,graph.getData(3));
            assertEquals(Color.RED,graph.getData(4));
            assertEquals(Color.BLUE,graph.getData(5));
            assertEquals(Color.BLUE,graph.getData(6));
        } catch (GraphException e) {
            e.printStackTrace();
        }
    }
    @Test void multipleChainPropagateDecrease(){
        var graph = graph2(true);
        GameMoveImpl gameMove = new GameMoveImpl(graph);
        try {
            gameMove.decreaseWeight(3,0);
            assertEquals(Color.BLUE,graph.getData(0));
            assertEquals(Color.BLUE,graph.getData(1));
            assertEquals(Color.BLUE ,graph.getData(2));
            assertEquals(Color.RED,graph.getData(3));
            assertEquals(Color.RED,graph.getData(4));
            assertEquals(Color.BLUE,graph.getData(5));
            assertEquals(Color.BLUE,graph.getData(6));
        } catch (GraphException | NegativeWeightException e) {
            e.printStackTrace();
        }
    }
    @Test void multipleChainPropagateColor(){
        Graph<Color,Integer> graph = graph1(true);
        GameMoveImpl gameMove = new GameMoveImpl(graph);
        try {
            gameMove.setColor(0 , Color.BLUE);
            assertEquals(Color.BLUE, graph.getData(1));
            assertEquals(Color.BLUE, graph.getData(2));
        } catch (GraphException | ForcedColorException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void multiplePropagateIncrease(){
        var graph = graph3(true);
        GameMoveImpl gameMove = new GameMoveImpl(graph);
        try {
            gameMove.increaseWeight(1,0);
            assertEquals(Color.BLUE,graph.getData(0));
            assertEquals(Color.BLUE,graph.getData(1));
            assertEquals(Color.BLUE ,graph.getData(2));
            assertEquals(Color.RED,graph.getData(3));
            assertEquals(Color.RED,graph.getData(4));
            assertEquals(Color.BLUE,graph.getData(5));
            assertEquals(Color.BLUE,graph.getData(6));
        } catch (GraphException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void multiplePropagateDecrease(){
        var graph = graph3(true);
        GameMoveImpl gameMove = new GameMoveImpl(graph);
        try {
            gameMove.decreaseWeight(3,0);
            assertEquals(Color.BLUE,graph.getData(0));
            assertEquals(Color.BLUE,graph.getData(1));
            assertEquals(Color.BLUE ,graph.getData(2));
            assertEquals(Color.RED,graph.getData(3));
            assertEquals(Color.RED,graph.getData(4));
            assertEquals(Color.BLUE,graph.getData(5));
            assertEquals(Color.BLUE,graph.getData(6));
        } catch (GraphException | NegativeWeightException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void multiplePropagateColor(){
        Graph<Color,Integer> graph = graph1(true);
        GameMoveImpl gameMove = new GameMoveImpl(graph);
        try {
            gameMove.setColor(0 , Color.BLUE);
            assertEquals(Color.BLUE, graph.getData(1));
            assertEquals(Color.BLUE, graph.getData(3));        }
        catch (GraphException | ForcedColorException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void negativeWeight(){
        Graph<Color,Integer> graph = graph1(true);
        GameMoveImpl gameMove = new GameMoveImpl(graph);
        try {
            gameMove.decreaseWeight(0,1);
            Assertions.assertThrows(NegativeWeightException.class, () -> gameMove.decreaseWeight(0,1));
        }
        catch (GraphException | NegativeWeightException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void selfPointer(){
        var graph = selfGraph(true);
        GameMoveImpl gameMove = new GameMoveImpl(graph);
        assertTimeout(Duration.ofMillis(500), () -> {
            gameMove.setColor(0,Color.BLUE);
            assertEquals(Color.BLUE,graph.getData(0));
            Assertions.assertThrows(ForcedColorException.class, () -> gameMove.setColor(0, Color.RED));
        });
    }
    @Test
    public void circuit(){
        var graph = circuitGraph(true);
        GameMoveImpl gameMove = new GameMoveImpl(graph);
        assertTimeout(Duration.ofMillis(500), () -> {
            gameMove.setColor(0,Color.BLUE);
            assertEquals(Color.BLUE,graph.getData(0));
            assertEquals(Color.BLUE,graph.getData(1));
            assertEquals(Color.BLUE,graph.getData(2));
        });
    }
    @Test
    public void normalChangeColorIntern(){
        Graph<Color,Integer> graph = graph1(false);
        GameMoveImpl gameMove = new GameMoveImpl(graph);
        try {
            gameMove.setColor(0 , Color.BLUE);
            assertEquals(graph.getData(0), Color.BLUE);
        } catch (GraphException | ForcedColorException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void forcedChangeColorIntern(){
        Graph<Color,Integer> graph = graph1(false);
        GameMoveImpl gameMove = new GameMoveImpl(graph);
        try {
            gameMove.setColor(0 , Color.BLUE);
            gameMove.setColor(1 , Color.BLUE);
            Assertions.assertThrows(ForcedColorException.class,() ->gameMove.setColor(1,Color.GREEN));
        } catch (GraphException | ForcedColorException e) {
            e.printStackTrace();
        }
    }
    @Test void multipleChainPropagateIncreaseIntern(){
        var graph = graph2(false);
        GameMoveImpl gameMove = new GameMoveImpl(graph);
        try {
            gameMove.increaseWeight(1,0);
            assertEquals(Color.BLUE,graph.getData(0));
            assertEquals(Color.BLUE,graph.getData(1));
            assertEquals(Color.BLUE ,graph.getData(2));
            assertEquals(Color.RED,graph.getData(3));
            assertEquals(Color.RED,graph.getData(4));
            assertEquals(Color.BLUE,graph.getData(5));
            assertEquals(Color.BLUE,graph.getData(6));
        } catch (GraphException e) {
            e.printStackTrace();
        }
    }
    @Test void multipleChainPropagateDecreaseIntern(){
        var graph = graph2(false);
        GameMoveImpl gameMove = new GameMoveImpl(graph);
        try {
            gameMove.decreaseWeight(3,0);
            assertEquals(Color.BLUE,graph.getData(0));
            assertEquals(Color.BLUE,graph.getData(1));
            assertEquals(Color.BLUE ,graph.getData(2));
            assertEquals(Color.RED,graph.getData(3));
            assertEquals(Color.RED,graph.getData(4));
            assertEquals(Color.BLUE,graph.getData(5));
            assertEquals(Color.BLUE,graph.getData(6));
        } catch (GraphException | NegativeWeightException e) {
            e.printStackTrace();
        }
    }
    @Test void multipleChainPropagateColorIntern(){
        Graph<Color,Integer> graph = graph1(false);
        GameMoveImpl gameMove = new GameMoveImpl(graph);
        try {
            gameMove.setColor(0 , Color.BLUE);
            assertEquals(Color.BLUE, graph.getData(1));
            assertEquals(Color.BLUE, graph.getData(2));
        } catch (GraphException | ForcedColorException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void multiplePropagateIncreaseIntern(){
        var graph = graph3(false);
        GameMoveImpl gameMove = new GameMoveImpl(graph);
        try {
            gameMove.increaseWeight(1,0);
            assertEquals(Color.BLUE,graph.getData(0));
            assertEquals(Color.BLUE,graph.getData(1));
            assertEquals(Color.BLUE ,graph.getData(2));
            assertEquals(Color.RED,graph.getData(3));
            assertEquals(Color.RED,graph.getData(4));
            assertEquals(Color.BLUE,graph.getData(5));
            assertEquals(Color.BLUE,graph.getData(6));
        } catch (GraphException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void multiplePropagateDecreaseIntern(){
        var graph = graph3(false);
        GameMoveImpl gameMove = new GameMoveImpl(graph);
        try {
            gameMove.decreaseWeight(3,0);
            assertEquals(Color.BLUE,graph.getData(0));
            assertEquals(Color.BLUE,graph.getData(1));
            assertEquals(Color.BLUE ,graph.getData(2));
            assertEquals(Color.RED,graph.getData(3));
            assertEquals(Color.RED,graph.getData(4));
            assertEquals(Color.BLUE,graph.getData(5));
            assertEquals(Color.BLUE,graph.getData(6));
        } catch (GraphException | NegativeWeightException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void multiplePropagateColorIntern(){
        Graph<Color,Integer> graph = graph1(false);
        GameMoveImpl gameMove = new GameMoveImpl(graph);
        try {
            gameMove.setColor(0 , Color.BLUE);
            assertEquals(Color.BLUE, graph.getData(1));
            assertEquals(Color.BLUE, graph.getData(3));        }
        catch (GraphException | ForcedColorException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void negativeWeightIntern(){
        Graph<Color,Integer> graph = graph1(false);
        GameMoveImpl gameMove = new GameMoveImpl(graph);
        try {
            gameMove.decreaseWeight(0,1);
            Assertions.assertThrows(NegativeWeightException.class, () -> gameMove.decreaseWeight(0,1));
        }
        catch (GraphException | NegativeWeightException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void selfPointerIntern(){
        var graph = selfGraph(false);
        GameMoveImpl gameMove = new GameMoveImpl(graph);
        assertTimeout(Duration.ofMillis(500), () -> {
            gameMove.setColor(0,Color.BLUE);
            assertEquals(Color.BLUE,graph.getData(0));
            Assertions.assertThrows(ForcedColorException.class, () -> gameMove.setColor(0, Color.RED));
        });
    }
    @Test
    public void circuitIntern(){
        var graph = circuitGraph(false);
        GameMoveImpl gameMove = new GameMoveImpl(graph);
        assertTimeout(Duration.ofMillis(500), () -> {
            gameMove.setColor(0,Color.BLUE);
            assertEquals(Color.BLUE,graph.getData(0));
            assertEquals(Color.BLUE,graph.getData(1));
            assertEquals(Color.BLUE,graph.getData(2));
        });
    }
}
