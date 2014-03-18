package pt.orgmir.catanscorecounter.app.player;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.List;

import pt.orgmir.catanscorecounter.app.R;
import pt.orgmir.catanscorecounter.app.data.IDataManager;
import pt.orgmir.catanscorecounter.app.data.entities.CatanPlayer;

/**
 * Created by luisramos on 18/03/14. o/
 */
public class CatanPlayerListActivity extends ListActivity {

  private List<CatanPlayer> playerList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(android.R.layout.list_content);

    ListView list = (ListView) findViewById(android.R.id.list);
    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CatanPlayer player = playerList.get(position);
        editPlayer(player);
      }
    });
  }

  @Override
  protected void onResume() {
    super.onResume();

    playerList = IDataManager.getManager().getCatanPlayers();
    setListAdapter(new ArrayAdapter<CatanPlayer>(this,
        android.R.layout.simple_list_item_activated_1,
        android.R.id.text1, playerList));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_player_edit, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()){
      case R.id.new_player:
        editPlayer(null);
        break;

      default:
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  private void editPlayer(CatanPlayer player){
    Intent intent = new Intent(CatanPlayerListActivity.this, CatanPlayerEditActivity.class);
    if(player != null) {
      intent.putExtra("catanPlayer", new Gson().toJson(player));
    }
    startActivity(intent);
  }
}
