package de.tukl.programmierpraktikum2020.p1.a2;

import java.util.*;

import de.tukl.programmierpraktikum2020.p1.a1.PriorityQueue;

public class WeightedSetCovering<E> {

    Set<E> targetSet;
    Set<WeightedSet<E>> familyOfSets;
    PriorityQueue<WeightedSet<E>> queue;

    public WeightedSetCovering(Set<E> targetSet, Set<WeightedSet<E>> familyOfSets, PriorityQueue<WeightedSet<E>> queue) {
        this.targetSet = targetSet;
        this.familyOfSets = familyOfSets;
        this.queue = queue;
    }

    public Set<WeightedSet<E>> greedyWeightedCover() {
        Set<WeightedSet<E>> solution = new HashSet<>();
        Set<E> currSet = new HashSet<>();
        while (!(targetSet.equals(currSet))) {
            WeightedSet<E> cheapBundle = queue.deleteMax();
            boolean solAdded = false;
            for (WeightedSet<E> set : familyOfSets) {
                WeightedSet<E> actualSet = set;
                for(WeightedSet<E> sol : solution) {
                    actualSet = actualSet.subtractWeightedSet(sol);
                }
                if (!(actualSet.getSet().isEmpty()) && cheapBundle.equals(actualSet) && !solAdded) {
                    solution.add(set);
                    solAdded = true; // Es wird einfach nur das erste kleine hinzugefügt (wie in Erklärung).
                }
            }
            queue.map(e -> e.subtractWeightedSet(cheapBundle)); // map übernimmt automatisch Funktion von update, deshalb ist update hier nicht nötig.
            for (WeightedSet<E> sol : solution) {
                currSet.addAll(sol.getSet());
            }
        }
        return solution;
    }
}
