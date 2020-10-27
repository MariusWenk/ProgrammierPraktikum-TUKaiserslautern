package de.tukl.programmierpraktikum2020.mp1;

import java.util.Comparator;

public class TreeMap<K, V> implements  Map<K, V> {

    Comparator<K> c;
    public TreeMap(Comparator<K> c){
        this.c=c;
    }

    public static void main(String[] args) {
        Comparator<Integer> c = Comparator.naturalOrder();
        TreeMap<Integer,Integer> t = new TreeMap<Integer,Integer>(c);
        t.put(4,4);
        t.check();


        t.put(3,4);
        t.check();

        t.put(5,4);

        t.check();
        t.put(7,4);
        System.out.println(t);
        t.check();
        t.put(9,3);
        t.put(1,3);
        t.check();

        t.remove(4);
        //t.remove(7);
        //t.remove(5);

        System.out.println(t);
        t.check();

        System.out.println("TESTSTS");
    }

    @Override
    public String toString() {
        return root.toString();
    }

    Tree<K,V> root;

    @Override
    public V get(K key) {
        if(root == null) return null;
        return root.get(key, c);
    }
    public void check(){
        System.out.println(root.check(c));
    }

    @Override
    public void put(K key, V value) {
        assert(key != null);
        if(root == null) root = new Tree<>(key, value);
        else root.put(key,value,c);
    }

    @Override
    public void remove(K key) {
        //throw new UnsupportedOperationException();
        if(root == null) return;
        root = root.remove(key, c);
    }

    @Override
    public int size() {
        return root==null?0:root.size();
    }

    @Override
    public void keys(K[] array) {
        if(array == null) throw new IllegalArgumentException();
        root.keys(array);
    }
}
class Tree<K,V>{
    public K key;
    public V value;
    public Tree<K,V> lchild, rchild;
    public Tree(K key, V value, Tree<K,V> lchild, Tree<K,V> rchild){
        this.key=key;
        this.value=value;
        this.lchild=lchild;
        this.rchild=rchild;
    }
    public Tree(K key, V value){
        this(key, value, null, null);
    }
    public void keys(K[] array){
        keys(array, 0);
    }
    public int keys(K[] array,int i){
        if(i >= array.length) throw new IllegalArgumentException();
        array[i] = key;
        int index = i+1;
        if(lchild != null ) index = lchild.keys(array, index);
        if(rchild != null ) index = rchild.keys(array, index);
        return index;
    }
    public int size(){
        int r = 1;
        if(lchild != null) r += lchild.size();
        if(rchild != null) r += rchild.size();
        return r;
    }
    public V get(K key, Comparator<K> c){
        int comp = c.compare(key,this.key);
        if(comp == 0){
            return value;
        }else if(comp > 0){
            if(rchild == null) return null;
            return rchild.get(key, c);
        }else{
            if(lchild == null) return null;
            return lchild.get(key, c);
        }
    }
    public void put(K key, V value, Comparator<K> c){
        int comp = c.compare(key,this.key);
        if(comp == 0){
            this.value = value;
        }else if(comp > 0){
            if(rchild == null) rchild=new Tree<>(key,value);
            else rchild.put(key, value, c);
        }else{
            if(lchild == null) lchild=new Tree<>(key,value);
            else lchild.put(key, value, c);
        }
    }
    public Tree<K,V> remove(K key, Comparator<K> c){
        int comp = c.compare(key, this.key);
        if(comp == 0){
            if(lchild == null) return rchild;
            Tree<K,V> rightmost = lchild.findMostRightAndDelRef();
            rightmost.rchild = rchild;
            if(rightmost != lchild){
                rightmost.lchild = lchild;
            }
            return rightmost;
        }else if(comp > 0){
            if(rchild == null) return this;
            rchild = rchild.remove(key, c);
        }else{
            if(lchild == null) return this;
            lchild = lchild.remove(key, c);
        }
        return this;
    }


    private Tree<K,V> findMostRightAndDelRef(){
        Tree<K,V> t = this;
        Tree<K,V> parent = null;
        while(true){
            if(t.rchild==null){
                if(parent!= null) parent.rchild=t.lchild;
                System.out.println(parent);
                return t;
            }
            parent = t;
            t = t.rchild;
        }
    }
    public Tree<K,V> findMostLeft(){
        Tree<K,V> t = this;
        while(true){
            if(t.lchild==null){
                return t;
            }
            t = t.lchild;
        }
    }

    @Override
    public String toString() {
        return "{" + key +

                ", " + lchild +
                ", " + rchild +
                '}';
    }
    public boolean check(Comparator<K> c){
        boolean b = true;
        if(lchild != null)b&=lchild.smaller(c,this.key);
        if(rchild != null)b&=rchild.bigger(c,this.key);
        return b;
    }
    public boolean smaller(Comparator<K> c, K key){
        boolean b = c.compare(this.key, key) <= 0;
        if(lchild!= null) b &= lchild.smaller(c, key);
        if(rchild!= null) b &= rchild.smaller(c, key);
        return b;
    }
    public boolean bigger(Comparator<K> c, K key){
        boolean b = c.compare(this.key, key) >= 0;
        if(lchild!= null) b &= lchild.bigger(c, key);
        if(rchild!= null) b &= rchild.bigger(c, key);
        return b;
    }
}
