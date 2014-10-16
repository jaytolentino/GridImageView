package com.example.jltolent.gridimageview.net;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by jay on 10/16/14.
 */

public class NetworkHandler {

    public NetworkHandler() {}

    public static boolean networkIsAvailable(ConnectivityManager connectivityManager) {
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}