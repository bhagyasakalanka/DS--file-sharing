package org.uom.cse.cs4262.api;



public class Constant {

    public final static String BS_IP = "127.0.0.1";
    public final static int BS_PORT = 55555;
    public final static String BS_USERNAME = "Bootstrap";

    public final static String SERVER_PORT = "server.port";

    public final static int MIN_PORT = 40000;
    public final static int MAX_PORT = 50000;

    public final static String HTTP = "http://";


    public static class Action {
        public final static String REGISTER = "REG";
        public final static String REGISTER_OK = "REGOK";
        public final static String UNREGISTER = "UNREG";
        public final static String UNREGISTER_OK = "UNROK";
        public final static String JOIN = "JOIN";
        public final static String JOIN_OK = "JOINOK";
        public final static String LEAVE = "LEAVE";
        public final static String LEAVE_OK = "LEAVEOK";
        public final static String SEARCH = "SER";
        public final static String SEARCH_OK = "SEROK";
        public final static String DOWNLOAD = "DOWN";
        public final static String DOWNLOAD_OK = "DOWNOK";
    }

    public static class EndPoint {
        public final static String SEARCH = "/search";
        public final static String SEARCH_OK = "/searchok";
        public final static String JOIN = "/join";
        public final static String LEAVE = "/leave";
        public final static String DOWNLOAD = "/download";
        public final static String DOWNLOAD_OK = "/downloadok";

    }

    public static class ErrorCode {

        public static class Register {
            public final static int REGISTER_ERROR = 9996;
            public final static int DUPLICATE_IP_ERROR = 9997;
            public final static int ALREADY_REGISTERED_ERROR = 9998;
            public final static int COMMAND_ERROR = 9999;
        }

        public static class Search {
            public final static int ERROR_NODE_UNREACHABLE = 9999;
            public final static int ERROR_OTHER = 9998;

        }
        public static class Download {
            public final static int ERROR_NODE_UNREACHABLE = 9999;
            public final static int ERROR_OTHER = 9998;

        }

    }
}