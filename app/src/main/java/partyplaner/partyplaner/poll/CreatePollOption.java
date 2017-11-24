package partyplaner.partyplaner.poll;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

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
}
