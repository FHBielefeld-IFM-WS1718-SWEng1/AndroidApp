package partyplaner.api;

/**
 * Created by André on 08.12.2017.
 */

enum RouteType {
    GET,
    POST,
    PUT,
    DELETE;

    public static RouteType stringToRoute(String name) {
        switch (name) {
            case "GET":
                return GET;
            case "POST":
                return POST;
            case "PUT":
                return PUT;
            case "DELETE":
                return DELETE;
            default:
                return null;
        }
    }
}
