package com.tobi;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by tobiasz on 05.11.16.
 */
public class Alphabet {
    LinkedList<String> letters;
    int size;
    Random random=new Random();

    public String getLetter(int i){
        return letters.get(i);
    }

    private char getSamo(){
        int letterNum =random.nextInt(5);
        char letter;
        switch (letterNum){
            case 0:
                letter='a';
                break;
            case 1:
                letter='e';
                break;
            case 2:
                letter='i';
                break;
            case 3:
                letter='o';
                break;
            default:
                letter='u';
                break;
        }
        return letter;
    }

    private String generateLetter(){
        int letterLength;
        String letter=new String();
        char sign;
        letterLength=random.nextInt(4)+1;
        sign=(char)(random.nextInt(25)+97);
        letter+=sign;
        for(int i=1;i<letterLength;i++) {
            if (letter.charAt(i - 1) == 'a' || letter.charAt(i - 1) == 'e' || letter.charAt(i - 1) == 'i' || letter.charAt(i - 1) == 'o' || letter.charAt(i - 1) == 'u') {
                letter += (char) (random.nextInt(25) + 97);
            } else {
                letter += getSamo();
            }
        }
        return letter;
    }

    public Alphabet(){
        size=random.nextInt(10)+15;
        letters=new LinkedList<String>();
        String letter;
        for(int i=0;i<size;i++){
            letter=generateLetter();
            for(int j=0;j<letters.size();j++){
                if(letters.get(j)==letter){
                    j=0;
                    letter=generateLetter();
                }
            }
            letters.add(letter);
        }
    }
}
