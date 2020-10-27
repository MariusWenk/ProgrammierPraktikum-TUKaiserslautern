package de.tukl.programmierpraktikum2020.p1.a1;

import java.util.*;
import java.util.function.UnaryOperator;

public class FibonacciHeap<E> implements PriorityQueue<E> {

    Forest forest;
    Comparator<E> comp;

    @Override
    public String toString() {
        return forest
                .toString();
    }

    public FibonacciHeap(Comparator<E> comp) {
        this.comp = comp;
        forest = new Forest(comp);
    }

    @Override
    public void insert(E elem) {
        Tree t = new Tree(elem);
        forest.add(t);
    }

    /*public static void main(String[] args) {
        FibonacciHeap queue = new FibonacciHeap<Integer>(Comparator.naturalOrder());
        queue.insert(42);
        for(int i = 0; i<10; i++){
            queue.insert(i);
        }
        queue.deleteMax();
        queue.update(42,43);
        System.out.println(queue.max());
        System.out.println(queue.forest);

        queue.update(1, 21);
        System.out.println(queue.forest);
        System.out.println(queue.max());
        queue.update(2, 22);
        System.out.println(queue.forest);
        System.out.println(queue.max());
    }*/

    @Override
    public void merge(PriorityQueue<E> otherQueue) {
//        if(otherQueue instanceof FibonacciHeap){
//            var fib= (FibonacciHeap)otherQueue;
//            forest.addAll(fib.forest.trees);
//        }else
        while (!otherQueue.isEmpty()) {
            insert(otherQueue.deleteMax());
        }
    }

    @Override
    public E deleteMax() {
        if (isEmpty()) return null;
        var val = forest.max().val;
        Tree max = forest.max();
        forest.remove(max);
        forest.addAll(forest.max.children);
        max.clearChildren();
        forest.cleanup();
        forest.updateMax();

        return val;
    }

    @Override
    public E max() {
        return isEmpty() ? null : forest.max().val;
    }

    @Override
    public boolean isEmpty() {
        return forest.max() == null;
    }

    @Override
    public boolean update(E elem, E updatedElem) {
        List<Tree> l = forest.findAll(elem);
        for(Tree t : l){
            forest.update(t, updatedElem);
        }
        return !l.isEmpty();
    }

    @Override
    public void map(UnaryOperator<E> f) {
        Forest newForest = new Forest(comp);
        while (!isEmpty()) {
            newForest.add(new Tree(f.apply(deleteMax())));
        }
        forest = newForest;
    }

    //Heap
    private class Tree {
        E val;
        boolean colored;
        private ArrayList<Tree> children = new ArrayList<>();
        Tree parent = null;

        public Tree(E val) {
            this.val = val;
        }

        public void add(Tree t) {
            t.parent = this;
            children.add(t);
        }

        public void remove(Tree t) {
            t.parent = null;
            children.remove(t);
        }

        public int degree() {
            return children.size();
        }

        @Override
        public String toString() {
            if (children.size() == 0) return "" + val;
            String s = val + ":[";
            for (Tree t : children) {
                s += t.toString();
                s += " ";
            }
            return s + "]";
        }

        public void addAll(List<Tree> l, E val, Comparator<E> comp) {
            if(this.val==val) l.add(this);
            if(comp.compare(this.val,val) >= 0)
                for (Tree t : children){
                    t.addAll(l, val,comp);
                }
        }

        public void clearChildren() {
            for (Tree t : children){
                t.parent=null;
            }
            children.clear();
        }
    }

    private class Forest {
        private LinkedList<Tree> trees = new LinkedList<>();
        ArrayList<ArrayList<Tree>> degrees = new ArrayList<>();
        Comparator<E> comp;
        Tree max;

        public Forest(Comparator<E> comp) {
            this.comp = comp;
        }

        public List<Tree> findAll(E val) {
            List<Tree> l = new LinkedList<>();
            for (Tree t : trees) {
                t.addAll(l, val,comp);
            }
            return l;
        }

        public void addAll(Collection<Tree> c) {
            for (Tree t : c) {
                add(t);
            }
        }

        public void add(Tree t) {
            //if(t.parent != null) throw new RuntimeException("p!=n");
            //if(trees.contains(t)) return ; //throw new RuntimeException("tress contains t");
            trees.add(t);
            addDeg(t);
//            addDeg(t);
            if (max == null) max = t;
            else if (comp.compare(max.val, t.val) < 0) {
                max = t;
            }
        }

        public Tree max() {
            return max;
        }

