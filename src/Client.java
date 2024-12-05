import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket clientSocket = new Socket("localhost", 8080);
             InputStream serverInput = clientSocket.getInputStream();
             OutputStream serverOutput = clientSocket.getOutputStream();
             Scanner userInput = new Scanner(System.in)) {

            // Запрос числа от пользователя
            System.out.print("Вычисление простых чисел\nВведите верхний предел: ");
            int upperLimit = userInput.nextInt();

            // Отправка числа серверу
            DataOutputStream serverDataOut = new DataOutputStream(serverOutput);
            serverDataOut.writeInt(upperLimit);
            serverDataOut.flush();
            System.out.println("Клиент отправил число: " + upperLimit);

            // Получение ответа от сервера
            DataInputStream serverDataIn = new DataInputStream(serverInput);
            String serverResponse = serverDataIn.readUTF();
            System.out.println("Клиент получил ответ: " + serverResponse);

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
