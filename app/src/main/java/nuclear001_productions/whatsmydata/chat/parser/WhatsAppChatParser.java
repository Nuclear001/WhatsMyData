package nuclear001_productions.whatsmydata.chat.parser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nuclear001_productions.whatsmydata.chat.data.WhatsAppChatDataHolder;
import nuclear001_productions.whatsmydata.chat.item.WhatsAppChatEntry;
import nuclear001_productions.whatsmydata.chat.item.WhatsAppChatUserEntry;

public class WhatsAppChatParser implements ChatParser<WhatsAppChatDataHolder> {

    public static final Pattern CHAT_USER_ENTRY = Pattern.compile("(\\d\\d\\.\\d\\d.\\d\\d, \\d\\d:\\d\\d) - (.*): (.*)");
    public static final Pattern CHAT_ENTRY = Pattern.compile("(\\d\\d\\.\\d\\d\\.\\d\\d, \\d\\d:\\d\\d) - (.*)");
    private InputStream stream;

    private int lastEntryType = -1;
    private ArrayList<WhatsAppChatEntry> chatEntries = new ArrayList<>();
    private ArrayList<WhatsAppChatEntry> userEntries = new ArrayList<>();
    private StringBuilder entryBuilder = new StringBuilder();

    public WhatsAppChatParser(InputStream stream) {
        this.stream = stream;
    }

    public WhatsAppChatDataHolder analyzeChat() throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line;

        while ((line = reader.readLine()) != null) {
            if (CHAT_USER_ENTRY.matcher(line).matches()) {
                addEntry();
                lastEntryType = 1;
            } else if (CHAT_ENTRY.matcher(line).matches()) {
                addEntry();
                lastEntryType = 0;
            }
            entryBuilder.append(line);
        }
        addEntry();

        return new WhatsAppChatDataHolder(chatEntries, userEntries);
    }

    private void addEntry() {

        if (lastEntryType == 0) {
            Matcher matcher = CHAT_ENTRY.matcher(entryBuilder.toString());
            matcher.find();
            WhatsAppChatEntry lastEntry = new WhatsAppChatEntry(matcher.group(1), matcher.group(2));
            chatEntries.add(lastEntry);
        } else if (lastEntryType == 1) {
            Matcher matcher = CHAT_USER_ENTRY.matcher(entryBuilder.toString());
            matcher.find();
            WhatsAppChatEntry lastEntry = new WhatsAppChatUserEntry(matcher.group(1), matcher.group(2), matcher.group(3));
            userEntries.add(lastEntry);
        }
        entryBuilder = new StringBuilder();
    }

}
