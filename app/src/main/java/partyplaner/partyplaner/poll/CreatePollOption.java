package partyplaner.partyplaner.poll;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import partyplaner.api.APIService;
import partyplaner.data.user.I;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;

/**
 * Created by André on 17.11.2017.
 */

public class CreatePollOption extends Fragment{

    private EditText editText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_create_poll_option, container, false);

        editText = view.findViewById(R.id.option);
        editText.setHint("Option " + getArguments().getInt(CreatePoll.OPTION_NUMBER, 0));
        ImageView btnDelete = view.findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFragment();
            }
        });

        return view;
    }

    public void deleteFragment() {
        ((CreatePoll) getActivity()).deleteOption(this);
    }

    public void postOption(int votingid) {
        String text = editText.getText().toString().trim();
        if (!text.equals("")) {
            Intent apiHanlder = new Intent(getActivity(), APIService.class);
            apiHanlder.putExtra(Keys.EXTRA_URL, "/voting/choice?api=" + I.getMyself().getApiKey());
            apiHanlder.putExtra(Keys.EXTRA_REQUEST, "POST");
            apiHanlder.putExtra(Keys.EXTRA_DATA, "{\"text\":\"" + text +"\",\"voting_id\":" + votingid + "}");
            apiHanlder.putExtra(Keys.EXTRA_ID, Keys.EXTRA_POLL_CHOICE);
            apiHanlder.putExtra(Keys.EXTRA_SERVICE_TYPE, Keys.EXTRA_POLL);
            getActivity().startService(apiHanlder);
        }
    }
}
