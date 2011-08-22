package com.joshmcarthur.android.yamba;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class StatusActivity extends Activity implements OnClickListener {
	/** Define global variables */
	EditText editStatus;
	Button buttonUpdate;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.status);
		
		editStatus = (EditText) findViewById(R.id.editStatus);
		buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
		
		buttonUpdate.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		String status = editStatus.getText().toString();
		Log.d("Status Activity", "Clicked with status " + status);
	}
}