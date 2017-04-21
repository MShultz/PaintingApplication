package mshultz.charpel.rstead.bgoff.paintingapplication;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

/**
 * Created by Ryan on 4/20/2017.
 */

public class SliderDialogue extends DialogFragment {

    public interface SliderDialogueListener{
        public void onColorOkClick(DialogFragment dialog);
    }


    private SeekBar redSlider;
    private SeekBar blueSlider;
    private SeekBar greenSlider;

    public int redValue = 128;

    public int getRedValue() {
        return redValue;
    }

    public int getBlueValue() {
        return blueValue;
    }

    public int getGreenValue() {
        return greenValue;
    }

    public int blueValue = 128;
    public int greenValue = 128;

    private View activity;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater

        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        activity = inflater.inflate(R.layout.slide_dialogue, null);
        builder.setView(activity)
                // Add action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    sdListener.onColorOkClick(SliderDialogue.this);
                    }
                });

        setRGBSlider(redSlider,R.id.Red);
        setRGBSlider(greenSlider,R.id.Green);
        setRGBSlider(blueSlider,R.id.Blue);
        ((ImageView)activity.findViewById(R.id.imageView)).setColorFilter(Color.parseColor(String.format("#%02x%02x%02x", 128, 128, 128)));

        return builder.create();
    }

    private void setRGBSlider(SeekBar seekBar, int Id){
        seekBar=(SeekBar)activity.findViewById(Id);
        seekBar.setMax(255);
        seekBar.setProgress(128);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateColor(seekBar,progress);
                ((ImageView)activity.findViewById(R.id.imageView)).setColorFilter(Color.parseColor(String.format("#%02x%02x%02x", redValue, greenValue, blueValue)));
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
    }

    SliderDialogueListener sdListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            sdListener = (SliderDialogueListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
