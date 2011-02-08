package com.msg;

import java.util.Calendar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class settings extends Activity {
	/** Called when the activity is first created. */
	static int flag = 0;
	static int flag1 = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.settings);
		SQLiteDatabase myDB = this.openOrCreateDatabase("DatabaseName",
				MODE_PRIVATE, null);

		Cursor c = myDB.rawQuery("SELECT * FROM settings Where id='a' ;", null);
		c.moveToFirst();
		// Toast.makeText(this, c.getString(c.getColumnIndex("number")),
		// 5).show();
		((EditText) findViewById(R.id.editText1)).setText(c.getString(c
				.getColumnIndex("number")));

		((Button) findViewById(R.id.buttonsave))
				.setOnClickListener(new OnClickListener() {

					SQLiteDatabase myDB = settings.this.openOrCreateDatabase(
							"DatabaseName", MODE_PRIVATE, null);

					@Override
					public void onClick(View v) {
						String number;

						// TODO Auto-generated method stub
						number = ((EditText) findViewById(R.id.editText1))
								.getText().toString();
						SQLiteDatabase myDB = settings.this
								.openOrCreateDatabase("DatabaseName",
										MODE_PRIVATE, null);
						myDB.execSQL("CREATE TABLE IF NOT EXISTS " + "settings"
								+ " (id varchar(2) , number varchar(20));");

						myDB.execSQL("UPDATE settings SET number='" + number
								+ "'  WHERE id='a';");

						CheckBox c = ((CheckBox) findViewById(R.id.alarmck));

						if (c.isChecked()) {
							// get a Calendar object with current time
							Calendar cal = Calendar.getInstance();
							// add 5 minutes to the calendar object

							cal.add(Calendar.MINUTE,
									Integer.parseInt(((EditText) findViewById(R.id.alarmtm))
											.getText().toString()));
							Intent intent = new Intent(settings.this,
									AlarmReceiver.class);
							intent.putExtra("alarm_message", "O'Doyle Rules!");
							// In reality, you would want to have a static
							// variable for the request code instead of 192837
							PendingIntent sender = PendingIntent.getBroadcast(
									settings.this, 192837, intent,
									PendingIntent.FLAG_UPDATE_CURRENT);
							// Get the AlarmManager service
							AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
							if(((RadioButton)findViewById(R.id.radioButton1)).isChecked()){
							am.set(AlarmManager.RTC_WAKEUP,
									cal.getTimeInMillis(), sender);
							}
							else
							am.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),60000 , sender);
						}

						Toast.makeText(settings.this,
								"number" + number + " saved", 5).show();
						Intent intent = new Intent(settings.this,
								com.msg.msg.class);

						startActivity(intent);

					}
				});
		CheckBox c1 = ((CheckBox) findViewById(R.id.alarmck));
		c1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					((EditText) findViewById(R.id.alarmtm)).setEnabled(true);
					((RadioButton)findViewById(R.id.radioButton1)).setEnabled(true);
					((RadioButton)findViewById(R.id.radioButton2)).setEnabled(true);
					
					((RadioButton)findViewById(R.id.radioButton1)).setChecked(true);
					
				}
				else{
					((EditText) findViewById(R.id.alarmtm)).setEnabled(false);
					((RadioButton)findViewById(R.id.radioButton1)).setEnabled(false);
					((RadioButton)findViewById(R.id.radioButton2)).setEnabled(false);
					((RadioButton)findViewById(R.id.radioButton1)).setChecked(false);
					((RadioButton)findViewById(R.id.radioButton2)).setChecked(false);
					
				}
			}
		});
			
		  
	}
}