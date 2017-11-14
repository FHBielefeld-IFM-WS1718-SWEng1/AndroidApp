package partyplaner.partyplaner.Veranstaltung;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import partyplaner.partyplaner.R;

/**
 * Created by malte on 10.11.2017.
 */

public class VeranstaltungMainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_veranstaltung_main, container, false);

        Activity activity = getActivity();
        ExpandableListView expandableListView = (ExpandableListView) view.findViewById(R.id.expandableView);
        HashMap<String, List<String>> testHashmap = initHashmap();
        ArrayList<String> testHashmapKeys = new ArrayList<>(testHashmap.keySet());

        VeranstaltungViewAdaper adapter = new VeranstaltungViewAdaper(activity, testHashmap, testHashmapKeys);
        expandableListView.setAdapter(adapter);

        return view;
    }

    private HashMap<String, List<String>> initHashmap(){
        HashMap<String, List<String>> testHashmap = new HashMap<>();

        ArrayList<String> testStrings = new ArrayList<>();
        testStrings.add("Hallo1");
        testStrings.add("Hallo2");
        testHashmap.put("Hallo", testStrings);

        testStrings = new ArrayList<>();
        testStrings.add("Hi1");
        testStrings.add("Hi2");
        testHashmap.put("Hi", testStrings);

        return testHashmap;
    }
}
