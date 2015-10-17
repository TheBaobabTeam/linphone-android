package org.linphone.groupchat;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
<<<<<<< HEAD
=======
import android.util.Log;

>>>>>>> create_group_chat
import org.linphone.R;
/**
 * @Author TeamBaobab
 */
public class GroupChatRoomActivity extends FragmentActivity {
<<<<<<< HEAD
	private static final String CHAT_FRAGMENT = "GroupChatRoomFragment";
	private GroupChatRoomFragment GroupChatRoomFragment;
=======
	private static final String GROUPCHAT_FRAGMENT = "GroupChatRoomFragment";
	private GroupChatRoomFragment groupChatRoomFragment;
	//String msg = "GroupChatRoomActivity : ";
>>>>>>> create_group_chat
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_group_chat_room);
		
<<<<<<< HEAD
	/*	Bundle extras = new Bundle();
		extras.putString("SipUri", getIntent().getExtras().getString("SipUri"));
		extras.putString("DisplayName", getIntent().getExtras().getString("DisplayName"));
		extras.putString("PictureUri", getIntent().getExtras().getString("PictureUri"));
		extras.putString("ThumbnailUri", getIntent().getExtras().getString("ThumbnailUri"));*/
		
		GroupChatRoomFragment fragment = new GroupChatRoomFragment();
	//	fragment.setArguments(extras);
		getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainerNew, fragment, "GroupChatRoomFragment").commit();
		
		FragmentManager fm = getSupportFragmentManager();
		GroupChatRoomFragment = (GroupChatRoomFragment) fm.findFragmentByTag(CHAT_FRAGMENT);

	    // If the Fragment is non-null, then it is currently being
	    // retained across a configuration change.
		if (GroupChatRoomFragment == null) {
			GroupChatRoomFragment = new GroupChatRoomFragment();
			fm.beginTransaction().add(R.id.fragmentContainerNew, GroupChatRoomFragment, CHAT_FRAGMENT).commit();
=======
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
>>>>>>> create_group_chat
	    }
	}
	
	
}

