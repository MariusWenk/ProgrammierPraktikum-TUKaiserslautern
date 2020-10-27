package de.tukl.programmierpraktikum2020.mp1;


public class ArrayMap implements Map<String, Integer> {
    String[] key_array;
    Integer[] value_array;
    int size = 0;
    public ArrayMap(){
       key_array = new String[128];
       value_array = new Integer[128];
       //key und value sind nur durch ihre Position im array verbunden
    }

    @Override
    public Integer get(String key) {
        //if(key == null){return;/throw Exeption}
        for(int i = 0; i<this.size;i++){
            //array hat keine Lücken
            if(key_array[i].equals(key)){
                return value_array[i];
            }
        }
        return null;
    }

    @Override
    public void put(String key, Integer value) {
        //if(key == null){return;/throw Exeption}
        if(this.size==key_array.length){
            String[] temp_key = new String[(2*key_array.length)];
            for(int i= 0; i < key_array.length; i++){
                temp_key[i]=key_array[i];
                key_array = temp_key;
            }
            Integer[] temp_value = new Integer[(2*value_array.length)];
            for(int i= 0; i < value_array.length; i++){
                temp_value[i]= value_array[i];
                value_array = temp_value;
            }
        }
        for(int i= 0; i< this.size;i++){
            if(key_array[i].equals(key)){
                value_array[i] = value;
                return;
            }
        }
        key_array[size]= key;
        value_array[size]= value;
        this.size++;
    }

    @Override
    public void remove(String key) {
        //if(key == null){return;/throw Exeption}
        for(int i = 0; i < this.size;i++){
            if(key_array[i].equals(key)){
                for(int k = i; k< (key_array.length-1);k++){
                    key_array[k] = key_array[k+1];
                }
                key_array[key_array.length-1] = null;
                for(int k = i; k< (value_array.length-1);k++){
                    value_array[k] = value_array[k+1];
                }
                value_array[value_array.length-1] = 0;
                this.size--;
                if(((size*4)<= key_array.length)&&((size+128)<=key_array.length)) {
                    String[] temp_key = new String[(key_array.length / 2)];
                    for (int k = 0; k < size; k++) {
                        temp_key[k] = key_array[k];
                    }
                    key_array = temp_key;
                    Integer[] temp_value = new Integer[(value_array.length / 2)];
                    for (int k = 0; k < size; k++) {
                        temp_value[k] = value_array[k];
                    }
                    value_array = temp_value;
                }
                break;
            }
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void keys(String[] array) {
        if(array == null||(array.length < this.size)){ throw new IllegalArgumentException();}
        for(int i =0; i< size;i++){
            array[i]= key_array[i];
        }
    }
}
