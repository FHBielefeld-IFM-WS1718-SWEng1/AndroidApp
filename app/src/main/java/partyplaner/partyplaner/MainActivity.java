package partyplaner.partyplaner;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import partyplaner.api.APIService;
import partyplaner.api.GeneralAPIRequestHandler;
import partyplaner.api.RouteType;
import partyplaner.api.ServiceDateReceiver;
import partyplaner.data.party.Party;
import partyplaner.data.user.User;
import partyplaner.data.party.PartyList;
import partyplaner.data.user.I;
import partyplaner.partyplaner.ContactForm.ContactFragment;
import partyplaner.partyplaner.Contacts.AllContacts;
import partyplaner.partyplaner.LogIn.LogInActivity;
import partyplaner.partyplaner.LogIn.RegisterActivity;
import partyplaner.partyplaner.Profile.EditProfileActivity;
import partyplaner.partyplaner.Profile.ProfileFragment;
import partyplaner.partyplaner.Veranstaltung.EditEventActivity;
import partyplaner.partyplaner.Veranstaltung.Fragmente.IReceiveData;
import partyplaner.partyplaner.Veranstaltung.IServiceReceiver;
import partyplaner.partyplaner.home.HomeFragment;
import partyplaner.partyplaner.ownEvents.OwnEventsFragment;
import partyplaner.partyplaner.Imprint.ImprintFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IFragmentDataManeger, IServiceReceiver, AllContacts.ISetName {

    private static int currentTab = R.id.home;
    private IReceiveData currentTabReceiver;
    private Party[] parties;
    private User[] contactList;
    private Gson gson;
    private ServiceDateReceiver serviceDateReceiver;
    private String addName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(currentTab);

        gson = new Gson();

        setActiveFragment(currentTab);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();

        IntentFilter statusIntentFilter = new IntentFilter(Keys.EXTRA_MAIN_ACTIVITY);
        serviceDateReceiver = new ServiceDateReceiver(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(serviceDateReceiver, statusIntentFilter);

    }

    private void loadData() {
        startLoading();

        parties = null;
        Intent apiHanlder = new Intent(this, APIService.class);
        apiHanlder.putExtra(Keys.EXTRA_URL, "/party?api=" + I.getMyself().getApiKey());
        apiHanlder.putExtra(Keys.EXTRA_REQUEST, "GET");
        String data = null;
        apiHanlder.putExtra(Keys.EXTRA_DATA, data);
        apiHanlder.putExtra(Keys.EXTRA_ID, Keys.EXTRA_GET_PARTIES);
        apiHanlder.putExtra(Keys.EXTRA_SERVICE_TYPE, Keys.EXTRA_MAIN_ACTIVITY);
        this.startService(apiHanlder);

        //TODO: IN Service
        contactList = null;
        apiHanlder = new Intent(this, APIService.class);
        apiHanlder.putExtra(Keys.EXTRA_URL, "/user/contact?api=" + I.getMyself().getApiKey());
        apiHanlder.putExtra(Keys.EXTRA_REQUEST, "GET");
        data = null;
        apiHanlder.putExtra(Keys.EXTRA_DATA, data);
        apiHanlder.putExtra(Keys.EXTRA_ID, Keys.EXTRA_GET_CONTACTS);
        apiHanlder.putExtra(Keys.EXTRA_SERVICE_TYPE, Keys.EXTRA_MAIN_ACTIVITY);
        this.startService(apiHanlder);
        String json2 = GeneralAPIRequestHandler.request("/user/contact?api=" + I.getMyself().getApiKey(), RouteType.GET, null);
        //Log.e("MainActivity", json2 + "");

    }

    @Override
    public void onBackPressed() {
        //TODOFragment Bei zurück zum vorherigen Menüpunkt gehen
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_add_event:
                Intent intent = new Intent(this, EditEventActivity.class);
                intent.putExtra(Keys.EXTRA_EDIT_PARTY, false);
                startActivity(intent);
                break;
            case R.id.action_edit_profile:
                Intent editProfileIntent = new Intent(this, EditProfileActivity.class);
                startActivity(editProfileIntent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(serviceDateReceiver);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        setActiveFragment(id);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //receive DATA Speichern
    private void setActiveFragment(int id) {
        currentTab = id;
        if (id == R.id.home) {
            HomeFragment fragment = new HomeFragment();
            currentTabReceiver = fragment;
            setFragmentToContent(fragment);
        } else if (id == R.id.profile){
            setFragmentToContent(new ProfileFragment());
            currentTabReceiver = null;
        } else if (id == R.id.contacts) {
            AllContacts contacts = new AllContacts();
            currentTabReceiver = contacts;
            setFragmentToContent(contacts);
        } else if (id == R.id.ownEvents) {
            OwnEventsFragment ownEvent = new OwnEventsFragment();
            Bundle args = new Bundle();
            args.putSerializable(Keys.EXTRA_PARTYLIST, new PartyList());
            ownEvent.setArguments(args);
            currentTabReceiver = ownEvent;
            setFragmentToContent(ownEvent);
        } else if (id == R.id.help) {

        } else if (id == R.id.contactFormular) {
            currentTabReceiver = null;
            setFragmentToContent(new ContactFragment());
        } else if (id == R.id.impressum) {
            currentTabReceiver = null;
            setFragmentToContent(new ImprintFragment());
        } else if (id == R.id.logout) {
            currentTabReceiver = null;
            currentTab = R.id.home;
            logOut();
        }
    }

    private void logOut() {
        if (I.getMyself().logout()) {
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
        }
    }

    public void setFragmentToContent(Fragment fragment) {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        transaction.replace(R.id.content, fragment);

        transaction.commit();
    }

    @Override
    public Party[] getParties() {
        return parties;
    }

    @Override
    public Party[] getOwnParties() {
        ArrayList<Party> ownParties = new ArrayList<>();
        for (Party party : parties) {
            if (party.isErsteller()) {
                ownParties.add(party);
            }
        }
        return ownParties.toArray(new Party[ownParties.size()]);
    }

    @Override
    public boolean partyReceived() {
        if (parties == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void receiveData(String json, String id) {
        Log.e(getClass().getName(), id);
        if (json != null) {
            switch (id) {
                case Keys.EXTRA_GET_PARTIES:
                    if (json != null && !json.contains("error")) {
                        json = json.replaceAll(".*?\\[", "[");
                        json = json.replaceAll("].", "]");
                        parties = gson.fromJson(json, Party[].class);
                        sortParties();
                        if (currentTabReceiver != null) {
                            currentTabReceiver.receiveData();
                        }
                        endLoading();
                    } else {
                        Toast.makeText(this, "Daten konnten nicht geladen werden!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Keys.EXTRA_DELETE_PARTIES:
                    if (json != null && !json.contains("error")) {
                        Toast.makeText(this, "Party erfolgreich gelöscht!", Toast.LENGTH_SHORT).show();
                        loadData();
                    } else {
                        Toast.makeText(this, "Party konnte nicht gelöscht werden!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Keys.EXTRA_GET_CONTACTS:
                    Log.e(getClass().getName(), json);
                    if (!json.contains("error")) {
                        json = json.replaceAll(".*?\\[", "[");
                        json = json.replaceAll("].", "]");
                        contactList = gson.fromJson(json, User[].class);
                        if (currentTabReceiver != null) {
                            currentTabReceiver.receiveData();
                        }
                    } else {
                        Toast.makeText(this, "Kontakte konnten nicht geladen werden!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Keys.EXTRA_GET_USERS:
                    if(json != null && !json.contains("error")){
                        json = json.replaceAll(".*?\\[", "[");
                        json = json.replaceAll("].", "]");
                        User[] user = gson.fromJson(json, User[].class);
                        checkUserName(user);

                    }
                    break;
                case Keys.EXTRA_POST_CONTACT:
                    if(json != null && !json.contains("error")){
                        loadData();
                    }else{
                        Toast.makeText(this, "Hinzufügen fehlgeschlagen",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }else {
            Toast.makeText(this, "Daten konnten nicht geladen werden!", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkUserName(User[] user) {
        int placeholder = -1;
        for (int i = 0; i < user.length; i++) {
            if (user[i].getName().equals(addName)) {
                placeholder = i;
            }
        }
        boolean alreadyAContact = false;
        if(placeholder >= 0){
            for(int i = 0; i<contactList.length; i++){
                if(user[i].getName().equals(addName)){
                    alreadyAContact = true;
                }
            }
            if(alreadyAContact!=true){
                Intent apiHanlder = new Intent(this, APIService.class);
                apiHanlder.putExtra(Keys.EXTRA_URL, "/user/contact?api=" + I.getMyself().getApiKey());
                apiHanlder.putExtra(Keys.EXTRA_REQUEST, "POST");
                String data = "{\"id\":"+placeholder+"}";
                apiHanlder.putExtra(Keys.EXTRA_DATA, data);
                apiHanlder.putExtra(Keys.EXTRA_ID, Keys.EXTRA_POST_CONTACT);
                apiHanlder.putExtra(Keys.EXTRA_SERVICE_TYPE, Keys.EXTRA_MAIN_ACTIVITY);
                this.startService(apiHanlder);
            }
        }
    }

    private void sortParties() {
        Log.e(getClass().getName(), "sort()");
        Arrays.sort(parties, new Comparator<Party>() {
            @Override
            public int compare(Party p1, Party p2) {
                int[] p1Date = p1.getStartDateArray();
                int[] p2Date = p2.getStartDateArray();
                for (int i = 0; i < p1Date.length - 1; i++) {
                    if (Integer.compare(p1Date[i], p2Date[i]) != 0) {
                        return Integer.compare(p1Date[i], p2Date[i]);
                    }
                }
                return Integer.compare(p1Date[4], p2Date[4]);
            }
        });
    }

    private void endLoading() {
        RelativeLayout lr = findViewById(R.id.loading);
        lr.setVisibility(View.INVISIBLE);
    }

    public void startLoading() {
        RelativeLayout lr = findViewById(R.id.loading);
        lr.setVisibility(View.VISIBLE);
    }

    public User[] getContacts() {
        return contactList;
    }

    @Override
    public void setName(String name) {
        addName = name;
    }
}
