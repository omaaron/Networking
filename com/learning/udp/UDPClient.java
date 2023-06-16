package com.learning.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class UDPClient {

    public static void main(String[] args) {


        String hostname = "192.168.86.82";
        int serverPort = 1200;

        try {
            InetAddress address = InetAddress.getByName(hostname);
            DatagramSocket socket = new DatagramSocket();
            Scanner scanner = new Scanner(System.in);

            while (true) {

                System.out.println("Enter message to be sent to the server");
                String message = scanner.nextLine();
                byte[] messageInBytes = message.getBytes();
                int length = messageInBytes.length;

                // notice how you have to specify the address and port number of server each time (as a connection is not established first)
                // the operating system will assign ip address and port number of client into the packet
                DatagramPacket request = new DatagramPacket(messageInBytes, length, address, serverPort);
                socket.send(request);

                byte[] buffer = new byte[512];
                DatagramPacket responseDataGram = new DatagramPacket(buffer, buffer.length);
                socket.receive(responseDataGram);

                String responseMessage = new String(buffer, 0, responseDataGram.getLength());

                System.out.println(responseMessage);
//                System.out.println();

//                Thread.sleep(10000);
            }

        } catch (SocketTimeoutException ex) {
            System.out.println("Timeout error: " + ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Client error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

}
