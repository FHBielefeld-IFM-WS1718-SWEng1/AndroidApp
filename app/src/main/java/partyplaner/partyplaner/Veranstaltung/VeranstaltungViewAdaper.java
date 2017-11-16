package partyplaner.partyplaner.Veranstaltung;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import partyplaner.partyplaner.R;
import partyplaner.partyplaner.Veranstaltung.Fragmente.Aufgabenliste;

/**
 * Created by malte on 11.11.2017.
 */

public class VeranstaltungViewAdaper extends BaseExpandableListAdapter {

    private Context context;
    private HashMap<String, Integer> parts;
    private List<String> partKeys;
    private FragmentManager manager;
    private List<ImageView> gallery;

    public VeranstaltungViewAdaper(Context context, HashMap<String, Integer> parts,
                                   List<String> partKeys, FragmentManager manager, List<ImageView> gallery) {
        this.context = context;
        this.parts = parts;
        this.partKeys = partKeys;
        this.manager = manager;
        this.gallery = gallery;
    }

    @Override
    public int getGroupCount() {
        return partKeys.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return partKeys.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return parts.get(partKeys.get(groupPosition));
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String groupTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.parent_item, parent, false);
        }
        TextView parentTextView = (TextView) convertView.findViewById(R.id.parentTitle);
        parentTextView.setText(groupTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        int childTitle = (int) getChild(groupPosition, childPosition);
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(childTitle, parent, false);

            switch (groupPosition){
                case 0:
                    setUpGallery(convertView);
                    break;
                default:
                    break;
            }

        }

        return convertView;
    }

    private void setUpGallery(View convertView) {
        LinearLayout layout = convertView.findViewById(R.id.imageBox);

        for(ImageView image : gallery) {
            image.setLayoutParams(new LinearLayout.LayoutParams(512, 512));
            image.setPadding(8,8,8,8);
            layout.addView(image);
        }
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

}
