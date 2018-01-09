package partyplaner.partyplaner;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.Response;
import okio.Utf8;
import partyplaner.api.GeneralAPIRequestHandler;
import partyplaner.api.RouteType;
import partyplaner.data.party.Party;
import partyplaner.data.user.I;
import partyplaner.partyplaner.Veranstaltung.EventMainFragment;

public class EventMainActivity extends AppCompatActivity implements IFragmentDataManeger{

    private Party party;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_main);

        gson = new Gson();

        Intent args = getIntent();
        String partyJson = args.getStringExtra(Keys.EXTRA_PARTY);

        partyJson = partyJson.replaceAll(",\"ersteller\":", ",\"owner\":");
        partyJson = partyJson.replaceAll("User", "user");
        Log.e("EventMainActivity", partyJson);

        party = gson.fromJson(partyJson, Party.class);
    }

    @Override
    public Party[] getParties() {
        return new Party[0];
    }

    @Override
    public Party[] getOwnParties() {
        return new Party[0];
    }
}
