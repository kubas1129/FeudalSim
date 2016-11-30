package com.tobi;

import java.util.Random;

/**
 * Created by Woronko on 2016-07-20.
 */
public class NameGenerator {
    Random random;
    Alphabet alphabet;

    public NameGenerator(Alphabet alphabet){
        random=new Random();
        this.alphabet=alphabet;
    }

    public String getFirstLetter(){
        String letter= alphabet.getLetter(random.nextInt(alphabet.size));
        char capitalLetter=letter.charAt(0);
        String newLetter=new String();
        capitalLetter-=32;
        newLetter+=capitalLetter;
        for(int i=1;i<letter.length();i++){
            newLetter+=letter.charAt(i);
        }
        return newLetter;
    }


    public String generateName(){

        int nameLength=random.nextInt(2)+2;
        String name=new String();
        name+=getFirstLetter();
        for(int i=1;i<nameLength;i++){
            name+=alphabet.getLetter(random.nextInt(alphabet.size));
        }

        return name;
    }
}
