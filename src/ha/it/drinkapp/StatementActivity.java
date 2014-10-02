package ha.it.drinkapp;

import ha.it.drinkapp.StatementFragment.StatementButtonListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class StatementActivity extends FragmentActivity implements StatementButtonListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statement_activity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.statement_activity, menu);
		return true;
	}

	@Override
	public void onOKButtonSelected() {
		setResult(RESULT_OK);
		finish();
	}

}
