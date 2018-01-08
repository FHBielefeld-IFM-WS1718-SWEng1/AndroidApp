package partyplaner.partyplaner.home;

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

import java.util.Arrays;

import partyplaner.api.GeneralAPIRequestHandler;
import partyplaner.api.RouteType;
import partyplaner.data.user.I;
import partyplaner.data.user.User;
import partyplaner.partyplaner.EventMainActivity;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;

/**
 * Created by André on 10.11.2017.
 */

public class PartyHomeFragment extends Fragment {

    private Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_party, container, false);

        Bundle args = getArguments();

        gson = new Gson();

        setText(view, args);

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

    private void setText(View view, Bundle args) {
        TextView name = view.findViewById(R.id.textPartyname);
        name.setText(args.getString(Keys.EXTRA_PARTY));

        TextView when = view.findViewById(R.id.textWhen);
        String date = parseDate(args.getString(Keys.EXTRA_WHEN));
        when.setText("Wann? " + date);

        TextView description = view.findViewById(R.id.textDescription);
        description.setText(args.getString(Keys.EXTRA_DESCRIPTION));

        TextView who = view.findViewById(R.id.textWho);
        User owner = getUser(args);
        who.setText("Wer? " + owner.getName());
    }

    private User getUser(Bundle args) {
        int userId = args.getInt(Keys.EXTRA_USERID);
        String reqeust = "/user/" + userId + "?api=" + I.getMyself().getApiKey();
        String ownerJson = GeneralAPIRequestHandler.request(
               reqeust , RouteType.GET, null);
        return gson.fromJson(ownerJson, User.class);
    }

    private String parseDate(String when) {
        String[] timeDate = when.split("T");
        String[] date = timeDate[0].split("-");

        return date[2] + "." + date[1] + "." + date[0] + ", " + timeDate[1].substring(0, 5) + "Uhr";
    }
}
