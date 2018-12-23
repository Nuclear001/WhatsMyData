package nuclear001_productions.whatsmydata.chat.item;

import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WhatsAppChatEntry extends ChatEntry {
    private static DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault());
    private static DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
    private static DateFormat dateTimeFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.getDefault());
    private static SimpleDateFormat parseFormat = new SimpleDateFormat("dd.MM.yy, HH:mm", Locale.getDefault());

    private String shortMessage;
    private Date date;

    public WhatsAppChatEntry(String date, String message) {
        super(date + " - " + message);
        this.shortMessage = message;
        try {
            this.date = parseFormat.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    public Date getDate() {
        return date;
    }

    @NonNull
    @Override
    public String getMessage() {
        return shortMessage;
    }

    @NonNull
    public String getTimeAsString() {
        return timeFormat.format(date);
    }

    @NonNull
    public String getDateAsString() {
        return dateFormat.format(date);
    }

    @NonNull
    public String getDateTimeAsString() {
        return dateTimeFormat.format(date);
    }


}
