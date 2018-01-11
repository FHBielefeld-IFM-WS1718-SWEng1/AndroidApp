package partyplaner.partyplaner;

import android.app.Fragment;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.IBinder;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.Response;
import okio.Utf8;
import partyplaner.api.APIService;
import partyplaner.api.GeneralAPIRequestHandler;
import partyplaner.api.RouteType;
import partyplaner.api.ServiceDateReceiver;
import partyplaner.data.party.Guest;
import partyplaner.data.party.Party;
import partyplaner.data.party.Task;
import partyplaner.data.user.I;
import partyplaner.partyplaner.Veranstaltung.EventMainFragment;
import partyplaner.partyplaner.Veranstaltung.IEventDataManager;

public class EventMainActivity extends AppCompatActivity implements IEventDataManager{

    private Party party;
    private Gson gson;
    private EventMainFragment eventMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_main);
        eventMainFragment = (EventMainFragment)getFragmentManager().findFragmentById(R.id.event_fragment);

        gson = new Gson();

        Intent apiHanlder = new Intent(this, APIService.class);
        apiHanlder.putExtra(Keys.EXTRA_URL, "/party/241121?api=" + I.getMyself().getApiKey());
        apiHanlder.putExtra(Keys.EXTRA_REQUEST, "GET");
        String data = null;
        apiHanlder.putExtra(Keys.EXTRA_DATA, data);
        this.startService(apiHanlder);

        IntentFilter statusIntentFilter = new IntentFilter(Keys.EXTRA_SERVICE);
        ServiceDateReceiver serviceDateReceiver = new ServiceDateReceiver(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(serviceDateReceiver, statusIntentFilter);
    }

    @Override
    public String[] getGeneralInformations() {
        String[] infos = {party.getName(), party.getOwner().getName(), party.getStartDate(), party.getLocation(), party.getDescription()};
        return infos;
    }

    @Override
    public Party getParty() {
        return party;
    }

    public void receiveData(String json) {
        json = json.replaceAll(",\"ersteller\":", ",\"owner\":");
        json = json.replaceAll("User", "user");

        if (json.contains("Error")) {
            //TODO: Fehlerbhandlung
        } else {
            party = gson.fromJson(json, Party.class);
            if (party != null) {
                eventMainFragment.receiveData();

                LinearLayout eventHolder = findViewById(R.id.event_loading_indicator);
                eventHolder.setVisibility(View.INVISIBLE);
                ProgressBar progressBar = findViewById(R.id.progressBar);
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    }
}
