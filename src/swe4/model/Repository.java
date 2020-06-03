package swe4.model;

import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static swe4.model.DishRepository.getDishes;
import static swe4.model.OrderRepository.getOrders;
import static swe4.model.TimeSlotRepository.getTimeSlots;
import static swe4.model.UserRepository.getUsers;
import static swe4.model.UserRepository.receiveUsers;

public class Repository {
  public static void loadData() throws ClassNotFoundException {
    try {
      try (Socket socket = new Socket("localhost", 5004);
           ObjectOutput out = new ObjectOutputStream(socket.getOutputStream())) {
        out.writeObject("users");
        System.out.println("client, requested users from server");
        receiveUsers();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void saveData() throws IOException {
    try (Socket socket = new Socket("localhost", 5004);
         ObjectOutput out = new ObjectOutputStream(socket.getOutputStream())) {
      Object[] userData = new Object[getUsers().size()];
      for (int i = 0; i < userData.length; ++i) {
        userData[i] = getUsers().get(i);
      }
      out.writeObject(userData);
      System.out.println("client, sent users to server: " + userData);

      Object[] dishData = new Object[getDishes().size()];
      for (int i = 0; i < dishData.length; ++i) {
        dishData[i] = getDishes().get(i);
      }
      out.writeObject(dishData);
      System.out.println("client, sent dishes to server: " + dishData);

      Object[] orderData = new Object[getOrders().size()];
      for (int i = 0; i < orderData.length; ++i) {
        orderData[i] = getOrders().get(i);
      }
      out.writeObject(orderData);
      System.out.println("client, sent orders to server: " + orderData);

      Object[] timeSlotData = new Object[getTimeSlots().size()];
      for (int i = 0; i < timeSlotData.length; ++i) {
        timeSlotData[i] = getTimeSlots().get(i);
      }
      out.writeObject(timeSlotData);
      System.out.println("client, sent timeSlots to server: " + timeSlotData);
    }
  }
}