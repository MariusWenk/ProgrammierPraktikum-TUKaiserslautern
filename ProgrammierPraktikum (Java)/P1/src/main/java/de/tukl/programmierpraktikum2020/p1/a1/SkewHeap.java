package de.tukl.programmierpraktikum2020.p1.a1;

import java.util.Comparator;
import java.util.function.UnaryOperator;

// Quelle: https://de.wikipedia.org/wiki/Skew_Heap#Nicht-rekursive_Verschmelzung_zweier_Heaps_(non-recursive_merge)

public class SkewHeap<E> implements PriorityQueue<E> {
    E route;
    SkewHeap<E> leftElement;
    SkewHeap<E> rightElement;
    Comparator<E> comp;

    public SkewHeap(Comparator<E> comp) {
        route = null;
        leftElement = null;
        rightElement = null;
        this.comp = comp;
    }

    public SkewHeap(Comparator<E> comp, E r) {
        route = r;
        leftElement = null;
        rightElement = null;
        this.comp = comp;
    }

    public SkewHeap(Comparator<E> comp, E r, SkewHeap<E> lElement, SkewHeap<E> rElement) {
        route = r;
        leftElement = lElement;
        rightElement = rElement;
        this.comp = comp;
    }

    @Override
    public void insert(E elem) {
        if (elem == null) {
            return;
        }
        if (route == null) {
            route = elem;
            return;
        }
        if (comp.compare(route, elem) > 0) {
            SkewHeap<E> tmpSon = rightElement;
            rightElement = leftElement;
            if (tmpSon == null) {
                tmpSon = new SkewHeap<E>(this.comp, elem);
            } else {
                tmpSon.insert(elem);
            }
            leftElement = tmpSon;
        } else {
            if (comp.compare(route, elem) <= 0) {
                SkewHeap<E> tmpHeap = new SkewHeap<E>(this.comp, route, this.leftElement, this.rightElement);
                route = elem;
                leftElement = tmpHeap;
                rightElement = null;
            }
        }
    }

    @Override
    public void merge(PriorityQueue<E> otherQueue) {
        if (otherQueue == null) {
            return;
        }
        while (!otherQueue.isEmpty()) {
            this.insert(otherQueue.deleteMax());
        }
    }

    @Override
    public E deleteMax() {
        if (route == null) {
            return null;
        }
        E tmpRoute = route;
        SkewHeap<E> tmpSon = rightElement;
        if (leftElement == null) {
            route = null;
            rightElement = null;
        } else {
            route = leftElement.route;
            rightElement = leftElement.rightElement;
            leftElement = leftElement.leftElement;
        }
        if (tmpSon != null) {
            merge(tmpSon);
        }
        return tmpRoute;
    }

    @Override
    public E max() {
        return route;
    }

    @Override
    public boolean isEmpty() {
        return route == null;
    }

    @Override
    public boolean update(E elem, E updatedElem) {
        if (comp.compare(elem, updatedElem) == 0) {
            return true;
        }
        if (this.remove(elem)) {
            insert(updatedElem);
            while (this.remove(elem)) {
                insert(updatedElem);
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean remove(E elem) {
        if (elem == null) {
            return false;
        }
        if (route == null) {
            return false;
        }
        int compare_result = comp.compare(route, elem);
        if (compare_result == 0) {
            SkewHeap<E> tmpSon = this.rightElement;
            if (leftElement == null) {
                route = null;
                rightElement = null;
            } else {
                route = leftElement.route;
                rightElement = leftElement.rightElement;
                leftElement = leftElement.leftElement;
            }
            merge(tmpSon);
            return true;
        } else if (compare_result < 0) {
            return false;
        } else {
            if (leftElement == null && rightElement == null) {
                return false;
            }
            if (leftElement == null) {
                return rightElement.remove(elem);
            }
            if (rightElement == null) {
                return leftElement.remove(elem);
            }
            return (leftElement.remove(elem) || rightElement.remove(elem));
        }
    }

    @Override
    public void map(UnaryOperator<E> f) {
        SkewHeap<E> tmp = new SkewHeap<E>(this.comp);
        while (this.route != null) {
            E tmpValue = this.deleteMax();
            E fValue = f.apply(tmpValue);
            tmp.insert(fValue);
        }
        this.route = tmp.route;
        this.rightElement = tmp.rightElement;
        this.leftElement = tmp.leftElement;
    }
}