package pt.orgmir.catanscorecounter.app.game;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pt.orgmir.catanscorecounter.app.R;
import pt.orgmir.catanscorecounter.app.data.IDataManager;
import pt.orgmir.catanscorecounter.app.data.entities.CatanGame;
import pt.orgmir.catanscorecounter.app.game.fragments.DatePickerFragment;

/**
 * Created by luisramos on 18/03/14. o/
 */
public class CatanGameEditActivity extends Activity {

  private CatanGame game;

  private Button dateButton;
  private Button winnerButton;
  private Button playersButton;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_catangame_edit);


    game = new CatanGame();

    dateButton = (Button) findViewById(R.id.pick_data_button);
    winnerButton = (Button) findViewById(R.id.pick_winner_button);
    playersButton = (Button) findViewById(R.id.pick_players_button);

    setDateButtonClickListener();
    setWinnerButtonClickListener();
    setPlayersButtonClickListener();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_game_edit, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()){
      case R.id.save_game:

        IDataManager.getManager().saveCatanGame(game);
        finish();

        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void setDateButtonClickListener(){
    dateButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setListener(new DatePickerDialog.OnDateSetListener() {
          @Override
          public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar c = Calendar.getInstance();
            c.set(year, monthOfYear, dayOfMonth);
            CatanGameEditActivity.this.game.playedOn = c.getTime();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            CatanGameEditActivity.this.dateButton.setText("Game Date: " + dateFormat.format(CatanGameEditActivity.this.game.playedOn));
          }
        });
        newFragment.show(getFragmentManager(), "datePicker");
      }
    });
  }

  private void setWinnerButtonClickListener(){

  }

  private void setPlayersButtonClickListener(){

  }

}
