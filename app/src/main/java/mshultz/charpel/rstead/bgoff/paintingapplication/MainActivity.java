package mshultz.charpel.rstead.bgoff.paintingapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private PaintView paintView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paintView = (PaintView)findViewById(R.id.signature_canvas);

    }

    public void clearCanvas(View view){
        paintView.clearCanvas();
    }
    public void setColor(View view){ paintView.setColor(255, 255, 219,225);}
    public void setBrushSize(View view){paintView.setBrushSize(15f);}
}
