package pt.orgmir.catanscorecounter.app.data.entities;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by luisramos on 17/03/14. o/
 */
public class CatanMaster {

  public Integer playerIdCounter;
  public Integer gameIdCounter;

  public Map<String, CatanPlayer> catanPlayersMap;
  public Map<String, CatanGame> catanGamesMap;

  public CatanMaster(){
    this.catanPlayersMap = new HashMap<String, CatanPlayer>();
    this.catanGamesMap = new HashMap<String, CatanGame>();
    this.playerIdCounter = 0;
    this.gameIdCounter = 0;
  }

}
