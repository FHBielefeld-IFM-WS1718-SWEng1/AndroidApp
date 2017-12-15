package partyplaner.partyplaner;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import partyplaner.partyplaner.Veranstaltung.EventMainFragment;

public class EventMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_main);
        //TODO dem EventMainFragment getIntent() übergeben
    }
}
