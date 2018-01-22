package partyplaner.partyplaner.Veranstaltung.Fragmente;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import partyplaner.data.party.Party;
import partyplaner.data.party.Task;
import partyplaner.data.user.I;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;
import partyplaner.partyplaner.Veranstaltung.IEventDataManager;
import partyplaner.partyplaner.poll.CreatePoll;

/**
 * Created by malte on 24.11.2017.
 */

public class Poll extends Fragment implements IReceiveData {

    private Party party;
    private List<Fragment> fragments = new ArrayList<>();
    private partyplaner.data.party.Poll[] votings;
    private ExpandableFragment expandableFragment;
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
                intent.putExtra(Keys.EXTRA_PARTYID, party.getId());
                startActivity(intent);
                }
            });
            if (party != null && party.getOwner().getId() != I.getMyself().getId()) {
                createPoll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0));
            }
            update();
        }
        return view;
    }

    private void update() {
        for (Fragment f : fragments) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.remove(f);
            transaction.commit();
        }
        fragments.clear();

        if (votings != null) {
            for (partyplaner.data.party.Poll poll : votings) {
                addPoll(poll);
            }
        }
    }

    private void addPoll(partyplaner.data.party.Poll poll) {
        if (getView() != null) {
            Bundle arguments = new Bundle();
            arguments.putString(Keys.EXTRA_NAME, poll.getQuestion());
            arguments.putInt(Keys.EXTRA_ID, poll.getId());
            arguments.putSerializable(Keys.EXTRA_POLL, poll);

            SinglePoll pollfragment = new SinglePoll();
            pollfragment.setExpandable(expandableFragment);
            pollfragment.setArguments(arguments);
            fragments.add(pollfragment);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.body_poll, pollfragment);
            transaction.commit();
        }
    }

    @Override
    public void receiveData() {
        if (data.getParty() != null) {
            party = data.getParty();
            votings = party.getVotings();
            update();
            if (getView() != null && party.getOwner().getId() != I.getMyself().getId()) {
                Button button = getView().findViewById(R.id.create_poll);
                button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0));
            }
        }
    }

    @Override
    public void setExpandable(ExpandableFragment fragment) {
        this.expandableFragment = fragment;
    }

}
