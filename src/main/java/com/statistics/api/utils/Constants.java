package com.statistics.api.utils;

public class Constants {
    
    public static final String IP_STACK_URL = "http://api.ipstack.com/";

    //TODO - borrar esto de acá y ponerlo como env variable en la instancia al momento del deploy
    public static final String IP_STACK_ACCESS_KEY = "f6ae78026243789416ef752878bf75c9";

    public static final String STATISTICS_QUEUE = "statistics_queue";

    public static final String[] IP_HEADER_CANDIDATES = {
        "X-Forwarded-For",
        "Proxy-Client-IP",
        "WL-Proxy-Client-IP",
        "HTTP_X_FORWARDED_FOR",
        "HTTP_X_FORWARDED",
        "HTTP_X_CLUSTER_CLIENT_IP",
        "HTTP_CLIENT_IP",
        "HTTP_FORWARDED_FOR",
        "HTTP_FORWARDED",
        "HTTP_VIA",
        "REMOTE_ADDR"
    };

    private static final String DB_TABLE_PREFIX = "short_url_shard_";
    
    public static final String DB_SHARD_A = DB_TABLE_PREFIX + "a";

    public static final String DB_SHARD_B = DB_TABLE_PREFIX + "b";

    public static final String DB_SHARD_C = DB_TABLE_PREFIX + "c";

    public static final String DB_SHARD_D = DB_TABLE_PREFIX + "d";

    public static final String DB_SHARD_E = DB_TABLE_PREFIX + "e";
}
