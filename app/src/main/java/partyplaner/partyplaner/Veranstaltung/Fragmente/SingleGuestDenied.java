package partyplaner.partyplaner.Veranstaltung.Fragmente;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;

/**
 * Created by malte on 03.12.2017.
 */

public class SingleGuestDenied extends Fragment {

    private ExpandableFragment expandableFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_fragment_single_guest_denied, container, false);

        if (savedInstanceState == null) {
            Bundle args = getArguments();
            TextView name = view.findViewById(R.id.name_denied);
            name.setText(args.getString(Keys.EXTRA_NAME));
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        expandableFragment.reexpandGroup();
    }

    public void setExpandable(ExpandableFragment expandableFragment) {
        this.expandableFragment = expandableFragment;
    }
}
