package mshultz.charpel.rstead.bgoff.paintingapplication;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Mary on 4/21/2017.
 */

public class PreferenceHandler {

    private final String COLOR_QUERY = "Colors";
    private SharedPreferences sharedPreferences;
    private ArrayList<Integer> favoriteColors;

    public PreferenceHandler(SharedPreferences sharedPreferences) {
        this.setPreference(sharedPreferences);
        setColorList();
    }

    private void setPreference(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    private void setColorList(){
        try {
            favoriteColors = (ArrayList<Integer>) ObjectSerializer.deserialize(sharedPreferences.getString(COLOR_QUERY,ObjectSerializer.serialize(new ArrayList<Integer>())));
            if(favoriteColors == null)
                favoriteColors = new ArrayList<>();
        } catch (IOException e) {
            Log.e("ReadingPreference", e.toString());
        }
    }

    public int getNumColors(){
        return favoriteColors.size();
    }

    public boolean addColor(Integer currentColor){
        favoriteColors.add(currentColor);
        return writeList();
    }
    public boolean removeColor(){
        favoriteColors.remove(favoriteColors.size()-1);
        return writeList();
    }
    public ArrayList<Integer> getFavorites(){
        return favoriteColors;
    }
    private boolean writeList(){
        boolean success = true;
        try{
            sharedPreferences.edit().putString(COLOR_QUERY, ObjectSerializer.serialize(favoriteColors)).apply();
        }catch(IOException e){
            Log.e("WritingPreference", e.toString());
            success = false;
        }
        return success;
    }
}
