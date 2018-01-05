package partyplaner.api;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import partyplaner.data.user.I;
import partyplaner.data.user.LoginData;
import partyplaner.data.user.RegistrationData;

/**
 * Created by André on 08.12.2017.
 */

public class APIConnectionHandler {

    private class Connection extends AsyncTask<String, Void, Response> {
        @Override
        protected Response doInBackground(String... arg0) {
            try {
                return connect(arg0[0], arg0[1]);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    private static final String baseURL = "http://api.dleunig.de";

    private static APIConnectionHandler apiConHandler;
    private Gson gson;

    private APIConnectionHandler() {
        gson = new Gson();
    }


    static APIConnectionHandler getAPIConnectionHandler() {
        if(apiConHandler == null) {
            apiConHandler = new APIConnectionHandler();
        }
        return apiConHandler;
    }

    public I login(LoginData data) throws IOException {
        String postResponse = post(baseURL+ APIConnectionType.LOGIN.getRoute(), gson.toJson(data));
        if (!postResponse.toLowerCase().contains("error")) {
            return gson.fromJson(postResponse, I.class);
        } else {
            return null;
        }
    }

    public boolean register(RegistrationData data) throws IOException {
        String postResponse = post(baseURL+ APIConnectionType.REGISTER.getRoute(), gson.toJson(data));
        if (!postResponse.toLowerCase().contains("error")) {
            return true;
        } else {
            return false;
        }
    }

    private String post(String url, String data) throws IOException {

        Connection conn = new Connection();
        try {
            Response response= conn.execute(url, data).get();
            return  response.body().string();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Response connect(String url, String data) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, data);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .build();

        Response response = client.newCall(request).execute();
        return response;
    }
}
