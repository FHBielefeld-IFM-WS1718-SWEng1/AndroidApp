package partyplaner.partyplaner.LogIn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import partyplaner.partyplaner.MainActivity;
import partyplaner.partyplaner.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void saveRegister(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
