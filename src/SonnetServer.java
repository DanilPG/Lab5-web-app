import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SonnetServer {
    public static void main(String[] args) {
        int port = 12345;
        List<String> sonnets = loadSonnets("src/sonnets.txt");

        if (sonnets.isEmpty()) {
            System.out.println("Файл с сонетами пуст или отсутствует. Проверьте файл sonnets.txt.");
            return;
        }

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту " + port);

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    System.out.println("Клиент подключен: " + clientSocket.getInetAddress());

                    // Выбор случайного сонета
                    String randomSonnet = getRandomSonnet(sonnets);

                    // Отправка сонета клиенту
                    out.println(randomSonnet);
                    System.out.println("Сонет отправлен клиенту.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static List<String> loadSonnets(String fileName) {
        List<String> sonnets = new ArrayList<>();
        StringBuilder currentSonnet = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Сонет")) {
                    if (currentSonnet.length() > 0) {
                        sonnets.add(currentSonnet.toString().trim());
                        currentSonnet.setLength(0);
                    }
                }
                currentSonnet.append(line).append("\n");
            }

            if (currentSonnet.length() > 0) {
                sonnets.add(currentSonnet.toString().trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sonnets;
    }

    // Метод для выбора случайного сонета
    private static String getRandomSonnet(List<String> sonnets) {
        Random random = new Random();
        return sonnets.get(random.nextInt(sonnets.size()));
    }
}