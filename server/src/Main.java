import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Main {
    public static void main(String[] args) {
        try (DatagramSocket serverSocket = new DatagramSocket(2525);
             BufferedWriter file = new BufferedWriter(new FileWriter("output.txt", true)) ) {
            System.out.println("Сервер стартовал...");
            System.out.println("at IP = " + InetAddress.getLocalHost().getHostAddress());
            System.out.println("at port = " + serverSocket.getLocalPort());
            String[] receivedData;
            String s;
            double result;
            InetAddress senderAddress;
            DatagramPacket outputPacket;
            byte[] receivingDataBuffer = new byte[5];
            byte[] sendingDataBuffer;
            DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
            System.out.println("Ожидание сообщения от клиента...");
            double x, y, z;
            while (true) {
                serverSocket.receive(inputPacket);
                receivedData = (new String(inputPacket.getData())).split(";");
                x = Double.parseDouble(receivedData[0]);
                y = Double.parseDouble(receivedData[1]);
                z = Double.parseDouble(receivedData[2]);
                System.out.println("Получено сообщение: \n\t" + "x = " + x + "\ty = " + y + "\tz = " + z);
                result = Func.calculateFunc(x, y, z);
                sendingDataBuffer = (result + "").getBytes();
                System.out.println("~server~:\n\tЗначение функции: " + result);
                s = x + ", " + y + ", " + z + " -> " + result + "\n";
                file.write(s);
                file.flush();
                senderAddress = inputPacket.getAddress();
                int senderPort = inputPacket.getPort();
                outputPacket = new DatagramPacket(sendingDataBuffer, sendingDataBuffer.length, senderAddress, senderPort);
                serverSocket.send(outputPacket);
            }
        } catch (IOException e) {
            System.out.println("Произошла ошибка");
        }
    }
}