/*
 * Class SenderThread.java
 * Author: Ryan Kasprzyk
 * Last Edited: 4/22/2012
 * 
 * Course: COSC 331: Fundamentals of Computer Networks
 * Instructor: Dr. George Rinard
 * 
 * This class is one of two multithreading classes that allow the client and
 * server to send and receive data at the same time, without having to wait in
 * turns for responses before sending. This thread is solely dedicated to
 * sending packets to their destination.
 * 
 * Based on the "Writing a Datagram Client and Server" example from the Java
 * Tutorials
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class SenderThread extends Thread {

    protected DatagramSocket socket = null;
    Scanner scan = new Scanner(System.in);
    String received = null;
    InetAddress address = null;
    int port;
    String screenName;

    /**
     * Constructor for the SenderThread. Accepts a String name, DatagramSocket
     * inSocket, integer inPort, InetAddress ip, and String screen. The name
     * field invokes the name field of Thread, and all other parameters are
     * passed to run().
     * @param name
     * @param inSocket
     * @param inPort
     * @param ip
     * @param screen
     * @throws IOException 
     */
    public SenderThread(String name, DatagramSocket inSocket, int inPort, InetAddress ip, String screen) throws IOException {
        super(name);
        socket = inSocket;
        address = ip;
        port = inPort;
        screenName = screen;
    }

    public void run() {


        try {
            // Byte array used for packet translation and storage.
            byte[] buf = new byte[1024];
            // Initialized DatagramPacket packet.
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            // User input string.
            String dString = null;

            // Infinite loop until program is terminated.
            while (true) {
                // Write the user's screen name and message to dString.
                dString = screenName + " says: " + scan.nextLine();
                // Store string as a byte array
                buf = dString.getBytes();
                // Write data and address information to packet.
                packet = new DatagramPacket(buf, buf.length, address, port);
                // Send packet
                socket.send(packet);
                // Reset byte array.
                buf = new byte[1024];
            }
        } catch (IOException e) {
        }
    }
}
