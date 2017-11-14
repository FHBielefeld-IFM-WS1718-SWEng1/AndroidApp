package partyplaner.partyplaner.Veranstaltung;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import partyplaner.partyplaner.R;

/**
 * Created by malte on 11.11.2017.
 */

public class VeranstaltungViewAdaper extends BaseExpandableListAdapter {

    private Context context;
    private HashMap<String, List<String>> parts;
    private List<String> partKeys;

    public VeranstaltungViewAdaper(Context context, HashMap<String, List<String>> parts, List<String> partKeys) {
        this.context = context;
        this.parts = parts;
        this.partKeys = partKeys;
    }

    @Override
    public int getGroupCount() {
        return partKeys.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return parts.get(partKeys.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return partKeys.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return parts.get(partKeys.get(groupPosition)).get(childPosition);
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
        String childTitle = (String) getChild(groupPosition, childPosition);
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_item, parent, false);
        }
        TextView childTextView = (TextView) convertView.findViewById(R.id.childExampleTextView);
        childTextView.setText(childTitle);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
