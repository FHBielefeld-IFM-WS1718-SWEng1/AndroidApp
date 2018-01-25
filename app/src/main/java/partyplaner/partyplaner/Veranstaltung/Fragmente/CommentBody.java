package partyplaner.partyplaner.Veranstaltung.Fragmente;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import partyplaner.api.ServiceDateReceiver;
import partyplaner.data.party.Party;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;
import partyplaner.partyplaner.Veranstaltung.IEventDataManager;

/**
 * Created by malte on 27.11.2017.
 */

public class CommentBody extends Fragment implements IReceiveData {


    public static final Pattern VALID_COMMENT = Pattern.compile("[ ]*");
    public static boolean validateComment(String comment) {
        Matcher matcher = VALID_COMMENT.matcher(comment);
        return matcher.matches();
    }
    private ExpandableFragment expandableFragment;
    private int parentid;
    private List<Fragment> fragments = new ArrayList<>();
    private Party party;
    private partyplaner.data.party.Comment[] comments;
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
        final View view = inflater.inflate(R.layout.event_fragment_comment, container, false);
        if (savedInstanceState == null) {
            ImageView send = view.findViewById(R.id.send_first_comment);
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendComment(view);
                }
            });
        }
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

    @Override
    public void receiveData() {
        if (data.getParty() != null) {
            party = data.getParty();
            comments = party.getComments();
            update();
        }
    }

    private void update() {
        /*for (Fragment f : fragments) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.remove(f);
            transaction.commit();
        }
        fragments.clear();

        if (comments != null) {
            for (partyplaner.data.party.Comment comment : comments) {
                addComment(comment);
            }
        }*/
    }

    private void addComment(partyplaner.data.party.Comment comment) {
        Bundle args = new Bundle();
        args.putInt(Keys.EXTRA_COMMENT_PARENTID, -1);

        Comment fragment = new Comment();
        fragment.setArguments(args);
        fragment.setExpandable(expandableFragment);
        fragment.setComment(comment);
        fragments.add(fragment);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.body_comment, fragment);
        transaction.commit();
    }

    @Override
    public void setExpandable(ExpandableFragment fragment) {
        expandableFragment = fragment;
    }
}
