package partyplaner.partyplaner.ownEvents;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import partyplaner.api.APIService;
import partyplaner.api.GeneralAPIRequestHandler;
import partyplaner.api.RouteType;
import partyplaner.data.party.Party;
import partyplaner.data.user.I;
import partyplaner.data.user.User;
import partyplaner.partyplaner.EventMainActivity;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.MainActivity;
import partyplaner.partyplaner.R;
import partyplaner.partyplaner.Veranstaltung.EventMainFragment;
import partyplaner.partyplaner.Veranstaltung.IEventDataManager;

/**
 * Created by André on 17.11.2017.
 */

public class OwnEventFragment extends Fragment {

    private int id;
    private MainActivity data;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            data = (MainActivity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ownevents_ownevent, container, false);

        Bundle args = getArguments();
        id = args.getInt(Keys.EXTRA_PARTYID);
        setText(view, args);

        LinearLayout background = view.findViewById(R.id.own_events_back);
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EventMainActivity.class);
                intent.putExtra(Keys.EXTRA_OWNER, true);
                intent.putExtra(Keys.EXTRA_PARTYID, id);
                startActivity(intent);
            }
        });
        background.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Willst du die Party wirklich löschen?")
                        .setPositiveButton("LÖSCHEN", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteParty();
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("ABBRECHEN", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).create().show();

                return true;
            }
        });
        return view;
    }

    private void deleteParty() {
        Intent apiHanlder = new Intent(getActivity(), APIService.class);
        apiHanlder.putExtra(Keys.EXTRA_URL, "/party/" + id + "?api=" + I.getMyself().getApiKey());
        apiHanlder.putExtra(Keys.EXTRA_REQUEST, "DELETE");
        String data = "{\"id\":" + id + "}";
        apiHanlder.putExtra(Keys.EXTRA_DATA, data);
        apiHanlder.putExtra(Keys.EXTRA_ID, Keys.EXTRA_DELETE_PARTIES);
        apiHanlder.putExtra(Keys.EXTRA_SERVICE_TYPE, Keys.EXTRA_MAIN_ACTIVITY);
        getActivity().startService(apiHanlder);
        this.data.startLoading();
    }

    private void setText(View view, Bundle args) {
        TextView name = view.findViewById(R.id.textPartyname);
        name.setText(args.getString(Keys.EXTRA_PARTY));

        TextView when = view.findViewById(R.id.textWhen);
        String date = Party.parseDate(args.getString(Keys.EXTRA_WHEN));
        when.setText("Wann? " + date);

        TextView description = view.findViewById(R.id.textDescription);
        String descript = args.getString(Keys.EXTRA_DESCRIPTION);
        if (descript != null && descript.length() > 80) {
            descript = descript.substring(0, 80) + "...";
        }
        description.setText(descript);
    }

}
