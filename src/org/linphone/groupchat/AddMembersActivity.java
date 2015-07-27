package org.linphone.groupchat;

import org.linphone.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * @author Mpedi Mello
 */
public class AddMembersActivity extends FragmentActivity {
	private static final String ADD_MEMBERS_FRAGMENT = "addMembersFragment";
	private AddMembersFragment addMembersFragment;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_members_activity);
		
		AddMembersFragment fragment = new AddMembersFragment();
		getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, fragment, "AddMembersFragment").commit();
		
		FragmentManager fm = getSupportFragmentManager();
		addMembersFragment = (AddMembersFragment) fm.findFragmentByTag(ADD_MEMBERS_FRAGMENT);

	    // If the Fragment is non-null, then it is currently being
	    // retained across a configuration change.
		if (addMembersFragment == null) {
			addMembersFragment = new AddMembersFragment();
			fm.beginTransaction().add(R.id.fragmentContainer, addMembersFragment, ADD_MEMBERS_FRAGMENT).commit();
	    }
	}
}

