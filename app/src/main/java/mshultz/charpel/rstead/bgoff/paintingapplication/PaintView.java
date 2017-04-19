package mshultz.charpel.rstead.bgoff.paintingapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Mary on 4/19/2017.
 */

public class PaintView extends View {

    private final int BIT_HEIGHT = 1700;
    private final int BIT_WIDTH = 1080;
    private Context context;
    private Paint painter;
    private Path path;
    private Bitmap bitmap;
    private Canvas canvas;

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setContext(context);
        path = new Path();

        initializePainter();
        initializeCanvas();
    }


    public void clearCanvas() {
        path.reset();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, painter);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        path.moveTo(event.getX(), event.getY());
        invalidate();
        return true;
    }

    private void setContext(Context context) {
        this.context = context;
    }

    private void initializePainter() {
        painter = new Paint();
        painter.setAntiAlias(true);
        painter.setColor(Color.BLACK);
        painter.setStrokeJoin(Paint.Join.ROUND);
        painter.setStyle(Paint.Style.STROKE);
        painter.setStrokeWidth(5f);
    }

    private void initializeCanvas() {
        bitmap = Bitmap.createBitmap(BIT_WIDTH, BIT_HEIGHT, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }
}
