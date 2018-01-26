package partyplaner.api;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import partyplaner.partyplaner.EventMainActivity;
import partyplaner.partyplaner.Keys;
import partyplaner.partyplaner.Veranstaltung.IServiceReceiver;

/**
 * Created by malte on 10.01.2018.
 */

public class ServiceDateReceiver extends BroadcastReceiver {

    private IServiceReceiver main;

    public ServiceDateReceiver() {
    }

    public ServiceDateReceiver(IServiceReceiver eventMainActivity) {
        main = eventMainActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        main.receiveData(intent.getStringExtra(Keys.EXTRA_DATA), intent.getStringExtra(Keys.EXTRA_ID));
    }
}
