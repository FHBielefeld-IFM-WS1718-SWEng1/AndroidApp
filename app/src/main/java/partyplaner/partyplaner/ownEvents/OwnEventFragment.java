package partyplaner.partyplaner.ownEvents;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import partyplaner.api.GeneralAPIRequestHandler;
import partyplaner.api.RouteType;
import partyplaner.data.user.I;
import partyplaner.data.user.User;
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

        Bundle args = getArguments();
        setText(view, args);

        LinearLayout background = view.findViewById(R.id.own_events_back);
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EventMainActivity.class);
                //TODO: intent.putExtra(Keys.EXTRA_OWNER, true);
                startActivity(intent);
            }
        });
        return view;
    }


    private void setText(View view, Bundle args) {
        TextView name = view.findViewById(R.id.textPartyname);
        name.setText(args.getString(Keys.EXTRA_PARTY));

        TextView when = view.findViewById(R.id.textWhen);
        String date = parseDate(args.getString(Keys.EXTRA_WHEN));
        when.setText("Wann? " + date);

        TextView description = view.findViewById(R.id.textDescription);
        description.setText(args.getString(Keys.EXTRA_DESCRIPTION));
    }

    private String parseDate(String when) {
        String[] timeDate = when.split("T");
        String[] date = timeDate[0].split("-");

        return date[2] + "." + date[1] + "." + date[0] + ", " + timeDate[1].substring(0, 5) + "Uhr";
    }
}
