package partyplaner.partyplaner.Contacts;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import partyplaner.api.APIService;
import partyplaner.data.party.Party;
import partyplaner.data.user.I;
import partyplaner.partyplaner.IFragmentDataManeger;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;
import partyplaner.data.user.User;
import partyplaner.partyplaner.Veranstaltung.Fragmente.ExpandableFragment;
import partyplaner.partyplaner.Veranstaltung.Fragmente.IReceiveData;

/**
 * Created by micha on 24.11.2017.
 */

public class AllContacts extends Fragment implements IReceiveData{

    public interface ISetName{
        public void setName(String name);
    }
    private LinearLayout contactHolder;
    private IFragmentDataManeger data;
    private List<Fragment> fragments = new ArrayList<>();
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_contacts, container, false);
        contactHolder = view.findViewById(R.id.layout_all_single_contacts);
        contactList = data.getContacts();
        updateContacts();

        ImageButton searchButton = view.findViewById(R.id.button_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchContact();
            }
        });
        Button addButton = view.findViewById(R.id.button_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewContact(inflater);
            }
        });
        return view;
    }

    private void updateContacts() {
        if(contactHolder != null && contactList != null){
            for (Fragment f : fragments) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.remove(f);
                transaction.commit();
            }
            fragments.clear();

            sortContacts();
            for (User user : contactList) {
                addContact(user);
            }
        }
    }

    private void addContact(User user) {
        Bundle args = new Bundle();
        args.putString(Keys.EXTRA_NAME, user.getName());
        args.putInt(Keys.EXTRA_USERID, user.getId());
        args.putString(Keys.EXTRA_PICTURE, user.getProfilePicture());
        //args.putString(Keys.EXTRA_PICTURE, null);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        SingleContact singleContact = new SingleContact();
        singleContact.setArguments(args);
        fragments.add(singleContact);

        fragmentTransaction.add(R.id.layout_all_single_contacts, singleContact);
        fragmentTransaction.commit();
    }

    private User searchContact(){
        EditText search = getView().findViewById(R.id.SearchText);
        String searched = search.getText().toString().trim();
        if(searched.equals("")){
            updateContacts();
        } else {
            for (Fragment f : fragments) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.remove(f);
                transaction.commit();
            }
        }
        fragments.clear();

        for(User user: contactList){
            if(user.getName().trim().equals(searched)){
                addContact(user);
            }
        }
        return null;
    }

    private void addNewContact(LayoutInflater inflater){
        Log.e("AllContacts", "addNewContact anfang");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View dialogView = inflater.inflate(R.layout.single_input_dialog, null);
        builder.setView(dialogView);
        builder.setMessage("Geben sie den Namen des Users ein");
        builder.setPositiveButton("Hinzufügen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText text = dialogView.findViewById(R.id.dialog_input);
                startAddContactService(text.getText().toString().trim());
                dialogInterface.cancel();
            }
        });
        builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).create().show();
        Log.e("AllContacts", "addNewContact ende");
    }

    private void startAddContactService(String string){
        Log.e("AllContacts", "startService anfang");
        ((ISetName) data).setName(string);
        Intent apiHanlder = new Intent(getActivity(), APIService.class);
        apiHanlder.putExtra(Keys.EXTRA_URL, "/user?api=" + I.getMyself().getApiKey());
        apiHanlder.putExtra(Keys.EXTRA_REQUEST, "GET");
        String data = null;
        apiHanlder.putExtra(Keys.EXTRA_DATA, data);
        apiHanlder.putExtra(Keys.EXTRA_ID, Keys.EXTRA_GET_USERS);
        apiHanlder.putExtra(Keys.EXTRA_SERVICE_TYPE, Keys.EXTRA_MAIN_ACTIVITY);
        getActivity().startService(apiHanlder);
        Log.e("AllContacts", "startService ende");
    }


    @Override
    public void receiveData() {
        contactList = data.getContacts();
        if(contactList != null) {
            updateContacts();
        }
    }

    private void sortContacts() {
        if (contactList != null) {
            Arrays.sort(contactList, new Comparator<User>() {
                @Override
                public int compare(User u1, User u2) {
                    return u1.getName().trim().compareTo(u2.getName().trim());
                }
            });
        }
    }

    @Override
    public void setExpandable(ExpandableFragment fragment) {


    }
}
