package partyplaner.partyplaner.Veranstaltung.Fragmente;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;

/**
 * Created by malte on 24.11.2017.
 */

public class Poll extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_fragment_poll, container, false);

        testSetup(view);
        testSetup(view);
        return view;
    }

    private void testSetup(View view) {

        Bundle arguments = new Bundle();
        arguments.putString(Keys.EXTRA_NAME, "Abstimmung");
        arguments.putBoolean(Keys.EXTRA_OWNER, true);

        SinglePoll poll = new SinglePoll();
        poll.setArguments(arguments);

        addPoll(view, poll);

    }

    public void addPoll(View view, Fragment fragment) {
        LinearLayout body = view.findViewById(R.id.body_poll);
        body.removeAllViews();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.body_poll, fragment);
        transaction.commit();
    }
}
