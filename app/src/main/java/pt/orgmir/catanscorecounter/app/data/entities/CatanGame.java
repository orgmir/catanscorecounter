package pt.orgmir.catanscorecounter.app.data.entities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by luisramos on 17/03/14. o/
 */
public class CatanGame {
  public String id;
  public Date playedOn;
  public List<String> catanPlayersId;
  public String winnerId;

  public CatanGame(){
    catanPlayersId = new ArrayList<String>();
  }

  @Override
  public String toString(){
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    return "Game #" + id + " " + df.format(this.playedOn);
  }
}
