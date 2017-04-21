package mshultz.charpel.rstead.bgoff.paintingapplication;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.provider.ContactsContract;
import android.provider.MediaStore;

import android.graphics.Point;
import android.media.Image;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


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
    private ArrayList<Paintable> archivedStrokes;
    private int currentColor;
    private float currentSize;
    private int backgroundColor;
    private boolean isUsingBitmap = false;

    public void setUsingBitmap(boolean input){
        isUsingBitmap = input;
    }

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
        isUsingBitmap = false;
        archivedStrokes = new ArrayList<>();
        archivedStrokes.add(new Stroke(path, painter));
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(Paintable stroke : archivedStrokes){
            stroke.paintStroke(canvas);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(isUsingBitmap){
                    archivedStrokes.add(new ImageStroke(currentStamp));
                    addPointToBitmapStroke((int)event.getX(), (int)event.getY());
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
                    addPointToBitmapStroke((int)event.getX(), (int)event.getY());
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

    private void addPointToBitmapStroke(int x, int y){
        int width = ((ImageStroke)archivedStrokes.get(archivedStrokes.size() - 1)).getBitmap().getWidth();
        int height = ((ImageStroke)archivedStrokes.get(archivedStrokes.size() - 1)).getBitmap().getHeight();
        ((ImageStroke)archivedStrokes.get(archivedStrokes.size() - 1)).getPath().add(new Point(x - width / 2, y - height / 2));
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

    public void setBrushImage(Bitmap bitmap, int dimension){
        currentStamp = Bitmap.createScaledBitmap(bitmap, dimension, dimension, false);
        isUsingBitmap = true;
    }

    public void setEraser(){
        path = new Path();
        initializePainter(backgroundColor);
        archivedStrokes.add(new Stroke(path, painter));
    }
    public void save(ContentResolver resolver){
        canvas.save();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        Date date = new Date();
        String test= MediaStore.Images.Media.insertImage(resolver,bitmap, dateFormat.format(date), "From Paint app");
        if(test!=null){
            Toast.makeText(getContext(),"Save Success",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getContext(),"Save Failure",Toast.LENGTH_LONG).show();
        }
//        File file=new File("/storage/emulated/0/Pictures/",dateFormat.format(date)+".jpg");
//        FileOutputStream out=null;
//        try {
//            out = new FileOutputStream(file);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
//            Toast.makeText(getContext(),"Save Sucess",Toast.LENGTH_LONG).show();
//        } catch (Exception e) {
//            Toast.makeText(getContext(),"Save Failure",Toast.LENGTH_LONG).show();
//            e.printStackTrace();
//        } finally {
//            try {
//                if (out != null) {
//                    out.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                Toast.makeText(getContext(),"Save Failure",Toast.LENGTH_LONG).show();
//            }
//        }


    }
}
