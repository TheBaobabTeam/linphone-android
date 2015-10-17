package org.linphone;
/*
ChatActivity.java
Copyright (C) 2015  Belledonne Communications, Grenoble, France

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * @author Margaux Clerc
 */
public class ChatActivity extends FragmentActivity {
	private static final String CHAT_FRAGMENT = "chatFragment";
	private ChatFragment chatFragment;
	
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
		
		ChatFragment fragment = new ChatFragment();
		fragment.setArguments(extras);
		getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainerNew, fragment, "ChatFragment").commit();
		
		FragmentManager fm = getSupportFragmentManager();
		chatFragment = (ChatFragment) fm.findFragmentByTag(CHAT_FRAGMENT);

	    // If the Fragment is non-null, then it is currently being
	    // retained across a configuration change.
		if (chatFragment == null) {
			chatFragment = new ChatFragment();
			chatFragment.setArguments(extras);
			fm.beginTransaction().add(R.id.fragmentContainerNew, chatFragment, CHAT_FRAGMENT).commit();
	    }
	}
}

