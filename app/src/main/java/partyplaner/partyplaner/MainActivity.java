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

import partyplaner.api.APIService;
import partyplaner.api.GeneralAPIRequestHandler;
import partyplaner.api.RouteType;
import partyplaner.api.ServiceDateReceiver;
import partyplaner.data.party.Party;
import partyplaner.data.party.PartyList;
import partyplaner.data.user.I;
import partyplaner.partyplaner.ContactForm.ContactFragment;
import partyplaner.partyplaner.Contacts.AllContacts;
import partyplaner.partyplaner.Profile.ProfileFragment;
import partyplaner.partyplaner.Veranstaltung.EditEventActivity;
import partyplaner.partyplaner.Veranstaltung.Fragmente.IReceiveData;
import partyplaner.partyplaner.Veranstaltung.IServiceReceiver;
import partyplaner.partyplaner.home.HomeFragment;
import partyplaner.partyplaner.ownEvents.OwnEventsFragment;
import partyplaner.partyplaner.Imprint.ImprintFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IFragmentDataManeger, IServiceReceiver {

    private static int currentTab = R.id.home;
    private IReceiveData currentTabReceiver;
    private Party[] parties;
    private Gson gson;
    private ServiceDateReceiver serviceDateReceiver;

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
                startActivity(intent);
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
            AllContacts all_contacts = new AllContacts();
            Bundle args = new Bundle();
            args.putString(Keys.EXTRA_NAME, "");
            currentTabReceiver = null;
            setFragmentToContent(new AllContacts());
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
        if (!json.contains("error")) {
            json = json.replaceAll(".*?\\[", "[");
            json = json.replaceAll("].", "]");
            parties = gson.fromJson(json, Party[].class);

            if (currentTabReceiver != null) {
                currentTabReceiver.receiveData();
            }
            endLoading();
        } else {
            Toast.makeText(this, "Daten konnten nicht geladen werden!", Toast.LENGTH_SHORT).show();
        }
    }

    private void endLoading() {
        RelativeLayout lr = findViewById(R.id.loading);
        lr.setVisibility(View.INVISIBLE);
    }

    private void startLoading() {
        RelativeLayout lr = findViewById(R.id.loading);
        lr.setVisibility(View.VISIBLE);
    }
}
