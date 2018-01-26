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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import partyplaner.api.GeneralAPIRequestHandler;
import partyplaner.api.RouteType;
import partyplaner.data.party.Party;
import partyplaner.data.user.I;
import partyplaner.partyplaner.IFragmentDataManeger;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;
import partyplaner.partyplaner.Veranstaltung.Fragmente.ExpandableFragment;
import partyplaner.partyplaner.Veranstaltung.Fragmente.IReceiveData;
import partyplaner.partyplaner.ownEvents.OwnEventFragment;

/**
 * Created by André on 10.11.2017.
 */

public class HomeFragment extends Fragment implements IReceiveData{

    private IFragmentDataManeger data;
    private LinearLayout partyHolder;
    private List<Fragment> fragments = new ArrayList<>();
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

        if (savedInstanceState == null) {
            partyHolder = view.findViewById(R.id.party_list);

            if (data.partyReceived()) {
                parties = data.getParties();
                updateParties();
            }
        }

        return view;
    }

    private void updateParties() {
        for (Fragment f : fragments) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.remove(f);
            transaction.commit();
        }
        fragments.clear();
        for (Party party : parties) {
            addParty(party);
        }
    }

    private void addParty(Party party) {
        Bundle args = new Bundle();
        args.putString(Keys.EXTRA_PARTY, party.getName());
        args.putInt(Keys.EXTRA_PARTYID, party.getId());
        args.putString(Keys.EXTRA_WHEN, party.getStartDate());
        args.putString(Keys.EXTRA_DESCRIPTION, party.getDescription());
        args.putInt(Keys.EXTRA_PARTYID, party.getId());
        args.putString(Keys.EXTRA_FILENAME, party.getPicture());
        args.putInt(Keys.EXTRA_USERID, party.getUserID());
        args.putString(Keys.EXTRA_NAME, party.getOwner().getName());

        if (getFragmentManager() != null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (party.isErsteller()) {
                OwnEventFragment ownEventFragment = new OwnEventFragment();
                ownEventFragment.setArguments(args);
                fragmentTransaction.add(R.id.party_list, ownEventFragment);
                fragments.add(ownEventFragment);
            } else {
                PartyHomeFragment partyHomeFragment = new PartyHomeFragment();
                partyHomeFragment.setArguments(args);
                fragmentTransaction.add(R.id.party_list, partyHomeFragment);
                fragments.add(partyHomeFragment);
            }
            fragmentTransaction.commit();
        }

    }

    @Override
    public void receiveData() {
        parties = data.getParties();
        updateParties();
    }

    @Override
    public void setExpandable(ExpandableFragment fragment) {

    }
}
