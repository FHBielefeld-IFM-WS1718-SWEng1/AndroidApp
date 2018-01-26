package partyplaner.partyplaner.Veranstaltung.Fragmente;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import partyplaner.data.party.Guest;
import partyplaner.data.party.Party;
import partyplaner.data.user.I;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;
import partyplaner.partyplaner.Veranstaltung.IEventDataManager;
import partyplaner.partyplaner.Veranstaltung.InviteUser;

/**
 * Created by Jan Augstein on 30.11.2017.
 */

public class GuestList extends Fragment implements IReceiveData {
    private ArrayList<Guest> accepted = new ArrayList<>();
    private ArrayList<Guest> denied = new ArrayList<>();
    private ArrayList<Guest> pending = new ArrayList<>();

    private ArrayList<Fragment> acceptedFragments = new ArrayList<>();
    private ArrayList<Fragment> deniedFragments = new ArrayList<>();
    private ArrayList<Fragment> pendingFragments = new ArrayList<>();

    private IEventDataManager data;
    private ExpandableFragment expandableFragment;
    private Party party;

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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_fragment_guestlist, container, false);
        if (savedInstanceState == null) {
            initTabhost(view);
            setEmptyList(view);
            Button add = view.findViewById(R.id.invite_user);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), InviteUser.class);
                    intent.putExtra(Keys.EXTRA_PARTYID, data.getParty().getId());
                    startActivity(intent);
                }
            });
            setLists();
            setAddButton();
        }
        return view;
    }

    private void setEmptyList(View view) {
        TextView empty;
        empty = view.findViewById(R.id.empty_list_accepted);
        if (accepted.size() > 0) {
            empty.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
        } else {
            empty.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        empty = view.findViewById(R.id.empty_list_denied);
        if (denied.size() > 0) {
            empty.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
        } else {
            empty.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        empty = view.findViewById(R.id.empty_list_pending);
        if (pending.size() > 0) {
            empty.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
        } else {
            empty.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    private void initTabhost(View view) {
        TabHost tabHost = view.findViewById(R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tabZusagen = tabHost.newTabSpec("Zugesagt");
        TabHost.TabSpec tabAbsagen = tabHost.newTabSpec("Abgesagt");
        TabHost.TabSpec tabAusstehend = tabHost.newTabSpec("Eingeladen");

        tabZusagen.setIndicator("Zugesagt");
        tabAbsagen.setIndicator("Abgesagt");
        tabAusstehend.setIndicator("Eingeladen");

        tabZusagen.setContent(R.id.zusagen);
        tabAbsagen.setContent(R.id.absagen);
        tabAusstehend.setContent(R.id.ausstehend);

        tabHost.addTab(tabZusagen);
        tabHost.addTab(tabAbsagen);
        tabHost.addTab(tabAusstehend);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                expandableFragment.reexpandGroup();
            }
        });

        tabHost.setCurrentTab(0);
    }

    private void setLists() {
        setAcceptedList();
        setDeniedList();
        setPendingList();
    }

    private void setPendingList() {
        for (Fragment f : pendingFragments) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.remove(f);
            transaction.commit();
        }
        for (Guest guests : pending) {
            Bundle args = new Bundle();
            if (guests.getUser() != null) {
                args.putString(Keys.EXTRA_NAME, guests.getUser().getName());
                args.putBoolean(Keys.EXTRA_OWNER, party.getOwner().getId() == I.getMyself().getId());
                args.putBoolean(Keys.EXTRA_I_AM_GUEST, guests.getUser().getId() == I.getMyself().getId());
                args.putInt(Keys.EXTRA_ID, guests.getId());

                SingleGuestPending fragment = new SingleGuestPending();
                fragment.setExpandable(expandableFragment);
                pendingFragments.add(fragment);
                addGuest(R.id.ausstehend, fragment, args);
            }
        }
    }

    private void setDeniedList() {
        for (Fragment f : deniedFragments) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.remove(f);
            transaction.commit();
        }
        for (Guest guests : denied) {
            Bundle args = new Bundle();
            args.putString(Keys.EXTRA_NAME, guests.getUser().getName());
            args.putBoolean(Keys.EXTRA_I_AM_GUEST, guests.getUser().getId() == I.getMyself().getId());

            SingleGuestDenied fragment = new SingleGuestDenied();
            fragment.setExpandable(expandableFragment);
            deniedFragments.add(fragment);
            addGuest(R.id.absagen, fragment, args);
        }
    }

    private void setAcceptedList() {
        for (Fragment f : acceptedFragments) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.remove(f);
            transaction.commit();
        }
        for (Guest guests : accepted) {
            Bundle args = new Bundle();
            args.putString(Keys.EXTRA_NAME, guests.getUser().getName());
            args.putBoolean(Keys.EXTRA_OWNER, party.getId() == I.getMyself().getId());
            args.putBoolean(Keys.EXTRA_I_AM_GUEST, guests.getUser().getId() == I.getMyself().getId());
            args.putBoolean(Keys.EXTRA_ADMIN, false);

            SingleGuestAccepted fragment = new SingleGuestAccepted();
            fragment.setExpandable(expandableFragment);
            acceptedFragments.add(fragment);
            addGuest(R.id.zusagen, fragment, args);
        }
    }

    private void addGuest(int layout, Fragment fragment, Bundle args) {
        fragment.setArguments(args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(layout, fragment);
        transaction.commit();
        if (getView() != null) {
            getView().findViewById(layout).refreshDrawableState();
        }
    }

    @Override
    public void receiveData() {
        if (data.getParty() != null) {
            pending.clear();
            accepted.clear();
            denied.clear();
            party = data.getParty();
            Guest[] guests = data.getParty().getGuests();
            for (Guest guest : guests) {
                if (guest.getInviteState() == 0) {
                    pending.add(guest);
                } else if (guest.getInviteState() == 1) {
                    accepted.add(guest);
                } else if (guest.getInviteState() == 2) {
                    denied.add(guest);
                }
            }
            setLists();
            setEmptyList(getView());
            setAddButton();
        }
    }

    private void setAddButton() {
        if (getView() != null) {
            Button add = getView().findViewById(R.id.invite_user);
            if (party != null && party.getOwner().getId() != I.getMyself().getId()) {
                add.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
            } else {
                add.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        }
    }

    @Override
    public void setExpandable(ExpandableFragment fragment) {
        expandableFragment = fragment;
    }
}
