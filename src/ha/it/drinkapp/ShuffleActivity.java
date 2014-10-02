package ha.it.drinkapp;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.RotateAnimation;
import android.widget.Button;

public class ShuffleActivity extends Activity implements SensorEventListener {

	private boolean audioIsPlaying = false;
	private SensorManager sensorManager;
	private boolean color = false;
	private View view;
	private long lastUpdate;
	int shakeCalculator = 0;
	Button playButton;
	MediaPlayer shuffleAudio;
	Card card;
	DeckCreator shuffleDeck;
	RotateAnimation rotate;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shuffle_activity);
		
		    view = findViewById(R.id.imageButton);
		    playButton = (Button) findViewById(R.id.shuffleButton);
		    playButton.setBackgroundColor(Color.parseColor("#80000000"));
		    //shuffleDeck = new DeckCreator();
		    shuffleAudio = MediaPlayer.create(this,R.raw.shuff);
		    sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		    lastUpdate = System.currentTimeMillis();
		    view.setBackgroundResource(R.drawable.backside);
			
			
				playButton.setOnClickListener(new OnClickListener() {
		 
					@Override
					public void onClick(View v) {
					
						playButton.setBackgroundColor(Color.GRAY);
						
							if (shuffleAudio.isPlaying()) {
								shuffleAudio.stop();
							}
							//if (shakeCalculator > 3) {
								Intent startPlaying = new Intent(view.getContext(), PlayActivity.class);
								playButton.setBackgroundColor(Color.parseColor("#80000000"));
								startActivity(startPlaying);
							//}
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shuffle, menu);
		return true;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@SuppressLint("NewApi")
	@Override
	public void onSensorChanged(SensorEvent event) {
		 if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
		      getAccelerometer(event);
		    }
		
	}
	 @SuppressLint("NewApi")
	private void getAccelerometer(SensorEvent event) {
		    float[] values = event.values;
		    // Movement
		    float x = values[0];
		    float y = values[1];
		    float z = values[2];

		    float accelationSquareRoot = (x * x + y * y + z * z)
		    		/ (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
		    long actualTime = System.currentTimeMillis();
		    if (accelationSquareRoot >= 2) //
		    {
		      if (actualTime - lastUpdate < 200) {
		        return;
		      }
		      lastUpdate = actualTime;
		      
		      //Toast.makeText(this, "Device was shuffed", Toast.LENGTH_SHORT).show();
		      rotate = new RotateAnimation(90,180);
		      rotate.setDuration(500);
		      view.startAnimation(rotate);
		      
		      if (color) {
		        //view.setBackgroundColor(Color.GREEN);
		    	  if (audioIsPlaying == false) {
		    		  shuffleAudio.start();
		    		  audioIsPlaying = true;
		    	  }
		    	  
		    	  shakeCalculator++;
		    	  
		    	  if (shakeCalculator >= 15) {
		    		  view.setBackgroundResource(R.drawable.shuffle_enough);
		    		  playButton.setBackgroundColor(Color.parseColor("#131313"));
		    		  playButton.setTextColor(Color.parseColor("#33B5E5"));
		    		  playButton.setText(R.string.shuffle_enough);
		    	  }
		    	  if (shakeCalculator >= 25) {
		    		  view.setBackgroundResource(R.drawable.shuffle_toomuch);
		    		  playButton.setText(R.string.shuffle_toomuch);
		    	  }
		    	  if (shakeCalculator >= 35) {
		    		  view.setBackgroundResource(R.drawable.shuffle_waytoomuch);
		    		  playButton.setText(R.string.shuffle_waytoomuch);
		    	  }
		    	
		    	  shakeCalculator++;

		    	  rotate = new RotateAnimation(0,180, 50, 50);
			      rotate.setDuration(500);
			      view.startAnimation(rotate);
		      } 
		      color = !color;
		    }
		  }
	  @SuppressLint("NewApi")
	@Override
	  protected void onResume() {
	    super.onResume();
	    // register this class as a listener for the orientation and
	    // accelerometer sensors
	    sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
	    SensorManager.SENSOR_DELAY_NORMAL);
	  }

	  @SuppressLint("NewApi")
	@Override
	  protected void onPause() {
	    // unregister listener
	    super.onPause();
	    sensorManager.unregisterListener(this);
	  }
	  
	  
} 

