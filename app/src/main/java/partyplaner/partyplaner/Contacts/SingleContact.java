package partyplaner.partyplaner.Contacts;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Layout;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.time.chrono.IsoChronology;

import partyplaner.api.APIService;
import partyplaner.api.ServiceDateReceiver;
import partyplaner.data.user.I;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;
import partyplaner.partyplaner.Veranstaltung.IServiceReceiver;

/**
 * Created by micha on 24.11.2017.
 */

public class SingleContact extends Fragment implements IServiceReceiver{

    private String profilepicture;
    private ServiceDateReceiver serviceDateReceiver;
    private int userid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_contact, container, false);
        Bundle args = getArguments();
        TextView name = view.findViewById(R.id.contact_name);
        name.setText(args.getString(Keys.EXTRA_NAME));
        userid = args.getInt(Keys.EXTRA_USERID);
        profilepicture = args.getString(Keys.EXTRA_PICTURE);
        TextView singleContact = view.findViewById(R.id.contact_name);

        loadImage();
        singleContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShowContact.class);
                intent.putExtra(Keys.EXTRA_ID, userid);
                getActivity().startActivity(intent);
            }
        });

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

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter statusIntentFilter = new IntentFilter(Keys.EXTRA_LOAD_CONTACT_IMAGE + userid);
        serviceDateReceiver = new ServiceDateReceiver(this);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(serviceDateReceiver, statusIntentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(serviceDateReceiver);
    }

    private void loadImage() {
        Intent apiHanlder = new Intent(getActivity(), APIService.class);
        apiHanlder.putExtra(Keys.EXTRA_URL, "/image/" + profilepicture + "?api=" + I.getMyself().getApiKey());
        apiHanlder.putExtra(Keys.EXTRA_REQUEST, "GET");
        String data = null;
        apiHanlder.putExtra(Keys.EXTRA_DATA, data);
        apiHanlder.putExtra(Keys.EXTRA_ID, Keys.EXTRA_GET_PROFILEPICTURE);
        apiHanlder.putExtra(Keys.EXTRA_SERVICE_TYPE, Keys.EXTRA_LOAD_CONTACT_IMAGE + userid);
        getActivity().startService(apiHanlder);
    }

    public void startDeleteContactService(){
        Intent apiHanlder = new Intent(getActivity(), APIService.class);
        apiHanlder.putExtra(Keys.EXTRA_URL, "/user/contact?api=" + I.getMyself().getApiKey());
        apiHanlder.putExtra(Keys.EXTRA_REQUEST, "DELETE");
        String data = "{\"userid\":" + userid + "}";
        Log.e("SingleContact", data);
        apiHanlder.putExtra(Keys.EXTRA_DATA, data);
        apiHanlder.putExtra(Keys.EXTRA_ID, Keys.EXTRA_DELETE_PARTIES);
        apiHanlder.putExtra(Keys.EXTRA_SERVICE_TYPE, Keys.EXTRA_MAIN_ACTIVITY);
        getActivity().startService(apiHanlder);

    }

    @Override
    public void receiveData(String json, String id) {
        if (json != null && !json.contains("error")) {
            json = json.replaceAll("\\{\"data\":\"", "");
            json = json.replaceAll("\"\\}", "");
            Gson gson = new Gson();
            if (getActivity() != null) {
                byte[] decoded = Base64.decode(json, Base64.DEFAULT);
                Bitmap image = BitmapFactory.decodeByteArray(decoded, 0, decoded.length);
                ImageView imageView = getView().findViewById(R.id.button_delete);
                imageView.setImageBitmap(image);
            }
        }
    }
}
