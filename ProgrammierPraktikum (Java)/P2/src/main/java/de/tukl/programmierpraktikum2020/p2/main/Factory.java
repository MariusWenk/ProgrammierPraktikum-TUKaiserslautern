package de.tukl.programmierpraktikum2020.p2.main;

import de.tukl.programmierpraktikum2020.p2.a1.Graph;
import de.tukl.programmierpraktikum2020.p2.a1.GraphImpl;
import de.tukl.programmierpraktikum2020.p2.a2.Color;
import de.tukl.programmierpraktikum2020.p2.a2.GameMove;
import de.tukl.programmierpraktikum2020.p2.a2.GameMoveImpl;
import de.tukl.programmierpraktikum2020.p2.a4.ComputerPlayer;
import de.tukl.programmierpraktikum2020.p2.a4.RandomComputerPlayer;

/**
 * Factory class to wrap the constructors of the student's {@link Graph} and {@link GameMove} implementations.
 */
public class Factory {
    public static <D, W> Graph<D, W> constructGraph() {
        return new GraphImpl<>();
    }

    public static GameMove constructGameMove(Graph<Color, Integer> graph) {
        return new GameMoveImpl(graph);
    }

    public static ComputerPlayer constructComputerPlayer() {
        // Hier können Sie Ihren ComputerPlayer eintragen (freiwillige Aufgabe 4)
        return new RandomComputerPlayer();
    }
}
