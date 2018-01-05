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
import partyplaner.partyplaner.MainActivity;
import partyplaner.partyplaner.R;

/**
 * Created by malte on 08.11.2017.
 */

public class SignInFragment extends Fragment {

    private EditText username;
    private EditText password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_signin, container, false);
        //view.findViewById(R.id.s)

        Button btnLogin = view.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(view);
            }
        });
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);

        return view;
    }

    public void login(View view) {
        LoginData loginData = new LoginData(username.getText().toString(), password.getText().toString());
        I i = loginData.login();
        if (i != null) {
            Intent intent = new Intent(this.getActivity(), MainActivity.class);
            startActivity(intent);
        } else {
            Context context = getActivity();
            CharSequence text = "Login fehlgeschlagen!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}
