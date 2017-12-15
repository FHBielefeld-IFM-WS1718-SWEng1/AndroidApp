package partyplaner.partyplaner.Veranstaltung.Fragmente;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import partyplaner.data.party.*;
import partyplaner.data.party.Poll;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;
import partyplaner.partyplaner.poll.*;

/**
 * Created by malte on 24.11.2017.
 */

public class SinglePoll extends Fragment {

    private partyplaner.data.party.Poll poll;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_fragment_single_poll, container, false);
        Bundle arg = getArguments();

        testPoll();

        if(arg.getBoolean(Keys.EXTRA_OWNER)) {
            LinearLayout body = view.findViewById(R.id.single_poll);
            ImageView delete = new ImageView(getActivity());
            delete.setImageResource(R.drawable.delete_icon);
            delete.setLayoutParams(new LinearLayout.LayoutParams(64, 64));
            body.addView(delete);
        }

        TextView name = view.findViewById(R.id.poll_name);
        name.setText(arg.getString(Keys.EXTRA_NAME));

        ImageView goToPoll = view.findViewById(R.id.go_to_poll);
        goToPoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), partyplaner.partyplaner.poll.Poll.class);
                intent.putExtra(Keys.EXTRA_POLL, poll);
                startActivity(intent);
            }
        });
        return view;
    }

    private void testPoll() {
        List<PollOption> options = new ArrayList<>();
        options.add(new PollOption("Option1", 2));
        options.add(new PollOption("Option2", 3));
        options.add(new PollOption("Option3", 4));
        options.add(new PollOption("Option4", 5));
        this.poll = new Poll("Tim?", options);
    }
}
