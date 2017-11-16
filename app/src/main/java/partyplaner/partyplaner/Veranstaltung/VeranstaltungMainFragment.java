package partyplaner.partyplaner.Veranstaltung;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import partyplaner.partyplaner.R;
import partyplaner.partyplaner.Veranstaltung.Fragmente.Aufgabenliste;
import partyplaner.partyplaner.Veranstaltung.Fragmente.Galerie;

/**
 * Created by malte on 10.11.2017.
 */

public class VeranstaltungMainFragment extends Fragment {

    ExpandableListView expandableListView;
    VeranstaltungViewAdaper adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_veranstaltung_main, container, false);

        Activity activity = getActivity();
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableView);
        List<String> testHashmapKeys = Arrays.asList(getResources().getStringArray(R.array.veranstaltungs_header));
        HashMap<String, Integer> testHashmap = initHashmap(testHashmapKeys);
        List<ImageView> gallery = initImageList();

        adapter = new VeranstaltungViewAdaper(activity, testHashmap, testHashmapKeys, getFragmentManager(), gallery);
        expandableListView.setAdapter(adapter);
        openAllGroups();
        return view;
    }

    private void openAllGroups() {
        int count = adapter.getGroupCount();
        for (int position = 0; position < count; position++)
            expandableListView.expandGroup(position);
    }

    private void closeAllGroups() {
        int count = adapter.getGroupCount();
        for (int position = 0; position < count; position++)
            expandableListView.collapseGroup(position);
    }

    private HashMap<String, Integer> initHashmap(List<String> keys){
        HashMap<String, Integer> testHashmap = new HashMap<>();
        testHashmap.put(keys.get(0), R.layout.veranstaltung_fragment_galerie);
        testHashmap.put(keys.get(1), R.layout.veranstaltung_fragment_aufgabenliste);

        return testHashmap;
    }

    private List<ImageView> initImageList(){
        List<ImageView> view = new ArrayList<ImageView>();
        for(int i = 0; i < 10; i++) {
            ImageView image = new ImageView(getActivity());
            image.setImageResource(R.drawable.ic_launcher_background);
            view.add(image);
        }

        return view;
    }
}
