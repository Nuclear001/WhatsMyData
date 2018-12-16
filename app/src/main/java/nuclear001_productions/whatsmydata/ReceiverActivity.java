package nuclear001_productions.whatsmydata;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ReceiverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);

        Intent incomingData = getIntent(); // <<--- Breakpoint

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(incomingData.toString());
        builder.show();
    }
}
