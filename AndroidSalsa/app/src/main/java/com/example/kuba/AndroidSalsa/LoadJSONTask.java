package com.example.kuba.AndroidSalsa;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class LoadJSONTask extends AsyncTask<String, Void, Response> {

    //konstruktor klasy, w kótrym tworzony jest listener nasłuchujący odpowiedzi z serwera
    public LoadJSONTask(Listener listener) {

        mListener = listener;
    }

    //interfejs listenera
    public interface Listener {

        void onLoaded(List<EventDescription> eventList);

        void onError();
    }

    //definicja listenera
    private Listener mListener;

    @Override //metoda obsługująca dekodowanie danych z tablicy JSON
    //wykonywana jest w tle by nie blokować intefejsu użytkownika
    protected Response doInBackground(String... strings) {
        try {
            //utworznie tablicy stringResponse przechowującej dane otrzymane w tablicy JSON
            String stringResponse = loadJSON(strings[0]);
            //utworzenie obiektu gson klasy Gson, która umożliwia łatwe dekodowanie danych
            //z tablicy JSON
            Gson gson = new Gson();
            //odczytanie wiadomości z tablicy stringResponse i zapisanie jej do listy event z klasy Response
            return gson.fromJson(stringResponse, Response.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override //metoda wywoływana po zdekodowaniu informacji z JSON
    protected void onPostExecute(Response response) {

        if (response != null) {
            //pobranie informacji na temat wydarzenia za pomocą getEvent z obiektu klasy Response
            //ta metoda zwraca wydarzenie zapisane w liście event, zawierającej obiekty klasy EventDescription
            mListener.onLoaded(response.getEvent());

        } else {

            mListener.onError();
        }
    }

    //metoda służąca do łączenia się z serwerem
    //argumentem jest adres skryptu php obsługującego bazę danych
    private String loadJSON(String jsonURL) throws IOException {

        URL url = new URL(jsonURL); //utworzenie nowego obkeitu klasy URL
        //utworzenie połączenia z podanym adresem
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //ustanowienie parametrów połączenia
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();

        //utworzenie obiektu klasy BufferedReader służącego do odczytywania danych
        //ze strumienia danych
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();

        //odczytywanie danych linia po linii
        while ((line = in.readLine()) != null) {

            response.append(line);
        }

        in.close();
        return response.toString();
    }
}
