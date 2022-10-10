package com.igorbulinski.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class LoginServer
{
  public static void main(String[] args) throws IOException
  {
    ServerSocket serverSocket = new ServerSocket(8080);
    Broadcaster broadcaster = new Broadcaster();

    while (true) {
      System.out.println("Awaiting connection at " + serverSocket.getLocalPort());
      Socket socket = serverSocket.accept();
      System.out.println("Client connected - " + socket.getInetAddress());
      MessageCommunicator messageCommunicator = new MessageCommunicator(socket, broadcaster);
      Thread thread = new Thread(messageCommunicator);
      thread.start();
    }
  }
}
