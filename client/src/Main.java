import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (DatagramSocket clientSocket = new DatagramSocket()) {
            InetAddress IPAddress = InetAddress.getByName("localhost");
            Scanner scanner = new Scanner(System.in);
            byte[] receivingData = new byte[5];
            byte[] sendingData, receivedData;
            DatagramPacket sendingPacket, receivingPacket;
            int x = 0, y = 0, z = 0;
            String string;

            int choice = 0;
            boolean flag = true;
            while (flag) {
                System.out.println("[1] - ввести значения x, y, z");
                System.out.println("[2] - завершение программы");
                choice = scanner.nextInt();
                switch (choice) {
                    case 1: {
                        System.out.print("Введите x - ");
                        x = scanner.nextInt();
                        System.out.print("Введите y - ");
                        y = scanner.nextInt();
                        System.out.print("Введите z - ");
                        z = scanner.nextInt();
                        System.out.println("Вы ввели: x = " + x + ", y = " + y + ", z = " + z);
                        string = x + ";" + y + ";" + z;
                        sendingData = string.getBytes();

                        sendingPacket = new DatagramPacket(sendingData, sendingData.length, IPAddress, 2525);
                        clientSocket.send(sendingPacket);
                        receivingPacket = new DatagramPacket(receivingData, receivingData.length);
                        clientSocket.receive(receivingPacket);
                        receivedData = receivingPacket.getData();
                        double d = Double.parseDouble(new String(receivedData));
                        System.out.println("~server~: " + d);
                        break;
                    }
                    case 2: {
                        flag = false;
                        break;
                    }
                    default: {
                        System.out.println("Введено неверное значение");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Произошла ошибка");
        }
    }
}