package wtf.nuf.exeter.events;

import wtf.nuf.exeter.mcapi.eventsystem.Event;

public class ChatMessageEvent extends Event {
    private String message;

    public ChatMessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
