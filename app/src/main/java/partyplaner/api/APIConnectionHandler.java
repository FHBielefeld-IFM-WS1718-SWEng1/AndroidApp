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
                return connect(arg0[0], RouteType.stringToRoute(arg0[1]), arg0[2]);
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
        String postResponse = post(APIConnectionType.LOGIN.getRoute(), gson.toJson(data));
        if (postResponse != null && !postResponse.toLowerCase().contains("error")) {
            return gson.fromJson(postResponse, I.class);
        } else {
            return null;
        }
    }

    public boolean logout() throws IOException {
        String postResponse = delete(APIConnectionType.LOGOUT.getRoute() + "?api=" + I.getMyself().getApiKey(), null);
        if (postResponse != null && !postResponse.toLowerCase().contains("error")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean register(RegistrationData data) throws IOException {
        String postResponse = post(APIConnectionType.REGISTER.getRoute(), gson.toJson(data));
        if (postResponse != null && !postResponse.toLowerCase().contains("error")) {
            return true;
        } else {
            return false;
        }
    }

    String get(String url) throws IOException {
        Connection conn = new Connection();
        try {
            Response response = conn.execute(baseURL + url, "GET", null).get();
            if (response != null) {
                return response.body().string();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    String put(String url, String data) throws IOException {
        Connection conn = new Connection();
        try {
            Response response = conn.execute(baseURL + url, "PUT", data).get();
            if (response != null) {
                return response.body().string();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    String post(String url, String data) throws IOException {
        Connection conn = new Connection();
        try {
            Response response = conn.execute(baseURL + url, "POST", data).get();
            if (response != null) {
                return response.body().string();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    String delete(String url, String data) throws IOException {
        Connection conn = new Connection();
        try {
            Response response = conn.execute(baseURL + url, "DELETE", data).get();
            if (response != null) {
                return response.body().string();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Response connect(String url, RouteType route,  String data) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = null;
        Request.Builder builder = new Request.Builder()
                .url(url);

        MediaType mediaType = MediaType.parse("application/json");
        switch (route) {
            case GET:
                builder.get();
                break;
            case PUT:
                RequestBody body = RequestBody.create(mediaType, data);
                builder.put(body);
                break;
            case POST:
                body = RequestBody.create(mediaType, data);
                builder.post(body);
                break;
            case DELETE:
                body = RequestBody.create(mediaType, data);
                builder.post(body);
                break;
            default:
                break;
        }
        request = builder.addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .build();

        if (request != null) {
            Response response = client.newCall(request).execute();
            return response;
        }
        return null;
    }
}
