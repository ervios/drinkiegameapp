package ha.it.drinkapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

public class SettingsFragment extends Fragment {

	SettingsButtonListener settingsButtonListener;
	private Button okButton, cancelButton;
	private CheckBox autoDeckOption, muteOption, vibrationOption;
	private Vibrator vibrator;
	
	public interface SettingsButtonListener {
		public void onCancelButtonSelected();
		public void onOKButtonSelected();
		public void onAutoDeckOptionsSelected();
		public void onMuteOptionsSelected();
		public void onVibrationOptionsSelected();
	}
	
	@Override
	public void onAttach(Activity activity) {
	        super.onAttach(activity);
	        try {
	        	settingsButtonListener = (SettingsButtonListener) activity;
	        }
	        catch (ClassCastException e) {
	            throw new ClassCastException(activity.toString()
	                    + " must implement imageButtonListener");
	        }
	       
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			return inflater.inflate(R.layout.settings_fragment, container, false);
	}
	
	@Override
	 public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			autoDeckOption	= (CheckBox) getView().findViewById(R.id.checkbox_autoDeck);
        	muteOption		= (CheckBox) getView().findViewById(R.id.checkbox_mute);
        	vibrationOption = (CheckBox) getView().findViewById(R.id.checkbox_vibration);
			cancelButton 	= (Button) getView().findViewById(R.id.settings_cancel_button);
			okButton 		= (Button) getView().findViewById(R.id.settings_ok_button);
			
			vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
			
			autoDeckOption.setOnCheckedChangeListener(new OnCheckedChangeListener() { 

				@Override 
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { 

					if (buttonView.isChecked()) { 
						Toast.makeText(getActivity(), "Automatic deck creation Unchecked!", Toast.LENGTH_SHORT).show();
					} 
					else 
					{
						Toast.makeText(getActivity(), "Automatic deck creation Checked!", Toast.LENGTH_SHORT).show();
					} 

				}
			});
			
			muteOption.setOnCheckedChangeListener(new OnCheckedChangeListener() { 

				@Override 
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { 

					if (buttonView.isChecked()) { 
						Toast.makeText(getActivity(), "Sound Muted!", Toast.LENGTH_SHORT).show();
					} 
					else 
					{
						Toast.makeText(getActivity(), "Sound Unmuted!", Toast.LENGTH_SHORT).show();
					} 

				}
			});

			vibrationOption.setOnCheckedChangeListener(new OnCheckedChangeListener() { 

				@Override 
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { 

					if (buttonView.isChecked()) { 
						vibrator.cancel();
						Toast.makeText(getActivity(), "Vibration Off!", Toast.LENGTH_SHORT).show();
					} 
					else 
					{
						
						vibrator.vibrate(500);
						Toast.makeText(getActivity(), "Vibration On!", Toast.LENGTH_SHORT).show();
					} 

				}
			});
			
			
			
			//CANCELBUTTON PRESSED-->
				cancelButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						settingsButtonListener.onCancelButtonSelected();
						
					}
				});
				
			//OKBUTTON PRESSED-->
				okButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						settingsButtonListener.onOKButtonSelected();
					}
				});
	}
	
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
	}
	

}
