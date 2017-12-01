package partyplaner.partyplaner.Veranstaltung.Fragmente;

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
 * Created by Jan Augstein on 30.11.2017.
 */

public class GuestList extends Fragment {
    private LinearLayout guestHolder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_fragment_guestlist, container, false);
        guestHolder = view.findViewById(R.id.guest_list);

        updateGuests();

        return view;
    }

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
        fragmentTransaction.add(R.id.guest_list, new SingleGuest());
        fragmentTransaction.commit();
    }

    private int getGuestCount(){
        return new Random().nextInt(6) + 2;
    }
}
