package com.example.kuba.AndroidSalsa;

/**
 * Created by Kuba on 2016-12-19.
 */

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import static com.example.kuba.AndroidSalsa.MainActivity.getLatitude;
import static com.example.kuba.AndroidSalsa.MainActivity.getLongitude;
import static com.example.kuba.AndroidSalsa.MainActivity.getSearchRadius;

public class AsyncGetUserInfo extends AsyncTask<Void,Void,Void> {

    private static String sessionID;

    @Override
    protected Void doInBackground(Void... params) {

        try {
            URL url = new URL("http://www.projektgrupowy.cba.pl/getuserinfo.php?longitude="+getLongitude()+"&latitude="+getLatitude()+"&searchRadius="+getSearchRadius());
            URLConnection connection = url.openConnection();
            connection.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;

            while((inputLine = in.readLine()) != null)
            {
                if(inputLine.startsWith("sid=")){
                    sessionID = inputLine.split("\\<", 2)[0];
                    sessionID=sessionID.substring(4);
                    System.out.println("SessionID from AsyncTask="+sessionID);
                }
            }
            in.close();

        } catch (Exception e) {
            System.out.println("Error");
        }

        return null;
    }

    public static String getSessionID(){
        return sessionID;
    }
}
