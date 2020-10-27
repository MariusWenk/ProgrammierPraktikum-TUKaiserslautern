package de.tukl.programmierpraktikum2020.p1.a2;

import de.tukl.programmierpraktikum2020.p1.a1.*;
import de.tukl.programmierpraktikum2020.p1.a1.PriorityQueue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class WeightedSetCoveringTest {
    public static List<PriorityQueue<WeightedSet<Integer>>> getPriorityQueueInstances() {
        // Wir nutzen einfach die PriorityQueue Instanzen aus der anderen Testklasse.
        // Allerdings mit anderem Comparator.
        List<PriorityQueue<WeightedSet<Integer>>> implementations = new LinkedList<>();
        Comparator<WeightedSet<Integer>> comp;
        comp = Comparator.comparingDouble((WeightedSet::getWeight));
        implementations.add(new ListQueue<>(comp.reversed()));
        implementations.add(new SkewHeap<>(comp.reversed()));
        implementations.add(new FibonacciHeap<>(comp.reversed()));
        return implementations;
    }

    @ParameterizedTest
    @MethodSource("getPriorityQueueInstances")
    public void weightedSetBeispiel(PriorityQueue<WeightedSet<Integer>> queue) {
        System.out.println("Teste weightedSetBeispiel mit " + queue.getClass().getSimpleName());
        Set<Integer> s1 = new HashSet<>();
        s1.add(1);
        s1.add(2);
        Set<Integer> s2 = new HashSet<>();
        s2.add(1);
        s2.add(3);
        Set<Integer> s3 = new HashSet<>();
        s3.add(2);
        s3.add(3);
        s3.add(5);
        Set<Integer> s4 = new HashSet<>();
        s4.add(4);
        Set<Integer> s5 = new HashSet<>();
        s5.add(1);
        s5.add(5);
        Set<Integer> s6 = new HashSet<>();
        s6.add(2);
        s6.add(3);
        s6.add(4);
        Bundle c1 = new Bundle("1", 1, s1);
        Bundle c2 = new Bundle("2", 2, s2);
        Bundle c3 = new Bundle("3", 10, s3);
        Bundle c4 = new Bundle("4", 3, s4);
        Bundle c5 = new Bundle("5", 4, s5);
        Bundle c6 = new Bundle("6", 10, s6);
        queue.insert(c1);
        queue.insert(c2);
        queue.insert(c3);
        queue.insert(c4);
        queue.insert(c5);
        queue.insert(c6);
        Set<Integer> targetSet = new HashSet<>();
        targetSet.add(1);
        targetSet.add(2);
        targetSet.add(3);
        targetSet.add(4);
        targetSet.add(5);
        Set<WeightedSet<Integer>> familyOfSets = new HashSet<>();
        familyOfSets.add(c1);
        familyOfSets.add(c2);
        familyOfSets.add(c3);
        familyOfSets.add(c4);
        familyOfSets.add(c5);
        familyOfSets.add(c6);
        WeightedSetCovering<Integer> WSC = new WeightedSetCovering<>(targetSet,familyOfSets,queue);
        Set<WeightedSet<Integer>> solution = WSC.greedyWeightedCover();
        double finalPrice = 0;
        for(WeightedSet<Integer> sol : solution){
            finalPrice += (sol.getWeight()) * (sol.getSet().size());
        }
        Set<String> expectedNames = new HashSet<>();
        expectedNames.add("Bundle 1");
        expectedNames.add("Bundle 2");
        expectedNames.add("Bundle 4");
        expectedNames.add("Bundle 5");
        Set<String> actualNames = new HashSet<>();
        Set<Integer> actualCoverage = new HashSet<>();
        for(WeightedSet<Integer> set : solution){
            actualNames.add(set.toString());
            actualCoverage.addAll(set.getSet());
        }
        Set<WeightedSet<Integer>> expectedBundles = new HashSet<>();
        expectedBundles.add(c1);
        expectedBundles.add(c2);
        expectedBundles.add(c4);
        expectedBundles.add(c5);
        assertEquals(targetSet,actualCoverage);
        assertEquals(expectedBundles,solution);
        assertEquals(expectedNames,actualNames);
        assertEquals(10,finalPrice);
        assertEquals(4,solution.size());
    }
}
