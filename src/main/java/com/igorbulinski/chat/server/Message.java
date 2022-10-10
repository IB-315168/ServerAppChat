package com.igorbulinski.chat.server;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Message
{
  private String username;
  private Timestamp timestamp;
  private String contents;
  private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  public Message(String username, String contents)
  {
    this.username = username;
    this.timestamp = null;
    this.contents = contents;
  }

  public void setTimestamp(Timestamp timestamp)
  {
    this.timestamp = timestamp;
  }

  @Override public String toString()
  {
    return sdf.format(timestamp) + " - " + username + ": " + contents;
  }
}
