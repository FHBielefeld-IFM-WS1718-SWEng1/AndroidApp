package partyplaner.partyplaner.Veranstaltung.Fragmente;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;

import partyplaner.api.APIService;
import partyplaner.data.party.Party;
import partyplaner.data.user.I;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;
import partyplaner.partyplaner.Veranstaltung.IEventDataManager;

/**
 * Created by malte on 01.12.2017.
 */

public class Rating extends Fragment implements IReceiveData {

    private IEventDataManager data;
    private int partyId;
    private int rating;

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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_fragment_rating, container, false);

        if (savedInstanceState == null) {
            setRatingBar();
            setUpAddRatingButton(inflater, view);
        }
        return view;
    }

    private void setUpAddRatingButton(final LayoutInflater inflater, View view) {
        Button addRating = view.findViewById(R.id.add_rating);
        addRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View dialogView = inflater.inflate(R.layout.dialog_pick_rating, null);
                builder.setView(dialogView)
                        .setMessage("Bewerte die Party!")
                        .setPositiveButton("BESTÄTIGEN", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RatingBar ratingBar = dialogView.findViewById(R.id.user_rating);
                                float rating = ratingBar.getRating();
                                postRating((int)(rating * 2.0));
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("ABBRECHEN", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).create().show();
            }
        });
    }

    private void postRating(int rating) {
        Intent apiHanlder = new Intent(getActivity(), APIService.class);
        apiHanlder.putExtra(Keys.EXTRA_URL, "/party/rating?api=" + I.getMyself().getApiKey());
        apiHanlder.putExtra(Keys.EXTRA_REQUEST, "POST");
        String data = "{\"partyid\":" + partyId + ",\"rating\":" + rating + "}";
        apiHanlder.putExtra(Keys.EXTRA_DATA, data);
        apiHanlder.putExtra(Keys.EXTRA_ID, Keys.EXTRA_PUT_TASK);
        apiHanlder.putExtra(Keys.EXTRA_SERVICE_TYPE, Keys.EXTRA_SERVICE);
        getActivity().startService(apiHanlder);
    }

    @Override
    public void receiveData() {
        if (data.getParty() != null){
            partyId = data.getParty().getId();
            rating = data.getParty().getAverageRating();
            setRatingBar();
        }
    }

    private void setRatingBar() {
        if (getView() != null) {
            RatingBar ratingBar = getView().findViewById(R.id.rating_bar);
            ratingBar.setRating(((float)rating) / 2.0f);
        }

    }

    @Override
    public void setExpandable(ExpandableFragment fragment) {

    }
}
