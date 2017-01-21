package com.example.kuba.AndroidSalsa;

import android.content.Intent;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

public class DescriptionActivity extends AppCompatActivity {

    //inicjalizacja zmiennych odpowiedzialnych za przechowywanie danych o wydarzeniu
    private String day;
    private String hour;
    private String city;
    private String cityBlock;
    private String description;
    private String name;
    private String toPay;
    private String adress;
    private String administratorName;
    private String telephoneNumber;
    private String email;
    private String keywords;

    //metoda wywoływana przy utworzeniu obiektu klasy DescriptionActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description); //powiązanie obiektu klasy DescriptionActivity
        //z plikiem activity_description przechowującym layout okna zawierającego informacje o wybranym wydarzeniu

        Bundle bundle = getIntent().getExtras(); //zaczerpnięcie danych z obiektu bundle klasy Bundle
        //pliki bundle są plikami przechowującymi zmienne lokalne

        //przypisanie zmiennym wartości zmiennych o tych samych nazwach zapisanych w bundle
        day = bundle.getString("day");
        hour = bundle.getString("hour");
        city = bundle.getString("city");
        cityBlock = bundle.getString("cityBlock");
        description = bundle.getString("description");
        name = bundle.getString("name");
        toPay = bundle.getString("toPay");
        adress = bundle.getString("adress");
        administratorName = bundle.getString("administratorName");
        telephoneNumber = bundle.getString("telephoneNumber");
        email = bundle.getString("email");
        keywords = bundle.getString("keywords");

        //powiązanie obiektów klasy pole tekstowe zdefiniowanym w tej klasie z polami tekstowymi z layoutu activity_description
        TextView dayTextView = (TextView) findViewById(R.id.dayTextView);
        TextView hourTextView = (TextView) findViewById(R.id.hourTextView);
        TextView cityTextView = (TextView) findViewById(R.id.cityTextView);
        TextView cityBlockTextView = (TextView) findViewById(R.id.cityBlockTextView);
        TextView descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
        TextView nameTextView = (TextView) findViewById(R.id.nameTextView);
        TextView toPayTextView = (TextView) findViewById(R.id.toPayTextView);
        TextView adressTextView = (TextView) findViewById(R.id.adressTextView);
        TextView administratorNameTextView = (TextView) findViewById(R.id.administratorNameTextView);
        TextView telephoneNumberTextView = (TextView) findViewById(R.id.telephoneNumberTextView);
        TextView emailTextView = (TextView) findViewById(R.id.emailTextView);
        TextView keywordsTextView = (TextView) findViewById(R.id.keywordsTextView);

        //przypisanie zdefiniowanym wyżej polom tekstowym wartości pobranych z obiektu bundle
        dayTextView.setText(day);
        hourTextView.setText(hour);
        cityTextView.setText(city);
        cityBlockTextView.setText(cityBlock);
        descriptionTextView.setText(description);
        nameTextView.setText(name);
        toPayTextView.setText(toPay);
        adressTextView.setText(adress);
        administratorNameTextView.setText(administratorName);
        telephoneNumberTextView.setText(telephoneNumber);
        emailTextView.setText(email);
        keywordsTextView.setText(keywords);
    }

    // Metoda dodająca wydarzenie do kalendarza
    public void addEventToCalendar(View view)
    {
        long startTime = getData(day,hour);
        long endDate = startTime + 1000 * 60 * 60 * 2; // Ustawianie wydarzenia domyslnie na 2h

        // Dokładny opis wydarzenia
        String eventDescription = "Opis: "+ description +"\nOrganizator: "+ administratorName +"\nDo zapłaty: "+toPay+"zł\nKontakt:\nAdres: "+adress+ "\nTel: "+telephoneNumber+"\nEmail: "+email;

        if (Build.VERSION.SDK_INT >= 14) {

            Intent intent = new Intent(Intent.ACTION_INSERT)
                    .setData(CalendarContract.Events.CONTENT_URI)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime)
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endDate)
                    .putExtra(CalendarContract.Events.TITLE, name)
                    .putExtra(CalendarContract.Events.DESCRIPTION, eventDescription)
                    .putExtra(CalendarContract.Events.EVENT_LOCATION, adress)
                    .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);

            startActivity(intent);
        }
    }

    // Metoda pobierająca argumenty day oraz hour (z bazy danych) zwracająca wartość long (Ilość milisekund od roku 1970)
    private long getData(String day,String hour){

        Calendar calendar = Calendar.getInstance();

        // Wybranie roku,miesiaca oraz godziny ze zmiennyc day oraz hour

        String Year = day.substring(0,4);
        String Month = day.substring(5,7);
        String Day = day.substring(8,10);

        String Hour = hour.substring(0,2);
        String Min = hour.substring(3,5);

        // Ustawianie kalendarza za pomoca petli switch poniewaz wystepowaly przeklamania przy wpisywaniu miesiaca jako int.

        switch(Integer.parseInt(Month)){

            case 1: {
                calendar.set(Integer.parseInt(Year),Calendar.JANUARY,Integer.parseInt(Day),Integer.parseInt(Hour),Integer.parseInt(Min));
                break;
            }
            case 2: {
                calendar.set(Integer.parseInt(Year), Calendar.FEBRUARY, Integer.parseInt(Day), Integer.parseInt(Hour), Integer.parseInt(Min));
                break;
            }
            case 3: {
                calendar.set(Integer.parseInt(Year), Calendar.MARCH, Integer.parseInt(Day), Integer.parseInt(Hour), Integer.parseInt(Min));
                break;
            }
            case 4: {
                calendar.set(Integer.parseInt(Year), Calendar.APRIL, Integer.parseInt(Day), Integer.parseInt(Hour), Integer.parseInt(Min));
                break;
            }
            case 5: {
                calendar.set(Integer.parseInt(Year), Calendar.MAY, Integer.parseInt(Day), Integer.parseInt(Hour), Integer.parseInt(Min));
                break;
            }
            case 6: {
                calendar.set(Integer.parseInt(Year), Calendar.JUNE, Integer.parseInt(Day), Integer.parseInt(Hour), Integer.parseInt(Min));
                break;
            }
            case 7: {
                calendar.set(Integer.parseInt(Year), Calendar.JULY, Integer.parseInt(Day), Integer.parseInt(Hour), Integer.parseInt(Min));
                break;
            }
            case 8: {
                calendar.set(Integer.parseInt(Year), Calendar.AUGUST, Integer.parseInt(Day), Integer.parseInt(Hour), Integer.parseInt(Min));
                break;
            }
            case 9: {
                calendar.set(Integer.parseInt(Year), Calendar.SEPTEMBER, Integer.parseInt(Day), Integer.parseInt(Hour), Integer.parseInt(Min));
                break;
            }
            case 10: {
                calendar.set(Integer.parseInt(Year), Calendar.OCTOBER, Integer.parseInt(Day), Integer.parseInt(Hour), Integer.parseInt(Min));
                break;
            }
            case 11: {
                calendar.set(Integer.parseInt(Year), Calendar.NOVEMBER, Integer.parseInt(Day), Integer.parseInt(Hour), Integer.parseInt(Min));
                break;
            }
            case 12: {
                calendar.set(Integer.parseInt(Year), Calendar.DECEMBER, Integer.parseInt(Day), Integer.parseInt(Hour), Integer.parseInt(Min));
                break;
            }

        }
        return calendar.getTimeInMillis();
    }
}