//Created by Hannah Thomas and Wendy Huang on 5/29/25
//This is the Pop Sub class for the piano game

public class Pop extends Song { //Pop class is a sub class of Song class
    private final int times; //number of times the song is replayed when vibrating

    //Notes for all pop songs in game for reference
    // mary had a little lam = {"k", "j", "h", "j", "k", "k", "k", "j", "j", "j", "k", "k", "k", "k", "j", "h", "j", "k", "k", "k", "k", "j", "j", "k", "j", "h"};
    // twinkle twinkle little star = {"d", "d", "j", "j", "k", "k", "j", "h", "h", "g", "g", "f", "f", "d", "j", "j", "h", "h", "g", "g", "f", "j", "j", "h", "h", "g", "g", "f", "d", "d", "j", "j", "k", "k", "j", "h", "h", "g", "g", "f", "f", "d"};
    // hot cross buns = {"k", "j", "h", "k", "j", "h", "h", "h", "h", "h", "j", "j", "j", "j", "k", "j", "h"};
    // london bridge is falling down = {"j", "k", "j", "h", "g", "h", "j", "f", "g", "h", "g", "h", "j", "j", "k", "j", "h", "g", "h", "j", "d", "h", "g", "d"};

    public Pop(String[] notes, String name) { //Pop song contructor
        super(notes, name); //call constructor of super class
        times = 1; //all pop songs are vibrated once
    }

    public String[] getNotes() { //method to get the notes of the pop song object (same as super class)
        return super.getNotes(); //call getNotes method of super class
    }

    public int getTimes() { //method to get the times variable of the pop song object (same as super class)
        return times; //call getTimes method of super class
    }
}