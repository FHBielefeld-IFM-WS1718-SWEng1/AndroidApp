package partyplaner.api;

/**
 * Created by André on 08.12.2017.
 */

enum APIConnectionType {
    LOGIN("/login", RouteType.POST, false),
    LOGOUT("/logout", RouteType.DELETE, false),
    REGISTER("/register", RouteType.POST, false);


    private String route;
    private RouteType type;
    private boolean custom;

    APIConnectionType(String route, RouteType type, boolean custom) {
        this.route = route;
        this.type = type;
        this.custom = custom;
    }

    String getRoute() {
        return  route;
    }

    RouteType getRouteType() {
        return type;
    }

    boolean needsCustomRoute() {
        return custom;
    }
}
