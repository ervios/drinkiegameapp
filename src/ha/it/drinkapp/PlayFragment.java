package ha.it.drinkapp;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class PlayFragment extends Fragment {

	private boolean newDeck = false;
	public int sipCounter = 0;
	
	DeckCreator deck;
	Card card = null;
	TextView bottomText, sips, cardCounter;
	Button addButton;
	ImageButton imageButton;
	MediaPlayer gameSound;
	AlphaAnimation alpha;

	
	
	
	ImageButtonListener imageButtonListener;

	public interface ImageButtonListener {
		public void onImageButtonSelected();
	}
	
	 @Override
	 public void onAttach(Activity activity) {
	        super.onAttach(activity);
	        try {
	        	imageButtonListener = (ImageButtonListener) activity;
	        }
	        catch (ClassCastException e) {
	            throw new ClassCastException(activity.toString()
	                    + " must implement imageButtonListener");
	        }
	    }    	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return inflater.inflate(R.layout.play_fragment, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		imageButton = (ImageButton) getView().findViewById(R.id.imageButton);
		bottomText 	= (TextView) getView().findViewById(R.id.bottom_textField);
		gameSound 	= MediaPlayer.create(getActivity(), R.raw.drawcard);
		sips 		= (TextView) getView().findViewById(R.id.sipCounter);
		addButton 	= (Button) getView().findViewById(R.id.addfive_button);
		cardCounter	= (TextView) getView().findViewById(R.id.card_counter);
		
		alpha = new AlphaAnimation(0,1);
		alpha.setDuration(3000);
		imageButton.startAnimation(alpha);
		imageButton.setBackgroundResource(R.drawable.backside);
    	bottomText.setText(R.string.touch_card);
    	
    	//ADDFIVE_BUTTON LISTENER -->
	    	addButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					countSips(5);
					sips.setText(String.valueOf(getTotalSips()));
				}
			});
    	
    	//IMAGEBUTTON LISTENER--->
			imageButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					if (newDeck == false) {
						startGame();	
					}
					if (newDeck == true) {
						imageButton.setBackgroundResource(R.drawable.backside);
						gameSound.start();
						imageButtonAnimationAway();
						imageButtonAnimationComeback();
			        	bottomText.setText(R.string.blank);
					}
				}
			});
		
		//SOUND STOPS LISTENER--->
			gameSound.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer arg0) {
					getCorrectCard();
				}
			});
	}
	
	public void startGame() {
		deck = new DeckCreator();
		newDeck = true;
	}
	
	public void getCorrectCard() {
		
		if (deck.getTotalCards() == 0) {
			newDeck = false;
			Toast.makeText(getActivity(),"Made a new deck of cards!", Toast.LENGTH_SHORT).show();
		}
		else {
			gameLogic();
		}
	}
	
	
	public void imageButtonAnimationAway() {
		alpha = new AlphaAnimation(1,0);
		alpha.setDuration(3000);
		imageButton.startAnimation(alpha);
	}
	public void imageButtonAnimationComeback() {
		alpha = new AlphaAnimation(0,1);
		alpha.setDuration(3800);
		imageButton.startAnimation(alpha);
	}

	public int getTotalSips() {
		return sipCounter;
	}
	
	public void countSips(int value) {
		
		//TODO different rules for the Ace-card
		if(value == 1) {
			sipCounter += value;
		}
		if (value <= 10 ) {
			sipCounter += value;
		}
		
	}
	
	public int getCardPicture(String cardName) {
		return getResources().getIdentifier(cardName, "drawable", "ha.it.drinkapp");
	}
	
	public void gameLogic() {
		card = deck.drawFromDeck();
		loadCardRules();
		int resID = getCardPicture(card.drawableToString());
		imageButton.setBackgroundResource(resID);
		bottomText.setText(card.getRuleForCard(card.getValue()));
		countSips(card.getValue());
		sips.setText(String.valueOf(getTotalSips()));
		cardCounter.setText(String.valueOf(deck.getTotalCards()));
	}
	
	public void loadCardRules() {
		
		card.backupRules();
	}
}
