package partyplaner.partyplaner.Veranstaltung.Fragmente;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;

/**
 * Created by malte on 17.11.2017.
 */

public class Task extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_fragment_task, container, false);

        Bundle arguments = getArguments();
        String name = arguments.getString(Keys.EXTRA_NAME);
        String task = arguments.getString(Keys.EXTRA_TASK);
        boolean status = arguments.getBoolean(Keys.EXTRA_STATUS);
        boolean owner = arguments.getBoolean(Keys.EXTRA_OWNER);

        TextView nameText = view.findViewById(R.id.name_tasklist);
        TextView taskText = view.findViewById(R.id.task_tasklist);
        CheckBox statusBox = view.findViewById(R.id.status_tasklist);
        if (!owner) {
            view.findViewById(R.id.deleteButtonTask).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.editButtonTask).setVisibility(View.INVISIBLE);
        }

        nameText.setText(name);
        taskText.setText(task);
        statusBox.setChecked(status);

        return view;
    }
}
