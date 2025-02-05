package telran.chat.model;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

// Homework
public class Message implements Serializable {
    public static final long serialVersionUID = 20250205111140L;
    private String nickName;
    private LocalTime time;
    private String message;

    public Message(String nickName, LocalTime time, String message) {
        this.nickName = nickName;
        this.time = time;
        this.message = message;
    }

    public String getNickName() {
        return nickName;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return nickName + " [" + time.format(DateTimeFormatter.ofPattern("HH:mm")) + "] " + message;
    }
}
