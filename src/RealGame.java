//Created by Hannah Thomas and Wendy Huang on 6/5/25
//This is the Driver class for the piano game

//import necessary libraries for game and other class objects to work

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.util.Scanner;

public class RealGame {
    //initialize private variables needed for game
    private final SerialPort[] availableComPorts;
    private boolean play;
    private final Scanner scanner = new Scanner(System.in);
    private String input;
    private final Vibrator vibrator;
    private int curr_speed;
    private String[] curr_song;
    private boolean win;
    private boolean piano; //check if user is using a piano keyboard or not
    //String[] of keys for each song in game
    //Classical
    private final String[] odeJoyNotes = {"h", "h", "j", "k", "k", "j", "h", "g", "f", "f", "g", "h", "h", "g", "g", "h", "h", "j", "k", "k", "j", "h", "g", "f", "f", "g", "h", "g", "f", "f"};

    //Pop
    private final String[] maryLambNotes = {"k", "j", "h", "j", "k", "k", "k", "j", "j", "j", "k", "k", "k", "k", "j", "h", "j", "k", "k", "k", "k", "j", "j", "k", "j", "h"};
    private final String[] twinkleStarNotes = {"d", "d", "j", "j", "k", "k", "j", "h", "h", "g", "g", "f", "f", "d", "j", "j", "h", "h", "g", "g", "f", "j", "j", "h", "h", "g", "g", "f", "d", "d", "j", "j", "k", "k", "j", "h", "h", "g", "g", "f", "f", "d"};
    private final String[] hotCrBNotes = {"k", "j", "h", "k", "j", "h", "h", "h", "h", "h", "j", "j", "j", "j", "k", "j", "h"};
    private final String[] londonBridgeNotes = {"j", "k", "j", "h", "g", "h", "j", "f", "g", "h", "g", "h", "j", "j", "k", "j", "h", "g", "h", "j", "d", "h", "g", "d"};


    //RealGame constructor - this code runs when game starts
    public RealGame() throws Main.NoSerialPortException {
        play = true;
        availableComPorts = SerialPort.getCommPorts();
        //if there is issue with serial port connection to device, show print statement to tell user
        if (availableComPorts.length == 0) {
            throw new Main.NoSerialPortException("No vibrator connected to this machine");
        }
        vibrator = new Vibrator(availableComPorts[0]);
        gameLoop();
    }

    //method for what happens when game is started
    public void gameLoop() {

        System.out.println("Welcome to VibeKeys. This is a vibration-based piano game that can help beginners get accustomed to the feel of playing a piano. It can also be used for recreational purposes.");
        System.out.println("Would you like to play on the computer keyboard or the piano keyboard? For a computer keyboard instruction, return 1; for a piano keyboard instruction, return 2");
        System.out.println("Good Luck!!");

        //loop that asks user to type input and does an action based on what they types (actions shows in parser method)

        while (play) {
            input = scanner.nextLine().toLowerCase();
            if (input.contains("exit")) {
                System.exit(0);
            }
            //method needs try catch in case there is an issue with the vibrating device
            try {
                parser();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("An error occurred while communicating with the vibrator.");
                play = false; // optional: you can end the game if there's an error
            }

            if (!play) {
                break;
            }
        }


    }

