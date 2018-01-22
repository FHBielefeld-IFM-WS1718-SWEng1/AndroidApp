package partyplaner.partyplaner.Contacts;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import partyplaner.api.APIService;
import partyplaner.api.ServiceDateReceiver;
import partyplaner.data.user.Gender;
import partyplaner.data.user.I;
import partyplaner.data.user.User;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;
import partyplaner.partyplaner.Veranstaltung.IServiceReceiver;

public class ShowContact extends AppCompatActivity implements IServiceReceiver {

    private User user;
    private int userid;
    private ServiceDateReceiver serviceDateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contact);
        userid = getIntent().getIntExtra(Keys.EXTRA_ID, 0);

    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();

        IntentFilter statusIntentFilter = new IntentFilter(Keys.EXTRA_SHOW_CONTACT);
        serviceDateReceiver = new ServiceDateReceiver(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(serviceDateReceiver, statusIntentFilter);

    }

    private void loadData() {
        startLoading();
        Intent apiHanlder = new Intent(this, APIService.class);
        apiHanlder.putExtra(Keys.EXTRA_URL, "/user/" + userid + "?api=" + I.getMyself().getApiKey());
        apiHanlder.putExtra(Keys.EXTRA_REQUEST, "GET");
        String data = null;
        apiHanlder.putExtra(Keys.EXTRA_DATA, data);
        apiHanlder.putExtra(Keys.EXTRA_ID, Keys.EXTRA_GET_USERS);
        apiHanlder.putExtra(Keys.EXTRA_SERVICE_TYPE, Keys.EXTRA_SHOW_CONTACT);
        this.startService(apiHanlder);
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

    @Override
    public void receiveData(String json, String id) {
        if (json != null) {
            switch (id) {
                case Keys.EXTRA_GET_USERS:
                    if (!json.contains("error")) {
                        Gson gson = new Gson();
                        user = gson.fromJson(json, User.class);
                        setData();
                        loadImage();
                        endLoading();
                    }
                    break;
                case Keys.EXTRA_IMAGE_IMAGE:
                    json = json.replaceAll("\\{\"data\":\"", "");
                    json = json.replaceAll("\"\\}", "");
                    Gson gson = new Gson();
                    byte[] decoded = Base64.decode(json, Base64.DEFAULT);
                    Bitmap image = BitmapFactory.decodeByteArray(decoded, 0, decoded.length);
                    ImageView imageView = findViewById(R.id.profile_picture);
                    imageView.setImageBitmap(image);
                    break;
            }
        }

    }

    private void setData() {
        TextView name = findViewById(R.id.NameTextView);
        TextView gender = findViewById(R.id.GenderTextView);
        TextView birthday = findViewById(R.id.BirthdateTextView);
        TextView email = findViewById(R.id.EmailTextView);

        name.setText(user.getName());
        gender.setText(Gender.getGenderNameByID(user.getGender()));
        birthday.setText(User.formatDate(user.getBirthdate()));
        email.setText(user.getEmail());
    }

    private void startLoading() {
        RelativeLayout rl = findViewById(R.id.loading_screen);
        rl.setVisibility(View.VISIBLE);
    }

    private void endLoading() {
        RelativeLayout rl = findViewById(R.id.loading_screen);
        rl.setVisibility(View.INVISIBLE);
    }

    private void loadImage() {
        Intent apiHanlder = new Intent(this, APIService.class);
        apiHanlder.putExtra(Keys.EXTRA_URL, "/image/" + user.getProfilePicture() + "?api=" + I.getMyself().getApiKey());
        apiHanlder.putExtra(Keys.EXTRA_REQUEST, "GET");
        String data = null;
        apiHanlder.putExtra(Keys.EXTRA_DATA, data);
        apiHanlder.putExtra(Keys.EXTRA_ID, Keys.EXTRA_IMAGE_IMAGE);
        apiHanlder.putExtra(Keys.EXTRA_SERVICE_TYPE, Keys.EXTRA_SHOW_CONTACT);
        this.startService(apiHanlder);
    }
}
