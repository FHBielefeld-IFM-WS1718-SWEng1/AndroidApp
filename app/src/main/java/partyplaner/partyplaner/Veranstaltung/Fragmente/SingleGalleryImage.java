package partyplaner.partyplaner.Veranstaltung.Fragmente;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
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
import android.widget.EditText;
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

    private int partyid;
    private int id;
    private String name;
    private String caption;
    private String filename;
    private ServiceDateReceiver serviceDateReceiver;
    private ExpandableFragment expandable;
    private boolean invisible = true;
    private boolean owner;
    private LayoutInflater inflater;
    private String text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_fragment_gallery_image, container, false);
        this.inflater = inflater;
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
        owner = args.getBoolean(Keys.EXTRA_OWNER);
        partyid = args.getInt(Keys.EXTRA_PARTYID);

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
        if (owner) {
            back.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    editOrDelete();
                    return true;
                }
            });
        }

        username.setText(this.name);
        caption.setText(this.caption);
    }

    private void editOrDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Wollen sie die Bildunterschrift bearbeiten oder das Bild löschen")
                .setPositiveButton("Löschen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteImage();
                        dialog.cancel();
                    }
                })
                .setNeutralButton("Bearbeiten", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editImage();
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create().show();
    }

    private void editImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View dialogView = inflater.inflate(R.layout.single_input_dialog, null);
        final EditText input = dialogView.findViewById(R.id.dialog_input);
        input.setHint("Text");
        builder.setMessage("Gib eine neue Bildunterschrift an.")
                .setView(dialogView)
                .setPositiveButton("Bestätigen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        text = input.getText().toString().trim();
                        String url = "/party/gallery/" + id + "?api=" + I.getMyself().getApiKey();
                        String data = "{\"text\":\"" + text + "\",\"file\":\"" + filename + "\"}";
                        startService(url, "PUT", data, Keys.EXTRA_PUT_GALLERY);
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create().show();


    }

    private void deleteImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Willst du das Bild wirklich löschen.")
                .setPositiveButton("Bestätigen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String url = "/party/gallery/" + id + "?api=" + I.getMyself().getApiKey();
                        String data = null;
                        startService(url, "DELETE", data, Keys.EXTRA_DELETE_GALLERY);
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create().show();
    }

    private void startService(String url, String reqest, String data, String id) {
        Intent apiHanlder = new Intent(getActivity(), APIService.class);
        apiHanlder.putExtra(Keys.EXTRA_URL, url);
        apiHanlder.putExtra(Keys.EXTRA_REQUEST, reqest);
        apiHanlder.putExtra(Keys.EXTRA_DATA, data);
        apiHanlder.putExtra(Keys.EXTRA_ID, id);
        apiHanlder.putExtra(Keys.EXTRA_SERVICE_TYPE, Keys.EXTRA_GALLERY_IMAGE + this.id);
        getActivity().startService(apiHanlder);
    }

    private void toggleTextVisibility() {
        if (getView() != null) {
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
    }

    @Override
    public void receiveData(String json, String id) {
        if (json != null && !json.contains("error")) {
            switch (id)  {
                case Keys.EXTRA_GET_PROFILEPICTURE:
                    json = json.replaceAll("\\{\"data\":\"", "");
                    json = json.replaceAll("\"\\}", "");
                    Gson gson = new Gson();
                    if (getActivity() != null) {
                        byte[] decoded = Base64.decode(json, Base64.DEFAULT);
                        Bitmap image = BitmapFactory.decodeByteArray(decoded, 0, decoded.length);
                        if (getView() != null) {
                            ImageView imageView = getView().findViewById(R.id.gallery_image);
                            imageView.setImageBitmap(image);
                            TextView caption = getView().findViewById(R.id.caption);
                            caption.setMaxWidth(image.getWidth() - 20);
                        }
                    }
                    break;
                case Keys.EXTRA_PUT_GALLERY:
                    TextView caption = getView().findViewById(R.id.caption);
                    caption.setText(text);
                    break;
                case Keys.EXTRA_DELETE_GALLERY:
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.remove(this).commit();
                    break;
            }
        }
    }

    public void setExpandable(ExpandableFragment expandable) {
        this.expandable = expandable;
    }


}
