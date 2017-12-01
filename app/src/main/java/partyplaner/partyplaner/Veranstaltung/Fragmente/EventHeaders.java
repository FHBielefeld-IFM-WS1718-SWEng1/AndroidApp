package partyplaner.partyplaner.Veranstaltung.Fragmente;

import android.app.Fragment;

/**
 * Created by malte on 17.11.2017.
 */

public enum EventHeaders {
    GALLERY(new Gallery()),
    TASKLIST(new TaskList()),
    TODO(new TODO()),
    POLL(new Poll()),
    COMMENT(new CommentBody()),
    RATING(new Rating());

    private Fragment fragment;
    EventHeaders(Fragment fragment) {
        this.fragment = fragment;
    }

    public Fragment getFragment() {
        return fragment;
    }

}
