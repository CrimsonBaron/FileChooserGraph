package com.filechooser.filechooser.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileDataManager {

    private File file;
    private Map<Character, Double> characterPercentageMap;
    private Map<String,Double> wordPercantageMap;
    private String inputString;
    private Scanner sc;

    public FileDataManager(File file) throws FileNotFoundException {
        this.file = file;
        this.characterPercentageMap = new HashMap<>();
        this.wordPercantageMap = new HashMap<>();
        this.sc = new Scanner(file);
        this.inputString = initInput();
        calculateCharPercentage(inputString);
        calculateWordPercentage(inputString);
    }

    private String initInput() {
        String inputString = "";
        while (sc.hasNext()){
            inputString += sc.nextLine();
        }
        return inputString;
    }

    private void calculateCharPercentage(String input){
        Map<Character, Integer> charCounter = new HashMap<>();
        for(int i = 0; i < inputString.length(); i++) {
            Character character = inputString.charAt(i);
            if(charCounter.containsKey(character)) {
                Integer count = charCounter.get(character);
                count = count + 1;
                charCounter.put(character, count);
            } else {
                charCounter.put(character, 1);
            }
        }
        double charPercent = inputString.toCharArray().length / 100;

        for (Character a: charCounter.keySet()) {
            characterPercentageMap.put(a,charCounter.get(a)/charPercent);
        }




    }

    private void calculateWordPercentage(String inputString) {
        List<String> words = Arrays.stream(inputString.split(" ")).toList();
        List<String> sortedWords = new ArrayList<>();
        for (String s: words) {
            if (s.length()>1){
                sortedWords.add(s);
            }
        }
        Set<String> wordsSet = new HashSet<>(sortedWords);
        Map<String, Integer> wordCounter = new HashMap<>();
        for (String s: wordsSet) {
            int count = 0;
            for (String k :words) {
                if (k.equals(s)){
                    count++;
                }
            }
            wordCounter.put(s,count);
        }

        double charPercent = words.size() / 100;
        Map<String,Double> everyWordPercantageMap = new HashMap<>();
        for (String a: wordCounter.keySet()) {
            everyWordPercantageMap.put(a,wordCounter.get(a)/charPercent);
        }

        List<Double> maximums = new ArrayList<>();
        for (int i = 0; i < 5 ; i++) {
            double max = 0;
            for (String s: everyWordPercantageMap.keySet()) {
                double val = everyWordPercantageMap.get(s);
                if (val > max && !maximums.contains(val)){
                    max = val;
                }
            }
            maximums.add(max);
        }

        for (Double d :maximums) {
            for (String s: everyWordPercantageMap.keySet()) {
                double val = everyWordPercantageMap.get(s);
                if (val == d){
                    wordPercantageMap.put(s,val);
                }
            }
        }

    }



    public Map<Character, Double> getCharacterPercentageMap() {
        return characterPercentageMap;
    }

    public Map<String, Double> getWordPercantageMap() {
        return wordPercantageMap;
    }
}
