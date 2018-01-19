package partyplaner.partyplaner.Profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.Arrays;

import partyplaner.api.APIService;
import partyplaner.api.ServiceDateReceiver;
import partyplaner.data.party.Party;
import partyplaner.data.user.Gender;
import partyplaner.data.user.I;
import partyplaner.data.user.User;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;
import partyplaner.partyplaner.Veranstaltung.IServiceReceiver;

public class EditProfileActivity extends AppCompatActivity implements IServiceReceiver {

    private String date;
    private ServiceDateReceiver serviceDateReceiver;
    private String name;
    private String birthdate;
    private int gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        defaultSetup();
    }

    @Override
    public void onResume() {
        super.onResume();

        IntentFilter statusIntentFilter = new IntentFilter(Keys.EXTRA_EDIT_PROFILE);
        serviceDateReceiver = new ServiceDateReceiver(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(serviceDateReceiver, statusIntentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(serviceDateReceiver);
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


    private void defaultSetup() {
        final EditText birthdate = findViewById(R.id.edit_profile_birthdate);
        EditText name = findViewById(R.id.edit_profile_name);
        Button save = findViewById(R.id.edit_profile_save);
        Spinner gender = findViewById(R.id.edit_profile_gender);
        date = User.formatDate(I.getMyself().getBirthdate());

        name.setText(I.getMyself().getName());
        birthdate.setText(date);
        gender.setSelection(I.getMyself().getGender());

        birthdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    pickDateTime();
                    birthdate.clearFocus();
                }
            }
        });
        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDateTime();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEdit();
            }
        });
    }

    private void saveEdit() {
        EditText nameText = findViewById(R.id.edit_profile_name);
        EditText birthdateText = findViewById(R.id.edit_profile_birthdate);
        Spinner genderText = findViewById(R.id.edit_profile_gender);

        name = nameText.getText().toString().trim();
        birthdate = birthdateText.getText().toString().trim();
        gender = genderText.getSelectedItemPosition();

        if (!name.equals("") && !birthdate.equals("")) {
            Intent apiHanlder = new Intent(this, APIService.class);
            apiHanlder.putExtra(Keys.EXTRA_URL, "/user/" + I.getMyself().getId() + "?api=" + I.getMyself().getApiKey());
            apiHanlder.putExtra(Keys.EXTRA_REQUEST, "PUT");
            String data = "{\"name\":\"" + name + "\",\"gender\":" + gender + ",\"birthdate\":\"" + formatDate() + "\"}";
            apiHanlder.putExtra(Keys.EXTRA_DATA, data);
            apiHanlder.putExtra(Keys.EXTRA_ID, Keys.EXTRA_PUT_PROFILE);
            apiHanlder.putExtra(Keys.EXTRA_SERVICE_TYPE, Keys.EXTRA_EDIT_PROFILE);
            this.startService(apiHanlder);
        }
    }

    public void pickDateTime() {
        final DecimalFormat formatter = new DecimalFormat("00");
        AlertDialog.Builder pickDate = new AlertDialog.Builder(this);
        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_date_picker, null);
        final DatePicker datePicker = dialogView.findViewById(R.id.datePicker);
        pickDate.setView(dialogView);
        pickDate.setMessage("Wähle den Tag")
                .setPositiveButton("Bestätigen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setDate(formatter.format(datePicker.getDayOfMonth()) + "." + formatter.format(datePicker.getMonth() + 1) + "." + datePicker.getYear());
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
        TextView birthday = findViewById(R.id.edit_profile_birthdate);
        birthday.setText(date);
    }

    private String formatDate() {
        String[] date = this.date.split("\\.");
        return date[2] + "-" + date [1] + "-" + date[0];
    }

    private void setDate(String date) {
        this.date = date;
    }

    @Override
    public void receiveData(String json, String id) {
        Log.e(getClass().getName(), json);
        if (json != null) {
            if (!json.contains("error")) {
                Gson gson = new Gson();
                User i = gson.fromJson(json, User.class);
                I.getMyself().setName(i.getName());
                I.getMyself().setGender(i.getGender());
                Log.e(getClass().getName(), i.getBirthdate().substring(0, 10));
                I.getMyself().setBirthdate(i.getBirthdate().substring(0, 10));
                finish();
            } else {
                Toast.makeText(this, "Bearbeiten fehgeschlagen!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Bearbeiten fehgeschlagen!", Toast.LENGTH_SHORT).show();
        }
    }
}
