package ha.it.drinkapp;

import ha.it.drinkapp.MenuFragment.ButtonListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class MenuActivity extends FragmentActivity implements ButtonListener {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_activity);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings_activity, menu);
		return true;
	}

	@Override
	public void onPlayButtonSelected() {
	}

	@Override
	public void onRulesButtonSelected() {
	}
	
	@Override
	public void onSettingsButtonSelected() {
	}

	@Override
	public void onAboutButtonSelected() {
	}

	
}
