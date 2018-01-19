package partyplaner.partyplaner.Contacts;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import partyplaner.api.APIService;
import partyplaner.data.user.I;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;

/**
 * Created by micha on 24.11.2017.
 */

public class SingleContact extends Fragment {
    private int userid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_contact, container, false);
        Bundle args = getArguments();
        TextView name = view.findViewById(R.id.contact_name);
        name.setText(args.getString(Keys.EXTRA_NAME));
        userid = args.getInt(Keys.EXTRA_USERID);
        TextView singleContact = view.findViewById(R.id.contact_name);
        singleContact.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Kontakt löschen");
                builder.setPositiveButton("Löschen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startDeleteContactService();
                        dialogInterface.cancel();
                    }
                });
                builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).create().show();
                return true;
            }
        });

        return view;
    }
    public void startDeleteContactService(){
        Intent apiHanlder = new Intent(getActivity(), APIService.class);
        apiHanlder.putExtra(Keys.EXTRA_URL, "/user/contact?api=" + I.getMyself().getApiKey());
        apiHanlder.putExtra(Keys.EXTRA_REQUEST, "DELETE");
        String data = "{\"userid\":"+userid+"}";
        Log.e("SingleContact", data);
        apiHanlder.putExtra(Keys.EXTRA_DATA, data);
        apiHanlder.putExtra(Keys.EXTRA_ID, Keys.EXTRA_DELETE_PARTIES);
        apiHanlder.putExtra(Keys.EXTRA_SERVICE_TYPE, Keys.EXTRA_MAIN_ACTIVITY);
        getActivity().startService(apiHanlder);

    }
}
