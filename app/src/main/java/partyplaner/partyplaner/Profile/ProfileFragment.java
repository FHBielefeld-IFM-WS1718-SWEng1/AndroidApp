package partyplaner.partyplaner.Profile;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import partyplaner.data.user.I;
import partyplaner.partyplaner.R;

/**
 * Created by Jan Augstein on 24.11.2017.
 */

public class ProfileFragment extends Fragment {
    private final static int PICK_IMAGE = 42;
    ImageView profile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        I profil = I.getMyself();
        TextView nameText = view.findViewById(R.id.NameTextView);
        nameText.setText(profil.getName());
        TextView adressText = view.findViewById(R.id.GenderTextView);
        adressText.setText(profil.getGender() + "");
        TextView birthdateText = view.findViewById(R.id.BirthdateTextView);
        birthdateText.setText(profil.getBirthdate());
        TextView emailText = view.findViewById(R.id.EmailTextView);
        emailText.setText(profil.getEmail());

        profile = view.findViewById(R.id.profile_picture);
        profile.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, PICK_IMAGE);
                return true;
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            final Uri imageUri = data.getData();
            final InputStream imageStream;
            try {
                imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                int height = 1024;
                int width = (int)((double)selectedImage.getWidth() / ((double)selectedImage.getHeight() / 1024.0));

                profile.setImageBitmap(Bitmap.createScaledBitmap(selectedImage, width, height, false));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            /*Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            profile.setImageBitmap(imageBitmap);
            profile.requestLayout();
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                Bitmap profileBit = BitmapFactory.decodeStream(bufferedInputStream);
                profile.setImageBitmap(profileBit);
                profile.requestLayout();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }*/
        }
    }

    //TODO hochladen des Images
    //Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
}
