package mshultz.charpel.rstead.bgoff.paintingapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.app.DialogFragment;
import android.content.res.Configuration;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetBehavior;
//import android.support.v4.app.DialogFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements SliderDialogue.SliderDialogueListener {
    DialogFragment dialogue;
    private PaintView paintView;
    private SeekBar redSlider, greenSlider, blueSlider;
    private int redValue = 255, greenValue = 255, blueValue = 255;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        paintView = (PaintView) findViewById(R.id.signature_canvas);
    }

    public void clearCanvas(View view) {
        paintView.clearCanvas();
    }

    public void setEraser(View view) {
        paintView.setEraser();
    }

    public void setBrushShape(View view) {
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.lips);
        paintView.setBrushImage(image, 20);
    }

    public void setColor(View view) {
        dialogue = new SliderDialogue();
        dialogue.show(getFragmentManager(), "Hello");
    }


    public void setBrushSize(View view) {
        paintView.setBrushSize(15f);
    }
    public void saveToGallery(View view){
        paintView.save(getContentResolver());
    }
    @Override
    public void onOKClick(DialogFragment dialog) {
        redValue = ((SliderDialogue) dialogue).getRedValue();
        blueValue = ((SliderDialogue) dialogue).getBlueValue();
        greenValue = ((SliderDialogue) dialogue).getGreenValue();
        paintView.setColor(255, redValue, greenValue, blueValue);
    }

}
