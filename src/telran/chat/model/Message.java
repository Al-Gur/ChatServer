package telran.chat.model;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

// Homework
public class Message implements Serializable {
    private String nickName;
    private LocalTime time;
    private String message;

    public Message(String nickName, LocalTime time, String message) {
        this.nickName = nickName;
        this.time = time;
        this.message = message;
    }

    @Override
    public String toString() {
        return nickName + " [" + time.format(DateTimeFormatter.ofPattern("HH:mm")) + "] " + message;
    }
}
