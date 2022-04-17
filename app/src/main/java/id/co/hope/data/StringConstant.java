package id.co.hope.data;


import id.co.hope.BuildConfig;

public class StringConstant {
    private static String package_name = BuildConfig.APPLICATION_ID;

//    public static final String SERVICECHAT_RECEIVE_MESSAGE      = "received message";
    public static final String SERVICECHAT_RECEIVE_MESSAGE      = "listen";
    public static final String SERVICECHAT_RECEIVE_UPDATESTATUS = "listen_update_status";

    public static final String[] mimeTypes =
            {"image/*","application/pdf","application/msword","application/vnd.ms-powerpoint","application/vnd.ms-excel","text/plain"};

    public static final String[] mimeTypesFile =
            {"application/pdf","application/msword","application/vnd.ms-powerpoint","application/vnd.ms-excel"};
}
