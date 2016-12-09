package com.example.kuba.lokalizacja_01_11_2016;

/**
 * Created by Kuba on 2016-11-30.
 */

import java.util.ArrayList;
import java.util.List;

public class Response {

    private List<eventDescription> event = new ArrayList<eventDescription>();

    public List<eventDescription> getEvent() {
        return event;
    }
}
