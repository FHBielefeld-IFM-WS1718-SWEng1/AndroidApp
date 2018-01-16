package partyplaner.partyplaner.Veranstaltung.Fragmente;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;

/**
 * Created by Jan Augstein on 30.11.2017.
 */

public class SingleGuestAccepted extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_fragment_single_guest_accepted, container, false);

        if (savedInstanceState == null) {
            Bundle args = getArguments();
            TextView name = view.findViewById(R.id.name_accepted);
            name.setText(args.getString(Keys.EXTRA_NAME));

            CheckBox admin = view.findViewById(R.id.admin_status);
            if (args.getBoolean(Keys.EXTRA_OWNER)) {
                admin.setVisibility(View.VISIBLE);
                admin.setChecked(args.getBoolean(Keys.EXTRA_ADMIN));
            }
        }
        return view;
    }
}
