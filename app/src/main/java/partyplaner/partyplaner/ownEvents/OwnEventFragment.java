package partyplaner.partyplaner.ownEvents;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import partyplaner.partyplaner.EventMainActivity;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.MainActivity;
import partyplaner.partyplaner.R;
import partyplaner.partyplaner.Veranstaltung.EventMainFragment;

/**
 * Created by André on 17.11.2017.
 */

public class OwnEventFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ownevents_ownevent, container, false);
        LinearLayout background = view.findViewById(R.id.own_events_back);
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EventMainActivity.class);
                //intent.putExtra(Keys.EXTRA_OWNER, true);
                startActivity(intent);
            }
        });
        return view;
    }
}
