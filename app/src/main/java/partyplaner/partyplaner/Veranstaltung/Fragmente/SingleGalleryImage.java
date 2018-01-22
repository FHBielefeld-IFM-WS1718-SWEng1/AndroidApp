package partyplaner.partyplaner.Veranstaltung.Fragmente;

import android.app.Fragment;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import partyplaner.api.APIService;
import partyplaner.api.ServiceDateReceiver;
import partyplaner.data.user.I;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;
import partyplaner.partyplaner.Veranstaltung.IServiceReceiver;

/**
 * Created by malte on 22.01.2018.
 */

public class SingleGalleryImage extends Fragment implements IServiceReceiver {

    private int id;
    private String name;
    private String caption;
    private String filename;
    private ServiceDateReceiver serviceDateReceiver;
    private ExpandableFragment expandable;
    private boolean invisible = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_fragment_gallery_image, container, false);
        Bundle args = getArguments();

        loadSetUp(view, args);
        loadImage();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (expandable != null) {
            expandable.reexpandGroup();
        }
        IntentFilter statusIntentFilter = new IntentFilter(Keys.EXTRA_GALLERY_IMAGE + id);
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
        apiHanlder.putExtra(Keys.EXTRA_URL, "/image/" + filename + "?api=" + I.getMyself().getApiKey());
        apiHanlder.putExtra(Keys.EXTRA_REQUEST, "GET");
        String data = null;
        apiHanlder.putExtra(Keys.EXTRA_DATA, data);
        apiHanlder.putExtra(Keys.EXTRA_ID, Keys.EXTRA_GET_PROFILEPICTURE);
        apiHanlder.putExtra(Keys.EXTRA_SERVICE_TYPE, Keys.EXTRA_GALLERY_IMAGE + id);
        getActivity().startService(apiHanlder);
    }

    private void loadSetUp(View view, Bundle args) {
        id = args.getInt(Keys.EXTRA_ID);
        name = args.getString(Keys.EXTRA_NAME);
        caption = args.getString(Keys.EXTRA_CAPTION);
        filename = args.getString(Keys.EXTRA_FILENAME);

        TextView username = view.findViewById(R.id.username);
        TextView caption = view.findViewById(R.id.caption);
        LinearLayout back = view.findViewById(R.id.single_image_holder);
        caption.setMaxWidth(200);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleTextVisibility();
            }
        });

        username.setText(this.name);
        caption.setText(this.caption);
    }

    private void toggleTextVisibility() {
        TextView username = getView().findViewById(R.id.username);
        TextView caption = getView().findViewById(R.id.caption);
        if (invisible) {
            username.setVisibility(View.VISIBLE);
            caption.setVisibility(View.VISIBLE);
        } else {
            username.setVisibility(View.INVISIBLE);
            caption.setVisibility(View.INVISIBLE);
        }
        invisible = !invisible;
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
                ImageView imageView = getView().findViewById(R.id.gallery_image);
                imageView.setImageBitmap(image);
                TextView caption = getView().findViewById(R.id.caption);
                caption.setMaxWidth(image.getWidth() - 20);
            }
        }
    }

    public void setExpandable(ExpandableFragment expandable) {
        this.expandable = expandable;
    }
}
