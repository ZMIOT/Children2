package utils;

import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import static com.marshalchen.ultimaterecyclerview.expanx.LinearExpanxURVAdapter.TAG;

public class MyXFormatter implements IAxisValueFormatter {
    private String[] mValues;

    public MyXFormatter(String[] mValues) {
        this.mValues = mValues;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
        Log.d(TAG, "----->getFormattedValue: " + value);
        return mValues[(int) value % mValues.length];
    }
}
