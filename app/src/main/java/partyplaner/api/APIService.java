package partyplaner.api;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import partyplaner.partyplaner.Keys;

/**
 * Created by malte on 10.01.2018.
 */

public class APIService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public APIService(String name) {
        super(name);
    }

    public APIService() {
        super("Default");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String url = intent.getStringExtra(Keys.EXTRA_URL);
        String request = intent.getStringExtra(Keys.EXTRA_REQUEST);
        String data = intent.getStringExtra(Keys.EXTRA_DATA);
        String type = intent.getStringExtra(Keys.EXTRA_SERVICE_TYPE);

        String response = GeneralAPIRequestHandler.request(url, RouteType.stringToRoute(request), data);

        Intent localIntent = new Intent(type)
                        .putExtra(Keys.EXTRA_DATA, response)
                .putExtra(Keys.EXTRA_ID, intent.getStringExtra(Keys.EXTRA_ID));
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }
}
