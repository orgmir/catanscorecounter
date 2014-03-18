package pt.orgmir.catanscorecounter.app.data.entities;

/**
 * Created by luisramos on 17/03/14. o/
 */
public class CatanPlayer {
  public String id;
  public String name;

  @Override
  public String toString(){
    return "P: " + name;
  }
}
