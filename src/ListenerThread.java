
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Scanner;

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
 * receiving and printing incoming packets.
 * 
 * Based on the "Writing a Datagram Client and Server" example from the Java
 * Tutorials
 */



public class ListenerThread extends Thread {

    protected DatagramSocket socket = null;
    Scanner scan = new Scanner(System.in);
    String received = null;
    
    /**
     * Constructor for the ListenerThread. Accepts a String name and
     * DatagramSocket inSocket. The name field invokes the name field from 
     * Thread, and the inSocket is passed to run().
     * @param name
     * @param inSocket
     * @throws IOException 
     */
    public ListenerThread(String name, DatagramSocket inSocket) throws IOException {
        super(name);
        socket = inSocket;
        
    }

    public void run() {


        try {
            // Byte array used for packet translation and storage.
            byte[] buf = new byte[1024];
            // Initialize DatagramPacket packet.
            DatagramPacket packet = new DatagramPacket(buf, buf.length);

            // Infinite loop until program is terminated.
            while (true) {
                // Reset packet.
                packet = new DatagramPacket(buf, buf.length);
                // Receive data.
                socket.receive(packet);
                // Write String data to a new String holder.
                received = new String(packet.getData(), 0, packet.getLength());
                // Print message.
                System.out.println(received + "\n");
                // Reset byte array.
                buf = new byte[1024];

            }
        } catch (IOException e) {
        }
    }
}
