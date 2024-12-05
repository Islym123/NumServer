import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8080);
        System.out.println("Сервер запущен и ожидает подключения клиентов...");

        while (true) {
            try (Socket client = server.accept();
                 InputStream clientInput = client.getInputStream();
                 OutputStream clientOutput = client.getOutputStream()) {

                System.out.println("Подключён новый клиент: " + client.getInetAddress());

                // Чтение данных от клиента
                DataInputStream clientDataIn = new DataInputStream(clientInput);
                int upperLimit = clientDataIn.readInt();
                System.out.println("Сервер получил число: " + upperLimit);

                // Вычисление простых чисел
                String primeNumbers = IntStream.rangeClosed(1, upperLimit)
                        .filter(Server::isPrimeNumber)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(", "));

                // Отправка результата клиенту
                DataOutputStream clientDataOut = new DataOutputStream(clientOutput);
                clientDataOut.writeUTF(primeNumbers);
                clientDataOut.flush();
                System.out.println("Сервер отправил список простых чисел: " + primeNumbers);
            }
        }
    }

    public static boolean isPrimeNumber(final int value) {
        if (value < 2) return false;
        return IntStream.rangeClosed(2, (int) Math.sqrt(value)).allMatch(divisor -> value % divisor != 0);
    }
}
