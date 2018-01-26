package partyplaner.api;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;

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
        Uri image = intent.getData();
        String imageString = "";
        if (data == null && image != null) {
            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(image);
                Bitmap imageBitmap = BitmapFactory.decodeStream(imageStream);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream .toByteArray();

                imageString = Base64.encodeToString(byteArray, Base64.DEFAULT);
                Log.e(getClass().getName(), "Array Größe " + byteArray.length + " String Größe " + imageString.length() + " " + url);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            data = "{\"data\":\"" + imageString.replaceAll("\n", "") + "\"}";
        }
        String response = GeneralAPIRequestHandler.request(url, RouteType.stringToRoute(request), data);

        Uri responseUri = Uri.parse(response);

        Log.e(getClass().getName(), "response" + responseUri.toString());
        Intent localIntent = new Intent(type);
        //localIntent.setData(responseUri);
        localIntent.putExtra(Keys.EXTRA_DATA, response);
        localIntent.putExtra(Keys.EXTRA_ID, intent.getStringExtra(Keys.EXTRA_ID));
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }
}
