package partyplaner.partyplaner.poll;

import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
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

import partyplaner.api.ServiceDateReceiver;
import partyplaner.data.party.PollOption;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.R;
import partyplaner.partyplaner.Veranstaltung.IServiceReceiver;

public class Poll extends AppCompatActivity implements IServiceReceiver{

    private int id;
    private partyplaner.data.party.Poll poll;
    private PieChart pieChart ;
    private ArrayList<Entry> entries ;
    private ArrayList<String> PieEntryLabels ;
    private PieDataSet pieDataSet ;
    private PieData pieData ;
    private ServiceDateReceiver serviceDateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);

        poll = (partyplaner.data.party.Poll) getIntent().getSerializableExtra(Keys.EXTRA_POLL);

        if (poll != null) {
            setUpView();
            setOptions();
        }
    }

    private void setUpView() {
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

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter statusIntentFilter = new IntentFilter(Keys.EXTRA_SERVICE_POLL);
        serviceDateReceiver = new ServiceDateReceiver(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(serviceDateReceiver, statusIntentFilter);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

    private void setOptions() {
        RadioGroup radioGroup = findViewById(R.id.radio_group);
        for (partyplaner.data.party.PollOption option : poll.getChoices()) {
            RadioButton optionButton = new RadioButton(this);
            optionButton.setText(option.getText());
            radioGroup.addView(optionButton);

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(serviceDateReceiver);
    }

    public void AddValuesToPIEENTRY(){
        for (int index = 0; index < poll.getChoices().length; index++) {
            entries.add(new BarEntry(poll.getChoices()[index].getVotes(), index));
        }
    }

    public void AddValuesToPieEntryLabels(){
        for (int index = 0; index < poll.getChoices().length; index++) {
            PieEntryLabels.add(poll.getChoices()[index].getText());
        }
    }

    @Override
    public void receiveData(String json, String id) {

    }
}
