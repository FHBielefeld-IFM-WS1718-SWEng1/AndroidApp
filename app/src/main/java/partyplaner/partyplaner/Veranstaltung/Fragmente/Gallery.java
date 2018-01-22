package partyplaner.partyplaner.Veranstaltung.Fragmente;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import partyplaner.api.APIService;
import partyplaner.api.ServiceDateReceiver;
import partyplaner.data.party.GalleryImage;
import partyplaner.data.party.Party;
import partyplaner.data.user.I;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;
import partyplaner.partyplaner.Veranstaltung.IEventDataManager;
import partyplaner.partyplaner.Veranstaltung.IServiceReceiver;

/**
 * Displays pictues in a gallery and has the option to add new.
 * @author Malte
 * @since 16.11.17
 */

public class Gallery extends Fragment implements IReceiveData, IServiceReceiver{

    private static final int PICK_PICTURE = 73;
    private Party party;
    private IEventDataManager data;
    private Button addImage;
    private ServiceDateReceiver serviceDateReceiver;
    private String caption;
    private LayoutInflater inflater;
    private List<Fragment> fragments = new ArrayList<>();
    private ExpandableFragment expandable;

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
        View view = inflater.inflate(R.layout.event_fragment_gallery, container, false);
        this.inflater = inflater;
        addImage = view.findViewById(R.id.addPictures);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageFromMobile();
            }
        });
        if (savedInstanceState == null) {
            if (party != null) {
                updateImages();
            }
        }
        return view;
    }

    private void updateImages() {
        for (Fragment f : fragments) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.remove(f);
            transaction.commit();
        }
        fragments.clear();

        for (GalleryImage image : party.getGallery()) {
            addPicture(image, image.getUploader().getName());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter statusIntentFilter = new IntentFilter(Keys.EXTRA_GALLERY);
        serviceDateReceiver = new ServiceDateReceiver(this);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(serviceDateReceiver, statusIntentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(serviceDateReceiver);
    }

    @Override
    public void receiveData() {
        party = data.getParty();
        updateImages();
    }

    private void getImageFromMobile() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, PICK_PICTURE);
    }

    private void postPicture(Uri image) {
        Intent apiHandler = new Intent(getActivity(), APIService.class);
        apiHandler.putExtra(Keys.EXTRA_URL, "/image?api=" + I.getMyself().getApiKey());
        apiHandler.putExtra(Keys.EXTRA_REQUEST, "POST");
        String data = null;
        apiHandler.putExtra(Keys.EXTRA_DATA, data);
        apiHandler.putExtra(Keys.EXTRA_ID, Keys.EXTRA_POST_IMAGE);
        apiHandler.putExtra(Keys.EXTRA_SERVICE_TYPE, Keys.EXTRA_GALLERY);
        apiHandler.setData(image);
        getActivity().startService(apiHandler);
    }

    private void addPitureToGallery(String filename) {
        String url = "/party/gallery?api=" + I.getMyself().getApiKey();
        String data = "{\"party_id\":" + party.getId() + ",\"text\":\"" + caption + "\",\"file\":\"" + filename + "\"}";
        Log.e(getClass().getName(), data);
        startService(url, "POST", data, Keys.EXTRA_POST_GALLERY);
    }

    private void startService(String url, String request, String data, String id) {
        Intent apiHanlder = new Intent(getActivity(), APIService.class);
        apiHanlder.putExtra(Keys.EXTRA_URL, url);
        apiHanlder.putExtra(Keys.EXTRA_REQUEST, request);
        apiHanlder.putExtra(Keys.EXTRA_DATA, data);
        apiHanlder.putExtra(Keys.EXTRA_ID, id);
        apiHanlder.putExtra(Keys.EXTRA_SERVICE_TYPE, Keys.EXTRA_GALLERY);
        getActivity().startService(apiHanlder);
    }
    @Override
    public void setExpandable(ExpandableFragment fragment) {
        this.expandable = fragment;
    }

    @Override
    public void receiveData(String json, String id) {
        Log.e(getClass().getName(), id);
        if (json != null) {
            Log.e(getClass().getName(), json);
            switch (id) {
                case Keys.EXTRA_POST_IMAGE:
                    if (!json.contains("error")) {
                        String filename = json.replace("{\"filename\":\"", "").replace("\"}", "");
                        addPitureToGallery(filename);
                    } else {
                        Toast.makeText(getActivity(), "Bild hochladen fehgeschlagen!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Keys.EXTRA_POST_GALLERY:
                    if (!json.contains("error")) {
                        Gson gson = new Gson();
                        GalleryImage image = gson.fromJson(json, GalleryImage.class);
                        addPicture(image, I.getMyself().getName());
                    } else {
                        Toast.makeText(getActivity(), "Einfügen in die Gallerie fehgeschlagen!", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    private void addPicture(GalleryImage image, String name) {
        Bundle args = new Bundle();
        args.putString(Keys.EXTRA_FILENAME, image.getFile());
        args.putString(Keys.EXTRA_CAPTION, image.getText());
        args.putInt(Keys.EXTRA_ID, image.getId());
        args.putString(Keys.EXTRA_NAME, name);

        SingleGalleryImage imageFragment = new SingleGalleryImage();
        imageFragment.setArguments(args);
        imageFragment.setExpandable(expandable);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.imageBox, imageFragment);
        transaction.commit();
        fragments.add(imageFragment);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PICTURE && resultCode == Activity.RESULT_OK) {
            getCaption(data.getData());
        }
    }

    private void getCaption(final Uri image) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View dialogView = inflater.inflate(R.layout.dialog_with_image, null);
        ImageView imageView = dialogView.findViewById(R.id.image_preview);
        try {
            InputStream imageStream = getActivity().getContentResolver().openInputStream(image);
            Bitmap pictureBitmap = BitmapFactory.decodeStream(imageStream);
            imageView.setImageBitmap(pictureBitmap);
        } catch (FileNotFoundException e) {
            Toast.makeText(getActivity(), "Vorschau konnte nicht geladen werden!", Toast.LENGTH_SHORT).show();
        }
        builder.setMessage("Welche Bildunterschrift soll das neue Bild besitzen?")
                .setView(dialogView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText input = dialogView.findViewById(R.id.dialog_image_input);
                        caption = input.getText().toString().trim();
                        postPicture(image);
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
}
