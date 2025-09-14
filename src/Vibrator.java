//Created by Hannah Thomas and Wendy Huang on 5/30/25
//This is the Driver class for the piano game

//import necessary libraries to control STM32 board + program game

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Vibrator { //Vibrator class is for methods that involve controlling the vibrators

    SerialPort vibratorSerialPort; //allows us to send data from computer to STM32 board that states which ring to vibrate
    private int sleepTime = 2000; //sleep time variable allows users to change speed

    public Vibrator(SerialPort serialPort) { //when calling constructor, the private variable vibratorSerialPort is set to the serialPort parameter
        vibratorSerialPort = serialPort;
    }

    //method to connect the device to the laptop
    public void connect() {
        //set default values that allow data to be transfered to the STM32 board
        vibratorSerialPort.setBaudRate(115200);
        vibratorSerialPort.setNumDataBits(8);
        vibratorSerialPort.setNumStopBits(SerialPort.ONE_STOP_BIT);
        vibratorSerialPort.setParity(SerialPort.NO_PARITY);
        //if the port was opened, then show print statement to confirm
        if (vibratorSerialPort.openPort()) {
            System.out.println("Port opened successfully");
        }
    }

    //method to disconnect the device
    public void disconnect() {
        //if the port is open, close the port and show print statement to confirm
        if (vibratorSerialPort.isOpen()) {
            vibratorSerialPort.closePort();
            System.out.println("Port closed successfully");
        }
    }

    //method that when given one of the six keys that represent a note in a song, returns a string arraylist that tells the STM32 board which ring to vibrate
    //if the 10th element in the array is 1 and all others are 0, then only the 10th ring will vibrate
    public ArrayList<String> letterToList(String letter) {
        if (letter.equals("d")) {
            return new ArrayList<String>(Arrays.asList("0", "0", "0", "0", "0", "0", "0", "0", "0", "1"));
        } else if (letter.equals("f")) {
            return new ArrayList<String>(Arrays.asList("0", "0", "0", "0", "0", "0", "1", "0", "0", "0"));
        } else if (letter.equals("g")) {
            return new ArrayList<String>(Arrays.asList("0", "0", "0", "0", "0", "1", "0", "0", "0", "0"));
        } else if (letter.equals("h")) {
            return new ArrayList<String>(Arrays.asList("0", "0", "0", "0", "1", "0", "0", "0", "0", "0"));
        } else if (letter.equals("j")) {
            return new ArrayList<String>(Arrays.asList("0", "0", "1", "0", "0", "0", "0", "0", "0", "0"));
        } else {
            return new ArrayList<String>(Arrays.asList("1", "0", "0", "0", "0", "0", "0", "0", "0", "0"));
        }
    }

    //given the string array that holds the keys for the song, vibrate the corresponding rings on the device
    public void vibrateSong(String[] song) throws IOException { //throw IO exception if the port is not yet opened
        //loop through each note in the song array and use letterToList to create an arraylist with the values needed to input to the STM32 board
        for (String note : song) {
            ArrayList<String> channelValues = letterToList(note);
            //IllegalArgumentException needed because channelValues must be 10 elements in length
            if (channelValues.size() != 10) {
                throw new IllegalArgumentException("Parameter channelValues must contain exactly 10 values");
            }

            //IOException needed in case the port is not open
            if (!vibratorSerialPort.isOpen()) {
                throw new IOException("Serial port: " + vibratorSerialPort.getSystemPortName() + " is not opened");
            }
            //vibrate the rings and have try-catch in case of error caused by exception
            vibrate(channelValues);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    //method that vibrates one ring given a list of length 10 with all values being "0" except for the ring that is to be vibrated (what is returned by letterToList method)
    public void vibrate(List<String> channelValues) throws IOException {
        //same exceptions as above
        if (channelValues.size() != 10) {
            throw new IllegalArgumentException("Parameter channelValues must contain exactly 10 values");
        }

        if (!vibratorSerialPort.isOpen()) {
            throw new IOException("Serial port: " + vibratorSerialPort.getSystemPortName() + " is not opened");
        }

        OutputStream portOutput = vibratorSerialPort.getOutputStream();
        String send = "$SET=" + String.join(",", channelValues) + "#"; //format needed to send information to STM32
        portOutput.write(send.getBytes(StandardCharsets.US_ASCII));
    }

    //getter and setter for the variable sleepTime
    public int getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(int newSleepTime) {
        sleepTime = newSleepTime;
    }
}
