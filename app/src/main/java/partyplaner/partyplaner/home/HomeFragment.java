package partyplaner.partyplaner.home;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Random;

import partyplaner.partyplaner.R;

/**
 * Created by André on 10.11.2017.
 */

public class HomeFragment extends Fragment{

    private LinearLayout partyHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        partyHolder = view.findViewById(R.id.party_list);

        updateParties();

        return view;
    }

    private void updateParties() {
        if(partyHolder != null)
            partyHolder.removeAllViews();

        int partyCount = getPartyCount();

        for (int i = 0; i <= partyCount; i++) {
            addParty();
        }
    }

    private void addParty() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.party_list, new PartyHomeFragment());
        fragmentTransaction.commit();
    }

    private int getPartyCount() {
        return new Random().nextInt(6) + 2;
    }
}
