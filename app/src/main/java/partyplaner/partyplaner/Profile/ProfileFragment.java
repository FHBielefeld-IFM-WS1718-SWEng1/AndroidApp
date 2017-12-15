package partyplaner.partyplaner.Profile;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import partyplaner.data.user.I;
import partyplaner.partyplaner.R;

/**
 * Created by Jan Augstein on 24.11.2017.
 */

public class ProfileFragment extends Fragment {

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
        return view;
    }
}
