package com.example.kuba.AndroidSalsa;

public class EventDescription {
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

    //definicja getterów zwracających dane na temat wydarzenia
    public String getDay() {
        return day;
    }

    public String getHour() {
        return hour;
    }

    public String getCity() {
        return city;
    }

    public String getCityBlock() {
        return cityBlock;
    }

    public String getDescription() {
        return description;
    }

    public String getName(){
        return name;
    }

    public String getToPay(){
        return toPay;
    }

    public String getAdress(){
        return adress;
    }

    public String getAdministratorName(){
        return administratorName;
    }

    public String getTelephoneNumber(){
        return telephoneNumber;
    }

    public String getEmail(){
        return email;
    }

    public String getKeywords(){
        return keywords;
    }
}
