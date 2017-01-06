package com.example.kuba.AndroidSalsa;

import android.Manifest;
import android.content.Intent;
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

import static com.example.kuba.AndroidSalsa.AsyncGetUserInfo.getSessionID;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, View.OnClickListener, SeekBar.OnSeekBarChangeListener,
        LoadJSONTask.Listener, AdapterView.OnItemClickListener {

    public static String sid = "0";

    //GPS>>
    private TextView searchRadiusText;
    private SeekBar searchRadiusSeekBar;
    private Button refreshButton;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private static double latitude;
    private static double longitude;
    private static int searchRadius;

    //GPS<<

    //Database connection and ListView>>
    private ListView mListView;
    //public static final String URL = "http://www.projektgrupowy.cba.pl/pehap.php?sid=" + getSessionID; //?longitude="+getLongitude()+"&latitude="+getLatitude()+"&searchRadius="+getSearchRadius();

    private List<HashMap<String, String>> mEventMapList = new ArrayList<>();

    private static final String KEY_DAY = "day"; //klucz, wg którego wartości wyciągane są z bazy danych?
    private static final String KEY_HOUR = "hour";
    private static final String KEY_CITY = "city";
    private static final String KEY_CITYBLOCK = "cityBlock";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_NAME = "name";
    private static final String KEY_TOPAY = "toPay";
    private static final String KEY_ADRESS = "adress";
    private static final String KEY_ADMINISTRATORNAME = "administratorName";
    private static final String KEY_TELEPHONENUMBER = "telephoneNumber";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_KEYWORDS = "keywords";
    //Database connection and ListView<<

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //GPS>>
        searchRadiusText = (TextView) findViewById((R.id.searchRadiusTextView));
        refreshButton = (Button) findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(MainActivity.this);
        searchRadiusSeekBar = (SeekBar)findViewById(R.id.seekBar);
        searchRadiusSeekBar.setOnSeekBarChangeListener(this);
        buildGoogleApiClient();
        searchRadius = 10;
        String searchRadiusString=Double.toString(searchRadius);
        searchRadiusText.setText(searchRadiusString);
        //GPS<<

        //Database connection and ListView>>
        mListView = (ListView) findViewById(R.id.list_view);
        mListView.setOnItemClickListener(this);
        //Database connection and ListView<<
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(30000);
        mLocationRequest.setFastestInterval(30000);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        buildGoogleApiClient();
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
    public void onLoaded(List<EventDescription> eventList) {

        for (EventDescription event : eventList) {

            HashMap<String, String> map = new HashMap<>();

            map.put(KEY_DAY, event.getDay()); //wpisanie wartosci do listy
            map.put(KEY_HOUR, event.getHour());
            map.put(KEY_CITY, event.getCity());
            map.put(KEY_CITYBLOCK, event.getCityBlock());
            map.put(KEY_DESCRIPTION, event.getDescription());
            map.put(KEY_NAME, event.getName());
            map.put(KEY_TOPAY, event.getToPay());
            map.put(KEY_ADRESS, event.getAdress());
            map.put(KEY_ADMINISTRATORNAME, event.getAdministratorName());
            map.put(KEY_TELEPHONENUMBER, event.getTelephoneNumber());
            map.put(KEY_EMAIL, event.getEmail());
            map.put(KEY_KEYWORDS, event.getKeywords());

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

        Intent intent = new Intent(this, DescriptionActivity.class);
        intent.putExtra("day", mEventMapList.get(i).get(KEY_DAY));
        intent.putExtra("hour", mEventMapList.get(i).get(KEY_HOUR));
        intent.putExtra("city", mEventMapList.get(i).get(KEY_CITY));
        intent.putExtra("cityBlock", mEventMapList.get(i).get(KEY_CITYBLOCK));
        intent.putExtra("description", mEventMapList.get(i).get(KEY_DESCRIPTION));
        intent.putExtra("name", mEventMapList.get(i).get(KEY_NAME));
        intent.putExtra("toPay", mEventMapList.get(i).get(KEY_TOPAY));
        intent.putExtra("adress", mEventMapList.get(i).get(KEY_ADRESS));
        intent.putExtra("administratorName", mEventMapList.get(i).get(KEY_ADMINISTRATORNAME));
        intent.putExtra("telephoneNumber", mEventMapList.get(i).get(KEY_TELEPHONENUMBER));
        intent.putExtra("email", mEventMapList.get(i).get(KEY_EMAIL));
        intent.putExtra("keywords", mEventMapList.get(i).get(KEY_KEYWORDS));
        startActivity(intent);
    }

    private void loadListView() {

        ListAdapter adapter = new SimpleAdapter(MainActivity.this, mEventMapList, R.layout.list_item,
                new String[] { KEY_DAY, KEY_HOUR, KEY_CITY, KEY_CITYBLOCK, KEY_DESCRIPTION, KEY_NAME },
                new int[] { R.id.day,R.id.hour, R.id.city, R.id.cityBlock, R.id.description, R.id.name }); //odwołanie do pól tekstowych z list_item.xml

        mListView.setAdapter(adapter);

    }
    //Database connection and ListView<<

    @Override
    public void onClick(View v) {
        mListView.setAdapter(null);
        mEventMapList.clear();
        mGoogleApiClient.disconnect();
        mGoogleApiClient.connect();
        System.out.println(getSearchRadius());
        AsyncGetUserInfo asyncGetUserInfo = new AsyncGetUserInfo();
        try {
            asyncGetUserInfo.execute().get();
        }
        catch (Exception e){
            System.out.println("Error");
        }
        sid=getSessionID();
        System.out.println("Session ID from MainThread="+sid);
        new LoadJSONTask(this).execute("http://www.projektgrupowy.cba.pl/pehap.php?sid=" + sid);
    }

    //method called when the progress bar is changed
    public void onProgressChanged(SeekBar searchRadiusSeekBar, int progress,
                                  boolean fromUser) {
        searchRadius = progress+1;
        String searchRadiusString=Double.toString(searchRadius);
        searchRadiusText.setText(searchRadiusString);
    }
    //method called when the progress bar is first touched
    public void onStartTrackingTouch(SeekBar seekBar) {
    }
    //method called when the progress bar is released
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
    public static double getLatitude(){
        return latitude;
    }
    public static double getLongitude(){
        return longitude;
    }
    public static int getSearchRadius(){
        return searchRadius;
    }
}

