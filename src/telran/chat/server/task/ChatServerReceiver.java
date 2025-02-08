package telran.chat.server.task;

import telran.chat.model.Message;
import telran.chat.server.mediation.BlkQueue;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

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
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            while (true) {
                Message message = (Message) ois.readObject();
                messageBox.push(message);
            }
        } catch (IOException e) {
//			e.printStackTrace();
            System.out.println("Client host: " + socket.getInetAddress() + ":" + socket.getPort() + " disconnected");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
