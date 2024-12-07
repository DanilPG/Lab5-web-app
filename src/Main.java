import java.io.*;
import java.net.*;

public class Main {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 12345;

        try (Socket socket = new Socket(serverAddress, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Чтение ответа от сервера
            StringBuilder sonnet = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                sonnet.append(line).append("\n");
            }

            // Печать сонета
            System.out.println("Полученный сонет:\n");
            System.out.println(sonnet);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}