package partyplaner.partyplaner.Veranstaltung.Fragmente;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;

/**
 * Created by malte on 16.11.2017.
 */

public class TaskList extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_fragment_tasklist, container, false);

        setUpTestTask();

        return view;
    }

    private void setUpTestTask() {
        Bundle arguments = new Bundle();
        arguments.putString(Keys.EXTRA_NAME, "Tim");
        arguments.putString(Keys.EXTRA_TASK, "Aufgabe");
        arguments.putBoolean(Keys.EXTRA_STATUS, true);
        arguments.putBoolean(Keys.EXTRA_OWNER, true);
        Task task = new Task();
        task.setArguments(arguments);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.body_tasklist, task);
        transaction.commit();

        arguments = new Bundle();
        arguments.putString(Keys.EXTRA_NAME, "Florian");
        arguments.putString(Keys.EXTRA_TASK, "Arbeiten");
        arguments.putBoolean(Keys.EXTRA_STATUS, false);
        arguments.putBoolean(Keys.EXTRA_OWNER, false);
        task = new Task();
        task.setArguments(arguments);

        transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.body_tasklist, task);
        transaction.commit();
    }
}
