package partyplaner.partyplaner.LogIn;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import partyplaner.partyplaner.R;

/**
 * Created by malte on 09.11.2017.
 */

public class RegisterFragment extends Fragment {

    private EditText username;
    private EditText email;
    private EditText password;
    private EditText passwordRepeated;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        username = view.findViewById(R.id.regName);
        email = view.findViewById(R.id.regEmail);
        password = view.findViewById(R.id.regPass);
        passwordRepeated = view.findViewById(R.id.regPassWdg);

        return view;
    }
}
