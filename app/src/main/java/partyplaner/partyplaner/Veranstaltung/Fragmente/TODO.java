package partyplaner.partyplaner.Veranstaltung.Fragmente;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;

/**
 * Created by malte on 23.11.2017.
 */

public class TODO extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_fragment_todo, container, false);

        testSetUp();

        return view;
    }

    private void testSetUp() {
        Bundle arguments = new Bundle();
        SingleTODO todo = new SingleTODO();
        arguments.putString(Keys.EXTRA_NAME, "Arbeit1");
        arguments.putDouble(Keys.EXTRA_PRICE, 0.0);
        arguments.putBoolean(Keys.EXTRA_OWNER, true);
        todo.setArguments(arguments);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.body_todo, todo);
        transaction.commit();

        arguments = new Bundle();
        todo = new SingleTODO();
        arguments.putString(Keys.EXTRA_NAME, "Arbeit2");
        arguments.putDouble(Keys.EXTRA_PRICE, 100.999);
        arguments.putBoolean(Keys.EXTRA_OWNER, false);
        todo.setArguments(arguments);

        transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.body_todo, todo);
        transaction.commit();
    }
}
