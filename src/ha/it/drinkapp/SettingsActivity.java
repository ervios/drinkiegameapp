package ha.it.drinkapp;



import ha.it.drinkapp.SettingsFragment.SettingsButtonListener;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.Window;

public class SettingsActivity extends FragmentActivity implements SettingsButtonListener {

	Vibrator vibrator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.settings_activity);
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public void onCancelButtonSelected() {
		setResult(RESULT_CANCELED);
		finish();
	}

	@Override
	public void onOKButtonSelected() {
		setResult(RESULT_OK);
		finish();
	}


	@Override
	public void onAutoDeckOptionsSelected() {
	}


	@Override
	public void onMuteOptionsSelected() {
	}


	@Override
	public void onVibrationOptionsSelected() {
		
	}

	

}
