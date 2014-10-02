package ha.it.drinkapp;


import ha.it.drinkapp.RulesFragment.RulesButtonListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class RulesActivity extends FragmentActivity implements RulesButtonListener {
	
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rules_activity);
	}

	
	
	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putBoolean("Init", DrinkappConstants.INIT_RULES);
		super.onSaveInstanceState(savedInstanceState);
		
	}

	@SuppressWarnings("unused")
	private void updateAdviceList(String advice) {
		FragmentManager fm = getSupportFragmentManager();
		RulesFragment list = (RulesFragment) fm.findFragmentById(R.id.rulesActivity_fragment);
		list.addAdvice(advice);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		
	}
	@Override
	public void onCancelSelected() {
		// TODO Auto-generated method stub
		setResult(RESULT_CANCELED);
		finish();
	}



	@Override
	public void onOKSelected() {
		// TODO Auto-generated method stub
		setResult(RESULT_OK);
		finish();
	}

	

}
