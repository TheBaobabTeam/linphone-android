package org.linphone.groupchat;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import org.linphone.R;
/**
 * @Author TeamBaobab
 */
public class GroupChatRoomActivity extends FragmentActivity {
	private static final String GROUPCHAT_FRAGMENT = "GroupChatRoomFragment";
	private GroupChatRoomFragment groupChatRoomFragment;
	//String msg = "GroupChatRoomActivity : ";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_group_chat_room);
		
		Bundle extras = new Bundle();
		if (getIntent().hasExtra("SipUri") == true) {
			extras.putString("SipUri", getIntent().getExtras().getString("SipUri"));
		}	
		if (getIntent().hasExtra("DisplayName") == true) {
			extras.putString("DisplayName", getIntent().getExtras().getString("DisplayName"));
		}
		if (getIntent().hasExtra("PictureUri") == true) {
			extras.putString("PictureUri", getIntent().getExtras().getString("PictureUri"));
		}
		if (getIntent().hasExtra("ThumbnailUri") == true) {
			extras.putString("ThumbnailUri", getIntent().getExtras().getString("ThumbnailUri"));
		}
		
		if (getIntent().hasExtra("GroupName") == true) {
			extras.putString("GroupName", getIntent().getExtras().getString("GroupName"));
		}
		if (getIntent().hasExtra("GroupMembers") == true) {
			extras.putStringArray("GroupMembers", getIntent().getExtras().getStringArray("GroupMembers"));
		}
		if (getIntent().hasExtra("GroupSize") == true) {
			extras.putInt("GroupSize", getIntent().getExtras().getInt("GroupSize"));
		}
		if (getIntent().hasExtra("ChatType") == true) {
			extras.putInt("ChatType", getIntent().getExtras().getInt("ChatType"));
		}
		
		GroupChatRoomFragment fragment = new GroupChatRoomFragment();
		fragment.setArguments(extras);
		getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainerNew, fragment, "GroupChatRoomFragment").commit();
		
		FragmentManager fm = getSupportFragmentManager();
		groupChatRoomFragment = (GroupChatRoomFragment) fm.findFragmentByTag(GROUPCHAT_FRAGMENT);

	    // If the Fragment is non-null, then it is currently being
	    // retained across a configuration change.
		if (groupChatRoomFragment == null) {
			groupChatRoomFragment = new GroupChatRoomFragment();
			groupChatRoomFragment.setArguments(extras);
			fm.beginTransaction().add(R.id.fragmentContainerNew, groupChatRoomFragment, GROUPCHAT_FRAGMENT).commit();
	    }
	}
	
	
}

