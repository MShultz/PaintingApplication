package mshultz.charpel.rstead.bgoff.paintingapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import java.util.ArrayList;

/**
 * Created by Ben Goff on 4/20/2017.
 */

public class ImageStroke implements Paintable{
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

    @Override
    public void paintStroke(Canvas canvas) {
        for(Point p : getPath()){
            canvas.drawBitmap(getBitmap(), p.x, p.y, null);
        }
    }
}
