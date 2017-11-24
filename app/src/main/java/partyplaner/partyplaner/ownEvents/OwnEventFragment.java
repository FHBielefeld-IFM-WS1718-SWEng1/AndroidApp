package partyplaner.partyplaner.ownEvents;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import partyplaner.partyplaner.R;

/**
 * Created by André on 17.11.2017.
 */

public class OwnEventFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ownevents_ownevent, container, false);
        //view.findViewById(R.id.s)
        return view;
    }
}
