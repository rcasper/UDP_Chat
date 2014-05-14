/*
 * Class Server.java
 * 
 * Author: Ryan Kasprzyk
 * Last Edited: 4/22/2012
 * 
 * Course: COSC 331: Fundamentals of Computer Networks
 * Instructor: Dr. George Rinard
 * 
 * This class serves as the host server for the chat program. On start, the
 * class presents the user with the connection information to be sent to the
 * client, then gives the user the prompt to choose a screen name. It then waits
 * to receive a connection request from a client. Upon receiving the request,
 * the server extracts the port and IP address information from the packet. The
 * server then creates a listener thread and passes it the local DatagramSocket,
 * then creates a sender thread and passes it the local socket, target port and
 * IP address, and the server screen name. The UDP session is maintained until
 * the program exits, and the threaded setup allows simultaneous sending and
 * receiving of messages.
 * 
 * Based on the "Writing a Datagram Client and Server" example from the Java
 * Tutorials
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        /*
         * Creates the socket and reserves port 4445 for the program, and
         * extracts the host IP address for display. A byte array is used to
         * process and store packets to and from the server.
         */
        DatagramSocket socket = new DatagramSocket(4445);
        InetAddress thisIP = InetAddress.getLocalHost();
        byte[] buf = new byte[1024];
        
        /*
         * Prints server connection information and prompts the user for a
         * screen name.
         */
        System.out.println("Server IP: " + thisIP);
        System.out.println("Server port: " + socket.getLocalPort());
        System.out.println("Enter your screen name: ");
        String screenName = scan.next();
        System.out.println();
        
        /*
         * Stops program execution to waits for a client request, then extracts
         * address information for return communication with the client.
         */
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        InetAddress address = packet.getAddress();
        int port = packet.getPort();
        
        /*
         * Creates sender and listener threads and passes in relevant address
         * and naming information.
         */
        new ListenerThread("ServerList", socket).start();
        new SenderThread("ServerSend", socket, port, address, screenName).start();
    }
}