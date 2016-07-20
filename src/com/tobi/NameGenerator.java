package com.tobi;

import java.util.Random;

/**
 * Created by Woronko on 2016-07-20.
 */
public class NameGenerator {
    Random random;

    public NameGenerator(){
        random=new Random();
    }

    public char getFirstLetter(){
        char letter= (char) (random.nextInt(25)+65);
        return letter;
    }

    public char getSamo(){
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

    public String generateName(){

        int nameLength=random.nextInt(6)+3;
        String name=new String();
        name+=getFirstLetter();
        for(int i=1;i<nameLength;i++){
            if(name.charAt(i-1)=='a'||name.charAt(i-1)=='e'||name.charAt(i-1)=='i'||name.charAt(i-1)=='o'||name.charAt(i-1)=='u'){
                name+=(char)(random.nextInt(25)+97);
            }else{
                name+=getSamo();
            }
        }
        return name;
    }
}
