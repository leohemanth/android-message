package com.msg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class newmsg extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.addnew);

		Button addnewbt = (Button) findViewById(R.id.savebtnadd);

		addnewbt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Takes to the Order Booking Page.
				SQLiteDatabase myDB = newmsg.this.openOrCreateDatabase(
						"DatabaseName", MODE_PRIVATE, null);

				EditText et = (EditText) findViewById(R.id.newmsg);
			//	Toast.makeText(newmsg.this, et.getText().toString(), 5).show();
				myDB.execSQL("INSERT INTO " + " mytable " + " (msg) values ('"
						+ et.getText().toString() + "');");
			
			}
		});

	}
}