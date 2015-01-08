package com.leo.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public void click(View view) {
		int id = view.getId();
		Intent intent = new Intent(this, DemoActivity.class);

		switch (id) {
		case R.id.custom_view_01:
			intent.putExtra("index", 1);
			break;
		case R.id.custom_view_02:
			intent.putExtra("index", 2);
			break;
		case R.id.custom_view_03:
			intent.putExtra("index", 3);
			break;
		case R.id.custom_view_04:
			intent.putExtra("index", 4);
			break;
		case R.id.custom_view_05:
			intent.putExtra("index", 5);
			break;

		default:
			break;
		}
		startActivity(intent);
	}
}
