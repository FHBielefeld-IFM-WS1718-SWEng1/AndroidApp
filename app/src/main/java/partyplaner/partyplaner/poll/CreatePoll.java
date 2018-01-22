package partyplaner.partyplaner.poll;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.app.Fragment;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import partyplaner.api.APIService;
import partyplaner.api.ServiceDateReceiver;
import partyplaner.data.party.*;
import partyplaner.data.party.Poll;
import partyplaner.data.user.I;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;
import partyplaner.partyplaner.Veranstaltung.IServiceReceiver;

public class CreatePoll extends AppCompatActivity implements IServiceReceiver{

    public static final String OPTION_NUMBER = "number";
    private int partyId;
    private int votingid;
    private List<CreatePollOption> optionFragments = new ArrayList<>();

    LinearLayout options;

    private int optionCount = 0;
    private ServiceDateReceiver serviceDateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_poll);

        partyId = getIntent().getIntExtra(Keys.EXTRA_PARTYID, 0);
        Button btnAddOption = findViewById(R.id.btn_add);
        btnAddOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOption();
            }
        });

        final Button createPoll = findViewById(R.id.create_poll);
        createPoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPoll();
            }
        });

        addOption();
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter statusIntentFilter = new IntentFilter(Keys.EXTRA_POLL);
        serviceDateReceiver = new ServiceDateReceiver(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(serviceDateReceiver, statusIntentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(serviceDateReceiver);
    }

    private void createPoll() {
        EditText nameEditText = findViewById(R.id.question);
        String name = nameEditText.getText().toString().trim();
        if (!name.equals("")) {
            String data = "{\"abstimmungsname\":\"" + name + "\",\"party_id\":" + partyId + "}";
            Log.e(getClass().getName(), data);
            startService("/party/vote?api=" + I.getMyself().getApiKey(), "POST", data, Keys.EXTRA_CREATE_POLL);
        }
    }

    private void startService(String url, String request, String data, String id) {
        Intent apiHanlder = new Intent(this, APIService.class);
        apiHanlder.putExtra(Keys.EXTRA_URL, url);
        apiHanlder.putExtra(Keys.EXTRA_REQUEST, request);
        apiHanlder.putExtra(Keys.EXTRA_DATA, data);
        apiHanlder.putExtra(Keys.EXTRA_ID, id);
        apiHanlder.putExtra(Keys.EXTRA_SERVICE_TYPE, Keys.EXTRA_POLL);
        this.startService(apiHanlder);
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

    public void addOption() {
        optionCount++;

        options = findViewById(R.id.options);

        Bundle arguments = new Bundle();
        arguments.putInt(OPTION_NUMBER, optionCount);

        CreatePollOption option = new CreatePollOption();
        option.setArguments(arguments);
        optionFragments.add(option);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.options, option);
        fragmentTransaction.commit();
    }

    public void deleteOption(Fragment option) {
        optionFragments.remove(option);
        getFragmentManager().beginTransaction().remove(option).commit();
    }

    @Override
    public void receiveData(String json, String id) {
        if (json != null) {
            Log.e(getClass().getName(), id + ": " +json);
            switch (id) {
                case Keys.EXTRA_CREATE_POLL:
                    if (!json.contains("error")) {
                        Gson gson = new Gson();
                        partyplaner.data.party.Poll poll = gson.fromJson(json, Poll.class);
                        votingid = poll.getId();
                        createOptions();
                    } else {
                        Toast.makeText(this, "Abstimmung konnte nicht erstellt werden!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Keys.EXTRA_POLL_CHOICE:
                    optionCount--;
                    if (optionCount <= 0) {
                        finish();
                    }
            }
        }
    }

    private void createOptions() {
        for (CreatePollOption fragment : optionFragments) {
            fragment.postOption(votingid);
        }
    }


}
