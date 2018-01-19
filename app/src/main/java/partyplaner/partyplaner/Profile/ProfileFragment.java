package partyplaner.partyplaner.Profile;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.Route;
import partyplaner.api.GeneralAPIRequestHandler;
import partyplaner.api.RouteType;
import partyplaner.data.Base64Image;
import partyplaner.data.PaPlaImage;
import partyplaner.data.user.Gender;
import partyplaner.data.user.I;
import partyplaner.data.user.User;
import partyplaner.partyplaner.R;

/**
 * Created by Jan Augstein on 24.11.2017.
 */

public class ProfileFragment extends Fragment {
    private final static int PICK_IMAGE = 42;
    private ImageView profile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

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

        //TODO:
        /*Base64Image b64img = new Gson().fromJson(GeneralAPIRequestHandler.request("/images/" + profil.getProfilePicture() + "?api=" + profil.getApiKey(), RouteType.GET, null), Base64Image.class);
        if (b64img != null && b64img.getData() != null) {
            PaPlaImage img = new PaPlaImage(b64img.getData());
            Bitmap bitmap = img.convertToBitmap();

            profile.setImageBitmap(Bitmap.createScaledBitmap(bitmap,
                    (int) ((double) bitmap.getWidth() / ((double) bitmap.getHeight() / 1024.0)),
                    1024,
                    false));
        }*/
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpView(getView());
    }

    private void setUpView(View view) {
        I profil = I.getMyself();
        TextView nameText = view.findViewById(R.id.NameTextView);
        nameText.setText(profil.getName());
        TextView adressText = view.findViewById(R.id.GenderTextView);
        adressText.setText(Gender.getGenderNameByID(profil.getGender()));
        TextView birthdateText = view.findViewById(R.id.BirthdateTextView);
        birthdateText.setText(User.formatDate(profil.getBirthdate()));
        TextView emailText = view.findViewById(R.id.EmailTextView);
        emailText.setText(profil.getEmail());
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
        }
    }


    //TODO hochladen des Images
    //Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
}
