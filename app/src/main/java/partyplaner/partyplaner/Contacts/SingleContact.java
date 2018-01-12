package partyplaner.partyplaner.Contacts;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;

/**
 * Created by micha on 24.11.2017.
 */

public class SingleContact extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_contact, container, false);
        Bundle args = getArguments();
        TextView name = view.findViewById(R.id.contact_name);
        name.setText(args.getString(Keys.EXTRA_NAME));
        return view;
    }
}
