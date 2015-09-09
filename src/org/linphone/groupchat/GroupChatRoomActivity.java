package org.linphone.groupchat;

import org.linphone.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class GroupChatRoomActivity extends FragmentActivity {
	
	private TextView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.groupchatroom);
		
		back = (TextView)findViewById(R.id.back);
		if (back != null) {
			back.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}
		
		//Intent intent = getIntent();
		 
        // 2. get message value from intent
        //String message = intent.getStringExtra("GroupName");
 
        // 3. show message on textView 
        //((TextView)findViewById(R.id.groupName)).setText(message);
	}
}
