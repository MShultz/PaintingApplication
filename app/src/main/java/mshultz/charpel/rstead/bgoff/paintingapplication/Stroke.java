package mshultz.charpel.rstead.bgoff.paintingapplication;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by Mary on 4/19/2017.
 */

public class Stroke implements Paintable {
    Path path;
    Paint paint;

    public Stroke(Path path, Paint paint) {
        this.setPath(path);
        this.setPaint(paint);
    }

    private void setPath(Path path) {
        this.path = path;
    }

    private void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Path getPath() {
        return path;
    }

    public Paint getPaint() {return paint; }

    @Override
    public void paintStroke(Canvas canvas) {
        canvas.drawPath(getPath(), getPaint());
    }
}
