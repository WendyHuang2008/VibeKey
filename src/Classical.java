//Created by Hannah Thomas and Wendy Huang on 5/29/25
//This is the Classical sub class for the piano game

public class Classical extends Song { //Classical class is a sub class of Song class
    private final int times; //number of times the song is replayed when vibrating

    //Notes for all classical songs in game for reference
    // Ode to Joy = {"h", "h", "j", "k", "k", "j", "h", "g", "f", "f", "g", "h", "h", "g", "g", "h", "h", "j", "k", "k", "j", "h", "g", "f", "f", "g", "h", "g", "f", "f"};

    public Classical(String[] notes, String name) { //Classical song contructor
        super(notes, name); //call constructor of super class
        times = 2; //all classical songs are only vibrated twice
    }

    public String[] getNotes() { //method to get the notes of the classical song object (same as super class)
        return super.getNotes(); //call getNotes method of super class
    }

    public int getTimes() { //method to get the times variable of the classical song object (same as super class)
        return times; //call getTimes method of super class
    }
}