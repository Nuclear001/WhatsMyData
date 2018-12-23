package nuclear001_productions.whatsmydata.chat.item;

import android.support.annotation.NonNull;

public class WhatsAppChatUserEntry extends WhatsAppChatEntry {
    private String user;
    private String shortMessage;

    public WhatsAppChatUserEntry(String date, String user, String message) {
        super(date, user + ": " + message);
        this.shortMessage = message;
        this.user = user;
    }

    @NonNull
    @Override
    public String getMessage() {
        return shortMessage;
    }

    @NonNull
    public String getUser() {
        return user;
    }

}
