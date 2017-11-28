package partyplaner.partyplaner.Contacts;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import java.util.Random;
import partyplaner.partyplaner.R;

/**
 * Created by micha on 24.11.2017.
 */

public class AllContacts extends Fragment {

    private LinearLayout contactHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_contacts, container, false);
        contactHolder = view.findViewById(R.id.layout_all_single_contacts);
        updateContacts();
        return view;
    }
    private void updateContacts() {

        int ContactsCount = getContactCount();

        for (int i = 0; i <= ContactsCount; i++) {
            addContact();
        }
    }
    private void addContact() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.layout_all_single_contacts, new SingleContact());
        fragmentTransaction.commit();
    }
    private int getContactCount() {
        return new Random().nextInt(6) + 2;

    }


}
