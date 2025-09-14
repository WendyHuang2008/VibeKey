//Created by Hannah Thomas and Wendy Huang on 5/24/25
//This is the Driver class for the piano game

//import necessary libraries to control STM32 board + program game

import java.io.IOException;

public class Main { // create driver class for the game
    public static void main(String[] args) throws IOException, NoSerialPortException { //main method to run the program

        RealGame rg = new RealGame(); //creating a RealGame object makes the game loop run


    }

    public static class NoSerialPortException extends Exception { //create class for new exception which displays message when the device is not connected
        public NoSerialPortException(String message) {
            super(message);
        }
    }
}
