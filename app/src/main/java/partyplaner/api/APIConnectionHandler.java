package partyplaner.api;

import com.google.gson.Gson;

import java.io.IOException;

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
        return gson.fromJson(post(baseURL+ APIConnectionType.LOGIN.getRoute(), gson.toJson(data)), I.class);
    }

    public boolean register(RegistrationData data) throws IOException {
        post(baseURL+ APIConnectionType.LOGIN.getRoute(), gson.toJson(data));
        return true;
    }

    private String post(String url, String data) throws IOException {
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

        return response.body().string();
    }

}
