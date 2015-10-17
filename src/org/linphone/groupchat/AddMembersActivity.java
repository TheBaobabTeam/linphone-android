package org.linphone.groupchat;

import org.linphone.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import org.linphone.core.LinphoneChatRoom;
import org.linphone.core.LinphoneCore;

import android.content.Intent;


/**
 * @author Mpedi Mello
 */
public class AddMembersActivity extends FragmentActivity{
	private static final String ADD_MEMBERS_FRAGMENT = "addMembersFragment";
	private AddMembersFragment addMembersFragment;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_members_activity);
		
		Bundle extras = new Bundle();
		extras.putString("GroupName", getIntent().getExtras().getString("GroupName"));
		
		AddMembersFragment fragment = new AddMembersFragment();
		fragment.setArguments(extras);
		getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainerMembers, fragment, "addMembersFragment").commit();
		
		FragmentManager fm = getSupportFragmentManager();
		addMembersFragment = (AddMembersFragment) fm.findFragmentByTag(ADD_MEMBERS_FRAGMENT);

	    // If the Fragment is non-null, then it is currently being
	    // retained across a configuration change.
		if (addMembersFragment == null) {
			addMembersFragment = new AddMembersFragment();
			addMembersFragment.setArguments(extras);
			fm.beginTransaction().add(R.id.fragmentContainerMembers, addMembersFragment, ADD_MEMBERS_FRAGMENT).commit();
	    }

	}
	
}

