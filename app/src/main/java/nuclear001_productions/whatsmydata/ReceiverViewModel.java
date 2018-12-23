package nuclear001_productions.whatsmydata;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import java.io.InputStream;

import nuclear001_productions.whatsmydata.chat.data.WhatsAppChatDataHolder;
import nuclear001_productions.whatsmydata.chat.parser.WhatsAppChatParser;

public class ReceiverViewModel extends ViewModel {

    private MutableLiveData<WhatsAppChatDataHolder> data;
    private InputStream chatStream;

    public ReceiverViewModel(InputStream stream) {
        this.data = new MutableLiveData<>();
        this.chatStream = stream;
        new ChatParser().execute();
    }

    public LiveData<WhatsAppChatDataHolder> getData() {
        return data;
    }

    private class ChatParser extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                WhatsAppChatDataHolder holder = new WhatsAppChatParser(chatStream).analyzeChat();
                data.postValue(holder);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
