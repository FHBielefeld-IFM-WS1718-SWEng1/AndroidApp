package partyplaner.partyplaner.Veranstaltung.Fragmente;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import partyplaner.data.party.Task;
import partyplaner.data.user.I;
import partyplaner.data.user.User;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;
import partyplaner.partyplaner.Veranstaltung.IEventDataManager;

/**
 * Created by malte on 16.11.2017.
 */

public class TaskList extends Fragment implements IReceiveData{

    private IEventDataManager data;
    private Task[] tasks;
    private View view;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_fragment_tasklist, container, false);
        this.view = view;
        return view;
    }

    private void addTask(Task task) {
        Bundle arguments = new Bundle();
        arguments.putString(Keys.EXTRA_NAME, task.getResponsibleUser().getName());
        arguments.putString(Keys.EXTRA_TASK, task.getTask());
        if(task.isDone() == 1) {
            arguments.putBoolean(Keys.EXTRA_STATUS, true);
        } else {
            arguments.putBoolean(Keys.EXTRA_STATUS, false);
        }
        arguments.putBoolean(Keys.EXTRA_OWNER, data.getParty().getOwner().getId() == I.getMyself().getId());
        TaskFragment taskFragment = new TaskFragment();
        taskFragment.setArguments(arguments);
        taskFragment.onCreate(null);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.body_tasklist, taskFragment);
        transaction.commit();

        LinearLayout linearLayout = view.findViewById(R.id.body_tasklist);
        linearLayout.refreshDrawableState();
    }

    @Override
    public void receiveData() {
        LinearLayout linearLayout = view.findViewById(R.id.body_tasklist);
        linearLayout.removeAllViewsInLayout();

        if (data.getParty() != null) {
            tasks = data.getParty().getTasks();
            for(Task task : tasks) {
                addTask(task);
            }
        }
    }

}
