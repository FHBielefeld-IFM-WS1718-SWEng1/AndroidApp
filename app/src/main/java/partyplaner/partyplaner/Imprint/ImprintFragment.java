package partyplaner.partyplaner.Imprint;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import partyplaner.partyplaner.R;

/**
 * Created by Jan Augstein on 17.11.2017.
 */

public class ImprintFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_imprint, container, false);
        return view;
    }
}
