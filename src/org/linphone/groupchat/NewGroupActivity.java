package org.linphone.groupchat;

import org.linphone.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * @author Mpedi Mello
 */
public class NewGroupActivity extends FragmentActivity {
	private static final String NEW_GROUP_FRAGMENT = "newGroupFragment";
	private NewGroupFragment newGroupFragment;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_group_activity);
		
		NewGroupFragment fragment = new NewGroupFragment();
		getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, fragment, "NewGroupFragment").commit();
		
		FragmentManager fm = getSupportFragmentManager();
		newGroupFragment = (NewGroupFragment) fm.findFragmentByTag(NEW_GROUP_FRAGMENT);

	    // If the Fragment is non-null, then it is currently being
	    // retained across a configuration change.
		if (newGroupFragment == null) {
			newGroupFragment = new NewGroupFragment();
			fm.beginTransaction().add(R.id.fragmentContainer, newGroupFragment, NEW_GROUP_FRAGMENT).commit();
	    }
	}
}

