package com.igorbulinski.chat.server;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public final class Log
{
  private static Log INSTANCE;
  private ArrayList<String> messages;

  private Log() throws IOException
  {
    messages = new ArrayList<>();
  }

  public static Log getLog() throws IOException
  {
    if (INSTANCE == null)
    {
      INSTANCE = new Log();
    }

    return INSTANCE;
  }

  public void addLog(String message) throws IOException
  {
    messages.add(message);
    save(message);
  }

  public void save(String message) throws IOException
  {
    Calendar calendar = Calendar.getInstance();
    FileWriter writer = new FileWriter(
        "logs-" + calendar.get(Calendar.DAY_OF_MONTH) + "-" +
            String.valueOf(calendar.get(Calendar.MONTH)+1) + "-" + calendar.get(Calendar.YEAR) +".txt", true);
    writer.write(message + "\n");
    writer.close();
  }
}
