package partyplaner.partyplaner.Veranstaltung;

import partyplaner.api.APIService;
import partyplaner.api.ServiceDateReceiver;
import partyplaner.data.party.Party;
import partyplaner.data.user.I;
import partyplaner.partyplaner.EventMainActivity;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Arrays;

public class EditEventActivity extends AppCompatActivity implements IServiceReceiver{

    private String date = "";
    private boolean edit;
    private int partyId;
    private ServiceDateReceiver serviceDateReceiver;
    private ImageView picture;
    private Bitmap pictureBitmap;
    private Uri pictureData;
    private String filename;
    private static final int PICK_PICTURE = 79;
    private boolean imageChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageChanged = false;
        setContentView(R.layout.activity_edit_event);
        edit = getIntent().getBooleanExtra(Keys.EXTRA_EDIT_PARTY, false);

        setUpView();
    }

    private void setUpView() {
        Button button = findViewById(R.id.create_party_button);
        if (edit) {
            button.setText("Bestätigen");
            setText();
        }

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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit && !imageChanged) {
                    editUser(filename);
                } else if (!edit && !imageChanged){
                    createUser("");
                } else {
                    editPicture();
                }
            }
        });
        picture = findViewById(R.id.EventImage);
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, PICK_PICTURE);
            }
        });
    }

    private void editPicture() {
        Intent apiHandler = new Intent(this, APIService.class);
        apiHandler.putExtra(Keys.EXTRA_URL, "/image?api=" + I.getMyself().getApiKey());
        apiHandler.putExtra(Keys.EXTRA_REQUEST, "POST");
        String data = null;
        apiHandler.putExtra(Keys.EXTRA_DATA, data);
        apiHandler.putExtra(Keys.EXTRA_ID, Keys.EXTRA_PUT_PROFILE);
        apiHandler.putExtra(Keys.EXTRA_SERVICE_TYPE, Keys.EXTRA_EDIT_PARTY_SERVICE);
        apiHandler.setData(pictureData);
        this.startService(apiHandler);
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter statusIntentFilter = new IntentFilter(Keys.EXTRA_EDIT_PARTY_SERVICE);
        serviceDateReceiver = new ServiceDateReceiver(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(serviceDateReceiver, statusIntentFilter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(serviceDateReceiver);
    }

    private void setText() {
        String name = getIntent().getStringExtra(Keys.EXTRA_NAME);
        String where = getIntent().getStringExtra(Keys.EXTRA_WHERE);
        String when = getIntent().getStringExtra(Keys.EXTRA_WHEN);
        String description = getIntent().getStringExtra(Keys.EXTRA_DESCRIPTION);
        partyId = getIntent().getIntExtra(Keys.EXTRA_PARTYID, -1);
        filename = getIntent().getStringExtra(Keys.EXTRA_PARTY_PICTURE);

        EditText nameText = findViewById(R.id.event_who);
        EditText whereText = findViewById(R.id.event_where);
        EditText whenText = findViewById(R.id.event_when);
        EditText descriptionText = findViewById(R.id.event_description);

        date = Party.parseDate(when).replace(",", "").replace("Uhr", "");
        nameText.setText(name);
        whereText.setText(where);
        whenText.setText(date);
        descriptionText.setText(description);
    }

    private void editUser(String filename) {
        TextView what = findViewById(R.id.event_who);
        TextView where = findViewById(R.id.event_where);
        TextView description = findViewById(R.id.event_description);

        String whatText = what.getText().toString().trim();
        String whereText = where.getText().toString().trim();
        String descriptionText = description.getText().toString().trim();

        if (!whatText.equals("") && !whereText.equals("") && !descriptionText.equals("") && !date.equals("") && partyId >= 0) {
            String dateTime = formatDate();
            String url = "/party/" + partyId + "?api=" + I.getMyself().getApiKey();
            String data = "{\"id\":" + partyId + ",\"name\":\"" + whatText + "\",\"description\":\"" + descriptionText +
                    "\",\"startDate\":\"" + dateTime + "\",\"endDate\":null,\"location\":\"" + whereText + "\",\"picture\":\"" + filename + "\"}";
            Log.e(getClass().getName(), data);
            startNewService(url, "PUT", data, Keys.EXTRA_EDIT_PARTY);
        } else {
            Toast.makeText(this, "Bitte alle Felder ausfüllen!", Toast.LENGTH_SHORT).show();
        }
    }

    private void startNewService(String url, String put, String data, String extraEditParty) {
        TextView what = findViewById(R.id.event_who);
        TextView where = findViewById(R.id.event_where);
        TextView description = findViewById(R.id.event_description);

        String whatText = what.getText().toString().trim();
        String whereText = where.getText().toString().trim();
        String descriptionText = description.getText().toString().trim();
        Intent apiHanlder = new Intent(this, APIService.class);

        if (!whatText.equals("") && !whereText.equals("") && !descriptionText.equals("") && !date.equals("")) {
            apiHanlder.putExtra(Keys.EXTRA_URL, url);
            apiHanlder.putExtra(Keys.EXTRA_REQUEST, put);
            apiHanlder.putExtra(Keys.EXTRA_DATA, data);
            apiHanlder.putExtra(Keys.EXTRA_ID, extraEditParty);
            apiHanlder.putExtra(Keys.EXTRA_SERVICE_TYPE, Keys.EXTRA_EDIT_PARTY_SERVICE);
            this.startService(apiHanlder);
        }
    }

    public void createUser(String filename){
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
                    "\",\"startDate\":\"" + dateTime + "\",\"endDate\":null,\"location\":\"" + whereText + "\",\"picture\":\"" + filename + "\"}";

            Log.e(getClass().getName(), data);
            startNewService(url, "POST", data, Keys.EXTRA_CREATE_PARTY);

        } else {
            Toast.makeText(this, "Bitte alle Felder ausfüllen!", Toast.LENGTH_SHORT).show();
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
        final DecimalFormat formatter = new DecimalFormat("00");
        AlertDialog.Builder pickDate = new AlertDialog.Builder(this);
        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_time_picker, null);
        final TimePicker timePicker = dialogView.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        pickDate.setView(dialogView);
        pickDate.setMessage("Wähle die Uhrzeit")
                .setPositiveButton("Bestätigen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setDate(getDate() + " " + formatter.format(timePicker.getCurrentHour()) + ":" + formatter.format(timePicker.getCurrentMinute()));
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

    @Override
    public void receiveData(String json, String id) {
        if (json != null) {
            Log.e(getClass().getName(), id + ": " +json);
            switch (id) {
                case Keys.EXTRA_EDIT_PARTY:
                    if (!json.contains("error")) {
                        finish();
                    } else {
                        Toast.makeText(this, "Fehler beim Updaten!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case  Keys.EXTRA_CREATE_PARTY:
                    if (!json.contains("error")) {
                        Intent intent = new Intent(this, EventMainActivity.class);
                        Gson gson = new Gson();
                        Party party = gson.fromJson(json, Party.class);
                        intent.putExtra(Keys.EXTRA_PARTYID, party.getId());
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Fehler beim Erstellen!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Keys.EXTRA_PUT_PROFILE:
                    if (!json.contains("error")) {
                        String filename = json.replace("{\"filename\":\"", "").replace("\"}", "");
                        Log.e(getClass().getName(), "Filename:" + filename);
                        if (edit) {
                            editUser(filename);
                        } else {
                            createUser(filename);
                        }
                    } else {
                        Toast.makeText(this, "Bild setzen fehgeschlagen!", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PICTURE && resultCode == Activity.RESULT_OK) {
            final Uri imageUri = data.getData();
            pictureData = imageUri;
            final InputStream imageStream;
            try {
                imageStream = getContentResolver().openInputStream(imageUri);
                pictureBitmap = BitmapFactory.decodeStream(imageStream);

                int height = 1024;
                int width = (int)((double)pictureBitmap.getWidth() / ((double)pictureBitmap.getHeight() / 1024.0));

                picture.setImageBitmap(Bitmap.createScaledBitmap(pictureBitmap, width, height, false));
                imageChanged = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
