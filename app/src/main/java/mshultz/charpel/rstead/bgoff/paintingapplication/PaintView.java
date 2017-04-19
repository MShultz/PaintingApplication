package mshultz.charpel.rstead.bgoff.paintingapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Mary on 4/19/2017.
 */

public class PaintView extends View {

    private Context context;
    private Paint painter;
    private Path path;

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.setContext(context);
        path = new Path();

        painter = new Paint();
        painter.setAntiAlias(true);
        painter.setColor(Color.BLACK);
        painter.setStrokeJoin(Paint.Join.ROUND);
        painter.setStyle(Paint.Style.STROKE);
        painter.setStrokeWidth(5f);
    }


    public void clearCanvas(){

    }
    @Override
    protected void onDraw(Canvas canvas){

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        return false;
    }

    private void setContext(Context context){
        this.context = context;
    }
}
