package partyplaner.partyplaner.poll;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import partyplaner.api.APIService;
import partyplaner.api.ServiceDateReceiver;
import partyplaner.data.party.PollOption;
import partyplaner.data.party.UserChoices;
import partyplaner.data.user.I;
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
    private int myChoice = -1;
    private List<Integer> radioIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);

        poll = (partyplaner.data.party.Poll) getIntent().getSerializableExtra(Keys.EXTRA_POLL);

        Button save = findViewById(R.id.btn_sendAnswer);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveVote();
            }
        });
        if (poll != null) {
            setOptions();
            setUpView();
        }
    }

    private void saveVote() {
        RadioGroup radioGroup = findViewById(R.id.radio_group);
        int checked = radioGroup.getCheckedRadioButtonId();
        if (myChoice == -1) {
            RadioButton  choice = findViewById(checked);
            myChoice = poll.getChoiceIdByText(choice.getText().toString());
            sendChanges(myChoice);
        } else {
            Toast.makeText(this, "Du hast bereits Abgestimmt!", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendChanges(int id) {
        if (id >= 0) {
            Intent apiHanlder = new Intent(this, APIService.class);
            apiHanlder.putExtra(Keys.EXTRA_URL, "/party/vote/choice/vote?api=" + I.getMyself().getApiKey());
            apiHanlder.putExtra(Keys.EXTRA_REQUEST, "POST");
            apiHanlder.putExtra(Keys.EXTRA_DATA, "{\"choice_id\":" + id + "}");
            apiHanlder.putExtra(Keys.EXTRA_ID, Keys.EXTRA_POLL_CHOICE);
            apiHanlder.putExtra(Keys.EXTRA_SERVICE_TYPE, Keys.EXTRA_SERVICE_POLL);
            this.startService(apiHanlder);
        }
    }

    private void setUpView() {
        TextView question = findViewById(R.id.poll_question);
        question.setText(poll.getQuestion());

        pieChart = findViewById(R.id.chart1);
        entries = new ArrayList<>();
        PieEntryLabels = new ArrayList<>();

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
        radioGroup.removeAllViews();
        List<RadioButton> buttons = new ArrayList<>();
        for (partyplaner.data.party.PollOption option : poll.getChoices()) {
            RadioButton optionButton = new RadioButton(this);
            optionButton.setText(option.getText());
            int id = View.generateViewId();
            optionButton.setId(id);
            radioIds.add(id);
            radioGroup.addView(optionButton);
            buttons.add(optionButton);
            if (option.isMyChoice()) {
                radioGroup.check(id);
                myChoice = id;
            }
        }
        if (myChoice != -1) {
            for (RadioButton button : buttons) {
                button.setClickable(false);
            }
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
        if (!json.contains("error")) {
            Log.e(getClass().getName(), json);
            poll.addVoting(myChoice);
            setUpView();
            Toast.makeText(this, "Änderungen gespeichert!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Änderungen konnten nicht gespeichert werden!", Toast.LENGTH_SHORT).show();
        }

    }
}
