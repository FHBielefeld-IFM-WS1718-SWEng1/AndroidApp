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
import android.widget.EditText;
import android.widget.LinearLayout;

import partyplaner.api.APIService;
import partyplaner.api.ServiceDateReceiver;
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
    private IEventDataManager data;

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
        setTasksToFragments();
        Button button = view.findViewById(R.id.add_todo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View dialogView = inflater.inflate(R.layout.single_input_dialog, null);
                builder.setView(dialogView);
                EditText input = null;
                builder.setMessage("Aufgabenname eingeben")
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
        return view;
    }

    public void createTodo (String name) {
        Log.e("TODOFragment", name);

        Intent apiHanlder = new Intent(getActivity(), APIService.class);
        apiHanlder.putExtra(Keys.EXTRA_URL, "/party/241121?api=" + I.getMyself().getApiKey());
        apiHanlder.putExtra(Keys.EXTRA_REQUEST, "GET");
        String data = null;
        apiHanlder.putExtra(Keys.EXTRA_DATA, data);
        apiHanlder.putExtra(Keys.EXTRA_ID, Keys.EXTRA_PUT_TASK);
        getActivity().startService(apiHanlder);

    }

    private void addTodo(Todo data) {
        Bundle arguments = new Bundle();
        SingleTODO todo = new SingleTODO();
        arguments.putString(Keys.EXTRA_NAME, data.getText());
        if(data.getStatus() == 1) {
            arguments.putBoolean(Keys.EXTRA_STATUS, true);
        } else {
            arguments.putBoolean(Keys.EXTRA_STATUS, false);
        }
        if (this.data.getParty() != null) {
            arguments.putBoolean(Keys.EXTRA_OWNER, this.data.getParty().getOwner().getId() == I.getMyself().getId());
        }
        todo.setArguments(arguments);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.body_todo, todo);
        transaction.commit();
    }

    @Override
    public void receiveData() {
        if (data.getParty() != null) {
            todos = data.getParty().getTodo();
        }
        setTasksToFragments();
    }

    private void setTasksToFragments() {
        LinearLayout linearLayout = view.findViewById(R.id.body_todo);
        linearLayout.removeAllViewsInLayout();
        if (todos != null && data.getParty() != null) {
            for (Todo todo : todos) {
                addTodo(todo);
            }
        }
        linearLayout.removeAllViews();
    }
}
