package partyplaner.partyplaner.Veranstaltung.Fragmente;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;

/**
 * Created by Jan Augstein on 30.11.2017.
 */

public class GuestList extends Fragment implements IReceiveData {
    private ArrayList<String> accepted = new ArrayList<>();
    private ArrayList<String> denied = new ArrayList<>();
    private ArrayList<String> pending = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_fragment_guestlist, container, false);
        if (savedInstanceState == null) {
            initTabhost(view);
            setUpTestLists();
            setLists();
            setEmptyList(view);
        }
        return view;
    }

    private void setEmptyList(View view) {
        TextView empty;
        if (accepted.size() > 0) {
            empty = view.findViewById(R.id.empty_list_accepted);
            empty.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
        }
        if (denied.size() > 0) {
            empty = view.findViewById(R.id.empty_list_denied);
            empty.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
        }
        if (pending.size() > 0) {
            empty = view.findViewById(R.id.empty_list_pending);
            empty.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
        }
    }

    private void setUpTestLists() {
        accepted.add("Test1");
        accepted.add("Test2");
        accepted.add("Test3");
        denied.add("Test4");
        denied.add("Test5");
        denied.add("Test6");
        pending.add("Test7");
        pending.add("Test8");
        pending.add("Test9");
        pending.add("Test10");
    }

    private void initTabhost(View view) {
        TabHost tabHost = view.findViewById(R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tabZusagen = tabHost.newTabSpec("Zusagen");
        TabHost.TabSpec tabAbsagen = tabHost.newTabSpec("Absagen");
        TabHost.TabSpec tabAusstehend = tabHost.newTabSpec("Ausstehend");

        tabZusagen.setIndicator("Zusagen");
        tabAbsagen.setIndicator("Absagen");
        tabAusstehend.setIndicator("Ausstehend");

        tabZusagen.setContent(R.id.zusagen);
        tabAbsagen.setContent(R.id.absagen);
        tabAusstehend.setContent(R.id.ausstehend);

        tabHost.addTab(tabZusagen);
        tabHost.addTab(tabAbsagen);
        tabHost.addTab(tabAusstehend);

        tabHost.setCurrentTab(0);
    }

    private void setLists() {
        setAcceptedList();
        setDeniedList();
        setPendingList();
    }

    private void setPendingList() {
        for (String guests : pending) {
            Bundle args = new Bundle();
            args.putString(Keys.EXTRA_NAME, guests);
            args.putBoolean(Keys.EXTRA_OWNER, true);

            addGuest(R.id.ausstehend, new SingleGuestPending(), args);
        }
    }

    private void setDeniedList() {
        for (String guests : denied) {
            Bundle args = new Bundle();
            args.putString(Keys.EXTRA_NAME, guests);

            addGuest(R.id.absagen, new SingleGuestDenied(), args);
        }
    }

    private void setAcceptedList() {
        for (String guests : accepted) {
            Bundle args = new Bundle();
            args.putString(Keys.EXTRA_NAME, guests);
            args.putBoolean(Keys.EXTRA_OWNER, true);
            args.putBoolean(Keys.EXTRA_ADMIN, true);

            addGuest(R.id.zusagen, new SingleGuestAccepted(), args);
        }
    }

    private void addGuest(int layout, Fragment fragment, Bundle args) {
        fragment.setArguments(args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(layout, fragment);
        transaction.commit();
    }

    @Override
    public void receiveData() {

    }

    /*
    private void updateGuests(){
        guestHolder.removeAllViews();
        int guestCount = getGuestCount();

        for(int i = 0; i<=guestCount; i++){
            addGuest();
        }

    }

    private void addGuest(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.add(R.id.guest_list, new SingleGuestAccepted());
        fragmentTransaction.commit();
    }

    private int getGuestCount(){
        return new Random().nextInt(6) + 2;
    }*/
}
