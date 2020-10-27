package de.tukl.programmierpraktikum2020.p1.a1;


import java.util.Comparator;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ListQueue<E> implements PriorityQueue<E> {
    LinkedList<E> sortedList;
    Comparator<E> comp;

    public ListQueue(
            Comparator<E> comp) {
        this.comp = comp;
        this.sortedList = new LinkedList<>();
    }


    //Searches smallest Element for that functor returns true
    //Assumes functor is montone decreasing, e.g. E1 >= E2 => f(E1) <= f(E2)
    private int linearSearch(Function<E, Boolean> functor) {
        int idx = 0;
        for (E elem : this.sortedList) {
            if (functor.apply(elem)) {
                return idx;
            }
            idx += 1;
        }
        return idx;
    }

    @Override
    public void insert(E elem) {
        int index = this.linearSearch(e -> {
            int c = comp.compare(e, elem);
            return (c < 0);
        });
        this.sortedList.add(index, elem);
    }

    @Override
    public void merge(PriorityQueue<E> otherQueue) {
        while (!otherQueue.isEmpty()) {
            this.insert(otherQueue.deleteMax());
        }
    }

    @Override
    public E deleteMax() {
        if (this.isEmpty()) {
            return null;
        } else {
            return this.sortedList.remove(0);
        }
    }

    @Override
    public E max() {
        if (this.isEmpty()) {
            return null;
        } else {
            return this.sortedList.get(0);
        }
    }

    @Override
    public boolean isEmpty() {
        return this.sortedList.isEmpty();
    }

    @Override
    public boolean update(E elem, E updatedElem) {
        int index = this.linearSearch(e -> {
            int c = comp.compare(e, elem);
            return (c <= 0);
        });
        int count = 0;
        while (index < this.sortedList.size() && this.comp.compare(this.sortedList.get(index), elem) == 0) {
            this.sortedList.remove(index);
            count++;
        }
        for (int i = 0; i < count; i++) {
            this.insert(updatedElem);
        }
        return count > 0;
    }

    @Override
    public void map(UnaryOperator<E> f) {
        LinkedList<E> elems = new LinkedList<E>();
        for (E elem : this.sortedList) {
            elems.push(elem);
        }
        this.sortedList.clear();
        for (E elem : elems) {
            this.insert(f.apply(elem));
        }
    }
}
