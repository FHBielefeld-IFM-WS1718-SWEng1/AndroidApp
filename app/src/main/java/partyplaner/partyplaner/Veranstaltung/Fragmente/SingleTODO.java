package partyplaner.partyplaner.Veranstaltung.Fragmente;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import partyplaner.api.APIService;
import partyplaner.data.user.I;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;
import partyplaner.partyplaner.Veranstaltung.IEventDataManager;

/**
 * Created by malte on 23.11.2017.
 */

public class SingleTODO extends Fragment {

    private  int id;
    private int partyId;
    private String text;
    private ExpandableFragment expandableFragment;
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
        View view = inflater.inflate(R.layout.event_fragment_single_todo, container, false);

        if (savedInstanceState == null) {
            Bundle arguments = getArguments();
            text = arguments.getString(Keys.EXTRA_NAME);
            boolean status = arguments.getBoolean(Keys.EXTRA_STATUS);
            boolean owner = arguments.getBoolean(Keys.EXTRA_OWNER);
            partyId = arguments.getInt(Keys.EXTRA_PARTYID);
            id = arguments.getInt(Keys.EXTRA_ID);

            TextView textName = view.findViewById(R.id.todo_name);
            CheckBox textStatus = view.findViewById(R.id.todo_status);
            ImageView button = view.findViewById(R.id.todo_delete);

            Log.e("SingleTodo", String.valueOf(owner));
            textName.setText(text);
            textStatus.setChecked(status);

            setDeleteButton(owner, textStatus, button);

            textStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    updateTODOStaus(isChecked);
                }
            });
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (expandableFragment != null) {
            expandableFragment.reexpandGroup();
        }
    }

    private void updateTODOStaus(boolean isChecked) {
        Intent apiHanlder = new Intent(getActivity(), APIService.class);
        apiHanlder.putExtra(Keys.EXTRA_URL, "/party/todo/" + id + "?api=" + I.getMyself().getApiKey());
        apiHanlder.putExtra(Keys.EXTRA_REQUEST, "PUT");
        int status = (isChecked) ? 1 : 0;
        String data = "{\"user_id\":" + I.getMyself().getId() + ",\"party_id\":" + partyId +",\"text\":\"" + text + "\",\"status\":" + status + "}";
        Log.e("SingleTodo", data);
        apiHanlder.putExtra(Keys.EXTRA_DATA, data);
        apiHanlder.putExtra(Keys.EXTRA_ID, Keys.EXTRA_POST_TASK);
        apiHanlder.putExtra(Keys.EXTRA_SERVICE_TYPE, Keys.EXTRA_SERVICE);
        getActivity().startService(apiHanlder);
    }

    private void setDeleteButton(boolean owner, CheckBox textStatus, ImageView button) {
        if (!owner) {
            button.setVisibility(View.INVISIBLE);
        } else {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Willst du die Aufagbe wirklich löschen?")
                            .setPositiveButton("Löschen", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteTodo();
                                    dialog.cancel();
                                }
                            }).setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).create().show();
                }
            });
            textStatus.setClickable(true);
        }
    }

    private void deleteTodo() {
        Intent apiHanlder = new Intent(getActivity(), APIService.class);
        apiHanlder.putExtra(Keys.EXTRA_URL, "/party/todo/" + id + "?api=" + I.getMyself().getApiKey());
        apiHanlder.putExtra(Keys.EXTRA_REQUEST, "DELETE");
        String data = "{\"id\":" + id + "}";
        Log.e("SingleTodo", data);
        apiHanlder.putExtra(Keys.EXTRA_DATA, data);
        apiHanlder.putExtra(Keys.EXTRA_ID, Keys.EXTRA_DELETE_TASK);
        apiHanlder.putExtra(Keys.EXTRA_SERVICE_TYPE, Keys.EXTRA_SERVICE);
        getActivity().startService(apiHanlder);
        this.data.startLoading();
    }

    public void setExpandableFragment(ExpandableFragment expandableFragment) {
        this.expandableFragment = expandableFragment;
    }
}
