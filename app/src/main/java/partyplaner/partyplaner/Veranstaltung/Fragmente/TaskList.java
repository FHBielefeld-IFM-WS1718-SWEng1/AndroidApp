package partyplaner.partyplaner.Veranstaltung.Fragmente;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import partyplaner.api.APIService;
import partyplaner.data.party.Guest;
import partyplaner.data.party.Party;
import partyplaner.data.party.Task;
import partyplaner.data.party.Todo;
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
    private int partyId;
    private boolean ersteller;
    private ExpandableFragment expandableFragment;
    private List<Fragment> fragments = new ArrayList<>();

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
        if (savedInstanceState == null) {
            setTasksToFragments();
            setAddButton(inflater, view);
        }
        return view;
    }

    private void setAddButton(final LayoutInflater inflater, View view) {
        Button button = view.findViewById(R.id.add_task);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View dialogView = inflater.inflate(R.layout.double_input_dialog, null);
                builder.setView(dialogView);
                EditText input = null;
                builder.setMessage("Aufgabenname eingeben")
                        .setPositiveButton("Einfügen", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText inputTask = dialogView.findViewById(R.id.double_dialog_name_input);
                                EditText inputUser = dialogView.findViewById(R.id.double_dialog_user_input);
                                createTask(inputUser.getText().toString(), inputTask.getText().toString());
                                dialog.cancel();
                            }
                        }).setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void createTask(String name, String task) {
        Log.e(getClass().getName(), "createTask");
        User us = null;
        if (data.getParty().getOwner().getName().equals(name)) {
            us = data.getParty().getOwner();
        }
        for (Guest guest : data.getParty().getGuests()) {
            if (guest.getUser().getName().equals(name)) {
                us = guest.getUser();
            }
        }

        if (us != null && !task.trim().equals("")) {
            Intent apiHanlder = new Intent(getActivity(), APIService.class);
            apiHanlder.putExtra(Keys.EXTRA_URL, "/party/task?api=" + I.getMyself().getApiKey());
            apiHanlder.putExtra(Keys.EXTRA_REQUEST, "POST");
            String data = "{\"user_id\":" + us.getId() + ",\"party_id\":" + partyId + ",\"text\":\"" + task.trim() + "\",\"status\":0" + "}";
            Log.e(getClass().getName(), data);
            apiHanlder.putExtra(Keys.EXTRA_DATA, data);
            apiHanlder.putExtra(Keys.EXTRA_ID, Keys.EXTRA_PUT_TASK);
            apiHanlder.putExtra(Keys.EXTRA_SERVICE_TYPE, Keys.EXTRA_SERVICE);
            getActivity().startService(apiHanlder);
            this.data.startLoading();
        }
    }

    private void addTask(Task task) {
        Bundle arguments = new Bundle();
        arguments.putString(Keys.EXTRA_NAME, task.getResponsibleUser().getName());
        arguments.putString(Keys.EXTRA_TASK, task.getTask());
        arguments.putInt(Keys.EXTRA_ID, task.getId());
        if(task.isDone() == 1) {
            arguments.putBoolean(Keys.EXTRA_STATUS, true);
        } else {
            arguments.putBoolean(Keys.EXTRA_STATUS, false);
        }
        if (data.getParty() != null) {
            arguments.putBoolean(Keys.EXTRA_OWNER, data.getParty().getOwner().getId() == I.getMyself().getId());
        }
        TaskFragment taskFragment = new TaskFragment();
        taskFragment.setArguments(arguments);
        taskFragment.onCreate(null);
        taskFragment.setExpandable(expandableFragment);

        fragments.add(taskFragment);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.body_tasklist, taskFragment);
        transaction.commit();

        LinearLayout linearLayout = view.findViewById(R.id.body_tasklist);
        linearLayout.refreshDrawableState();
        view.refreshDrawableState();
    }

    @Override
    public void receiveData() {
        if (data.getParty() != null) {
            tasks = data.getParty().getTasks();
            ersteller = data.getParty().getOwner().getId() == I.getMyself().getId();
            partyId = data.getParty().getId();
        }
        setTasksToFragments();
    }

    @Override
    public void setExpandable(ExpandableFragment fragment) {
        expandableFragment = fragment;
    }

    private void setTasksToFragments() {
        for (Fragment f : fragments) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.remove(f);
            transaction.commit();
        }

        fragments.clear();
        if (tasks != null && data.getParty() != null) {
            for (Task task : tasks) {
                addTask(task);
            }
        }
    }
}
