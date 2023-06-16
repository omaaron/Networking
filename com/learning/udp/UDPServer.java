package com.learning.udp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UDPServer {
    private DatagramSocket socket;
    private List<String> listQuotes = new ArrayList<String>();
    private Random random;

    public UDPServer(int port) throws SocketException {
        socket = new DatagramSocket(port);
    }

    public static void main(String[] args) {

        int port = 1200;

        try {

            UDPServer server = new UDPServer(1200);
            server.service();
        } catch (SocketException ex) {
            System.out.println("Socket error: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }

    private void service() throws IOException {
        while (true) {

            byte[] requestBuffer = new byte[512];

            DatagramPacket requestDataGram = new DatagramPacket(requestBuffer, requestBuffer.length);
            socket.receive(requestDataGram);

            String requestMessage = new String(requestBuffer, 0, requestDataGram.getLength());

            // creating response
            String response = requestMessage.toUpperCase();
            byte[] responeInBytes = response.getBytes();

            InetAddress clientAddress = requestDataGram.getAddress();
            int clientPort = requestDataGram.getPort();

            System.out.println("Recieved request from client ip "+ clientAddress.getHostAddress() + " and port "+clientPort);

            // have to specify client address and port no
            DatagramPacket responseDataGramPacket = new DatagramPacket(responeInBytes, responeInBytes.length, clientAddress, clientPort);
            socket.send(responseDataGramPacket);
        }
    }


}
