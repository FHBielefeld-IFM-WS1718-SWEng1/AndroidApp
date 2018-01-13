package partyplaner.partyplaner.Veranstaltung;

import partyplaner.api.APIService;
import partyplaner.api.GeneralAPIRequestHandler;
import partyplaner.api.RouteType;
import partyplaner.api.ServiceDateReceiver;
import partyplaner.data.party.Party;
import partyplaner.data.user.I;
import partyplaner.partyplaner.EventMainActivity;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;

import java.sql.Array;
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.Arrays;

public class EditEventActivity extends AppCompatActivity {

    private String date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        final TextView when = findViewById(R.id.event_when);
        when.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    pickDateTime();
                    when.clearFocus();
                }
            }
        });
        when.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDateTime();
            }
        });

        Button button = findViewById(R.id.create_party_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });


    }

    public void createUser(){
        TextView what = findViewById(R.id.event_who);
        TextView where = findViewById(R.id.event_where);
        TextView description = findViewById(R.id.event_description);
        String whatText = what.getText().toString().trim();
        String whereText = where.getText().toString().trim();
        String descriptionText = description.getText().toString().trim();
        if (!whatText.equals("") && !whereText.equals("") && !descriptionText.equals("") && !date.equals("")) {
            String dateTime = formatDate();
            String url = "/party?api=" + I.getMyself().getApiKey();
            String data = "{\"id\":0,\"name\":\"" + whatText + "\",\"description\":\"" + descriptionText +
                    "\",\"startDate\":\"" + dateTime + "\",\"endDate\":null,\"location\":\"" + whereText + "\"}";

            Log.e(EditEventActivity.class.getName(), data);
            String answer = GeneralAPIRequestHandler.request(url, RouteType.POST, data);
            Log.e(EditEventActivity.class.getName(), answer);
            if (!answer.contains("error")) {
                Intent intent = new Intent(this, EventMainActivity.class);
                Gson gson = new Gson();
                Party party = gson.fromJson(answer, Party.class);
                intent.putExtra(Keys.EXTRA_PARTYID, party.getId());
                startActivity(intent);
            }
        }
    }

    private String formatDate() {
        DecimalFormat formatter = new DecimalFormat("00");
        String[] time = (this.date.split(" ")[1]).split(":");
        String[] date = (this.date.split(" ")[0]).split("\\.");
         for(int i = 0; i < time.length; i++) {
             time[i] = formatter.format(Integer.parseInt(time[i]));
         }
        for(int i = 0; i < date.length - 1; i++) {
            date[i] = formatter.format(Integer.parseInt(date[i]));
        }
        Log.e(this.getClass().getName(), Arrays.toString(time) + Arrays.toString(date));
        return String.format("%s-%s-%sT%s:%s:00.000Z", date[2], date[1], date[0], time[0], time[1]);
    }

    public void pickDateTime() {
        AlertDialog.Builder pickDate = new AlertDialog.Builder(this);
        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_date_picker, null);
        final DatePicker datePicker = dialogView.findViewById(R.id.datePicker);
        pickDate.setView(dialogView);
        pickDate.setMessage("Wähle den Tag")
                .setPositiveButton("Bestätigen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setDate(datePicker.getDayOfMonth() + "." + (datePicker.getMonth() + 1) + "." + datePicker.getYear());
                pickTime();
                dialog.cancel();
            }
        }).setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        pickDate.create().show();
    }

    private void pickTime() {
        AlertDialog.Builder pickDate = new AlertDialog.Builder(this);
        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_time_picker, null);
        final TimePicker timePicker = dialogView.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        pickDate.setView(dialogView);
        pickDate.setMessage("Wähle die Uhrzeit")
                .setPositiveButton("Bestätigen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setDate(getDate() + " " + timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute());
                        setTime();
                        dialog.cancel();
                    }
                }).setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        pickDate.create().show();
    }

    private void setTime() {
        TextView when = findViewById(R.id.event_when);
        when.setText(date);
    }

    private String getDate() {
        return date;
    }

    private void setDate(String date) {
        this.date = date;
    }
}
