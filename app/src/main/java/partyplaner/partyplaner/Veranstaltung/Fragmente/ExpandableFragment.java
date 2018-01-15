package partyplaner.partyplaner.Veranstaltung.Fragmente;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import partyplaner.anmations.ExpandableAnimator;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;

/**
 * Fragment for the single Groups of the ExpandableView
 * @author Malte
 * @since 16.11.17
 */
public class ExpandableFragment extends Fragment {

    boolean expandend;
    private int id;
    private int height;
    private View view;
    private IReceiveData fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_expandable_fragment, container, false);
        expandend = true;

        Bundle arguments = getArguments();
        String title = arguments.getString(Keys.EXTRA_NAME);
        TextView text = view.findViewById(R.id.expandableTitle);
        text.setText(title);

        id = View.generateViewId();
        LinearLayout body = view.findViewById(R.id.body);
        body.setId(id);

        int id = arguments.getInt(Keys.EXTRA_ID);
        setFragment(view, id);

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

        //expandGroup();
        quickCollapseGroup(view);
        this.view = view;
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recalculateHeight();

    }

    public void receiveData() {
        fragment.receiveData();
        //TODO: reexpandGroup();
    }

    private void setFragment(View view, int id) {
        LinearLayout body = view.findViewById(this.id);
        body.removeAllViews();
        fragment = (IReceiveData) EventHeaders.values()[id].getFragment();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(this.id, EventHeaders.values()[id].getFragment());
        transaction.commit();
    }

    public void reexpandGroup() {
        LinearLayout body = getView().findViewById(id);
        LinearLayout back = getView().findViewById(R.id.expand_back);
        ImageView arrow = getView().findViewById(R.id.arrow);
        recalculateHeight();

        if (body.getLayoutParams().height > 0) {
            body.getLayoutParams().height = height;
        }
    }

    /**
     * This methode expands the body of the group.
     * Works only if the "onCreateView()" is called before.
     */
    public void expandGroup() {
        expandGroup(getView());
    }

    private void expandGroup(View fragment) {
        if(fragment != null) {
            LinearLayout body = fragment.findViewById(id);
            LinearLayout back = fragment.findViewById(R.id.expand_back);
            ImageView arrow = fragment.findViewById(R.id.arrow);
            recalculateHeight();

            ExpandableAnimator anim = new ExpandableAnimator(body, 0, height, arrow, -90, 0);
            anim.setDuration(300);
            back.startAnimation(anim);

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
            LinearLayout body = fragment.findViewById(id);
            LinearLayout back = fragment.findViewById(R.id.expand_back);
            ImageView arrow = fragment.findViewById(R.id.arrow);

            ExpandableAnimator anim = new ExpandableAnimator(body, height, 0, arrow, 0, -90);
            anim.setDuration(300);
            back.startAnimation(anim);

            expandend = false;
        }
    }

    @Deprecated
    private void quickCollapseGroup(View fragment) {
        if (fragment != null) {
            LinearLayout body = fragment.findViewById(id);
            LinearLayout back = fragment.findViewById(R.id.expand_back);
            ImageView arrow = fragment.findViewById(R.id.arrow);

            arrow.setRotation(-90);
            body.getLayoutParams().height = 0;

            expandend = false;
        }
    }

    private void recalculateHeight() {
        LinearLayout body = view.findViewById(id);
        body.measure(0,0);
        height = body.getMeasuredHeight();

    }
}
