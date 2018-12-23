package nuclear001_productions.whatsmydata.chat.data;

import java.util.ArrayList;
import java.util.List;

import nuclear001_productions.whatsmydata.chat.item.WhatsAppChatEntry;
import nuclear001_productions.whatsmydata.chat.item.WhatsAppChatUserEntry;

public class WhatsAppChatDataHolder {

    private List<WhatsAppChatEntry> whatsAppServiceMessages;
    private List<WhatsAppChatUserEntry> whatsAppUserMessages;

    public WhatsAppChatDataHolder(List<WhatsAppChatEntry> whatsAppServiceMessages, List<WhatsAppChatEntry> whatsAppUserMessages) {
        this.whatsAppServiceMessages = whatsAppServiceMessages;
        ArrayList<WhatsAppChatUserEntry> userEntries = new ArrayList<>();
        for (WhatsAppChatEntry entry : whatsAppUserMessages) {
            if (!(entry instanceof WhatsAppChatUserEntry)) {
                throw new IllegalArgumentException("User messages contains non user chat entry!");
            }
            userEntries.add((WhatsAppChatUserEntry) entry);
        }
        this.whatsAppUserMessages = userEntries;
    }

    public List<WhatsAppChatEntry> getWhatsAppServiceMessages() {
        return whatsAppServiceMessages;
    }

    public List<WhatsAppChatUserEntry> getWhatsAppUserMessages() {
        return whatsAppUserMessages;
    }
}
