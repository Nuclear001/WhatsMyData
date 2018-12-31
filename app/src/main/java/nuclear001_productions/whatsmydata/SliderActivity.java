package nuclear001_productions.whatsmydata;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import nuclear001_productions.whatsmydata.chat.data.WhatsAppChatDataHolder;

public class SliderActivity extends AppCompatActivity {

    private static final String TAG = "SliderActivity";

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;

    private TextView textViewMessageCounter;
    private TextView textViewRandomMessage;
    private Button buttonRandomMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        Log.d(TAG, "onCreate: Starting");

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


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
        viewModel.getData().observe(this, new Observer<WhatsAppChatDataHolder>() {
            @Override
            public void onChanged(@Nullable WhatsAppChatDataHolder data) {
                analyzeData(data);
            }
        });
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(),"Basics");
        adapter.addFragment(new Tab2Fragment(),"Charts");
        adapter.addFragment(new Tab3Fragment(),"Extended");
        viewPager.setAdapter(adapter);
    }

    public void analyzeData(final WhatsAppChatDataHolder holder) {
        // Do stuff
        System.out.println("It worked!");
        textViewMessageCounter.setText("Nachrichten: " + holder.getWhatsAppUserMessages().size());
        buttonRandomMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewRandomMessage.setText("Zufallsnachricht: " + holder.getWhatsAppUserMessages().get(randomBetween(holder.getWhatsAppUserMessages().size())));
            }
        });
    }

    private int randomBetween(int max) {
        return new Random().nextInt(max);
    }



}
