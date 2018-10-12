package com.company;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Main {

	static int bufferSize = 1024;

    public static void main(String[] args) throws Exception {
	    DatagramSocket clientSocket = new DatagramSocket();
		Scanner scanner = new Scanner(System.in);

	    System.out.print("Enter ip address (localhost by default): ");
	    String ipAddressStr = scanner.nextLine();

	    InetAddress ipAddress;
	    if (1 < ipAddressStr.length()) {
		    ipAddress = InetAddress.getByName(ipAddressStr);
	    }
	    else {
		    ipAddress = InetAddress.getByName("localhost");
	    }

	    byte[] sendData = new byte[bufferSize];
	    byte[] receiveData = new byte[bufferSize];

	    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, 8080);
	    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

	    System.out.println("Write msg!");

	    while (true) {
		    String msgSend = scanner.nextLine();

		    if (bufferSize < msgSend.length())
		    	continue;

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