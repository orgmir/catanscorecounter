package pt.orgmir.catanscorecounter.app.data;

import android.content.Context;

import java.util.List;

import pt.orgmir.catanscorecounter.app.data.entities.CatanGame;
import pt.orgmir.catanscorecounter.app.data.entities.CatanPlayer;

/**
 * Created by luisramos on 18/03/14. o/
 */
public abstract class IDataManager {

  protected static DataManager sharedManager;
  protected IDataManager(){} //Singleton

  public static IDataManager getManager(){
    if(sharedManager == null){
      sharedManager = new DataManager();
    }
    return sharedManager;
  }

  public abstract void init(Context context);

  public abstract CatanPlayer getCatanPlayer(String id);
  public abstract void saveCatanPlayer(CatanPlayer player);

  public abstract CatanGame getCatanGame(String id);
  public abstract void saveCatanGame(CatanGame game);

  public abstract List<CatanGame> getCatanGames();
  public abstract List<CatanPlayer> getCatanPlayers();
}
