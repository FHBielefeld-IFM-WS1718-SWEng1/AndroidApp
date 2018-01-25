package partyplaner.partyplaner.Veranstaltung.Fragmente;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import partyplaner.data.user.User;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;

/**
 * Created by malte on 27.11.2017.
 */

public class Comment extends Fragment {

    private Comment[] answers;
    private int parentId;
    private ExpandableFragment expandableFragment;
    private partyplaner.data.party.Comment comment;
    private int bodyId;
    private int authorId;
    private int textId;
    private int commentId;
    private int commentInputBackId;
    //evtl. private int sendButtonId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.event_fragment_single_comment, container, false);
            Bundle args = getArguments();
            bodyId = View.generateViewId();
            authorId = View.generateViewId();
            textId = View.generateViewId();
            commentId = View.generateViewId();
            commentInputBackId = View.generateViewId();
            parentId = args.getInt(Keys.EXTRA_PARTYID);

            LinearLayout body = view.findViewById(R.id.body_single_comment);
            TextView author = view.findViewById(R.id.comment_author);
            TextView text = view.findViewById(R.id.comment_text);
            EditText comment = view.findViewById(R.id.comment_input);
            LinearLayout commentInputBack = view.findViewById(R.id.comment_input_field);

            body.setId(bodyId);
            author.setId(authorId);
            text.setId(textId);
            comment.setId(commentId);
            commentInputBack.setId(commentInputBackId);

            ImageView send = view.findViewById(R.id.send_comment);
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendComment(view);
                }
            });
            update();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }

    private void sendComment(View view) {
        EditText input = view.findViewById(commentId);
        String comment = input.getText().toString();
        input.setText("");

        if(!CommentBody.validateComment(comment)) {
            Bundle args = new Bundle();
            args.putString(Keys.EXTRA_COMMENT, comment);
            args.putString(Keys.EXTRA_NAME, "Tim");

            Comment fragment = new Comment();
            fragment.setArguments(args);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(bodyId, fragment);
            transaction.commit();
        }
    }

    private void update() {
        if (comment != null && getView() != null) {
            TextView author = getView().findViewById(authorId);
            TextView text = getView().findViewById(textId);

            author.setText(comment.getUser().getName());
            text.setText(comment.getText());

            loadUser();

            if (parentId < 0) {
                LinearLayout back = getView().findViewById(commentInputBackId);
                back.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
            }
        }
    }

    private void loadUser() {

    }

    public void setExpandable(ExpandableFragment expandableFragment) {
        this.expandableFragment = expandableFragment;
    }

    public void setComment(partyplaner.data.party.Comment comment) {
        this.comment = comment;
        update();
    }
}
