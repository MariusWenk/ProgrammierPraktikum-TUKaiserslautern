package de.tukl.programmierpraktikum2020.mp1;

public class ListMap<K, V> implements Map<K, V> {
    private MapEntry<K, V> start;

    @Override
    public V get(K key) {
        if (this.start != null) {
            return this.start.key.equals(key) ? this.start.value : this.start.next.get(key);
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        assert (key != null);
        if (this.start == null) {
            this.start = new MapEntry(key, value);
        } else {
            if (this.start.key.equals(key)) {
                this.start.value = value;
            } else {
                this.start.next.put(key, value);
            }
        }
    }

    @Override
    public void remove(K key) {
        if (this.start != null) {
            if (this.start.key.equals(key)) {
                this.start = this.start.next.start;
            } else {
                this.start.next.remove(key);
            }
        }
    }

    @Override
    public int size() {
        if (this.start == null) {
            return 0;
        } else {
            return 1 + this.start.next.size();
        }
    }

    @Override
    public void keys(K[] array) {
        if (array == null || array.length < this.size()) {
            throw new IllegalArgumentException();
        }
        this.keys(array, 0);
    }

    private void keys(K[] array, int index) {
        if (this.start != null) {
            array[index] = this.start.key;
            this.start.next.keys(array, index + 1);
        }
    }
}

class MapEntry<K, V> {
    K key;
    V value;
    ListMap<K, V> next;

    MapEntry(K key, V value) {
        this.key = key;
        this.value = value;
        this.next = new ListMap();
    }
}