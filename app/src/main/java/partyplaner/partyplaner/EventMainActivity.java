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
import android.widget.Toast;

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
import partyplaner.partyplaner.Veranstaltung.IServiceReceiver;

public class EventMainActivity extends AppCompatActivity implements IEventDataManager, IServiceReceiver{

    private int id;
    private Party party;
    private Gson gson;
    private EventMainFragment eventMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_main);
        eventMainFragment = (EventMainFragment)getFragmentManager().findFragmentById(R.id.event_fragment);
        id = getIntent().getIntExtra(Keys.EXTRA_PARTYID, 0);

        gson = new Gson();

        if(id != 0) {
            getData();
        } else {
            startLoading();
        }
    }

    private void getData() {
        startLoading();

        Intent apiHanlder = new Intent(this, APIService.class);
        apiHanlder.putExtra(Keys.EXTRA_URL, "/party/" + id + "?api=" + I.getMyself().getApiKey());
        apiHanlder.putExtra(Keys.EXTRA_REQUEST, "GET");
        String data = null;
        apiHanlder.putExtra(Keys.EXTRA_DATA, data);
        apiHanlder.putExtra(Keys.EXTRA_ID, Keys.EXTRA_LOAD_PARTY);
        apiHanlder.putExtra(Keys.EXTRA_SERVICE_TYPE, Keys.EXTRA_SERVICE);
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

    @Override
    public void receiveData(String json, String id) {
        Log.e(this.getClass().getName(), json);
        switch (id) {
            case Keys.EXTRA_LOAD_PARTY:
                json = json.replaceAll(",\"ersteller\":", ",\"owner\":");
                json = json.replaceAll("User", "user");

                if (json.contains("error")) {
                    Toast.makeText(this, "Daten konnten nicht geladen werden!", Toast.LENGTH_SHORT).show();
                } else {
                    party = gson.fromJson(json, Party.class);
                    if (party != null) {
                        Log.e(this.getClass().getName(), "party geladen");
                        endLoading();
                        eventMainFragment.receiveData();
                    }
                }
                break;
                //urbiatorbi.va
                //armen
            case Keys.EXTRA_PUT_TASK:
                endLoading();
                if(json.contains("error")) {
                    Toast.makeText(this, "Erstellen fehlgeschlagen!", Toast.LENGTH_SHORT).show();
                } else {
                    getData();
                }
                break;

            case Keys.EXTRA_DELETE_TASK:
                endLoading();
                if(json.contains("error")) {
                    Toast.makeText(this, "Löschen fehlgeschlagen!", Toast.LENGTH_SHORT).show();
                } else {
                    getData();
                }
                break;
            case Keys.EXTRA_POST_TASK:
                Log.e(getClass().getName(), json);
                break;

        }
    }

    private void endLoading() {
        LinearLayout eventHolder = findViewById(R.id.event_loading_indicator);
        eventHolder.setVisibility(View.INVISIBLE);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void startLoading() {
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        LinearLayout eventHolder = findViewById(R.id.event_loading_indicator);
        eventHolder.setVisibility(View.VISIBLE);

    }
}
