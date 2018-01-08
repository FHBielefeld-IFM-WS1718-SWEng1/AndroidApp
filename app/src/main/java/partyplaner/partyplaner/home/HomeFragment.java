package partyplaner.partyplaner.home;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import java.util.Random;

import partyplaner.api.GeneralAPIRequestHandler;
import partyplaner.api.RouteType;
import partyplaner.data.party.Party;
import partyplaner.data.user.I;
import partyplaner.partyplaner.IFragmentDataManeger;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;
import partyplaner.partyplaner.ownEvents.OwnEventFragment;

/**
 * Created by André on 10.11.2017.
 */

public class HomeFragment extends Fragment{

    private IFragmentDataManeger data;
    private LinearLayout partyHolder;
    private Party[] parties;

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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //keine Doppelten Partys, wenn das Handy gekippt wird
        if (savedInstanceState == null) {
            partyHolder = view.findViewById(R.id.party_list);

            parties = data.getParties();
            updateParties();
        }

        return view;
    }

    private void updateParties() {
        if(partyHolder != null)
            partyHolder.removeAllViewsInLayout();

        for (Party party: parties) {
            addParty(party);
        }
    }

    private void addParty(Party party) {
        Bundle args = new Bundle();
        args.putString(Keys.EXTRA_PARTY, party.getName());
        args.putString(Keys.EXTRA_WHEN, party.getStartDate());
        args.putString(Keys.EXTRA_DESCRIPTION, party.getDescription());
        args.putInt(Keys.EXTRA_USERID, party.getUserID());


        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (party.isErsteller()) {
            OwnEventFragment ownEventFragment = new OwnEventFragment();
            ownEventFragment.setArguments(args);
            fragmentTransaction.add(R.id.party_list, ownEventFragment);
        } else {
            PartyHomeFragment partyHomeFragment = new PartyHomeFragment();
            partyHomeFragment.setArguments(args);
            fragmentTransaction.add(R.id.party_list, partyHomeFragment);
        }
        fragmentTransaction.commit();
    }
}
