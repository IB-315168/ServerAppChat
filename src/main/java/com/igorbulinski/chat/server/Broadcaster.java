package com.igorbulinski.chat.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Set;

public class Broadcaster
{
  private final HashMap<String, Socket> sockets;

  public Broadcaster()
  {
    sockets = new HashMap<>();
  }

  private void send(Socket socket, String message) throws IOException
  {
    PrintWriter writer = new PrintWriter(socket.getOutputStream());
    writer.println("broadcast");
    writer.println(message);
    writer.flush();
  }

  public boolean isValid(String username) {
    if(sockets.containsKey(username)) {
      return false;
    }
    return true;
  }

  public synchronized void addRecipient(String username, Socket socket)
  {
    sockets.put(username, socket);
  }

  public synchronized void removeRecipient(String username)
  {
    sockets.remove(username);
  }

  public Set<String> getUsers() {
    return sockets.keySet();
  }

  public synchronized void broadcast(String message) throws IOException
  {
    for (String s : sockets.keySet())
    {
      send(sockets.get(s), message);
    }
  }
}
