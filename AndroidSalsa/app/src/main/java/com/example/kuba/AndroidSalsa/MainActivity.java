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
        GoogleApiClient.OnConnectionFailedListener, LocationListener, SeekBar.OnSeekBarChangeListener,
        LoadJSONTask.Listener, AdapterView.OnItemClickListener {

    //definicja pól  i obiektów wykorzystwanych w procesie określania lokalizacji użytkownika
    private TextView searchRadiusText; //pole tekstowe wyświetlające wybrany przez użytkownika promień poszukiwań
    private SeekBar searchRadiusSeekBar; //suwak do wyboru promienia poszukiwań
    private Location mLastLocation; //obiekt klasy Location przechowujący dane o lokalizacji użytkownika
    private GoogleApiClient mGoogleApiClient; //obiekt klasy zawierającej metody do korzystania z usług Google
    private LocationRequest mLocationRequest; //obiekt zawierający informacje o żądaniu lokalizacji
    private static double latitude; //pole przechowujące szerokość geograficzną użytkownika
    private static double longitude; //pole przechowujące długość geograficzną użytkownika
    private static int searchRadius; //pole przechowujące promień poszukiwań
    //GPS<<

    //definicja pól i obiektów wykorzystywanych do utworzenia listy znalezionych wydarzeń
    private ListView mListView; //lista

    private List<HashMap<String, String>> mEventMapList = new ArrayList<>(); //definicja obiektu klasy HashMap
    //jest to tablica, w której przechowywane są szczegółowe dane na temat każdego z wydarzeń, które zostaje przesłane
    //z serwera jako wydarzenie znajdujące się w promieniu wyszukiwań

    //defiinicja klczy, za pomocą których dane są pobierane z tabeli mEventMapList
    private static final String KEY_DAY = "day";
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

    //metoda wywoływana po utworzeniu okna
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //ustawienie layoutu activity_main jako
        //layoutu tworzonego okna

        //powiązanie zdefiniowanych w tej klasie pola tekstowego i suwaka  z obiektami w layoucie
        searchRadiusText = (TextView) findViewById((R.id.searchRadiusTextView));
        searchRadiusSeekBar = (SeekBar)findViewById(R.id.seekBar);
        //ustawienie listenera wykrywającego zmianę wartości ustawionej na suwaku
        searchRadiusSeekBar.setOnSeekBarChangeListener(this);
        buildGoogleApiClient(); //metoda inicjująca pracę obiektu klasy GoogleApiClient zdefiniowanego wyżej
        searchRadius = 10; //ustawienie domyślnej wartości promienia poszukiwań
        String searchRadiusString=Double.toString(searchRadius); //przekonwertowanie wartości promienia poszukiwań do
        //String w celu wykorzystania go w polu tekstowym
        searchRadiusText.setText(searchRadiusString); //ustawienie wartości pola tekstowego

        //powiązanie zdefiniowanej w tej klasie listy z listą zdefiniowaną w layoucie
        //i ustawienie listenera wykrywającego kliknięcie w jeden z obiektów listy
        mListView = (ListView) findViewById(R.id.list_view);
        mListView.setOnItemClickListener(this);

    }

    @Override //metoda wywoływana po połączeniu z Usługami Google Play
    public void onConnected(Bundle bundle) { //argumetem metody jest obiekt klasy Bundle w którym przechowywane są zmienne i za
        //pomocą którego są przesyłane między aktywnościami (oknami)

        mLocationRequest = LocationRequest.create(); //utworzenie zapytania o lokalizację
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); //ustanowienie priorytetu zapytania
        //high accuracy oznacza, że poza sieciami WiFi i danymi z wież telefonii komórkowych zostanie również wykorzystany GPS
        mLocationRequest.setInterval(30000); //ustawienie interwału czasowego z jakim pobierane są informacje o lokalizacji
        //(w milisekundach)
        mLocationRequest.setFastestInterval(30000); //ustawienie najkrótszego interwału czasowego
        //metoda umożliwiająca update lokalizacji użytkownika
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        //instrukcja warunkowa sprawdzająca, czy aplikacji zostały przyznane uprawnienia do pobierania lokalizacji
        //(czy plik manifest zawiera odpowiednie zezwolenia)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override //metoda wywoływana po pierwszym odczycie lokalizacji i po jej zmianie
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude(); //przypisanie długości i szerogości geograficznej do zdefiniowanych wcześniej pól
        longitude = location.getLongitude();
    }

    @Override //metoda wywoływana po nieudanej próbie dostępu do Usług Google Play
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

    @Override //metoda wywoływana po załadowaniu listy wydarzeń
    //argumentem jest lista obiektów klasy EventDescription (klasa ta zawiera szczegółowe informacje na temat wydarzenia)
    public void onLoaded(List<EventDescription> eventList) {

        for (EventDescription event : eventList) { //pętla wywoływana dla każdego obiektu event klasy EventDescription
            //czyli dla każdego wydarzenia otrzymanego za pomocą JSON-a z serwera

            HashMap<String, String> map = new HashMap<>(); //stworzenie nowej tablicy klasy HashMap

            //wczytanie do tablicy informacji na temat wydarzenia, informacje pobierane z obiektu event za pomocą
            //zdefiniowanych w klasie EventDescription i przypisywane są im klucze zdefiniowane wyżej
            map.put(KEY_DAY, event.getDay());
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

            //dodanie informacji na temat wydarzenia do HashMapy mEventMapList przechowującej informacje
            //o wszystkich przesłanych wydarzeniach
            mEventMapList.add(map);
        }
        //załadowanie listy
        loadListView();
    }

    @Override //metoda wywołana przy błędzie wcztywania danych do listy
    public void onError() {

        Toast.makeText(this, "Error !", Toast.LENGTH_SHORT).show();
    }

    @Override //metoda wywoływana po kliknięciu w wydarzenie z list
    //licznik i zawiera informację o tym, które wydarzenie zostało kliknięte
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        //utworzenie nowego okna klasy DescriptionActivity (opis wybranego wydarzenia)
        Intent intent = new Intent(this, DescriptionActivity.class);
        //przesłanie do nowego okna informacji na temat klikniętego wydarzenia
        //argumenty: nazwia zmiennej oraz jej wartość wczytana z HashMapy przechowującej informacje na temat wydarzeń
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

    //metoda powodująca wczytanie listy wydarzeń
    private void loadListView() {
        //definicja adaptera listy, którego zadaniem jest powiązanie listy z layoutem
        ListAdapter adapter = new SimpleAdapter(MainActivity.this, mEventMapList, R.layout.list_item,
                //utworzenie tablicy z kluczami dostępu do informacji o wydarzeniach
                new String[] { KEY_DAY, KEY_HOUR, KEY_CITY, KEY_CITYBLOCK, KEY_DESCRIPTION, KEY_NAME },
                //utworzenie tablicy przechowującej identyfikatory pól tekstowych, do których zostaną
                //wczytane informacje o wydarzeniu
                new int[] { R.id.day,R.id.hour, R.id.city, R.id.cityBlock, R.id.description, R.id.name });

        mListView.setAdapter(adapter);

    }

    //metoda wywoływana po kliknięciu na przycisk "Odśwież" w głównym oknie aplikacji
    public void Refresh(View v) {
        //wyczyszczenie listy wydarzeń
        mListView.setAdapter(null);
        mEventMapList.clear();
        //rozłączenie z klientem lokalizacji i ponowne połączenie (w celu odświeżenia lokalizacji)
        mGoogleApiClient.disconnect();
        mGoogleApiClient.connect();
        //wysłanie za pomocą metody GET danych o lokalizacji użytkownika i promieniu poszukiwań do
        //skryptu php, którego zadaniem jest przetworzenie ich i odesłanie wydarzeń będących
        //w promieniu
        new LoadJSONTask(this).execute("http://www.projektgrupowy.cba.pl/datacompute.php?longitude=" + longitude + "&latitude=" + latitude + "&searchRadius=" + searchRadius);
    }

    //metoda wywoływana po zmianie wartości suwaka
    public void onProgressChanged(SeekBar searchRadiusSeekBar, int progress,
                                  boolean fromUser) {
        searchRadius = progress+1; //przypisanie wartości z paska promieniowi poszukiwań
        //jest ona zwiększona o 1, bo wartości suwaka rozpoczynają się od 0, najmniejszy
        //jaki może ustawić użytkownik to 1
        //konwersja promienia do Stringa i wstawienie go do pola tekstowego
        String searchRadiusString=Double.toString(searchRadius);
        searchRadiusText.setText(searchRadiusString);
    }
    //pozostałe metody obsługujące pasek, których definicja jest wymagana do kompilacji kodu
    public void onStartTrackingTouch(SeekBar seekBar) {
    }
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}

