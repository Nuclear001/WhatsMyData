package nuclear001_productions.whatsmydata.chat.item;

import android.support.annotation.NonNull;

public abstract class ChatEntry {

    private String message;

    public ChatEntry(String message) {
        this.message = message;
    }

    @NonNull
    public String getMessage() {
        return message;
    }

    @NonNull
    @Override
    public String toString() {
        return message;
    }
}
