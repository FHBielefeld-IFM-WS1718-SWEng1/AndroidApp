package partyplaner.partyplaner.Veranstaltung;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import partyplaner.api.APIService;
import partyplaner.api.ServiceDateReceiver;
import partyplaner.data.party.Party;
import partyplaner.data.user.I;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;
import partyplaner.partyplaner.Veranstaltung.Fragmente.ExpandableFragment;

/**
 * Fragment for the detailed event view.
 * @author Malte
 * @since 16.11.17
 */
public class EventMainFragment extends Fragment implements IServiceReceiver{

    private IEventDataManager data;
    private String what = "";
    private String who = "";
    private String where = "";
    private String when = "";
    private String description = "";
    private boolean shortText = true;
    private List<ExpandableFragment> fragments = new ArrayList<>();
    private String imageFilename;
    private View view;

    List<ExpandableFragment> headers = new ArrayList<>();
    private ServiceDateReceiver serviceDateReceiver;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_event_main, container, false);
        this.view = view;

        if(savedInstanceState == null) {
            setUpDescription(view);
            setUpExpandableView(view);

            Button more = view.findViewById(R.id.button_more);
            more.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter statusIntentFilter = new IntentFilter(Keys.EXTRA_IMAGE_IMAGE);
        serviceDateReceiver = new ServiceDateReceiver(this);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(serviceDateReceiver, statusIntentFilter);

    }

    public void receiveData() {
        String[] data = this.data.getGeneralInformations();
        this.what = data[0];
        this.who = data[1];
        this.when = Party.parseDate(data[2]);
        this.where = data[3];
        this.description = data[4];
        this.imageFilename = data[5];

        loadImage();
        setUpDescription(view);
        for(ExpandableFragment fragment : fragments) {
            fragment.receiveData();
        }
    }

    private void loadImage() {
        Intent apiHanlder = new Intent(getActivity(), APIService.class);
        apiHanlder.putExtra(Keys.EXTRA_URL, "/image/" + imageFilename + "?api=" + I.getMyself().getApiKey());
        apiHanlder.putExtra(Keys.EXTRA_REQUEST, "GET");
        String data = null;
        apiHanlder.putExtra(Keys.EXTRA_DATA, data);
        apiHanlder.putExtra(Keys.EXTRA_ID, Keys.EXTRA_GET_PROFILEPICTURE);
        apiHanlder.putExtra(Keys.EXTRA_SERVICE_TYPE, Keys.EXTRA_IMAGE_IMAGE);
        getActivity().startService(apiHanlder);
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(boolean bool) {
        shortText = bool;
    }

    private void setUpDescription(final View view) {
        TextView eventWhat = view.findViewById(R.id.event_what);
        TextView eventWho = view.findViewById(R.id.event_who);
        TextView eventWhere = view.findViewById(R.id.event_where);
        TextView eventWhen = view.findViewById(R.id.event_when);
        TextView eventDescription = view.findViewById(R.id.event_description);
        final Button more = view.findViewById(R.id.button_more);

        eventWhat.setText(what);
        eventWho.setText(who);
        eventWhere.setText(where);
        eventWhen.setText(when);
        if (description != null) {
            if (description.length() <= 80) {
                eventDescription.setText(description);
            } else {
                eventDescription.setText(description.substring(0, 80) + "...");
                more.setVisibility(View.VISIBLE);
                more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView description = view.findViewById(R.id.event_description);
                        if (shortText) {
                            description.setText(getDescription());
                            more.setText("weniger...");
                            shortText = false;
                        } else {
                            description.setText((getDescription().substring(0, 80) + "..."));
                            more.setText("mehr...");
                            shortText = true;
                        }

                    }
                });
            }
        }
    }

    private void addFragment(String name, int id) {
        FragmentManager fm = getFragmentManager();

        Bundle arguments = new Bundle();
        arguments.putString(Keys.EXTRA_NAME, name);
        arguments.putInt(Keys.EXTRA_ID, id);
        ExpandableFragment fragment = new ExpandableFragment();
        fragment.setArguments(arguments);
        fragments.add(fragment);

        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.eventBody, fragment);
        transaction.commit();
        headers.add(fragment);
    }

    private void setUpExpandableView(View view) {

        addFragment("Gallerie", 0);
        addFragment("Aufgaben", 1);
        if(true) {
            addFragment("TODO", 2);
        }
        addFragment("Gäste", 6);
        addFragment("Abstimmungen", 3);
        addFragment("Bewertungen", 5);
        addFragment("Kommentare", 4);
    }

    /**
     * This methode closes all ExpandableFragment of the event.
     */
    public void collapseAll() {
        for (ExpandableFragment fragment : headers) {
            fragment.collapseGroup();
        }
    }

    @Override
    public void receiveData(String json, String id) {
        Log.e(getClass().getName(), json);
        if (json != null && !json.contains("error")) {
            json = json.replaceAll("\\{\"data\":\"", "");
            json = json.replaceAll("\"\\}", "");
            Gson gson = new Gson();
            if (getActivity() != null) {
                byte[] decoded = Base64.decode(json, Base64.DEFAULT);
                Bitmap image = BitmapFactory.decodeByteArray(decoded, 0, decoded.length);
                ImageView imageView = getView().findViewById(R.id.party_picture);
                imageView.setImageBitmap(image);
            }
        } else {
            Toast.makeText(getActivity(), "Bild konnte nicht geladen werden!", Toast.LENGTH_SHORT).show();
        }
    }
}

