package com.leo.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class DemoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		int index = intent.getIntExtra("index", 1);
		switch (index) {
		case 1:
			setContentView(R.layout.random_text_view);
			break;
		case 2:
			setContentView(R.layout.image_withtext_layout);
			break;
		case 3:
			setContentView(R.layout.double_color_progressbar_main);
			break;
		case 4:
			setContentView(R.layout.custom_viewgrounp_main);
			break;
		case 5:
			setContentView(R.layout.volume_main);
			break;
		default:
			break;
		}
	}

}
