package com.igorbulinski.chat.servermodel;

import com.igorbulinski.chat.model.Message;
import dk.via.remote.observer.RemotePropertyChangeListener;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.ArrayList;

public interface RemoteMessage extends Remote
{
  void sendMessage(Message message) throws IOException,
      ServerNotActiveException;
  void login(String username) throws RemoteException;
  void logout(String username) throws RemoteException;

  ArrayList<String> getUsers() throws RemoteException;

  void addPropertyChangeListener(RemotePropertyChangeListener<Message> listener) throws
      RemoteException;

  void removePropertyChangeListener(RemotePropertyChangeListener<Message> listener) throws RemoteException;
}
