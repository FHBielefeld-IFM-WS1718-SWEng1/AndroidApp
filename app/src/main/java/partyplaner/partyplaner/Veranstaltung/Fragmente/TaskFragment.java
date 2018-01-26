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
import android.widget.TableLayout;
import android.widget.TextView;

import partyplaner.api.APIService;
import partyplaner.data.party.Guest;
import partyplaner.data.user.I;
import partyplaner.data.user.User;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;
import partyplaner.partyplaner.Veranstaltung.IEventDataManager;

/**
 * Created by malte on 17.11.2017.
 */

public class TaskFragment extends Fragment {

    private String user;
    private String task;
    private int id;
    private View view;
    private IEventDataManager data;
    private boolean isChecked;
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
        View view = inflater.inflate(R.layout.event_fragment_task, container, false);
        this.view = view;

        if (savedInstanceState == null) {
            Bundle arguments = getArguments();
            user = arguments.getString(Keys.EXTRA_NAME);
            task = arguments.getString(Keys.EXTRA_TASK);
            isChecked = arguments.getBoolean(Keys.EXTRA_STATUS);
            boolean owner = arguments.getBoolean(Keys.EXTRA_OWNER);
            id = arguments.getInt(Keys.EXTRA_ID);

            TextView nameText = view.findViewById(R.id.name_tasklist);
            TextView taskText = view.findViewById(R.id.task_tasklist);
            CheckBox statusBox = view.findViewById(R.id.status_tasklist);
            ImageView delete = view.findViewById(R.id.deleteButtonTask);
            ImageView edit = view.findViewById(R.id.editButtonTask);
            setDeleteEditButton(inflater, owner, statusBox, delete, edit);

            nameText.setText(user);
            taskText.setText(task);
            statusBox.setChecked(isChecked);

            statusBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    updateTask("", "", isChecked);
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

    private void setDeleteEditButton(final LayoutInflater inflater, boolean owner, CheckBox statusBox, ImageView delete, ImageView edit) {
        if (!owner) {
            delete.setVisibility(View.INVISIBLE);
        } else {
            delete.setOnClickListener(new View.OnClickListener() {
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
            statusBox.setClickable(true);
        }
        if (user != null) {
            if (I.getMyself().getName().equals(user)) {
                statusBox.setClickable(true);
            }
        }
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View dialogView = inflater.inflate(R.layout.double_input_dialog, null);
                builder.setMessage("Neuen Namen und/oder Nutzer eingeben. (Bei leerem Feld bleibt es unverändet)")
                        .setView(dialogView)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText inputTask = dialogView.findViewById(R.id.double_dialog_name_input);
                                EditText inputUser = dialogView.findViewById(R.id.double_dialog_user_input);
                                updateTask(inputUser.getText().toString(), inputTask.getText().toString(), isChecked);
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
    }

    private void updateTask(String name, String task, boolean status) {
        Log.e("SingleTodo", name + ", " + task);
        String newName = (name.trim().equals(""))? this.user : name.trim();
        String newTask = (task.trim().equals(""))? this.task : task.trim();
        User user = null;
        if (data.getParty().getOwner().getName().equals(newName)) {
            user = data.getParty().getOwner();
        }
        for (Guest guest : data.getParty().getGuests()) {
            if (guest.getUser().getName().equals(newName)) {
                user = guest.getUser();
            }
        }
        Log.e("SingleTodo", newName + ", " + newTask);
        TextView nameText = view.findViewById(R.id.name_tasklist);
        TextView taskText = view.findViewById(R.id.task_tasklist);

        nameText.setText(newName);
        taskText.setText(newTask);

        if (user != null && !newTask.equals("")) {
            Intent apiHanlder = new Intent(getActivity(), APIService.class);
            apiHanlder.putExtra(Keys.EXTRA_URL, "/party/task?api=" + I.getMyself().getApiKey());
            apiHanlder.putExtra(Keys.EXTRA_REQUEST, "PUT");
            int statusInt = (status) ? 1 : 0;
            String data = "{\"id\":" + id + ",\"userid\":" + I.getMyself().getId() + ",\"party_id\":" + this.data.getParty().getId() +
                    ",\"text\":\"" + newTask + "\",\"status\":" + statusInt + "}";
            Log.e("SingleTodo", data);
            apiHanlder.putExtra(Keys.EXTRA_DATA, data);
            if (status == isChecked) {
                apiHanlder.putExtra(Keys.EXTRA_ID, Keys.EXTRA_PUT_TASK);
            } else {
                isChecked = status;
                apiHanlder.putExtra(Keys.EXTRA_ID, Keys.EXTRA_POST_TASK);
            }
            apiHanlder.putExtra(Keys.EXTRA_SERVICE_TYPE, Keys.EXTRA_SERVICE);
            getActivity().startService(apiHanlder);
        }
    }

    private void deleteTodo() {
        Intent apiHanlder = new Intent(getActivity(), APIService.class);
        apiHanlder.putExtra(Keys.EXTRA_URL, "/party/task?api=" + I.getMyself().getApiKey());
        apiHanlder.putExtra(Keys.EXTRA_REQUEST, "DELETE");
        String data = "{\"id\":" + id + "}";
        Log.e(getClass().getName(), data);
        apiHanlder.putExtra(Keys.EXTRA_DATA, data);
        apiHanlder.putExtra(Keys.EXTRA_ID, Keys.EXTRA_DELETE_TASK);
        apiHanlder.putExtra(Keys.EXTRA_SERVICE_TYPE, Keys.EXTRA_SERVICE);
        getActivity().startService(apiHanlder);
        this.data.startLoading();

    }

    public void setExpandable(ExpandableFragment expandableFragment) {
        this.expandableFragment = expandableFragment;
    }
}
