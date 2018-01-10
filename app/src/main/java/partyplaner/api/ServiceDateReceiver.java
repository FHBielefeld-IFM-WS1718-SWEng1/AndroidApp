package partyplaner.api;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import partyplaner.partyplaner.EventMainActivity;
import partyplaner.partyplaner.Keys;

/**
 * Created by malte on 10.01.2018.
 */

public class ServiceDateReceiver extends BroadcastReceiver {

    private EventMainActivity main;

    public ServiceDateReceiver() {
    }

    public ServiceDateReceiver(EventMainActivity eventMainActivity) {
        main = eventMainActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        main.receiveData(intent.getStringExtra(Keys.EXTRA_DATA));
    }
}
