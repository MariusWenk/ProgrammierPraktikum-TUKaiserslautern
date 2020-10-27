package de.tukl.programmierpraktikum2020.mp1;

import java.util.Comparator;

public class BibleAnalyzer {
    public static void countWords(Map<String, Integer> counts) {
        Iterable<String> iter = Util.getBibleWords();
        for(String word: iter) {
            Integer number = counts.get(word);
            if(number == null){
                number = 1;
            }
            else{
                number += 1;
            }
            counts.put(word,number);
        }
    }

    public static void main(String[] args) {
        Comparator<String> comp = Comparator.<String>naturalOrder();
        TreeMap<String, Integer> bibleWords = new TreeMap<String, Integer>(comp);
        countWords(bibleWords);
        String[] words = new String[bibleWords.size()];
        bibleWords.keys(words);
        sort(words, bibleWords);
        for(String s: words){
            System.out.print(bibleWords.get(s)+" ");
            System.out.println(s);
        }
    }

    public static void sort(String[] words, Map<String, Integer> counts) {
        mergesort(words, counts, 0, words.length-1);
    }

    private static void mergesort(String[] words, Map<String, Integer> counts, int l, int r){
        if(l < r){
            int m = (l + r)/2;
            mergesort(words, counts, l, m);
            mergesort(words, counts, m+1, r);
            merge(words, counts, l , m, r);
        }
    }

    private static void merge(String[] words, Map<String, Integer> counts, int l, int m, int r){
        String[] zw = new String[r-l+1];
        int i = l;
        int j = m+1;
        int k = 0;
        while((i<=m) && (j<=r)){
            int num1 = counts.get(words[i]);
            int num2 = counts.get(words[j]);
            if(num1 < num2){
                zw[k] = words[i];
                i++;
            }
            else{
                zw[k] = words[j];
                j++;
            }
            k++;
        }

        if(i > m){
            for(int n = j; n<=r;n++){
                zw[k] = words[n];
                k++;
            }
        }
        else{
            for(int n = i; n<=m;n++){
                zw[k] = words[n];
                k++;
            }
        }
        for(int o = 0; o<r-l+1; o++){
            words[l+o] = zw[o];
        }
    }
}
