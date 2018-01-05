package partyplaner.partyplaner.LogIn;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import partyplaner.data.user.I;
import partyplaner.data.user.LoginData;
import partyplaner.data.user.RegistrationData;
import partyplaner.partyplaner.MainActivity;
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

        Button btnRegister = view.findViewById(R.id.regSave);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(view);
            }
        });

        username = view.findViewById(R.id.regName);
        email = view.findViewById(R.id.regEmail);
        password = view.findViewById(R.id.regPass);
        passwordRepeated = view.findViewById(R.id.regPassWdg);

        return view;
    }

    public void register(View view) {
        RegistrationData registrationData = new RegistrationData(email.getText().toString(),
                username.getText().toString(), password.getText().toString(),
                passwordRepeated.getText().toString());
        I i = registrationData.registerAndLogin();
        if (i != null) {
            Intent intent = new Intent(this.getActivity(), MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(), "Registrierung fehlgeschlagen!", Toast.LENGTH_SHORT).show();
        }
    }
}
