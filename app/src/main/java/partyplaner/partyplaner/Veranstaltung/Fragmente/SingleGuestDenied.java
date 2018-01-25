package partyplaner.partyplaner.Veranstaltung.Fragmente;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import partyplaner.api.APIService;
import partyplaner.data.user.I;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;
import partyplaner.partyplaner.Veranstaltung.IEventDataManager;

/**
 * Created by malte on 03.12.2017.
 */

public class SingleGuestDenied extends Fragment {

    private ExpandableFragment expandableFragment;
    private IEventDataManager data;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            data = (IEventDataManager) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_fragment_single_guest_denied, container, false);

        if (savedInstanceState == null) {
            Bundle args = getArguments();
            TextView name = view.findViewById(R.id.name_denied);
            ImageView accept = view.findViewById(R.id.denied_accept_invite);

            name.setText(args.getString(Keys.EXTRA_NAME));
            if (args.getBoolean(Keys.EXTRA_I_AM_GUEST)) {
                accept.setVisibility(View.VISIBLE);
                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateUserStatus(1);
                    }
                });
            }
        }
        return view;
    }

    private void updateUserStatus(int status) {
        Intent apiHanlder = new Intent(getActivity(), APIService.class);
        apiHanlder.putExtra(Keys.EXTRA_URL, "/party/guest?api=" + I.getMyself().getApiKey());
        apiHanlder.putExtra(Keys.EXTRA_REQUEST, "PUT");
        String data = "{\"partyid\":" + this.data.getParty().getId() + ",\"userid\":" + I.getMyself().getId()
                + ",\"status\":" + status + "}";
        Log.e(getClass().getName(), data);
        apiHanlder.putExtra(Keys.EXTRA_DATA, data);
        apiHanlder.putExtra(Keys.EXTRA_ID, Keys.EXTRA_PUT_TASK);
        apiHanlder.putExtra(Keys.EXTRA_SERVICE_TYPE, Keys.EXTRA_SERVICE);
        getActivity().startService(apiHanlder);
        this.data.startLoading();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (expandableFragment != null) {
            expandableFragment.reexpandGroup();
        }
    }

    public void setExpandable(ExpandableFragment expandableFragment) {
        this.expandableFragment = expandableFragment;
    }
}
