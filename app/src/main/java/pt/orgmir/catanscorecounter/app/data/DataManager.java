package pt.orgmir.catanscorecounter.app.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import pt.orgmir.catanscorecounter.app.data.entities.CatanGame;
import pt.orgmir.catanscorecounter.app.data.entities.CatanMaster;
import pt.orgmir.catanscorecounter.app.data.entities.CatanPlayer;

/**
 * Created by luisramos on 17/03/14. o/
 */
public class DataManager extends IDataManager {
  private static final String PREFS_NAME = "CatanPrefsFile";
  private static final String PREFS_MASTER_KEY = "CatanMasterKey";

  private Context context;
  private CatanMaster master;

  public void init(Context context){
    this.context = context;
    loadMaster(context);
  }

  public void loadMaster(Context context){
    String m = getSettings(context).getString(PREFS_MASTER_KEY, "");
    this.master = new Gson().fromJson(m, CatanMaster.class);
    if(this.master == null){
      this.master = new CatanMaster();
    }
  }

  public void saveMaster(){
    SharedPreferences.Editor editor = getSettings(context).edit();
    editor.putString(PREFS_MASTER_KEY, new Gson().toJson(master));
    editor.commit();
  }

  @Override
  public CatanPlayer getCatanPlayer(String id) {
    return master.catanPlayersMap.get(id);
  }

  @Override
  public void saveCatanPlayer(CatanPlayer player) {
    if(player.id == null || player.id.isEmpty()){
      master.playerIdCounter ++;
      player.id = master.playerIdCounter + "";
    }
    master.catanPlayersMap.put(player.id, player);
    saveMaster();
  }

  @Override
  public CatanGame getCatanGame(String id) {
    return master.catanGamesMap.get(id);
  }

  @Override
  public void saveCatanGame(CatanGame game) {
    if(game.id == null || game.id.isEmpty()){
      master.gameIdCounter ++;
      game.id = master.gameIdCounter + "";
    }
    master.catanGamesMap.put(game.id, game);
    saveMaster();
  }

  @Override
  public List<CatanGame> getCatanGames(){
    return new ArrayList<CatanGame>(master.catanGamesMap.values());
  }

  @Override
  public List<CatanPlayer> getCatanPlayers(){
    return new ArrayList<CatanPlayer>(master.catanPlayersMap.values());
  }

  // PRIVATE METHODS

  private SharedPreferences getSettings(Context context){
    return context.getSharedPreferences(PREFS_NAME, 0);
  }
}