        public void addDeg(Tree t){
            int deg = t.degree();
            while (degrees.size() <= deg) {
                degrees.add(new ArrayList<Tree>());
            }
            if(degrees.get(deg).contains(t)) throw new RuntimeException("BADDD");
            degrees.get(deg).add(t);
        }
        public void updateMax() {
            max = null;
            for (Tree t : trees) {
                if (max == null) max = t;
                else if (comp.compare(max.val, t.val) < 0) max = t;
            }
        }

        public void remove(Tree t) {
            trees.remove(t);
            removeDeg(t, t.degree());
        }
        public boolean removeDeg(Tree t, int deg){
            if(degrees.size() <= deg) return false;
            return degrees.get(deg).remove(t);
//            for (var a : degrees) {
//                a.remove(t);
//            }
        }
        public boolean removeDeg(Tree t){
//            boolean b = false;
//            for (int i = 0; i < degrees.size(); i++) {
//                b |= degrees.get(i).remove(t);
//            }
//            return b;
            return removeDeg(t, t.degree());
        }

        public void cleanup() {
            int i = 0;
//            degrees.clear();
//            for (Tree t : trees) {
//                while (degrees.size() <= t.degree()) {
//                    degrees.add(new ArrayList<Tree>());
//                }
//                degrees.get(t.degree()).add(t);
//            }
//            int j = 0;
            //System.out.println(this);
//            for (var is : degrees) {
//                System.out.println("Deg: "+j++);
//                is.forEach(System.out::print);
//                System.out.println("----");
//            }
            while (i < degrees.size()) {
                join(i, degrees);
                i++;
//                degrees.clear();
//                for (Tree t : trees) {
//                    while (degrees.size() <= t.degree()) {
//                        degrees.add(new ArrayList<Tree>());
//                    }
//                    degrees.get(t.degree()).add(t);
//                }
//                for (Tree t : trees) {
//                    int j=0;
//                    for (Tree t1 : trees) {
//                        if(t1==t) j++;
//                    }
//                    if(j>1) System.out.println("PROBLEM");
//                }
                        //System.out.println(i + ":: "+ forest);
            }
        }

        public void join(int deg, ArrayList<ArrayList<Tree>> degrees) {
            while (degrees.get(deg).size() > 1) {
                Tree fst = degrees.get(deg).get(0);
                Tree snd = degrees.get(deg).get(1);
//                if(fst==snd)
//                    System.out.println("CYCLE");
                if (comp.compare(fst.val, snd.val) < 0) {
                    Tree t = snd;
                    snd = fst;
                    fst = t;
                }
//                if(!trees.contains(fst))
//                    System.out.println("SSS "+ fst);
                degrees.get(deg).remove(fst);
                fst.add(snd);

                remove(snd);
                degrees.get(deg).remove(snd);
                if (degrees.size() <= fst.degree()) {
                    degrees.add(new ArrayList<Tree>());
                }
//                if(degrees.get(fst.degree()).contains(fst)) System.out.println("ERERE");
                degrees.get(fst.degree()).add(fst);
//                System.out.println(deg);
//                System.out.println(fst.degree());
//                System.out.println(degrees.get(deg).size());
//                System.out.println(this);
            }
        }

        public void update(Tree t, E elem) {
            if (comp.compare(t.val, elem) > 0) {
                t.val = elem;
                addAll(t.children);
                //System.out.println(t);
                boolean b = removeDeg(t);
                t.clearChildren();
                if(b) addDeg(t);
            } else {
                t.val = elem;
                extract(t);
            }
            updateMax();
            degrees.clear();
            for (Tree tree : trees) {
                while (degrees.size() <= tree.degree()) {
                    degrees.add(new ArrayList<Tree>());
                }
                degrees.get(tree.degree()).add(tree);
            }
        }

        private void extract(Tree t) {
            Tree p = t.parent;
//            for (Tree tree : trees){
//                if(tree.parent!= null) throw new RuntimeException("t=p");
//            }
            if(t == p) throw new RuntimeException("t=p");
            if (p == null) {
                t.colored = false;
                return;
            }
//            System.out.println(p + " deg " + p.degree());
            //if(p.parent == null)
//                removeDeg(p);
            p.remove(t);
            add(t);
//            if(p.parent == null) removeDeg(p);

            if (p.colored) {
//                if(p.parent != null)
                extract(p);
            }
//            System.out.println(p + " deg2 " + p.degree());


            p.colored = !p.colored;
            if(p.parent == null) {
                removeDeg(p,p.degree()+1);
                removeDeg(p,p.degree());
//                for (int i = 0; i < degrees.size(); i++) {
//                    System.out.print(p + " i:"+i + " "+ degrees.get(i).contains(p));
//                }
//                System.out.println();
                addDeg(p);
            }
        }

        @Override
        public String toString() {
            String s = "";
            for (Tree t : trees) {
                s += t + " ";
            }
            return s;
        }
    }
}
