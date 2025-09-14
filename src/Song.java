//Created by Hannah Thomas and Wendy Huang on 5/29/25
//This is the Song parent class for the piano game

public class Song { //Song class that holds information of all songs in game
    private final String[] notes; //private variable to hold notes of a song object
    private final String name; //private variable to hold the name of song object

    public Song(String[] notes, String name) { //Song constructor that takes the notes and name of a new song object
        //set private variables to the parameters in the method signature
        this.notes = notes;
        this.name = name;
        //print statements when new song object is created --> printed before song is vibrated
        System.out.println("Now, you will play " + this.name);
        System.out.println("Have fun and good luck!");
    }

    public String[] getNotes() { //method to get notes of the song object
        return notes;
    }
}