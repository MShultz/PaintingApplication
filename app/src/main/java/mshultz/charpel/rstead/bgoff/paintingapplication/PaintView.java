package mshultz.charpel.rstead.bgoff.paintingapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Mary on 4/19/2017.
 */

public class PaintView extends View {

    private final float DEFAULT_BRUSH_SIZE = 4f;
    private final int BIT_HEIGHT = 1700;
    private final int BIT_WIDTH = 1080;
    private Context context;
    private Paint painter;
    private Path path;
    private Bitmap bitmap;
    private Canvas canvas;
    private float lastX;
    private float lastY;
    private ArrayList<Stroke> archivedStrokes;
    private int currentColor;
    private float currentSize;
    private int backgroundColor;

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        backgroundColor = Color.WHITE;
        currentColor = Color.BLACK;
        currentSize = DEFAULT_BRUSH_SIZE;
        archivedStrokes = new ArrayList<>();
        this.setContext(context);
        path = new Path();
        initializePainter();
        initializeCanvas();
        archivedStrokes.add(new Stroke(path, painter));
    }


    public void clearCanvas() {
        path.reset();
        archivedStrokes = new ArrayList<>();
        archivedStrokes.add(new Stroke(path, painter));
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(Stroke currentStroke: archivedStrokes){
            canvas.drawPath(currentStroke.getPath(), currentStroke.getPaint());
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(event.getX(), event.getY());
                lastX = event.getX();
                lastY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                path.lineTo(lastX, lastY);
                break;
            case MotionEvent.ACTION_MOVE:
                float avgX = (event.getX() + lastX) / 2;
                float avgY = (event.getY() + lastY) / 2;
                path.quadTo(lastX, lastY, avgX, avgY);
                lastX = event.getX();
                lastY = event.getY();
                break;
        }
        invalidate();
        return true;
    }

    private void setContext(Context context) {
        this.context = context;
    }

    private void initializePainter() {
        painter = new Paint();
        painter.setAntiAlias(true);
        painter.setColor(currentColor);
        painter.setStrokeJoin(Paint.Join.ROUND);
        painter.setStyle(Paint.Style.STROKE);
        painter.setStrokeWidth(currentSize);
    }
    private void initializePainter(int color) {
        this.currentColor = color;
        painter = new Paint();
        painter.setAntiAlias(true);
        painter.setColor(color);
        painter.setStrokeJoin(Paint.Join.ROUND);
        painter.setStyle(Paint.Style.STROKE);
        painter.setStrokeWidth(currentSize);
    }

    private void initializePainter(float brushSize) {
        this.currentSize = brushSize;
        painter = new Paint();
        painter.setAntiAlias(true);
        painter.setColor(currentColor);
        painter.setStrokeJoin(Paint.Join.ROUND);
        painter.setStyle(Paint.Style.STROKE);
        painter.setStrokeWidth(currentSize);
    }

    private void initializeCanvas() {
        bitmap = Bitmap.createBitmap(BIT_WIDTH, BIT_HEIGHT, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }

    public void setColor(int a, int r, int g, int b) {
        path = new Path();
        initializePainter(Color.argb(a, r, g, b));
        archivedStrokes.add(new Stroke(path, painter));

    }

    public void setBrushSize(float brushSize){
        path = new Path();
        initializePainter(brushSize);
        archivedStrokes.add(new Stroke(path, painter));
    }

    public void setEraser(){
        path = new Path();
        initializePainter(backgroundColor);
        archivedStrokes.add(new Stroke(path, painter));
    }
}
