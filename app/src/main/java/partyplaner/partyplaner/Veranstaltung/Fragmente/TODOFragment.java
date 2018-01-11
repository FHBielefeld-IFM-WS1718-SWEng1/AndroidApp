package partyplaner.partyplaner.Veranstaltung.Fragmente;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import partyplaner.data.party.Todo;
import partyplaner.data.user.I;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_fragment_todo, container, false);
        this.view = view;
        Button button = view.findViewById(R.id.add_todo);
        return view;
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
        arguments.putBoolean(Keys.EXTRA_OWNER, this.data.getParty().getOwner().getId() == I.getMyself().getId());
        todo.setArguments(arguments);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.body_todo, todo);
        transaction.commit();
    }

    @Override
    public void receiveData() {
        LinearLayout linearLayout = view.findViewById(R.id.body_todo);
        linearLayout.removeAllViewsInLayout();

        if (data.getParty() != null) {
            todos = data.getParty().getTodo();
            for (Todo todo : todos) {
                addTodo(todo);
            }
        }
    }
}
