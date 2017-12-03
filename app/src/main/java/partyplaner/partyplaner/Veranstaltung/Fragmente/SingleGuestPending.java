package partyplaner.partyplaner.Veranstaltung.Fragmente;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;

/**
 * Created by malte on 03.12.2017.
 */

public class SingleGuestPending extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_fragment_single_guest_pending, container, false);

        Bundle args = getArguments();
        TextView name = view.findViewById(R.id.name_pending);
        name.setText(args.getString(Keys.EXTRA_NAME));

        ImageView delete = view.findViewById(R.id.delete_pending);
        if (args.getBoolean(Keys.EXTRA_OWNER)) {
            delete.setVisibility(View.VISIBLE);
        }

        return view;
    }
}
