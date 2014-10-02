package ha.it.drinkapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StatementFragment extends Fragment {

	StatementButtonListener statementButtonListener;
	Button stateokButton;
	
	public interface StatementButtonListener {
		public void onOKButtonSelected();
	}
	@Override
	public void onAttach(Activity activity) {
	        super.onAttach(activity);
	        try {
	        	statementButtonListener = (StatementButtonListener) activity;
	        }
	        catch (ClassCastException e) {
	            throw new ClassCastException(activity.toString()
	                    + " must implement imageButtonListener");
	        }
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			return inflater.inflate(R.layout.statement_fragment, container, false);
	}
	
	@Override
	 public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			stateokButton 		= (Button) getView().findViewById(R.id.statement_ok_button);
			stateokButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					statementButtonListener.onOKButtonSelected();
				}
			});
	}
}
