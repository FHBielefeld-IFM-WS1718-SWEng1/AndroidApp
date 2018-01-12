package partyplaner.partyplaner.Contacts;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import java.util.Random;

import partyplaner.partyplaner.IFragmentDataManeger;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;
import partyplaner.data.user.User;

/**
 * Created by micha on 24.11.2017.
 */

public class AllContacts extends Fragment {

    private LinearLayout contactHolder;
    private IFragmentDataManeger data;
    private User[] contactList;

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
        View view = inflater.inflate(R.layout.fragment_all_contacts, container, false);
        contactHolder = view.findViewById(R.id.layout_all_single_contacts);
        updateContacts();
        return view;
    }
    private void updateContacts() {
        if(contactHolder != null){
            contactHolder.removeAllViews();
            for(User user: contactList){
                addContact(user);
            }
        }
    }
    private void addContact(User user) {
        Bundle args = new Bundle();
        args.putString(Keys.EXTRA_NAME, user.getName());
        //  args.putString(Keys.EXTRA_EMAIL, user.getEmail());
        args.putString(Keys.EXTRA_PICTURE, null);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SingleContact singleContact = new SingleContact();
        singleContact.setArguments(args);
        fragmentTransaction.add(R.id.layout_all_single_contacts, singleContact);
        fragmentTransaction.commit();
    }
    private int getContactCount() {
        return new Random().nextInt(6) + 2;

    }


}
