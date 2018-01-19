package partyplaner.partyplaner.Profile;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Arrays;

import partyplaner.api.APIService;
import partyplaner.api.ServiceDateReceiver;
import partyplaner.data.PaPlaImage;
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
    private Bitmap imageBitmap;

    private final static int PICK_IMAGE = 42;
    private ImageView profile;
    private boolean imageChanged = false;

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

        profile = findViewById(R.id.edit_profile_picture);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, PICK_IMAGE);
            }
        });
    }

    private void saveEdit() {
        Log.e(getClass().getName(), "SaveEdit1");
        EditText nameText = findViewById(R.id.edit_profile_name);
        EditText birthdateText = findViewById(R.id.edit_profile_birthdate);
        name = nameText.getText().toString().trim();
        birthdate = birthdateText.getText().toString().trim();
        Log.e(getClass().getName(), "SaveEdit2");
        if (imageChanged && !name.equals("") && !birthdate.equals("")) {
            Log.e(getClass().getName(), "SaveEdit3");
            PaPlaImage imagePapla = new PaPlaImage(imageBitmap);
            Intent apiHandler = new Intent(this, APIService.class);
            apiHandler.putExtra(Keys.EXTRA_URL, "/image/" + I.getMyself().getId() + "?api=" + I.getMyself().getApiKey());
            apiHandler.putExtra(Keys.EXTRA_REQUEST, "POST");
            Log.e(getClass().getName(), "SaveEdit4");
            String data = "{\"data\":\"" + imagePapla.convertToBase64() + "\"}";
            apiHandler.putExtra(Keys.EXTRA_DATA, data);
            apiHandler.putExtra(Keys.EXTRA_ID, Keys.EXTRA_UPLOAD_PROFILE_PICTURE);
            apiHandler.putExtra(Keys.EXTRA_SERVICE_TYPE, Keys.EXTRA_EDIT_PROFILE);
            Log.e(getClass().getName(), "Kurz vor dem Service starten");
            this.startService(apiHandler);
        } else {
            saveProfileData();
        }
    }

    public void saveProfileData() {
        Log.e(getClass().getName(), "SaveProfileData");
        EditText nameText = findViewById(R.id.edit_profile_name);
        EditText birthdateText = findViewById(R.id.edit_profile_birthdate);
        Spinner genderText = findViewById(R.id.edit_profile_gender);

        name = nameText.getText().toString().trim();
        birthdate = birthdateText.getText().toString().trim();
        gender = genderText.getSelectedItemPosition();
        int profilePicID = I.getMyself().getProfilePicture();

        if (!name.equals("") && !birthdate.equals("")) {
            Intent apiHandler = new Intent(this, APIService.class);
            apiHandler.putExtra(Keys.EXTRA_URL, "/user/" + I.getMyself().getId() + "?api=" + I.getMyself().getApiKey());
            apiHandler.putExtra(Keys.EXTRA_REQUEST, "PUT");
            String data = "{\"name\":\"" + name + "\",\"gender\":" + gender + ",\"birthdate\":\"" + formatDate() +  ",\"profilepicture\":\"" + profilePicID + "\"}";
            apiHandler.putExtra(Keys.EXTRA_DATA, data);
            apiHandler.putExtra(Keys.EXTRA_ID, Keys.EXTRA_PUT_PROFILE);
            apiHandler.putExtra(Keys.EXTRA_SERVICE_TYPE, Keys.EXTRA_EDIT_PROFILE);
            Log.e(getClass().getName(), "Kurz vor dem Service starten: SaveProfileData");
            this.startService(apiHandler);
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
        Log.e(getClass().getName(), "ReceiveData1");
        if (id.equals(Keys.EXTRA_UPLOAD_PROFILE_PICTURE)) {
            if (json != null) {
                if (!json.contains("error")) {
                    Log.e(getClass().getName(), "ReceiveData2");
                    Gson gson = new Gson();
                    User i = gson.fromJson(json, User.class);
                    I.getMyself().setProfilePicture(i.getProfilePicture());
                    finish();
                } else {
                    Toast.makeText(this, "Bearbeiten fehgeschlagen!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Bearbeiten fehgeschlagen!", Toast.LENGTH_SHORT).show();
            }
            saveProfileData();
        } else if (id.equals(Keys.EXTRA_PUT_PROFILE)) {
            if (json != null) {
                if (!json.contains("error")) {
                    Gson gson = new Gson();
                    User i = gson.fromJson(json, User.class);
                    I.getMyself().setName(i.getName());
                    I.getMyself().setGender(i.getGender());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            final Uri imageUri = data.getData();
            final InputStream imageStream;
            try {
                imageStream = getContentResolver().openInputStream(imageUri);
                imageBitmap = BitmapFactory.decodeStream(imageStream);
                int height = 1024;
                int width = (int)((double)imageBitmap.getWidth() / ((double)imageBitmap.getHeight() / 1024.0));

                profile.setImageBitmap(Bitmap.createScaledBitmap(imageBitmap, width, height, false));
                imageChanged = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
