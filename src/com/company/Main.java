package com.company;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
	    DatagramSocket clientSocket = new DatagramSocket();
	    InetAddress IPAddress = InetAddress.getByName("localhost");

	    byte[] sendData = new byte[1024];
	    byte[] receiveData = new byte[1024];

	    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 8080);
	    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

	    Scanner scanner = new Scanner(System.in);
	    while (true) {
		    String msgSend = scanner.nextLine();

		    if (msgSend.equals("exit")) {
			    clientSocket.close();
			    return;
		    }

		    sendPacket.setData(msgSend.getBytes());
		    clientSocket.send(sendPacket);

		    clientSocket.receive(receivePacket);
		    String returnedString = new String(receivePacket.getData());
		    System.out.println(returnedString);
	    }
    }
}