package pt.orgmir.catanscorecounter.app.player;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import pt.orgmir.catanscorecounter.app.R;
import pt.orgmir.catanscorecounter.app.data.IDataManager;
import pt.orgmir.catanscorecounter.app.data.entities.CatanPlayer;

/**
 * Created by luisramos on 18/03/14. o/
 */
public class CatanPlayerEditActivity extends Activity {

  CatanPlayer player;

  EditText editText;
  Button saveButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_catanplayer_edit);

    String playerString = getIntent().getStringExtra("catanPlayer");
    if(playerString != null) {
      player = new Gson().fromJson(playerString, CatanPlayer.class);
    }else{
      player = new CatanPlayer();
    }

    editText = (EditText) findViewById(R.id.edit_text);
    saveButton = (Button) findViewById(R.id.save_button);

    saveButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        player.name = editText.getText().toString();

        IDataManager.getManager().saveCatanPlayer(player);

        finish();
      }
    });
  }
}
