package ha.it.drinkapp;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuFragment extends Fragment {

	/**
	 * This fragment handles the MenuActivity, 
	 * and implements ButtonListener for the menu's buttons.
	 * New activities starts with help of intents. 
	 */
	
	ButtonListener listener;
	Button playButton;
	Button rulesButton;
	Button settingsButton;
	Button aboutButton;
	MediaPlayer startAudio;
	boolean introHasPlayed = false;
	
	public interface ButtonListener {
		public void onPlayButtonSelected();
		public void onRulesButtonSelected();
		public void onSettingsButtonSelected();
		public void onAboutButtonSelected();
	}
	
	 @Override
	 public void onAttach(Activity activity) {
	        super.onAttach(activity);
	        try {
	        	listener = (ButtonListener) activity;
	        	if (introHasPlayed == false) {
	        		startAudio = MediaPlayer.create(getActivity(), R.raw.start);
	        		startAudio.start();
	        		introHasPlayed = true;
	        	}
	        }
	        catch (ClassCastException e) {
	            throw new ClassCastException(activity.toString()
	                    + " must implement imageButtonListener");
	        }
	    }  
	
	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			return inflater.inflate(R.layout.menu_fragment, container, false);
		}
	
	 @Override
	 public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			
			playButton 		= (Button) getView().findViewById(R.id.play_button);
			rulesButton 	= (Button) getView().findViewById(R.id.rules_button);
			settingsButton 	= (Button) getView().findViewById(R.id.game_options_button);
			aboutButton 	= (Button) getView().findViewById(R.id.statement_button);
			
			
			//PLAYBUTTON PRESSED--->
				playButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent playIntent = new Intent(getActivity(), ShuffleActivity.class);
						startActivity(playIntent);
					}
				});
				
			//RULESBUTTON PRESSED--->
				rulesButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent rulesIntent = new Intent(getActivity(), RulesActivity.class);
						startActivity(rulesIntent);
					}
				});
				
			//SETTINGSBUTTON PRESSED--->
				settingsButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent settingsIntent = new Intent(getActivity(), SettingsActivity.class);
						startActivity(settingsIntent);
					}
				});
				
			//ABOUTBUTTON PRESSED--->
				aboutButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent aboutIntent = new Intent(getActivity(), StatementActivity.class);
						startActivity(aboutIntent);
					}
				});
	 
		}
	 
}
