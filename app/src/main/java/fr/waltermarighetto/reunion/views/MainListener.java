package fr.waltermarighetto.reunion.views;

import java.time.LocalDate;
import java.util.ArrayList;

public interface MainListener {
    void onFiltersUpdated(ArrayList<String> roomNames, LocalDate date);
}
