package partyplaner.partyplaner.Veranstaltung.Fragmente;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;

/**
 * Created by malte on 24.11.2017.
 */

public class SinglePoll extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_fragment_single_poll, container, false);
        Bundle arg = getArguments();

        if(arg.getBoolean(Keys.EXTRA_OWNER)) {
            LinearLayout body = view.findViewById(R.id.single_poll);
            ImageView delete = new ImageView(getActivity());
            delete.setImageResource(R.drawable.delete_icon);
            delete.setLayoutParams(new LinearLayout.LayoutParams(64, 64));
            body.addView(delete);
        }

        TextView name = view.findViewById(R.id.poll_name);
        name.setText(arg.getString(Keys.EXTRA_NAME));
        return view;
    }
}
