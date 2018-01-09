package partyplaner.partyplaner.Veranstaltung;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;
import partyplaner.partyplaner.Veranstaltung.Fragmente.EventHeaders;
import partyplaner.partyplaner.Veranstaltung.Fragmente.ExpandableFragment;
import partyplaner.partyplaner.Veranstaltung.Fragmente.Gallery;
import partyplaner.partyplaner.Veranstaltung.Fragmente.TaskList;

/**
 * Fragment for the detailed event view.
 * @author Malte
 * @since 16.11.17
 */
public class EventMainFragment extends Fragment {

    private String what = "Feier";
    private String who = "Tim";
    private String where = "Hier";
    private String when = "Jetzt";
    private String description = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    private boolean shortText = true;

    List<ExpandableFragment> headers = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_event_main, container, false);
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
        return view;
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
        if(description.length() <= 80) {
            eventDescription.setText(description);
        } else {
            eventDescription.setText(description.substring(0, 80) + "...");
        }
    }

    private void addFragment(String name, int id) {
        FragmentManager fm = getFragmentManager();

        Bundle arguments = new Bundle();
        arguments.putString(Keys.EXTRA_NAME, name);
        arguments.putInt(Keys.EXTRA_ID, id);
        ExpandableFragment fragment = new ExpandableFragment();
        fragment.setArguments(arguments);

        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.eventBody, fragment);
        transaction.commit();
        headers.add(fragment);
    }

    private void setUpExpandableView(View view) {

        addFragment("Gallerie", 0);
        addFragment("Aufgaben", 1);
        if(true) {
            addFragment("TODOFragment", 2);
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

