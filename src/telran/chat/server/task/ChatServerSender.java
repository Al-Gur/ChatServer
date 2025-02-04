package telran.chat.server.task;

import telran.chat.model.Message;
import telran.chat.server.mediation.BlkQueue;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ChatServerSender implements Runnable {
    private final BlkQueue<Message> messageBox;
    private final Set<ObjectOutputStream> clients;

    public ChatServerSender(BlkQueue<Message> messageBox) {
        this.messageBox = messageBox;
        clients = new HashSet<>();
    }

    public synchronized boolean addClient(Socket socket) throws IOException {
        return clients.add(new ObjectOutputStream(socket.getOutputStream()));
        //PrintWriter(socket.getOutputStream(), true));
    }

    @Override
    public void run() {
        while (true) {
            Message message = messageBox.pop();
            synchronized (this) {
                Iterator<ObjectOutputStream> iterator = clients.iterator();
                while (iterator.hasNext()) {
                    ObjectOutputStream outputStream = iterator.next();
                    try {
                        outputStream.writeObject(message);
                        outputStream.flush();
                    } catch (IOException e) {
                        iterator.remove();
                        //e.printStackTrace();
                    }
//                    if (outputStream.) {
//                        iterator.remove();
//                    }
                }
                System.out.println("size = " + clients.size());
            }
        }
    }
}
