package mshultz.charpel.rstead.bgoff.paintingapplication;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by Ryan on 4/21/2017.
 */

public class BrushPropsDialogue extends DialogFragment {

    public interface BrushPropsListener {
        public void onBrushPropOKClick(DialogFragment dialog);
    }

    private BrushPropsListener bpListener;
    private SeekBar sizeSlider;
    private float brushSize = 5f;
    private View activity;

    public float getBrushSize() {
        return brushSize;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        activity = inflater.inflate(R.layout.brush_prop_dialogue, null);
        builder.setView(activity)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        bpListener.onBrushPropOKClick(BrushPropsDialogue.this);
                    }
                });
        sizeSlider = (SeekBar) activity.findViewById(R.id.sizeBar);
        sizeSlider.setMax(49);
        sizeSlider.setProgress(5);
        sizeSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                brushSize = progress + 1;
                ((TextView) activity.findViewById(R.id.sizeIndic)).setText("Current size: " + brushSize + "px");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            bpListener = (BrushPropsListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}

