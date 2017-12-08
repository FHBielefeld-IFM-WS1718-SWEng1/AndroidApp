package partyplaner.partyplaner.Veranstaltung.Fragmente;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;

/**
 * Created by malte on 27.11.2017.
 */

public class CommentBody extends Fragment {

    public static final Pattern VALID_COMMENT = Pattern.compile("[ ]*");
    public static boolean validateComment(String comment) {
        Matcher matcher = VALID_COMMENT.matcher(comment);
        return matcher.matches();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.event_fragment_comment, container, false);
        ImageView send = view.findViewById(R.id.send_first_comment);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendComment(view);
            }
        });
        return view;
    }

    private void sendComment(View view) {
        EditText input = view.findViewById(R.id.comment_first_input);
        String comment = input.getText().toString();
        input.setText("");

        if(!validateComment(comment)) {
            Bundle args = new Bundle();
            args.putString(Keys.EXTRA_COMMENT, comment);
            args.putString(Keys.EXTRA_NAME, "Tim");

            Comment fragment = new Comment();
            fragment.setArguments(args);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.body_comment, fragment);
            transaction.commit();
        }
    }
}
