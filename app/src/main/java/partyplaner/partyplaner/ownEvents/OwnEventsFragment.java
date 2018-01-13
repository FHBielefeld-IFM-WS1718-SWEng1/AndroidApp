package partyplaner.partyplaner.ownEvents;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Random;

import partyplaner.data.party.Party;
import partyplaner.partyplaner.IFragmentDataManeger;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;
import partyplaner.partyplaner.home.PartyHomeFragment;

/**
 * Created by André on 17.11.2017.
 */

public class OwnEventsFragment extends Fragment {

    private LinearLayout partyHolder;
    private Party[] parties;
    private IFragmentDataManeger data;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            data = (IFragmentDataManeger) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ownevents, container, false);

        if (savedInstanceState == null) {
            partyHolder = view.findViewById(R.id.ownEvent_list);

            parties = data.getOwnParties();
            updateParties();
        }

        return view;
    }

    private void updateParties() {
        partyHolder.removeAllViewsInLayout();

        Log.e("OwnEvents", parties.length + "");

        for (Party party: parties) {
            addParty(party);
        }
    }

    private void addParty(Party party) {
        Bundle args = new Bundle();
        args.putString(Keys.EXTRA_PARTY, party.getName());
        args.putString(Keys.EXTRA_WHEN, party.getStartDate());
        args.putString(Keys.EXTRA_DESCRIPTION, party.getDescription());
        args.putInt(Keys.EXTRA_PARTYID, party.getId());
        OwnEventFragment partyHomeFragment = new OwnEventFragment();
        partyHomeFragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.ownEvent_list, partyHomeFragment);
        fragmentTransaction.commit();
    }

}
