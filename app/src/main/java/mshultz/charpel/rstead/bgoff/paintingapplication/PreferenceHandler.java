package mshultz.charpel.rstead.bgoff.paintingapplication;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Mary on 4/21/2017.
 */

public class PreferenceHandler {

    private final String COLOR_QUERY = "Colors";
    private SharedPreferences sharedPreferences;
    private ArrayList<Color> favoriteColors;

    public PreferenceHandler(SharedPreferences sharedPreferences) {
        this.setPreference(sharedPreferences);
        setColorList();
    }

    private void setPreference(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    private void setColorList(){
        try {
            favoriteColors = (ArrayList<Color>) ObjectSerializer.deserialize(sharedPreferences.getString(COLOR_QUERY,ObjectSerializer.serialize(new ArrayList<Color>())));
            if(favoriteColors == null)
                favoriteColors = new ArrayList<>();
        } catch (IOException e) {
            Log.e("ReadingPreference", e.toString());
        }
    }

    public int getNumColors(){
        return favoriteColors.size();
    }

    public boolean addColor(Color currentColor){
        favoriteColors.add(currentColor);
       boolean success = true;
        try{
            sharedPreferences.edit().putString(COLOR_QUERY, ObjectSerializer.serialize(favoriteColors)).apply();
        }catch(IOException e){
            Log.e("WritingPreference", e.toString());
            success = false;
        }
        return success;
    }

    public ArrayList<Color> getFavorites(){
        return favoriteColors;
    }

}
