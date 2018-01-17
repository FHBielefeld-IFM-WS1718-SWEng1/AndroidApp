package partyplaner.partyplaner.Veranstaltung;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import partyplaner.api.APIService;
import partyplaner.api.ServiceDateReceiver;
import partyplaner.data.user.I;
import partyplaner.data.user.User;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;

public class InviteUser extends AppCompatActivity implements IServiceReceiver{

    private int partyId;
    private User[] allUser;
    private Gson gson;
    private LinearLayout holder;
    private List<EditText> inputField;
    private int count;
    private ServiceDateReceiver serviceDateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_invite_user);
        gson = new Gson();
        partyId = getIntent().getIntExtra(Keys.EXTRA_PARTYID, 0);

        inputField = new ArrayList<>();
        holder = findViewById(R.id.name_holder);
        Button add = findViewById(R.id.add_input_field);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTextEdit();
            }
        });
        final Button confirm = findViewById(R.id.confirm_users);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter statusIntentFilter = new IntentFilter(Keys.EXTRA_SERVICE_INVITE);
        serviceDateReceiver = new ServiceDateReceiver(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(serviceDateReceiver, statusIntentFilter);
        getData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(serviceDateReceiver);
    }

    private void addTextEdit() {
        final EditText firstInput = new EditText(this);
        firstInput.setHint("Name");
        firstInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    firstInput.setTextColor(Color.BLACK);
                }
            }
        });
        holder.addView(firstInput);
        holder.refreshDrawableState();
        inputField.add(firstInput);
    }

    private void confirm() {
        boolean valid = true;
        List<Integer> ids = new ArrayList<>();
        for (EditText text : inputField) {
            String name = text.getText().toString();
            if (!name.trim().equals("")) {
                int id = contains(name);
                if (id >= 0) {
                    Log.e(getClass().getName(), id + "");
                    ids.add(id);
                } else {
                    Log.e(getClass().getName(), id + "Invlaid");
                    valid = false;
                    text.setTextColor(Color.RED);
                }
            } else {
                holder.removeView(text);
            }
        }

        if (valid) {
            count = ids.size();
            for (int id : ids) {
                Intent apiHanlder = new Intent(this, APIService.class);
                apiHanlder.putExtra(Keys.EXTRA_URL, "/party/guest?api=" + I.getMyself().getApiKey());
                apiHanlder.putExtra(Keys.EXTRA_REQUEST, "POST");
                String data = "{\"userid\":" + id + ",\"partyid\":" + partyId + "}";
                Log.e(getClass().getName(), data);
                apiHanlder.putExtra(Keys.EXTRA_DATA, data);
                apiHanlder.putExtra(Keys.EXTRA_ID, Keys.EXTRA_LOAD_PARTY);
                apiHanlder.putExtra(Keys.EXTRA_SERVICE_TYPE, Keys.EXTRA_SERVICE_INVITE);
                this.startService(apiHanlder);
            }
        }
    }

    private int contains(String name) {
        for (int i = 0; i < allUser.length; i++) {
            if (allUser[i].getName().equals(name)) {
                return allUser[i].getId();
            }
        }
        return -1;
    }

    private void getData() {
        startLoading();

        Intent apiHanlder = new Intent(this, APIService.class);
        apiHanlder.putExtra(Keys.EXTRA_URL, "/user?api=" + I.getMyself().getApiKey());
        apiHanlder.putExtra(Keys.EXTRA_REQUEST, "GET");
        String data = null;
        apiHanlder.putExtra(Keys.EXTRA_DATA, data);
        apiHanlder.putExtra(Keys.EXTRA_ID, Keys.EXTRA_ADD_USERS);
        apiHanlder.putExtra(Keys.EXTRA_SERVICE_TYPE, Keys.EXTRA_SERVICE_INVITE);
        this.startService(apiHanlder);

        Log.e(getClass().getName(), "service startet");
    }

    private void startLoading() {
        RelativeLayout rl = findViewById(R.id.loading_view_invite);
        rl.setVisibility(View.VISIBLE);
    }

    @Override
    public void receiveData(String json, String id) {
        Log.e(getClass().getName(), json);
        switch (id) {
            case Keys.EXTRA_ADD_USERS:
                if (json.contains("error")) {
                    Toast.makeText(this, "User konnten nicht geladen werden!", Toast.LENGTH_SHORT).show();
                } else {
                    json = json.replaceAll(".*?\\[", "[");
                    json = json.replaceAll("].", "]");
                    allUser = gson.fromJson(json, User[].class);
                    Log.e(getClass().getName(), allUser.length + "");
                    endLoading();
                    addTextEdit();
                    Log.e(getClass().getName(), "service receiverd");
                }
                break;
            case Keys.EXTRA_LOAD_PARTY:
                if(json.contains("error")) {
                    Toast.makeText(this, "Fehler beim erstellen!", Toast.LENGTH_SHORT).show();
                } else {
                    count--;
                    if (count == 0) {
                        finish();
                    }
                }
                break;

        }
    }

    private void endLoading() {
        RelativeLayout rl = findViewById(R.id.loading_view_invite);
        rl.setVisibility(View.INVISIBLE);
    }
}
