package com.example.kuba.AndroidSalsa;

/**
 * Created by Kuba on 2016-11-30.
 */

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
