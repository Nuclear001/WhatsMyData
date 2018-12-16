package nuclear001_productions.whatsmydata;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReceiverActivity extends AppCompatActivity {

    private TextView textViewMessageCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);


        //Init layout
        textViewMessageCounter = findViewById(R.id.text_view_message_counter);


        Intent incomingData = getIntent();
        final Uri uri1 = incomingData.getClipData().getItemAt(0).getUri();

        ReceiverViewModel viewModel = ViewModelProviders.of(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            @SuppressWarnings("unchecked")
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                try {
                    return (T) new ReceiverViewModel(getContentResolver().openInputStream(uri1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return (T) new ReceiverViewModel(null);
            }
        }).get(ReceiverViewModel.class);
        viewModel.getData().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                analyzeData(strings);
            }
        });
    }

    public void analyzeData(List<String> chat) {
        // Do stuff
        System.out.println("It worked!");
        textViewMessageCounter.setText("Nachrichten: " + chat.size());
    }

}
