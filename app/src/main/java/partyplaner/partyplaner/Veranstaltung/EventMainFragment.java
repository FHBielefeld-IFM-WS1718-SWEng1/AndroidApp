package partyplaner.partyplaner.Veranstaltung;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import partyplaner.data.party.Party;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;
import partyplaner.partyplaner.Veranstaltung.Fragmente.ExpandableFragment;

/**
 * Fragment for the detailed event view.
 * @author Malte
 * @since 16.11.17
 */
public class EventMainFragment extends Fragment {

    private IEventDataManager data;
    private String what = "";
    private String who = "";
    private String where = "";
    private String when = "";
    private String description = "";
    private boolean shortText = true;
    private List<ExpandableFragment> fragments = new ArrayList<>();
    private View view;

    List<ExpandableFragment> headers = new ArrayList<>();

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_event_main, container, false);
        this.view = view;
        if(savedInstanceState == null) {
            setUpDescription(view);
            setUpExpandableView(view);

            final Button more = view.findViewById(R.id.button_more);
            more.setVisibility(View.INVISIBLE);

            if (description.length() > 80) {
                more.setVisibility(View.VISIBLE);
                more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView description = view.findViewById(R.id.event_description);
                        if (shortText) {
                            description.setText(getDescription());
                            more.setText("weniger...");
                            shortText = false;
                        } else {
                            description.setText((getDescription().substring(0, 80) + "..."));
                            more.setText("mehr...");
                            shortText = true;
                        }

                    }
                });
            }
        }
        return view;
    }

    public void receiveData() {
        String[] data = this.data.getGeneralInformations();
        this.what = data[0];
        this.who = data[1];
        this.when = Party.parseDate(data[2]);
        this.where = data[3];
        this.description = data[4];

        setUpDescription(view);
        for(ExpandableFragment fragment : fragments) {
            fragment.receiveData();
        }
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(boolean bool) {
        shortText = bool;
    }

    private void setUpDescription(View view) {
        TextView eventWhat = view.findViewById(R.id.event_what);
        TextView eventWho = view.findViewById(R.id.event_who);
        TextView eventWhere = view.findViewById(R.id.event_where);
        TextView eventWhen = view.findViewById(R.id.event_when);
        TextView eventDescription = view.findViewById(R.id.event_description);

        eventWhat.setText(what);
        eventWho.setText(who);
        eventWhere.setText(where);
        eventWhen.setText(when);
        if (description != null) {
            if (description.length() <= 80) {
                eventDescription.setText(description);
            } else {
                eventDescription.setText(description.substring(0, 80) + "...");
            }
        }
    }

    private void addFragment(String name, int id) {
        FragmentManager fm = getFragmentManager();

        Bundle arguments = new Bundle();
        arguments.putString(Keys.EXTRA_NAME, name);
        arguments.putInt(Keys.EXTRA_ID, id);
        ExpandableFragment fragment = new ExpandableFragment();
        fragment.setArguments(arguments);
        fragments.add(fragment);

        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.eventBody, fragment);
        transaction.commit();
        headers.add(fragment);
    }

    private void setUpExpandableView(View view) {

        addFragment("Gallerie", 0);
        addFragment("Aufgaben", 1);
        if(true) {
            addFragment("TODO", 2);
        }
        addFragment("Gäste", 6);
        addFragment("Abstimmungen", 3);
        addFragment("Bewertungen", 5);
        addFragment("Kommentare", 4);
    }

    /**
     * This methode closes all ExpandableFragment of the event.
     */
    public void collapseAll() {
        for (ExpandableFragment fragment : headers) {
            fragment.collapseGroup();
        }
    }
}

