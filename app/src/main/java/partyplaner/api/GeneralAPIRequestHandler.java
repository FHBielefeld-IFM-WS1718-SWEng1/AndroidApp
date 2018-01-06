package partyplaner.api;

import java.io.IOException;

/**
 * Created by André on 05.01.2018.
 */

public class GeneralAPIRequestHandler {

    public static String request(String url, RouteType routeType, String data) {
        try {
            switch (routeType) {
                case GET:
                    return APIConnectionHandler.getAPIConnectionHandler().get(url);
                case PUT:
                    return APIConnectionHandler.getAPIConnectionHandler().put(url, data);
                case POST:
                    return APIConnectionHandler.getAPIConnectionHandler().post(url, data);
                case DELETE:
                    return APIConnectionHandler.getAPIConnectionHandler().delete(url, data);
                default:
                    return null;
            }
        } catch (IOException e) {
            return null;
        }
    }
}
