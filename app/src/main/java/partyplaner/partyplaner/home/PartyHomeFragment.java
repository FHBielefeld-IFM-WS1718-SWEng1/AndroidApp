package partyplaner.partyplaner.home;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import partyplaner.partyplaner.EventMainActivity;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;

/**
 * Created by André on 10.11.2017.
 */

public class PartyHomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_party, container, false);
        LinearLayout background = view.findViewById(R.id.invited_event_back);
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EventMainActivity.class);
                intent.putExtra(Keys.EXTRA_OWNER, true);
                startActivity(intent);
            }
        });
        return view;
    }
}
