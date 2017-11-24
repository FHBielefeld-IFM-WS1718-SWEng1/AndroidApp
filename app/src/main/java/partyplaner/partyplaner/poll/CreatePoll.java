package partyplaner.partyplaner.poll;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.app.Fragment;

import partyplaner.partyplaner.R;

public class CreatePoll extends AppCompatActivity {

    public static final String OPTION_NUMBER = "number";

    LinearLayout options;

    private int optionCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_poll);

        Button btnAddOption = findViewById(R.id.btn_add);
        btnAddOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOption();
            }
        });

        addOption();
    }

    public void addOption() {
        optionCount++;

        options = findViewById(R.id.options);

        Bundle arguments = new Bundle();
        arguments.putInt(OPTION_NUMBER, optionCount);

        CreatePollOption option = new CreatePollOption();
        option.setArguments(arguments);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.options, option);
        fragmentTransaction.commit();
    }

    public void deleteOption(Fragment option) {
        getFragmentManager().beginTransaction().remove(option).commit();
    }
}
