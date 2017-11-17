package partyplaner.partyplaner.home;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import partyplaner.partyplaner.R;

/**
 * Created by André on 10.11.2017.
 */

public class HomeFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        LinearLayout layout = view.findViewById(R.id.party_list);
        //layout.removeAllViews();
        return view;
    }
}
