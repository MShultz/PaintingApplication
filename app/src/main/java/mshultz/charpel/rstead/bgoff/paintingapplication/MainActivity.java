package mshultz.charpel.rstead.bgoff.paintingapplication;

import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private PaintView paintView;
	private BottomSheetBehavior bottomSheetBehavior;    private SeekBar redSlider,greenSlider,blueSlider;
    private int redValue=255,greenValue=255,blueValue=255;    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View bottomSheet = findViewById(R.id.bottomDrawer);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        //bottomSheetBehavior.setPeekHeight(150);
        //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        paintView = (PaintView)findViewById(R.id.signature_canvas);

       // setRGBSlider(redSlider,R.id.Red);
        //setRGBSlider(greenSlider,R.id.Green);
        //setRGBSlider(blueSlider,R.id.Blue);
    }
    /*private void setRGBSlider(SeekBar seekBar,int Id){
        seekBar=(SeekBar)findViewById(Id);
        seekBar.setMax(255);
        seekBar.setProgress(255);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateColor(seekBar,progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    public void updateColor(SeekBar seekBar,int progress){
        switch (seekBar.getId()){
            case R.id.Red:
                redValue=progress;
                break;
            case R.id.Green:
                greenValue=progress;
                break;
            case R.id.Blue:
                blueValue=progress;
                break;
            default:
                Log.e("Invalid Slider","Update Color");
        }
        paintView.setColor(255,redValue,greenValue,blueValue);
    }*/
    public void clearCanvas(View view){
        paintView.clearCanvas();
    }
    public void setColor(View view){ paintView.setColor(255, 255, 219,225);}
    public void setBrushSize(View view){paintView.setBrushSize(15f);}

    public void openDrawer(View view){
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
    public void closeDrawer(View view){
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

}
