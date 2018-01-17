package partyplaner.partyplaner.Veranstaltung.Fragmente;

import android.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;

/**
 * Created by malte on 01.12.2017.
 */

public class Rating extends Fragment implements IReceiveData {
    private double rating = 0.25;
    private int numRates = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_fragment_rating, container, false);

        if (savedInstanceState == null) {
            TextView showRates = view.findViewById(R.id.numRates);
            showRates.setText(numRates + " haben abgestimmt");

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            double width = displayMetrics.widthPixels - 16;

            LinearLayout posRating = view.findViewById(R.id.posRating);
            posRating.setLayoutParams(new LinearLayout.LayoutParams((int) (width * rating), ViewGroup.LayoutParams.MATCH_PARENT));
        }
        return view;
    }

    @Override
    public void receiveData() {

    }

    @Override
    public void setExpandable(ExpandableFragment fragment) {

    }
}
