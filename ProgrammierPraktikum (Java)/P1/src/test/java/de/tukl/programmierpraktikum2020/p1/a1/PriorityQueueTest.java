package de.tukl.programmierpraktikum2020.p1.a1;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.UnaryOperator;

import static org.junit.jupiter.api.Assertions.*;

public class PriorityQueueTest {
    /**
     * Diese Methode wird verwendet, um Instanzen von PriorityQueue Implementierungen an Testmethoden zu übergeben.
     */
    public static List<PriorityQueue<Integer>> getPriorityQueueInstances() {
        List<PriorityQueue<Integer>> implementations = new LinkedList<>();
        // Um Compilefehler zu verhindern, sind die Instanziierungen der PriorityQueue Implementierungen auskommentiert.
        // Kommentieren Sie die Zeilen ein, sobald Sie die entsprechenden Klassen implementiert haben.
        implementations.add(new ListQueue<>(Comparator.<Integer>naturalOrder()));
        implementations.add(new SkewHeap<>(Comparator.<Integer>naturalOrder()));
        implementations.add(new FibonacciHeap<>(Comparator.<Integer>naturalOrder()));
        return implementations;
    }

    @ParameterizedTest
    @MethodSource("getPriorityQueueInstances")
    public void priorityQueueEmptyTest(PriorityQueue<Integer> queue) {
        System.out.println("Teste priorityQueueEmpty mit " + queue.getClass().getSimpleName());
        assertTrue(queue.isEmpty());
        queue.insert(42);
        assertFalse(queue.isEmpty());
    }
    @ParameterizedTest
    @MethodSource("getPriorityQueueInstances")
    public void priorityQueueSpeedTest(PriorityQueue<Integer> queue) {
        System.out.println("Teste priorityQueueSpeed mit " + queue.getClass().getSimpleName());
        int size = 100;
        for (int i = 0; i < size; i++) {
            queue.insert((i*26254)%size);
        }
        while(!queue.isEmpty()) queue.deleteMax();
        assertTrue(queue.isEmpty());
    }


    @ParameterizedTest
    @MethodSource("getPriorityQueueInstances")
    public void priorityQueueMassUpdateIncreasingTest(PriorityQueue<Integer> queue) {
        System.out.println("Teste priorityQueueMassUpdate mit " + queue.getClass().getSimpleName());
        int size = 20;
        for (int i = 0; i < size; i++) {
            queue.insert((int) (Math.random()*10));
        }
        for(int i = 0; i< size/10; i++){
            assertNotNull(queue.deleteMax());
        }

        size -= size/10;
        for(int i = 0; i<5; i++){
            queue.update(i,i+1);
        }
        for(int i = 9; i > 5; i--){
            queue.update(i, i-1);
        }
        System.out.println(queue);

        assertTrue(queue.update(5, 5));
        for(int i= 0; i< size; i++) {
            System.out.println(queue);
            assertEquals(5, queue.max());
            assertFalse(queue.isEmpty());
            assertEquals(5, queue.deleteMax());
        }
        assertTrue(queue.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("getPriorityQueueInstances")
    public void priorityQueueInsertDeleteTest(PriorityQueue<Integer> queue) {
        System.out.println("Teste priorityQueueInsertDelete mit " + queue.getClass().getSimpleName());
        for(int i= 0; i< 10; i++){
            queue.insert(i);
        }
        for(int i=0; i< 10 ;i++){
            assertFalse(queue.isEmpty());
            assertEquals(9-i, queue.max());
            assertEquals(9-i,queue.deleteMax());
        }
        for(int i= 0; i< 10; i++){
            queue.insert(9-i);
        }
        for(int i=0; i< 10 ;i++){
            assertFalse(queue.isEmpty());
            assertEquals(9-i, queue.max());
            assertEquals(9-i,queue.deleteMax());
        }
        assertTrue(queue.isEmpty());
        assertNull(queue.deleteMax());
        assertNull(queue.max());
    }

    @ParameterizedTest
    @MethodSource("getPriorityQueueInstances")
    public void priorityQueueMergeTest(PriorityQueue<Integer> queue) {
        System.out.println("Teste priorityQueueMerge mit " + queue.getClass().getSimpleName());
        assertTrue(queue.isEmpty());
        queue.merge(queue);
        assertTrue(queue.isEmpty());
        ListQueue<Integer> other = new ListQueue<Integer>(Comparator.<Integer>naturalOrder());
        for(int i=0; i<10; i++){
            queue.insert(i);
            other.insert(i);
        }
        queue.merge(other);
        for(int i=0; i< 10; i++){
            assertFalse(queue.isEmpty());
            assertEquals(9-i,queue.max());
            assertEquals(9-i,queue.deleteMax());
            assertFalse(queue.isEmpty());
            assertEquals(9-i,queue.max());
            assertEquals(9-i,queue.deleteMax());
        }
        assertTrue(queue.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("getPriorityQueueInstances")
    public void priorityQueueUpdateTest(PriorityQueue<Integer> queue) {
        System.out.println("Teste priorityQueueUpdate mit " + queue.getClass().getSimpleName());
        queue.insert(42);
        for(int i = 0; i<10; i++){
            queue.insert(i);
        }
        assertTrue(queue.update(42,43));
        assertEquals(43,queue.max());
        assertTrue(queue.update(9, 550));
        assertEquals(550,queue.max());
    }
    @ParameterizedTest
    @MethodSource("getPriorityQueueInstances")
    public void priorityQueueUpdateDoubledTest(PriorityQueue<Integer> queue) {
        System.out.println("Teste priorityQueueUpdate mit " + queue.getClass().getSimpleName());
        queue.insert(42);
        for(int i = 0; i<10; i++){
            queue.insert(i);
        }
        queue.insert(42);
        assertTrue(queue.update(42,43));
        assertEquals(43,queue.deleteMax());
        assertEquals(43,queue.deleteMax());
    }

    @ParameterizedTest
    @MethodSource("getPriorityQueueInstances")
    public void priorityQueueUpdateNonExistantTest(PriorityQueue<Integer> queue) {
        System.out.println("Teste priorityQueueUpdateNonExistant mit " + queue.getClass().getSimpleName());
        queue.insert(42);
        assertFalse(queue.update(1,2));
        assertEquals(42, queue.max());
    }

    @ParameterizedTest
    @MethodSource("getPriorityQueueInstances")
    public void priorityQueueMapTest(PriorityQueue<Integer> queue) {
        System.out.println("Teste priorityQueueMap mit " + queue.getClass().getSimpleName());
        for(int i= 0; i<10;i++){
            queue.insert(i);
        }
        queue.map(integer -> integer *integer);
        for(int i=0; i<10; i++){
            assertFalse(queue.isEmpty());
            assertEquals((9-i)*(9-i), queue.deleteMax());
        }
        assertTrue(queue.isEmpty());
    }
}
