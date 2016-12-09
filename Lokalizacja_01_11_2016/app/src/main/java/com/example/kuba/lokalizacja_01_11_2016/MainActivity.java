package com.example.kuba.lokalizacja_01_11_2016;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, View.OnClickListener, SeekBar.OnSeekBarChangeListener,
        LoadJSONTask.Listener, AdapterView.OnItemClickListener {

    //GPS>>
    private TextView txtOutputLat, txtOutputLon;
    private TextView oznaczeniePolaczenia;
    private TextView odleglosc;
    private TextView searchRadiusText;
    private SeekBar searchRadiusSeekBar;
    //private TextView oznaczenieBrakuPolaczenia;
    private Button odswiez;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double lat;
    private double lon;
    private double distance;
    private int searchRadius;
    //GPS<<

    //Database connection and ListView>>
    private ListView mListView;
    //public static final String URL = "https://api.learn2crack.com/android/jsonandroid/";
    public static final String URL = "http://10.0.2.2/PGnauka/pehap.php"; //łączenie z localhost dla emulatora
    //public static final String URL = "http://192.168.1.101:80/PGnauka/pehap.php"; //łączenie z localhost dla telefonu

    private List<HashMap<String, String>> mEventMapList = new ArrayList<>();

    private static final String KEY_DAY = "day"; //klucz, wg którego wartości wyciągane są z bazy danych?
    private static final String KEY_HOUR = "hour";
    private static final String KEY_CITY = "city";
    private static final String KEY_CITYBLOCK = "cityBlock";
    //Database connection and ListView<<

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //GPS>>
        txtOutputLat = (TextView) findViewById(R.id.textView);
        txtOutputLon = (TextView) findViewById(R.id.textView2);
        oznaczeniePolaczenia = (TextView) findViewById((R.id.textView3));
        odleglosc = (TextView) findViewById((R.id.odleglosc));
        searchRadiusText = (TextView) findViewById((R.id.searchRadiusTextView));
        //oznaczenieBrakuPolaczenia = (TextView) findViewById((R.id.textView4));
        odswiez = (Button) findViewById(R.id.odswiez);
        odswiez.setOnClickListener(MainActivity.this);
        searchRadiusSeekBar = (SeekBar)findViewById(R.id.seekBar);
        searchRadiusSeekBar.setOnSeekBarChangeListener(this);

        buildGoogleApiClient();
        //GPS<<

        //Database connection and ListView>>
        mListView = (ListView) findViewById(R.id.list_view);
        mListView.setOnItemClickListener(this);
        new LoadJSONTask(this).execute(URL);
        //Database connection and ListView<<
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        compareCoordinates();
        updateUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        oznaczeniePolaczenia.setText("Połączono z usługą google location");
        mLocationRequest = LocationRequest.create();
        //mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(30000);
        mLocationRequest.setFastestInterval(30000);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            /*if (mLastLocation != null) {
                lat = mLastLocation.getLatitude();
                lon = mLastLocation.getLongitude();
            }*/
            //updateUI();
        }
    }

    public void compareCoordinates() {
        double radius = 6372.8; // In kilometers
        double testLat=54.048799;
        double testLon=16.221209;
        //double latitude = 54.36036036036036;
        //double longitude = 18.615272389032913;
        double Latitude = lat;
        double Longitude = lon;
        double dLat = Math.toRadians(Latitude - testLat);
        double dLon = Math.toRadians(Longitude - testLon);
        testLat = Math.toRadians(testLat);
        Latitude = Math.toRadians(Latitude);

        double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(testLat) * Math.cos(Latitude);
        double c = 2 * Math.asin(Math.sqrt(a));
        distance =  radius * c;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lon = location.getLongitude();
        compareCoordinates();
        updateUI();
        //mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        buildGoogleApiClient();
        oznaczeniePolaczenia.setText("Brak połączenia");
    }

    synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


    }
    //Database connection and ListView>>
    @Override
    public void onLoaded(List<eventDescription> eventList) {

        for (eventDescription event : eventList) {

            HashMap<String, String> map = new HashMap<>();

            map.put(KEY_DAY, event.getDay()); //wpisanie wartosci do listy
            map.put(KEY_HOUR, event.getHour());
            map.put(KEY_CITY, event.getCity());
            map.put(KEY_CITYBLOCK, event.getCityBlock());

            mEventMapList.add(map);
        }

        loadListView();
    }

    @Override
    public void onError() {

        Toast.makeText(this, "Error !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Toast.makeText(this, mEventMapList.get(i).get(KEY_CITY),Toast.LENGTH_LONG).show();
    }

    private void loadListView() {

        ListAdapter adapter = new SimpleAdapter(MainActivity.this, mEventMapList, R.layout.list_item,
                new String[] { KEY_DAY, KEY_HOUR, KEY_CITY, KEY_CITYBLOCK },
                new int[] { R.id.day,R.id.hour, R.id.city, R.id.cityBlock }); //odwołanie do pól tekstowych z list_item.xml

        mListView.setAdapter(adapter);

    }
    //Database connection and ListView<<

    //GPS>>
    void updateUI() {
        String latString=Double.toString(lat);
        String lonString=Double.toString(lon);
        String distanceString=Double.toString(distance);
        txtOutputLat.setText(latString);
        txtOutputLon.setText(lonString);
        odleglosc.setText(distanceString);
    }

    @Override
    public void onClick(View v) {
        //getCoordinates();
        //compareCoordinates(54.36036036036036, 18.615272389032913, 54.048799, 16.221209);
        mGoogleApiClient.disconnect();
        mGoogleApiClient.connect();
        //compareCoordinates();
        //updateUI();

    }


    //method for when the progress bar is changed
    public void onProgressChanged(SeekBar searchRadiusSeekBar, int progress,
                                  boolean fromUser) {
        searchRadius = progress+1;
        String searchRadiusString=Double.toString(searchRadius);
        searchRadiusText.setText(searchRadiusString);
    }
    //method for when the progress bar is first touched
    public void onStartTrackingTouch(SeekBar seekBar) {
    }
    //method for when the progress bar is released
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
    //GPS<<
}

