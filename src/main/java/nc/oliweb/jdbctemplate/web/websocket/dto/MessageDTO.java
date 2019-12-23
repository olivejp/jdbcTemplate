package nc.oliweb.jdbctemplate.web.websocket.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class MessageDTO implements Serializable {
    private String from;
    private String text;
    private Instant time;

    public MessageDTO() {
    }

    public MessageDTO(String from, String text, Instant time) {
        this.from = from;
        this.text = text;
        this.time = time;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageDTO that = (MessageDTO) o;
        return Objects.equals(from, that.from) &&
            Objects.equals(text, that.text) &&
            Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, text, time);
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
            "from='" + from + '\'' +
            ", text='" + text + '\'' +
            ", time=" + time +
            '}';
    }
}
