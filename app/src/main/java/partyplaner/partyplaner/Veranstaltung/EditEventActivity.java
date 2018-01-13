package partyplaner.partyplaner.Veranstaltung;

import partyplaner.partyplaner.R;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class EditEventActivity extends AppCompatActivity {

    ImageView image = findViewById(R.id.EventImage);
    //image.OnClickListener(new View.OnClickListener);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
    }
}
