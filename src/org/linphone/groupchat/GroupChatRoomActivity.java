package org.linphone.groupchat;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import org.linphone.R;
/**
 * @Author TeamBaobab
 */
public class GroupChatRoomActivity extends FragmentActivity {
	private static final String CHAT_FRAGMENT = "GroupChatRoomFragment";
	private GroupChatRoomFragment GroupChatRoomFragment;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_activity);
		
	/*	Bundle extras = new Bundle();
		extras.putString("SipUri", getIntent().getExtras().getString("SipUri"));
		extras.putString("DisplayName", getIntent().getExtras().getString("DisplayName"));
		extras.putString("PictureUri", getIntent().getExtras().getString("PictureUri"));
		extras.putString("ThumbnailUri", getIntent().getExtras().getString("ThumbnailUri"));*/
		
		GroupChatRoomFragment fragment = new GroupChatRoomFragment();
	//	fragment.setArguments(extras);
		getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, fragment, "GroupChatRoomFragment").commit();
		
		FragmentManager fm = getSupportFragmentManager();
		GroupChatRoomFragment = (GroupChatRoomFragment) fm.findFragmentByTag(CHAT_FRAGMENT);

	    // If the Fragment is non-null, then it is currently being
	    // retained across a configuration change.
		if (GroupChatRoomFragment == null) {
			GroupChatRoomFragment = new GroupChatRoomFragment();
			fm.beginTransaction().add(R.id.fragmentContainer, GroupChatRoomFragment, CHAT_FRAGMENT).commit();
	    }
	}
}

