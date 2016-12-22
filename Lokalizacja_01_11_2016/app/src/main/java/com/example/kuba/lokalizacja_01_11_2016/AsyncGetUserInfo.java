package com.example.kuba.lokalizacja_01_11_2016;

/**
 * Created by Kuba on 2016-12-19.
 */

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class AsyncGetUserInfo extends AsyncTask<Void,Void,Void> {
    @Override
    protected Void doInBackground(Void... params) {

        try {

            URL url = new URL("http://www.projektgrupowy.cba.pl/getuserinfo.php?name=Kuba");
            //URL url = new URL("http://192.168.1.104:80/PGnauka/dataRec.php?name=Kuba");
            //URL url = new URL("http://10.0.2.2/PGnauka/dataRec.php?name=Kuba");
            URLConnection connection = url.openConnection();
            connection.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;

            while((inputLine = in.readLine()) != null)
            {
                System.out.println(inputLine);
            }

            in.close();

        } catch (Exception e) {

            //Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();

        }

        return null;
    }
}
