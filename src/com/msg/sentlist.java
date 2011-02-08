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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class sentlist extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.sentlist);

		ListView mListView = (ListView) findViewById(R.id.messagelistListView);

		SQLiteDatabase myDB = this.openOrCreateDatabase("DatabaseName",
				MODE_PRIVATE, null);

		myDB.execSQL("CREATE TABLE IF NOT EXISTS mytablesent (id integer primary key autoincrement , msg varchar(500) , date TEXT );");
		Cursor c = myDB.rawQuery("SELECT * FROM " + "mytablesent", null);

		c.moveToFirst();

		List<Map> lMapList = new ArrayList<Map>();

		if (c != null && !c.isAfterLast()) {
			// Loop through all Results
			do {
				Map mMap = new HashMap();
				mMap.put("Id", c.getInt(c.getColumnIndex("id")));
				mMap.put("msg", c.getString(c.getColumnIndex("msg")));
				mMap.put("date", c.getString(c.getColumnIndex("date")));
				lMapList.add(mMap);
			} while (c.moveToNext());
		}

		String[] strFrom = { "Id", "msg", "date" };
		int[] iTo = { R.id.textView1, R.id.textView2, R.id.textView3 };

		SimpleAdapter saAdapter = new SimpleAdapter(
				this.getApplicationContext(),
				(List<? extends Map<String, ?>>) lMapList,
				R.layout.sentlistelement, strFrom, iTo);
		mListView.setAdapter(saAdapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String msg = ((TextView) arg1.findViewById(R.id.textView2))
						.getText().toString();

				String Id = ((TextView) arg1.findViewById(R.id.textView1))
						.getText().toString();

				PendingIntent pi = PendingIntent.getActivity(sentlist.this, 0,
						new Intent(sentlist.this, sentlist.class), 0);
				SmsManager sms = SmsManager.getDefault();

				SQLiteDatabase myDB = sentlist.this.openOrCreateDatabase(
						"DatabaseName", MODE_PRIVATE, null);

				Cursor c = myDB.rawQuery(
						"SELECT * FROM settings Where id='a' ;", null);
				c.moveToFirst();
				// Toast.makeText(this, c.getString(c.getColumnIndex("number")),
				// 5).show();

				sms.sendTextMessage(c.getString(c.getColumnIndex("number")),
						null, msg, pi, null);
				Toast.makeText(
						sentlist.this,
						"message number" + Id + "  " + "sent again to "
								+ c.getString(c.getColumnIndex("number"))
								+ "and removed from list", 5).show();

				// myDB.execSQL("delete FROM mytable where id =" + Id + ";");

				sms.sendTextMessage(c.getString(c.getColumnIndex("number")),
						null, msg, pi, null);
				myDB.execSQL("insert into mytablesent (msg,date) values ('"
						+ msg + "','dbdate');");

				myDB.execSQL("update mytablesent set date=(select strftime('%d-%m-%Y %H:%M:%S', 'now'));");

				Intent intent = new Intent(sentlist.this,
						com.msg.sentlist.class);

				startActivity(intent);

			}

		});

	}
}