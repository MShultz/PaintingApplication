package mshultz.charpel.rstead.bgoff.paintingapplication;

import android.graphics.Bitmap;
import android.graphics.Point;

import java.util.ArrayList;

/**
 * Created by Ben Goff on 4/20/2017.
 */

public class ImageStroke {
    private ArrayList<Point> path;
    private Bitmap bitmap;

    public ImageStroke(Bitmap map){
        path = new ArrayList<>();
        this.bitmap = map;
    }

    public ArrayList<Point> getPath(){
        return path;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }
}
