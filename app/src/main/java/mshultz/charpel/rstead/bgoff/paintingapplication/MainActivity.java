package mshultz.charpel.rstead.bgoff.paintingapplication;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;

@TargetApi(25)
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
                paintView.setColor(255, 0, 0, 0);
                break;
            case R.id.eraser:
                brushType = 0;
                paintView.setEraser();
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

    public void onSaveColorClick(View view) {
        ColorDrawable saveColor = (ColorDrawable) ((ImageView) ((SliderDialogue) dialogue).getView(R.id.colorPrev)).getBackground();
        ImageView pref = (ImageView) (((SliderDialogue) dialogue).getView(R.id.pref1 + (preferenceHandler.getNumColors())));
        if (preferenceHandler.getNumColors() < 8) {
            pref.setBackgroundColor(saveColor.getColor());
            preferenceHandler.addColor(saveColor.getColor());
        } else {
            Toast.makeText(this, "Max Number of Favorites Added", Toast.LENGTH_SHORT).show();
        }
    }

    public void onDelColorClick(View view) {
        if (preferenceHandler.getNumColors() > 0) {
            ImageView pref = (ImageView) (((SliderDialogue) dialogue).getView(R.id.pref1 + (preferenceHandler.getNumColors() - 1)));
            pref.setBackgroundColor(0);
            preferenceHandler.removeColor();
        }
    }

    public void setColor(View view) throws InterruptedException {
        dialogue = new SliderDialogue();
        dialogue.show(getFragmentManager(), "ColorProps");
    }

    public void setFavorites(View view) {
        ArrayList<Integer> favoriteColors = preferenceHandler.getFavorites();
        for (int i = 0; i < favoriteColors.size() && preferenceHandler.getNumColors() > 0; i++) {
            ((ImageView) ((SliderDialogue) dialogue).getView(R.id.pref1 + i)).setBackgroundColor(favoriteColors.get(i));
        }
    }

    public void setBrushSize(View view) {
        dialogue = new BrushPropsDialogue();
        dialogue.show(getFragmentManager(), "BrushProps");
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

    public void onFavoriteClick(View view) {
        int color = ((ColorDrawable) view.getBackground()).getColor();
        setSliderValues(color);
        setSliderProgress();
        ((SliderDialogue) dialogue).setPreview(color);
    }

    private void setSliderValues(int color) {
        ((SliderDialogue) dialogue).setRedValue((color >> 16) & 0xFF);
        ((SliderDialogue) dialogue).setGreenValue((color >> 8) & 0xFF);
        ((SliderDialogue) dialogue).setBlueValue((color >> 0) & 0xFF);
    }

    private void setSliderProgress() {
        ((SeekBar) ((SliderDialogue) dialogue).getView(R.id.Red)).setProgress(((SliderDialogue) dialogue).getRedValue());
        ((SeekBar) ((SliderDialogue) dialogue).getView(R.id.Blue)).setProgress(((SliderDialogue) dialogue).getBlueValue());
        ((SeekBar) ((SliderDialogue) dialogue).getView(R.id.Green)).setProgress(((SliderDialogue) dialogue).getGreenValue());

    }
}
