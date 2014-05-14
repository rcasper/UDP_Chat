/*
 * Class Client.java
 * 
 * Author: Ryan Kasprzyk
 * Last Edited: 4/22/2012
 * 
 * Course: COSC 331: Fundamentals of Computer Networks
 * Instructor: Dr. George Rinard
 * 
 * This class serves as the client for the chat program. First the class prompts
 * the user for the host machine's IP address and port number, followed by the
 * users preferred screen name. The class then sends an empty packet to the
 * server to establish a connection. The class then creates a listener thread
 * and passes it the local DatagramSocket, then creates and sender thread and
 * passes it the local socket, the target IP address and port number, and the
 * client screen name. The UDP session is maintained until the program exits,
 * and the threaded setup allows simultaneous sending and receiving of messages.
 * 
 * Based on the "Writing a Datagram Client and Server" example from the Java
 * Tutorials
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        /*
         * Prompts the user for connection information and a preferred screen
         * name.
         */
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter your screen name: ");
        String screenName = scan.next();
        System.out.println("Enter target IP address: ");
        String ip = scan.next();
        System.out.println("Enter target port number: ");
        int port = scan.nextInt();
        System.out.println();

        /*
         * Creates a datagram socket and byte array to process and send 
         * Datagram packets, parses the String representation of the IP address
         * into an InetAddress, then uses the addressing information to send an
         * empty packet to the target server to be used for contact information.
         */
        DatagramSocket socket = new DatagramSocket();
        byte[] buf = new byte[1024];
        InetAddress address = InetAddress.getByName(ip);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
        socket.send(packet);
        
        /*
         * Creates sender and listener threads and passes in relevant address
         * and naming information.
         */
        new ListenerThread("ClientList", socket).start();
        new SenderThread("ClientSend", socket, port, address, screenName).start();
    }
}