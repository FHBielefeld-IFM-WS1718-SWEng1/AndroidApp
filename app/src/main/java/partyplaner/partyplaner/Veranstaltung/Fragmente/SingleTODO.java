package partyplaner.partyplaner.Veranstaltung.Fragmente;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;

/**
 * Created by malte on 23.11.2017.
 */

public class SingleTODO extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_fragment_single_todo, container, false);
        DecimalFormat format = new DecimalFormat("#0.00");

        Bundle arguments = getArguments();
        String name = arguments.getString(Keys.EXTRA_NAME);
        Double price = arguments.getDouble(Keys.EXTRA_PRICE);
        boolean owner = arguments.getBoolean(Keys.EXTRA_OWNER);

        TextView textName = view.findViewById(R.id.todo_name);
        TextView textPrice = view.findViewById(R.id.todo_price);
        ImageView button = view.findViewById(R.id.todo_delete);

        textName.setText(name);
        textPrice.setText(format.format(price));
        if (!owner) {
            button.setVisibility(View.INVISIBLE);
        }
        return view;
    }
}
