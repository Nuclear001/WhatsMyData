package nuclear001_productions.whatsmydata;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

public class ReceiverActivity extends AppCompatActivity {

    private TextView textViewMessageCounter;
    private TextView textViewRandomMessage;
    private Button buttonRandomMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);


        //Init layout
        textViewMessageCounter = findViewById(R.id.text_view_message_counter);
        textViewRandomMessage = findViewById(R.id.text_view_random_message);
        buttonRandomMessage = findViewById(R.id.button_random_message);


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

    public void analyzeData(final List<String> chat) {
        // Do stuff
        System.out.println("It worked!");
        textViewMessageCounter.setText("Nachrichten: " + chat.size());
        buttonRandomMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewRandomMessage.setText("Zufallsnachricht: " + chat.get(randomBetween(chat.size() - 1)));
            }
        });
    }

    private int randomBetween(int max) {
        return new Random().nextInt(max);
    }

}
