package partyplaner.partyplaner.Contacts;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import partyplaner.partyplaner.R;

/**
 * Created by micha on 24.11.2017.
 */

public class SingleContact extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_single_contact, container, false);
    }
}
