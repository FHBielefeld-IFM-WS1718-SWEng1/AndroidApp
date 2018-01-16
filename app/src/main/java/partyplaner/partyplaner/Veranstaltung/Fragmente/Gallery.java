package partyplaner.partyplaner.Veranstaltung.Fragmente;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import partyplaner.partyplaner.R;

/**
 * Displays pictues in a gallery and has the option to add new.
 * @author Malte
 * @since 16.11.17
 */

public class Gallery extends Fragment implements IReceiveData{

    List<Integer> imageRes = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_fragment_gallery, container, false);
        if (savedInstanceState == null) {
            setTestSetUp();
            initImages(view);
        }
        return view;
    }

    private void initImages(View view) {
        for(Integer res : imageRes) {
            addImage(view, res);
        }
    }

    /**
     * Adds an image at the end of the gallery(?) by an resource number.
     * @param res resource of the image
     */
    public void addImage(int res) {
        addImage(getView(), res);
    }

    private void addImage(View view, int res) {
        if (view != null) {
            LinearLayout imageBox = view.findViewById(R.id.imageBox);

            ImageView image = new ImageView(getActivity());
            image.setImageResource(res);
            image.setLayoutParams(new LinearLayout.LayoutParams(512, 512));
            image.setPadding(8, 8, 8, 8);

            imageBox.addView(image);
        }
    }

    private void setTestSetUp() {
        imageRes.add(R.drawable.ic_launcher_background);
        imageRes.add(R.drawable.ic_launcher_background);
        imageRes.add(R.drawable.ic_launcher_background);
        imageRes.add(R.drawable.ic_launcher_background);
        imageRes.add(R.drawable.ic_launcher_background);
        imageRes.add(R.drawable.ic_launcher_background);
        imageRes.add(R.drawable.ic_launcher_background);
        imageRes.add(R.drawable.ic_launcher_background);
        imageRes.add(R.drawable.ic_launcher_background);
    }

    @Override
    public void receiveData() {

    }
}
