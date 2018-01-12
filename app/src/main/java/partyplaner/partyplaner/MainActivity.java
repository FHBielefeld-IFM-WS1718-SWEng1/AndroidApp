package partyplaner.partyplaner;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import partyplaner.api.GeneralAPIRequestHandler;
import partyplaner.api.RouteType;
import partyplaner.data.party.Party;
import partyplaner.data.user.User;
import partyplaner.data.party.PartyList;
import partyplaner.data.user.I;
import partyplaner.partyplaner.ContactForm.ContactFragment;
import partyplaner.partyplaner.Contacts.AllContacts;
import partyplaner.partyplaner.Profile.ProfileFragment;
import partyplaner.partyplaner.home.HomeFragment;
import partyplaner.partyplaner.ownEvents.OwnEventsFragment;
import partyplaner.partyplaner.Imprint.ImprintFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IFragmentDataManeger {

    private static int currentTab = R.id.home;
    private Party[] parties;
    private User[] contactList;
    private Gson gson;

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
        loadData();

        setActiveFragment(currentTab);
    }

    private void loadData() {
        String json = GeneralAPIRequestHandler.request("/party?api=" + I.getMyself().getApiKey(), RouteType.GET, null);
        json = json.replaceAll(".*?\\[", "[");
        json = json.replaceAll("].", "]");
        parties = gson.fromJson(json, Party[].class);
        String json2 = GeneralAPIRequestHandler.request("user/contact/?api=" + I.getMyself().getApiKey(), RouteType.GET, null);
        json = json2.replaceAll(".*?\\[", "[");
        json = json2.replaceAll("].", "]");
        contactList = gson.fromJson(json, User[].class);
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
            case R.id.action_settings:

                break;
        }

        return super.onOptionsItemSelected(item);
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

    private void setActiveFragment(int id) {
        currentTab = id;
        if (id == R.id.home) {
            setFragmentToContent(new HomeFragment());
        } else if (id == R.id.profile){
            setFragmentToContent(new ProfileFragment());
        } else if (id == R.id.contacts) {
            AllContacts all_contacts = new AllContacts();
            Bundle args = new Bundle();
            args.putString(Keys.EXTRA_NAME, "");
            setFragmentToContent(new AllContacts());
        } else if (id == R.id.ownEvents) {
            OwnEventsFragment ownEvent = new OwnEventsFragment();
            Bundle args = new Bundle();
            args.putSerializable(Keys.EXTRA_PARTYLIST, new PartyList());
            ownEvent.setArguments(args);
            setFragmentToContent(ownEvent);
        } else if (id == R.id.help) {

        } else if (id == R.id.contactFormular) {
            setFragmentToContent(new ContactFragment());
        } else if (id == R.id.impressum) {
            setFragmentToContent(new ImprintFragment());
        } else if (id == R.id.logout) {
            logOut();
        }
    }

    private void logOut() {

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
    public User[] getContacts() {
        return contactList;
    }
}
