/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erhannis.udplistener;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * http://www.java2s.com/Code/Java/Network-Protocol/ReceiveUDPpockets.htm
 */
public class Main {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    try {
      int port = 10001;
      boolean cleanOutput = true;
      boolean echo = false;
      if (args.length > 0) {
        port = Integer.parseInt(args[0]);
      }
      if (args.length > 1) {
        cleanOutput = Boolean.parseBoolean(args[1]);
      }
      if (args.length > 2) {
        echo = Boolean.parseBoolean(args[2]);
      }

      // Create a socket to listen on the port.
      DatagramSocket dsocket = new DatagramSocket(port);

      // Create a buffer to read datagrams into. If a
      // packet is larger than this buffer, the
      // excess will simply be discarded!
      byte[] buffer = new byte[65507];

      // Create a packet to receive data into the buffer
      DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

      // Now loop forever, waiting to receive packets and printing them.
      while (true) {
        // Wait to receive a datagram
        dsocket.receive(packet);

        // Convert the contents to a string, and display them
        String msg = new String(buffer, 0, packet.getLength());
        if (cleanOutput) {
          System.out.print(msg);
        } else {
          System.out.println(packet.getAddress().getHostName() + ": " + msg);
        }

        if (echo) {
          dsocket.send(packet);
        }
        
        // Reset the length of the packet before reusing it.
        packet.setLength(buffer.length);
      }
    } catch (Exception e) {
      System.err.println(e);
        System.out.println("java -jar udplistener.jar PORT");
    }
  }
}
