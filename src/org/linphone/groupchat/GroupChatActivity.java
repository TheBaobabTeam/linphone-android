package org.linphone.groupchat;

import org.linphone.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * @author Mpedi Mello
 */
public class GroupChatActivity extends FragmentActivity {
	private static final String GROUP_CHAT_FRAGMENT = "groupChatFragment";
	private GroupChatFragment groupChatFragment;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_activity);
		
		Bundle extras = new Bundle();
		extras.putString("SipUri", getIntent().getExtras().getString("SipUri"));
		extras.putString("DisplayName", getIntent().getExtras().getString("DisplayName"));
		extras.putString("PictureUri", getIntent().getExtras().getString("PictureUri"));
		extras.putString("ThumbnailUri", getIntent().getExtras().getString("ThumbnailUri"));
		
		GroupChatFragment fragment = new GroupChatFragment();
		fragment.setArguments(extras);
		getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, fragment, "GroupChatFragment").commit();
		
		FragmentManager fm = getSupportFragmentManager();
		groupChatFragment = (GroupChatFragment) fm.findFragmentByTag(GROUP_CHAT_FRAGMENT);

		// If the Fragment is non-null, then it is currently being
		// retained across a configuration change.
		if (groupChatFragment == null) {
			groupChatFragment = new GroupChatFragment();
			groupChatFragment.setArguments(extras);
			fm.beginTransaction().add(R.id.fragmentContainer, groupChatFragment, GROUP_CHAT_FRAGMENT).commit();
		}
	}
}

