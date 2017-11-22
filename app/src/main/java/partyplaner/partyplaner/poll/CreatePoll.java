package partyplaner.partyplaner.poll;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import partyplaner.partyplaner.R;

public class CreatePoll extends AppCompatActivity {

    LinearLayout options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_poll);

        options = findViewById(R.id.options);

        Bundle arguments = new Bundle();
        arguments.putInt("number", 1);

        CreatePollOption option = new CreatePollOption();
        option.setArguments(arguments);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.options, option);
        fragmentTransaction.commit();
    }
}