    //method to understand what command user types and respond accordingly
    public void parser() throws IOException {

        //Options for players to choose computer keyboard based instruction or piano keyboard based instruction

        if (input.contains("1")) {
            System.out.println("How to play:");
            System.out.println("Plug in the device that comes with this game into the usb port of your computer. Wear the 6 rings on 6 of your fingers so that the numbers on the rings are in descending order from left to right. We recommend putting ring 10 on your left ring finger, ring 7 on your left middle finger, ring 6 on your left index finger, ring 5 on your right index finger, ring 3 on your right middle finger, and ring 1 on your right ring finger.");
            System.out.println("Rest your six fingers on the keys ‘dfghjk’. (left ring finger on ‘d’, right ring finger on ‘k’");
            System.out.println("To play a song, type whether you want classical or pop, then select a song from that list. When the ring vibrates, press the key that finger corresponds to.");
            System.out.println("If you make fewer than 3 mistakes, you win the game. Otherwise, you can play again or choose another song.");
            System.out.println("Your current speed is " + getSpeed() + " percent.");
            System.out.println("If you would like to change the speed at any time, please enter 'speed' and choose among the numbers given.");
            System.out.println("If at any point you wish to stop playing, type ‘exit’.");
        } else if (input.contains("2")) {
            System.out.println("How to play:");
            System.out.println("Plug in the device that comes with this game into the usb port of your computer. Wear the 6 rings on 6 of your fingers so that the numbers on the rings are in descending order from left to right. We recommend putting ring 10 on your left ring finger, ring 7 on your left middle finger, ring 6 on your left index finger, ring 5 on your right index finger, ring 3 on your right middle finger, and ring 1 on your right ring finger.");
            System.out.println("To play a song, type whether you want classical or pop, then select a song from that list. When the ring vibrates, press the key that finger corresponds to.");
            System.out.println("After the vibration stops, press Enter and ignore the 'you lost' message that comes afterwards. You can play again or choose another song.");
            System.out.println("Your current speed is " + getSpeed() + " percent.");
            System.out.println("If you would like to change the speed at any time, please enter 'speed' and choose among the numbers given.");
            System.out.println("If at any point you wish to stop playing, type ‘exit’.");
            piano = true;
        } else if (input.contains("classical")) {
            System.out.println("Ode to Joy");
        } else if (input.contains("pop")) {
            System.out.println("Mary Had a Little Lamb");
            System.out.println("Twinkle Twinkle Little Star");
            System.out.println("Hot Cross Buns");
            System.out.println("London Bridge is Falling Down");
        }

        // if user types a song name, use methods from vibrator class to play the song the correct number of times, get user's input, and determine if they win or not


        else if (input.contains("ode to joy")) {
            curr_song = odeJoyNotes;
            Classical odeJoy = new Classical(odeJoyNotes, "Ode to Joy");
            if (piano) {
                System.out.println("Place your fingers as shown:");
                System.out.println("    ┌──┐ ┌──┐     ┌──┐ ┌──┐ ┌──┐");
                System.out.println("    │C#│ │D#│     │F#│ │G#│ │A#│          ");
                System.out.println(" ┌──┴──┴────┴─────┴──┼─┴──┼─┴──┴───────");
                System.out.println(" │  C │  D   │  E │  F │  G │  A │  B │");
                System.out.println(" └────┴──────┴────┴────┴────┴────┴────┴");
                System.out.println("    L3   L2    R2   R3   R4");
                System.out.println();
                System.out.println("L3:left hand midddle finger");
                System.out.println("L2:left hand index finger");
                System.out.println("R2:right hand index finger");
                System.out.println("R3:right hand middle finger");
                System.out.println("R4:right hand ring finger");
            }

            System.out.println();
            System.out.println("Press Enter to start the vibrator...");
            scanner.nextLine();

            vibrator.connect();
            for (int x = 0; x < odeJoy.getTimes(); x++) {
                vibrator.vibrateSong(odeJoyNotes);
                if (!vibrator.vibratorSerialPort.isOpen()) {
                    throw new IOException("Serial port: " + vibrator.vibratorSerialPort.getSystemPortName() + " is not opened");
                }
            }

            playSong(odeJoy.getTimes());
        } else if (input.contains("mary had a little lamb")) {
            curr_song = maryLambNotes;
            Pop maryLamb = new Pop(maryLambNotes, "Mary Had a Little Lamb");
            if (piano) {
                System.out.println("Place your fingers as shown:");
                System.out.println("    ┌──┐ ┌──┐     ┌──┐ ┌──┐ ┌──┐");
                System.out.println("    │C#│ │D#│     │F#│ │G#│ │A#│          ");
                System.out.println(" ┌──┴──┴────┴─────┴──┼─┴──┼─┴──┴───────");
                System.out.println(" │  C │  D   │  E │  F │  G │  A │  B │");
                System.out.println(" └────┴──────┴────┴────┴────┴────┴────┴");
                System.out.println("   R2    R3    R4");
                System.out.println();
                System.out.println("R2:right hand index finger");
                System.out.println("R3:right hand middle finger");
                System.out.println("R4:right hand ring finger");
            }

            System.out.println();
            System.out.println("Press Enter to start the vibrator...");
            scanner.nextLine();

            vibrator.connect();
            for (int x = 0; x < maryLamb.getTimes(); x++) {
                vibrator.vibrateSong(maryLambNotes);
                if (!vibrator.vibratorSerialPort.isOpen()) {
                    throw new IOException("Serial port: " + vibrator.vibratorSerialPort.getSystemPortName() + " is not opened");
                }
            }

            playSong(maryLamb.getTimes());
        } else if (input.contains("twinkle twinkle")) {
            curr_song = twinkleStarNotes;
            Pop twinkleStar = new Pop(twinkleStarNotes, "Twinkle Twinkle Little Star");
            if (piano) {
                System.out.println("Place your fingers as shown:");
                System.out.println("    ┌──┐ ┌──┐     ┌──┐ ┌──┐ ┌──┐");
                System.out.println("    │C#│ │D#│     │F#│ │G#│ │A#│          ");
                System.out.println(" ┌──┴──┴────┴─────┴──┼─┴──┼─┴──┴───────");
                System.out.println(" │  C │  D   │  E │  F │  G │  A │  B │");
                System.out.println(" └────┴──────┴────┴────┴────┴────┴────┴");
                System.out.println("   L4    L3     L2  R2    R3  R4");
                System.out.println();
                System.out.println("L4:left hand ring finger");
                System.out.println("L3:left hand midddle finger");
                System.out.println("L2:left hand index finger");
                System.out.println("R2:right hand index finger");
                System.out.println("R3:right hand middle finger");
                System.out.println("R4:right hand ring finger");
            }

            System.out.println();
            System.out.println("Press Enter to start the vibrator...");
            scanner.nextLine();

            vibrator.connect();
            for (int x = 0; x < twinkleStar.getTimes(); x++) {
                vibrator.vibrateSong(twinkleStarNotes);
                if (!vibrator.vibratorSerialPort.isOpen()) {
                    throw new IOException("Serial port: " + vibrator.vibratorSerialPort.getSystemPortName() + " is not opened");
                }
            }

            playSong(twinkleStar.getTimes());
        } else if (input.contains("london bridge")) {
            curr_song = londonBridgeNotes;
            Pop londonBridge = new Pop(londonBridgeNotes, "Harmonious Blacksmith");
            if (piano) {
                System.out.println("Place your fingers as shown:");
                System.out.println("    ┌──┐ ┌──┐     ┌──┐ ┌──┐ ┌──┐");
                System.out.println("    │C#│ │D#│     │F#│ │G#│ │A#│          ");
                System.out.println(" ┌──┴──┴────┴─────┴──┼─┴──┼─┴──┴───────");
                System.out.println(" │  C │  D   │  E │  F │  G │  A │  B │");
                System.out.println(" └────┴──────┴────┴────┴────┴────┴────┴");
                System.out.println("   L4    L3     L2  R2    R3  R4");
                System.out.println();
                System.out.println("L4:left hand ring finger");
                System.out.println("L3:left hand midddle finger");
                System.out.println("L2:left hand index finger");
                System.out.println("R2:right hand index finger");
                System.out.println("R3:right hand middle finger");
                System.out.println("R4:right hand ring finger");
            }

            System.out.println();
            System.out.println("Press Enter to start the vibrator...");
            scanner.nextLine();

            vibrator.connect();
            for (int x = 0; x < londonBridge.getTimes(); x++) {
                vibrator.vibrateSong(londonBridgeNotes);
                if (!vibrator.vibratorSerialPort.isOpen()) {
                    throw new IOException("Serial port: " + vibrator.vibratorSerialPort.getSystemPortName() + " is not opened");
                }
            }

            playSong(londonBridge.getTimes());
        } else if (input.contains("hot cross buns")) {
            curr_song = hotCrBNotes;
            Pop hotBuns = new Pop(hotCrBNotes, "Hot Cross Buns");
            if (piano) {
                System.out.println("Place your fingers as shown:");
                System.out.println("    ┌──┐ ┌──┐     ┌──┐ ┌──┐ ┌──┐");
                System.out.println("    │C#│ │D#│     │F#│ │G#│ │A#│          ");
                System.out.println(" ┌──┴──┴────┴─────┴──┼─┴──┼─┴──┴───────");
                System.out.println(" │  C │  D   │  E │  F │  G │  A │  B │");
                System.out.println(" └────┴──────┴────┴────┴────┴────┴────┴");
                System.out.println("   R2    R3    R4");
                System.out.println();
                System.out.println("R2:right hand index finger");
                System.out.println("R3:right hand middle finger");
                System.out.println("R4:right hand ring finger");
            }

            System.out.println();
            System.out.println("Press Enter to start the vibrator...");
            scanner.nextLine();

            vibrator.connect();
            for (int x = 0; x < hotBuns.getTimes(); x++) {
                vibrator.vibrateSong(hotCrBNotes);
                if (!vibrator.vibratorSerialPort.isOpen()) {
                    throw new IOException("Serial port: " + vibrator.vibratorSerialPort.getSystemPortName() + " is not opened");
                }
            }

            playSong(hotBuns.getTimes());
        }


        //setting speed

        else if (input.contains("speed")) {
            System.out.println("Your current speed is " + getSpeed() + " percent");
            System.out.println("Please enter your desired speed number: 50, 75, or 100");
            input = scanner.nextLine();
            if (input.contains("50")) {
                setSpeed(50);
            } else if (input.contains("75")) {
                setSpeed(75);
            } else if (input.contains("100")) {
                setSpeed(100);
            } else {
                System.out.println("Invalid speed");
            }
            System.out.println("Now, your speed is " + getSpeed() + " percent!");
        }


        //tell user if command is invalid
        else {
            System.out.println("Invalid command.");
        }
    }

