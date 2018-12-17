package nuclear001_productions.whatsmydata;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReceiverViewModel extends ViewModel {

    private MutableLiveData<List<String>> data;
    private InputStream chatStream;

    public ReceiverViewModel(InputStream stream) {
        this.data = new MutableLiveData<>();
        this.chatStream = stream;
        new ChatParser().execute();
    }

    public LiveData<List<String>> getData() {
        return data;
    }

    private class ChatParser extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            ArrayList<String> chatList = new ArrayList<>();
            try {
                BufferedReader stream = new BufferedReader(new InputStreamReader(chatStream));

                String line;

                while ((line = stream.readLine()) != null) {
                    chatList.add(line);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            data.postValue(chatList);
            return null;
        }
    }
}
