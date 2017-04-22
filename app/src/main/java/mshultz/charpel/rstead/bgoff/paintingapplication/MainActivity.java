package mshultz.charpel.rstead.bgoff.paintingapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.app.DialogFragment;
import android.content.res.Configuration;
import android.graphics.Color;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements SliderDialogue.SliderDialogueListener, BrushPropsDialogue.BrushPropsListener {
    private final String PACKAGE_NAME = "mshultz.charpel.rstead.bgoff";
    DialogFragment dialogue;
    private PaintView paintView;
    private PreferenceHandler preferenceHandler;
    private int brushType;
    private int redValue = 255, greenValue = 255, blueValue = 255;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        paintView = (PaintView) findViewById(R.id.signature_canvas);
        preferenceHandler = new PreferenceHandler(this.getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE));
    }

    public void clearCanvas(View view) {
        paintView.clearCanvas();
    }

    public void setEraser(View view) {
        paintView.setEraser();
    }

    public void onShapeClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.lips:
                brushType = R.drawable.lips;
                break;
            case R.id.filledStar:
                brushType = R.drawable.black_star;
                break;
            case R.id.emptyStar:
                brushType = R.drawable.open_star;
                break;
            case R.id.heart:
                brushType = R.drawable.filled_heart;
                break;
            case R.id.defaultBrush:
                brushType = 0;
                break;
            default:
                throw new IllegalArgumentException("NO SUCH ID!");
        }
    }

    public void setBrushShape(int id) {
        if (id != 0) {
            Bitmap image = BitmapFactory.decodeResource(getResources(), id);
            paintView.setBrushImage(image);
            paintView.setUsingBitmap(true);
        } else {
            paintView.setUsingBitmap(false);
        }
    }


    public void onSaveClick(View view) {
        /*Toast.makeText(this, "Chris doesn't have this button implemented yet!", Toast.LENGTH_SHORT).show();*/
        paintView.save(getContentResolver());
    }

    public void setColor(View view) {
        dialogue = new SliderDialogue();
        dialogue.show(getFragmentManager(), "ColorProps");
    }

    public void setBrushSize(View view) {
        dialogue = new BrushPropsDialogue();
        dialogue.show(getFragmentManager(), "BrushProps");
    }
    public void savePref(View view) {
        //Get Color
        // boolean success = preferenceHandler.addColor(//Color you got);
        //if sucessful
        //Update shared pref.
        //ArrayList<Color> favoriteColors = preferenceHandler.getFavorites();
        //Otherwise
        //Toast to failure
    }

    @Override
    public void onColorOkClick(DialogFragment dialog) {
        redValue = ((SliderDialogue) dialogue).getRedValue();
        blueValue = ((SliderDialogue) dialogue).getBlueValue();
        greenValue = ((SliderDialogue) dialogue).getGreenValue();
        paintView.setColor(255, redValue, greenValue, blueValue);
    }

    @Override
    public void onBrushPropOKClick(DialogFragment dialog) {
        paintView.setBrushSize(((BrushPropsDialogue) dialogue).getBrushSize());
        setBrushShape(brushType);
    }
}
