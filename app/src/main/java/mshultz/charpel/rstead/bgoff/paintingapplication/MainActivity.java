package mshultz.charpel.rstead.bgoff.paintingapplication;

import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private PaintView paintView;
    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View bottomSheet = findViewById(R.id.bottomDrawer);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        //bottomSheetBehavior.setPeekHeight(150);
        //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        paintView = (PaintView)findViewById(R.id.signature_canvas);

    }

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
