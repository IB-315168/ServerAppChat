package com.igorbulinski.chat.server;

import com.igorbulinski.chat.servermodel.RemoteMessage;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StartServer
{
  public static void main(String[] args)
      throws IOException, AlreadyBoundException
  {
    Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
    RemoteMessage comm = new RemoteMessageImplementation();
    registry.bind("comm", comm);
    System.out.println("Server running on " + Registry.REGISTRY_PORT);
  }
}
