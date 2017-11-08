package partyplaner.partyplaner.LogIn;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import partyplaner.partyplaner.R;

/**
 * Created by malte on 08.11.2017.
 */

public class SignInFragment extends Fragment {

    private SignIn context;

    public interface SignIn {
        public void signUp();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_signin, container, false);
        //view.findViewById(R.id.s)
        return view;
    }

    /*public void log(View view) {
        context.signUp();

        /*Intent intent = new Intent(getActivity(), MainActivity.class);
        //Send User to main here
        startActivity(intent);
    }*/
}
