package pt.orgmir.catanscorecounter.app.game.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by luisramos on 18/03/14. o/
 */
public class DatePickerFragment extends DialogFragment
    implements DatePickerDialog.OnDateSetListener {

  public int year = -1, month = -1, day = -1;

  public DatePickerDialog.OnDateSetListener externalListener;

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    if(year == -1) {
      final Calendar c = Calendar.getInstance();
      year = c.get(Calendar.YEAR);
      month = c.get(Calendar.MONTH);
      day = c.get(Calendar.DAY_OF_MONTH);
    }

    return new DatePickerDialog(getActivity(), this, year, month, day);
  }

  public void onDateSet(DatePicker view, int year, int month, int day) {
    if(externalListener != null){
      externalListener.onDateSet(view, year, month, day);
    }
  }

  public void setListener(DatePickerDialog.OnDateSetListener listener){
    this.externalListener = listener;
  }
}
