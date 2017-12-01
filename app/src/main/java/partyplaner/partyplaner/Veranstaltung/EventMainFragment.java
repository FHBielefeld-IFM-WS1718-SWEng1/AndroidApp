package partyplaner.partyplaner.Veranstaltung;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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

    List<ExpandableFragment> headers = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_main, container, false);
        setUpExpandableView(view);
        return view;
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
        LinearLayout layout = view.findViewById(R.id.eventBody);
        layout.removeAllViews();

        addFragment("Gallerie", 0);
        addFragment("Aufgaben", 1);
        if(true) {
            addFragment("TODO", 2);
        }
        addFragment("Gäste", 5);
        addFragment("Abstimmungen", 3);
        addFragment("Bewertungen", 6);
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

