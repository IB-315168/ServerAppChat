package com.igorbulinski.chat.server;

import com.igorbulinski.chat.model.Message;
import com.igorbulinski.chat.servermodel.RemoteMessage;
import dk.via.remote.observer.RemotePropertyChangeListener;
import dk.via.remote.observer.RemotePropertyChangeSupport;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class RemoteMessageImplementation extends UnicastRemoteObject implements
    RemoteMessage
{
  private final RemotePropertyChangeSupport<Message> support;
  private ArrayList<String> users;
  private Log log = Log.getLog();

  public RemoteMessageImplementation() throws IOException
  {
    this.support = new RemotePropertyChangeSupport<>(this);
    this.users = new ArrayList<>();
  }

  @Override public void sendMessage(Message message)
      throws IOException, ServerNotActiveException
  {
    message.setTimestamp(new Timestamp(new Date().getTime()));
    log.addLog(getClientHost() + " - " + message.toString());
    support.firePropertyChange("newmessage", null, message);
  }

  @Override public void login(String username) throws RemoteException {
    if(users.contains(username)) {
      throw new IllegalArgumentException("Username already in use.");
    }
    users.add(username);
  }

  @Override public void logout(String username) throws RemoteException {
    users.remove(username);
  }

  @Override public ArrayList<String> getUsers() throws RemoteException {
    return users;
  }

  @Override public void addPropertyChangeListener(
      RemotePropertyChangeListener<Message> listener) throws RemoteException
  {
    support.addPropertyChangeListener(listener);
  }

  @Override public void removePropertyChangeListener(
      RemotePropertyChangeListener<Message> listener) throws RemoteException
  {
    support.removePropertyChangeListener(listener);
  }
}
