package pt.orgmir.catanscorecounter.app.game.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

import pt.orgmir.catanscorecounter.app.R;
import pt.orgmir.catanscorecounter.app.data.IDataManager;
import pt.orgmir.catanscorecounter.app.data.entities.CatanGame;
import pt.orgmir.catanscorecounter.app.data.entities.CatanPlayer;
import pt.orgmir.catanscorecounter.app.dummy.DummyContent;
import pt.orgmir.catanscorecounter.app.game.CatanGameEditActivity;
import pt.orgmir.catanscorecounter.app.player.CatanPlayerListActivity;

/**
 * A list fragment representing a list of CatanGames. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link CatanGameDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class CatanGameListFragment extends ListFragment {

  /**
   * The serialization (saved instance state) Bundle key representing the
   * activated item position. Only used on tablets.
   */
  private static final String STATE_ACTIVATED_POSITION = "activated_position";

  /**
   * The fragment's current callback object, which is notified of list item
   * clicks.
   */
  private Callbacks mCallbacks = sDummyCallbacks;

  /**
   * The current activated item position. Only used on tablets.
   */
  private int mActivatedPosition = ListView.INVALID_POSITION;

  /**
   * A callback interface that all activities containing this fragment must
   * implement. This mechanism allows activities to be notified of item
   * selections.
   */
  public interface Callbacks {
    /**
     * Callback for when an item has been selected.
     */
    public void onItemSelected(String id);
  }

  /**
   * A dummy implementation of the {@link Callbacks} interface that does
   * nothing. Used only when this fragment is not attached to an activity.
   */
  private static Callbacks sDummyCallbacks = new Callbacks() {
    @Override
    public void onItemSelected(String id) {
    }
  };

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public CatanGameListFragment() {
  }

  public List<CatanGame> games;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setHasOptionsMenu(true);

  }

  @Override
  public void onResume() {
    super.onResume();

    games = IDataManager.getManager().getCatanGames();
    setListAdapter(new CatanGameArrayAdapter(getActivity(),
        android.R.layout.simple_list_item_activated_1,
        android.R.id.text1, games));
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // Restore the previously serialized activated item position.
    if (savedInstanceState != null
        && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
      setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
    }
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);

    // Activities containing this fragment must implement its callbacks.
    if (!(activity instanceof Callbacks)) {
      throw new IllegalStateException("Activity must implement fragment's callbacks.");
    }

    mCallbacks = (Callbacks) activity;
  }

  @Override
  public void onDetach() {
    super.onDetach();

    // Reset the active callbacks interface to the dummy implementation.
    mCallbacks = sDummyCallbacks;
  }

  @Override
  public void onListItemClick(ListView listView, View view, int position, long id) {
    super.onListItemClick(listView, view, position, id);

    mCallbacks.onItemSelected(games.get(position).id);
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    if (mActivatedPosition != ListView.INVALID_POSITION) {
      // Serialize and persist the activated item position.
      outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
    }
  }

  /**
   * Turns on activate-on-click mode. When this mode is on, list items will be
   * given the 'activated' state when touched.
   */
  public void setActivateOnItemClick(boolean activateOnItemClick) {
    // When setting CHOICE_MODE_SINGLE, ListView will automatically
    // give items the 'activated' state when touched.
    getListView().setChoiceMode(activateOnItemClick
        ? ListView.CHOICE_MODE_SINGLE
        : ListView.CHOICE_MODE_NONE);
  }

  private void setActivatedPosition(int position) {
    if (position == ListView.INVALID_POSITION) {
      getListView().setItemChecked(mActivatedPosition, false);
    } else {
      getListView().setItemChecked(position, true);
    }

    mActivatedPosition = position;
  }


  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.game_menu, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.new_game:
        newGame();
        return true;
      case R.id.player_list:
        playerList();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void editGame(CatanGame game){
    Intent intent = new Intent(this.getActivity(), CatanGameEditActivity.class);
    if(game != null) {
      intent.putExtra("catanGame", new Gson().toJson(game));
    }
    this.getActivity().startActivityForResult(intent, Activity.RESULT_OK);
  }

  private void newGame(){
    editGame(null);
  }

  private void playerList(){
    Intent intent = new Intent(this.getActivity(), CatanPlayerListActivity.class);
    this.getActivity().startActivity(intent);
  }

  private class CatanGameArrayAdapter extends ArrayAdapter<CatanGame>{

    public CatanGameArrayAdapter(Context context, int resource, int textViewResourceId, List<CatanGame> games) {
      super(context, resource, textViewResourceId, games);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      if(convertView == null){
        convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_activated_1, parent, false);
      }

      TextView text = (TextView) convertView.findViewById(android.R.id.text1);
      CatanGame game = getItem(position);
      CatanPlayer winner = IDataManager.getManager().getCatanPlayer(game.winnerId);
      SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM", Locale.US);

      String winnerName = "";
      if(winner != null){
        winnerName = winner.name.toUpperCase();
      }
      String date = "";
      if(game.playedOn != null){
        date = dateFormat.format(game.playedOn);
      }

      text.setText("Game #" + game.id +" "+ winnerName +" "+ date);

      return convertView;
    }
  }
}
