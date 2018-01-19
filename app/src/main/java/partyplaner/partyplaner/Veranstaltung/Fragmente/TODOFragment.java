package partyplaner.partyplaner.Veranstaltung.Fragmente;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import partyplaner.api.APIService;
import partyplaner.data.party.Todo;
import partyplaner.data.user.I;
import partyplaner.partyplaner.EventMainActivity;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;
import partyplaner.partyplaner.Veranstaltung.IEventDataManager;

/**
 * Created by malte on 23.11.2017.
 */

public class TODOFragment extends Fragment implements IReceiveData{

    private Todo[] todos;
    private View view;
    private int partyId;
    private IEventDataManager data;
    private List<Fragment> fragments = new ArrayList<>();
    private ExpandableFragment expandableFragment;

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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_fragment_todo, container, false);
        this.view = view;
        if (savedInstanceState == null) {
            setTasksToFragments();
            setAddButton(inflater, view);
        }

        return view;
    }

    private void setAddButton(final LayoutInflater inflater, View view) {
        Button button = view.findViewById(R.id.add_todo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View dialogView = inflater.inflate(R.layout.single_input_dialog, null);
                builder.setView(dialogView);
                EditText input = null;
                builder.setMessage("Todo eingeben")
                        .setPositiveButton("Einfügen", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText input = dialogView.findViewById(R.id.dialog_input);
                            createTodo(input.getText().toString());
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

    public void createTodo (String name) {
        Log.e("TODOFragment", name);
        if(name != null) {
            name = name.trim();
            if (!name.equals("")) {
                Intent apiHanlder = new Intent(getActivity(), APIService.class);
                apiHanlder.putExtra(Keys.EXTRA_URL, "/party/todo?api=" + I.getMyself().getApiKey());
                apiHanlder.putExtra(Keys.EXTRA_REQUEST, "POST");
                String data = "{\"user_id\":" + I.getMyself().getId() + ",\"party_id\":" + partyId + ",\"text\":\"" + name + "\",\"status\":0" + "}";
                Log.e("TODO", data);
                apiHanlder.putExtra(Keys.EXTRA_DATA, data);
                apiHanlder.putExtra(Keys.EXTRA_ID, Keys.EXTRA_PUT_TASK);
                apiHanlder.putExtra(Keys.EXTRA_SERVICE_TYPE, Keys.EXTRA_SERVICE);
                getActivity().startService(apiHanlder);
                this.data.startLoading();

            }
        }
    }

    private void addTodo(Todo data) {
        Bundle arguments = new Bundle();
        SingleTODO todo = new SingleTODO();
        todo.setExpandableFragment(expandableFragment);
        arguments.putString(Keys.EXTRA_NAME, data.getText());
        arguments.putInt(Keys.EXTRA_PARTYID, partyId);
        arguments.putInt(Keys.EXTRA_ID, data.getId());
        if(data.getStatus() == 1) {
            arguments.putBoolean(Keys.EXTRA_STATUS, true);
        } else {
            arguments.putBoolean(Keys.EXTRA_STATUS, false);
        }
        if (this.data.getParty() != null) {
            arguments.putBoolean(Keys.EXTRA_OWNER, this.data.getParty().getOwner().getId() == I.getMyself().getId());
        }
        todo.setArguments(arguments);

        fragments.add(todo);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.body_todo, todo);
        transaction.commit();
    }

    @Override
    public void receiveData() {
        if (data.getParty() != null) {
            todos = data.getParty().getTodo();
            partyId = data.getParty().getId();
        }
        setTasksToFragments();
    }

    @Override
    public void setExpandable(ExpandableFragment fragment) {
        this.expandableFragment = fragment;
    }

    private void setTasksToFragments() {
        for (Fragment f : fragments) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.remove(f);
            transaction.commit();
        }
        fragments.clear();
        if (todos != null && data.getParty() != null) {
            for (Todo todo : todos) {
                addTodo(todo);
            }
        }
    }
}
