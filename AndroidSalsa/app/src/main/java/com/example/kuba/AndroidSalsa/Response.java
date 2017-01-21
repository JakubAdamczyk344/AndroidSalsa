package com.example.kuba.AndroidSalsa;

import java.util.ArrayList;
import java.util.List;

public class Response {
    //definicja listy przechowującej informacje o wydarzeniach
    private List<EventDescription> event = new ArrayList<EventDescription>();
    //getter do pobierania wydarzenia
    public List<EventDescription> getEvent() {
        return event;
    }
}
