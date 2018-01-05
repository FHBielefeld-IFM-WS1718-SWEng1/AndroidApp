package partyplaner.anmations;

import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by malte on 19.12.2017.
 */

public class ExpandableAnimator extends Animation {

    private LinearLayout body;
    private float startX;
    private float endX;

    private ImageView arrow;
    private float startDeg;
    private float endDeg;

    public ExpandableAnimator(LinearLayout layout, int startX, int endX, ImageView arrow, int startDeg, int endDeg) {
        this.body = layout;
        this.startX = startX;
        this.endX = endX;
        this.arrow = arrow;
        this.startDeg = startDeg;
        this.endDeg = endDeg;
    }

    @Override
    public void applyTransformation(float interpolatedTime, Transformation t) {
        float height = (endX - startX) * interpolatedTime + startX;
        ViewGroup.LayoutParams p = body.getLayoutParams();
        p.height = (int) height;
        body.requestLayout();

        float deg = (endDeg - startDeg) * interpolatedTime + startDeg;
        arrow.setRotation(deg);
        //arrow.requestLayout();
    }
}
