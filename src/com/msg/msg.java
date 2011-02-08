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
import android.widget.Toast;

public class msg extends Activity {
	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		
		 
		SQLiteDatabase myDB = this.openOrCreateDatabase("DatabaseName",
				MODE_PRIVATE, null);

		myDB.execSQL("CREATE TABLE IF NOT EXISTS mytablesent (id integer primary key autoincrement , msg varchar(500) , date TEXT );");

		myDB.execSQL("CREATE TABLE IF NOT EXISTS " + "settings"
				+ " (id varchar(2) , number varchar(20));");
		if (settings.flag1 == 1) {

			myDB.execSQL("INSERT INTO " + "settings" + " (id,number)"
					+ " VALUES ('a' ,'" + 123 + "');");
			settings.flag1 = 0;
		}

		myDB.execSQL("CREATE TABLE IF NOT EXISTS " + "mytable"
				+ " (id integer primary key autoincrement , msg varchar(500));");

		myDB.execSQL("INSERT INTO " + "mytable" + " (msg)"
				+ " VALUES ('hello');");

		myDB.execSQL("INSERT INTO " + "mytable" + " (msg)"
				+ " VALUES ('hello');");

		myDB.execSQL("INSERT INTO " + "mytable" + " (msg)"
				+ " VALUES ('hello');");

		myDB.execSQL("INSERT INTO " + "mytable" + " (msg)"
				+ " VALUES ('hello');");

		Button listpage = (Button) findViewById(R.id.listpagebutton);

		listpage.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Takes to the Order Booking Page.
				Intent intent = new Intent(msg.this, com.msg.list.class);

				startActivity(intent);
			}
		});

		Button setting = (Button) findViewById(R.id.mainbuttonsave);

		setting.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Takes to the Order Booking Page.
				Intent intent = new Intent(msg.this, com.msg.settings.class);

				startActivity(intent);
			}
		});

		Button sentbtn = (Button) findViewById(R.id.sentbtn);

		sentbtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Takes to the Order Booking Page.
				Intent intent = new Intent(msg.this, com.msg.sentlist.class);

				startActivity(intent);
			}
		});

		Button deletebtn = (Button) findViewById(R.id.deletebtn);

		deletebtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Takes to the Order Booking Page.
				SQLiteDatabase myDB = msg.this.openOrCreateDatabase(
						"DatabaseName", MODE_PRIVATE, null);

				myDB.execSQL("delete FROM mytable ;");
				myDB.execSQL("delete FROM mytablesent ;");

			}

		});

		Button addnewbt = (Button) findViewById(R.id.addnew);

		addnewbt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Takes to the Order Booking Page.
				Intent intent = new Intent(msg.this, com.msg.newmsg.class);

				startActivity(intent);

			}

		});
		Button sendrandbtn = (Button) findViewById(R.id.sendrandbtn);

		sendrandbtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				SQLiteDatabase myDB = msg.this.openOrCreateDatabase(
						"DatabaseName", MODE_PRIVATE, null);

				Cursor c = myDB.rawQuery("SELECT * FROM mytable ;", null);
				c.moveToFirst();
				int r = (int) (Math.random() * c.getCount());
				if (r <= 0)
					r = 1;
				int i = 0;
				if (c != null && !c.isAfterLast()) {
					do {

						if (r == 1)
							break;
						i++;
						r--;
					} while (c.moveToNext());

					Toast.makeText(
							msg.this,
							"msg no" + c.getInt(c.getColumnIndex("id"))
									+ " sent", 5).show();

					myDB.execSQL("delete FROM mytable where id ="
							+ c.getInt(c.getColumnIndex("id")) + ";");

					myDB.execSQL("insert into mytablesent (msg,date) values ('"
							+ c.getString(c.getColumnIndex("msg"))
							+ "','dbdate');");

					myDB.execSQL("update mytablesent set date=(select strftime('%d-%m-%Y %H:%M:%S', 'now'));");

				}

			}
		});

	}
}