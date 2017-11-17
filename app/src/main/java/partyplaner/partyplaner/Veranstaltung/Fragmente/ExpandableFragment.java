package partyplaner.partyplaner.Veranstaltung.Fragmente;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;

/**
 * Fragment for the single Groups of the ExpandableView
 * @author Malte
 * @since 16.11.17
 */
public class ExpandableFragment extends Fragment {

    boolean expandend;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_expandable_fragment, container, false);
        expandend = true;

        Bundle arguments = getArguments();
        String title = arguments.getString(Keys.EXTRA_NAME);
        TextView text = view.findViewById(R.id.expandableTitle);
        text.setText(title);

        RelativeLayout head = view.findViewById(R.id.head);
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expandend) {
                    collapseGroup();
                } else {
                    expandGroup();
                }
            }
        });

        expandGroup();
        collapseGroup(view);
        return view;
    }


    /**
     * This methode expands the body of the group.
     * Works only if the "onCreateView()" called before.
     */
    public void expandGroup() {
        expandGroup(getView());
    }

    private void expandGroup(View fragment) {
        if(fragment != null) {
            LinearLayout body = fragment.findViewById(R.id.body);
            ImageView arrow = fragment.findViewById(R.id.arrow);

            body.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            arrow.setRotation(0);
            expandend = true;
        }
    }

    /**
     * This methode collapses the body of the group.
     * Works only if the "onCreateView()" called before.
     */
    public void collapseGroup() {
        collapseGroup(getView());
    }

    private void collapseGroup(View fragment) {
        if (fragment != null) {
            LinearLayout body = fragment.findViewById(R.id.body);
            ImageView arrow = fragment.findViewById(R.id.arrow);

            body.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
            arrow.setRotation(-90);
            expandend = false;
        }
    }

}
