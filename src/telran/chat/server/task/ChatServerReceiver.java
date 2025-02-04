package telran.chat.server.task;

import telran.chat.model.Message;
import telran.chat.server.mediation.BlkQueue;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ChatServerReceiver implements Runnable {
    private final Socket socket;
    private final BlkQueue<Message> messageBox;

    public ChatServerReceiver(Socket socket, BlkQueue<Message> messageBox) {
        this.socket = socket;
        this.messageBox = messageBox;
    }

    @Override
    public void run() {
        try (Socket socket = this.socket) {
            //           BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            while (true) {
//                String message = socketReader.readLine();
                Message message = (Message) inputStream.readObject();
                if (message == null) {
                    System.out.println("Connection: " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + ", closed");
                    break;
                }
                messageBox.push(message);
            }
        } catch (EOFException | SocketException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
