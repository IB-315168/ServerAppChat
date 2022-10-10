package com.igorbulinski.chat.server;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Date;

public class MessageCommunicator implements Runnable
{
  private Socket socket;
  private Broadcaster broadcaster;
  private Gson gson;
  private Log log = Log.getLog();

  public MessageCommunicator(Socket socket, Broadcaster broadcaster)
      throws IOException
  {
    this.socket = socket;
    this.broadcaster = broadcaster;
    this.gson = new Gson();
  }

  @Override public void run()
  {
    try
    {
      communicate();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  private void communicate() throws IOException
  {

    BufferedReader reader = new BufferedReader(
        new InputStreamReader(socket.getInputStream()));
    PrintWriter writer = new PrintWriter(socket.getOutputStream());

    loop:
    while (true)
    {
      String message = reader.readLine();
      switch (message)
      {
        case "connect" -> {
          String username = reader.readLine();
          if (broadcaster.isValid(username))
          {
            broadcaster.addRecipient(username, socket);
            broadcaster.broadcast(username + " joined chat.");
            writer.println("Approved");
            writer.flush();
          }
          else
          {
            writer.println("Username is already in use");
            writer.flush();
          }
          break;
        }
        case "message" -> {
          Date date1 = new Date();
          Message message1 = gson.fromJson(reader.readLine(), Message.class);
          message1.setTimestamp(new Timestamp(date1.getTime()));
          broadcaster.broadcast(gson.toJson(message1));
          String line =
              "Client address: " + socket.getInetAddress() + "\n" + message1
                  + "\n";
          System.out.println(
              "---------------------MESSAGE-----------------------\n" + line
                  + "---------------------------------------------------");
          log.addLog(line);

        }
        case "users" -> {
          writer.println(gson.toJson(broadcaster.getUsers()));
          writer.flush();
          break;
        }
        case "exit" -> {
          String username = reader.readLine();
          writer.println("Disconnected");
          writer.flush();
          socket.close();
          broadcaster.removeRecipient(username);
          broadcaster.broadcast(username + " left the chat.");
          break loop;
        }
      }
      //      broadcaster.broadcast(reader.readLine());
      //      writer.println("Approved");
      //      writer.flush();
    }

  }
}
