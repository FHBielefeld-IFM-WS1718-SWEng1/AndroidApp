package partyplaner.partyplaner.Veranstaltung.Fragmente;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;
import partyplaner.partyplaner.Veranstaltung.IEventDataManager;
import partyplaner.partyplaner.poll.CreatePoll;

/**
 * Created by malte on 24.11.2017.
 */

public class Poll extends Fragment implements IReceiveData {

    private int partyid;
    private IEventDataManager data;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            data = (IEventDataManager) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_fragment_poll, container, false);

        if (savedInstanceState == null) {
            Button createPoll = view.findViewById(R.id.create_poll);
            createPoll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreatePoll.class);
                intent.putExtra(Keys.EXTRA_PARTYID, partyid);
                startActivity(intent);
                }
            });
            testSetup(view);
            testSetup(view);
        }
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
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.body_poll, fragment);
        transaction.commit();
    }

    @Override
    public void receiveData() {
        partyid = data.getParty().getId();
    }

    @Override
    public void setExpandable(ExpandableFragment fragment) {

    }

}
