package partyplaner.partyplaner.home;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
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
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;

/**
 * Created by André on 10.11.2017.
 */

public class HomeFragment extends Fragment{

    private LinearLayout partyHolder;
    private Party[] parties;
    private Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //keine Doppelten Partys, wenn das Handy gekippt wird
        if (savedInstanceState == null) {
            partyHolder = view.findViewById(R.id.party_list);

            gson = new Gson();
            parties = loadData();
            updateParties();
        }

        return view;
    }

    private Party[] loadData() {
        String json = GeneralAPIRequestHandler.request("/party?api=" + I.getMyself().getApiKey(), RouteType.GET, null);
        json = json.replaceAll(".*?\\[", "[");
        json = json.replaceAll("].", "]");
        return gson.fromJson(json, Party[].class);
    }

    private void updateParties() {
        partyHolder.removeAllViewsInLayout();
        Log.e("Home", "parties:" + parties.length);

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
        PartyHomeFragment partyHomeFragment = new PartyHomeFragment();
        partyHomeFragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.party_list, partyHomeFragment);
        fragmentTransaction.commit();
    }
}
