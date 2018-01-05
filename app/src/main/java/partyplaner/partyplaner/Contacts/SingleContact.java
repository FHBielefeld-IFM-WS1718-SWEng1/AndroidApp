package partyplaner.partyplaner.Contacts;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import partyplaner.partyplaner.LogIn.RegisterActivity;
import partyplaner.partyplaner.R;

/**
 * Created by micha on 24.11.2017.
 */

public class SingleContact extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_contact, container, false);
        LinearLayout layout = view.findViewById(R.id.single_contact_layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ShowContactActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
