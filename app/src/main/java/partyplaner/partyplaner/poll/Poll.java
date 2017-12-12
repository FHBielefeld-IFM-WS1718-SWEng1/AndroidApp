package partyplaner.partyplaner.poll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.Serializable;
import java.util.ArrayList;

import partyplaner.data.party.PollOption;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;

public class Poll extends AppCompatActivity {

    private partyplaner.data.party.Poll poll;
    PieChart pieChart ;
    ArrayList<Entry> entries ;
    ArrayList<String> PieEntryLabels ;
    PieDataSet pieDataSet ;
    PieData pieData ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);

        poll = (partyplaner.data.party.Poll) getIntent().getSerializableExtra(Keys.EXTRA_POLL);
        setOptions();

        TextView question = findViewById(R.id.poll_question);
        question.setText(poll.getQuestion());

        pieChart = findViewById(R.id.chart1);
        entries = new ArrayList<>();
        PieEntryLabels = new ArrayList<String>();

        AddValuesToPIEENTRY();
        AddValuesToPieEntryLabels();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        pieChart.setLayoutParams(new LinearLayout.LayoutParams(displayMetrics.widthPixels, displayMetrics.widthPixels));

        pieDataSet = new PieDataSet(entries, "");
        pieData = new PieData(PieEntryLabels, pieDataSet);
        pieData.setValueTextSize(14);

        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.setData(pieData);
        pieChart.animateY(3000);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setDescription("");
        pieChart.getLegend().setTextSize(18);
    }

    private void setOptions() {
        RadioGroup radioGroup = findViewById(R.id.radio_group);
        for (partyplaner.data.party.PollOption option : poll.getPollOptions()) {
            RadioButton optionButton = new RadioButton(this);
            optionButton.setText(option.getName());
            radioGroup.addView(optionButton);

        }
    }

    public void AddValuesToPIEENTRY(){
        for (int index = 0; index < poll.getPollOptions().size(); index++) {
            entries.add(new BarEntry(poll.getPollOptions().get(index).getVotedUsers(), index));
        }
    }

    public void AddValuesToPieEntryLabels(){
        for (int index = 0; index < poll.getPollOptions().size(); index++) {
            PieEntryLabels.add(poll.getOptionTitles().get(index));
        }
    }
}
