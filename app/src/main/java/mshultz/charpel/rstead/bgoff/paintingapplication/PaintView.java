package mshultz.charpel.rstead.bgoff.paintingapplication;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Vector;

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
    private Bitmap currentStamp;
    private float lastX;
    private float lastY;
    private ArrayList<Stroke> archivedStrokes;
    private int currentColor;
    private float currentSize;
    private ArrayList<ImageStroke> imageStrokes;
    private boolean isUsingBitmap = false;

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        imageStrokes = new ArrayList<>();
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
        imageStrokes.clear();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(ImageStroke stroke : imageStrokes){
            for(Point p : stroke.getPath()){
                canvas.drawBitmap(stroke.getBitmap(), p.x, p.y, null);
            }
        }
        for(Stroke currentStroke: archivedStrokes){
            canvas.drawPath(currentStroke.getPath(), currentStroke.getPaint());
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(isUsingBitmap){
                    imageStrokes.add(new ImageStroke(currentStamp));
                }else{
                    path.moveTo(event.getX(), event.getY());
                    lastX = event.getX();
                    lastY = event.getY();
                }

                break;
            case MotionEvent.ACTION_UP:
                path.lineTo(lastX, lastY);
                break;
            case MotionEvent.ACTION_MOVE:
                if(isUsingBitmap){
                    int width = imageStrokes.get(imageStrokes.size() - 1).getBitmap().getWidth();
                    int height = imageStrokes.get(imageStrokes.size() - 1).getBitmap().getHeight();
                    imageStrokes.get(imageStrokes.size() - 1).getPath().add(new Point((int)event.getX() - width / 2, (int)event.getY() - height / 2));
                }else{
                    float avgX = (event.getX() + lastX) / 2;
                    float avgY = (event.getY() + lastY) / 2;
                    path.quadTo(lastX, lastY, avgX, avgY);
                    lastX = event.getX();
                    lastY = event.getY();
                }

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
        isUsingBitmap = false;

    }

    public void setBrushSize(float brushSize){
        path = new Path();
        initializePainter(brushSize);
        archivedStrokes.add(new Stroke(path, painter));
        isUsingBitmap = false;
    }

    public void setBrushImage(Bitmap bitmap){
        currentStamp = bitmap;
        isUsingBitmap = true;
    }
}