    //a method that checks win condition (must be less than 3 mistakes)

    public boolean checkWin(String inp, int times) {
        int mistakes = 0;
        int length = 0;
        if (inp.length() > curr_song.length * times) {
            length = curr_song.length * times;
            mistakes = inp.length() - (curr_song.length * times);
        } else {
            length = inp.length();
            mistakes = (curr_song.length * times) - inp.length();
        }
        for (int i = 0; i < length; i++) {
            if (inp.substring(i, i + 1).equals(curr_song[i % curr_song.length])) {
                mistakes = mistakes;
            } else {
                mistakes += 1;
            }
        }
        return mistakes <= 3;
    }

    // method that gets player's input, checks if they won, and shows a print statement accordingly
    public void playSong(int times) {
        input = scanner.nextLine();
        vibrator.disconnect();

        win = checkWin(input, times);
        if (win) {
            System.out.println("You won! Congratulations!!");
            play = false;
        } else {
            System.out.println("Oh no! You made more than 3 mistakes and lost! Try another song.");
        }
    }

    //getter and setter for variable curr_speed
    public int getSpeed() {
        curr_speed = 100 - ((vibrator.getSleepTime() - 1000) / 20);
        return curr_speed;
    }

    public void setSpeed(int newSpeed) {
        curr_speed = newSpeed;
        vibrator.setSleepTime(1000 + (100 - curr_speed) * 20);
    }
}