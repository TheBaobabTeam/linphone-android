package org.linphone.groupchat;

import org.linphone.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;


/**
 * @author Mpedi Mello
 */
public class GroupDetailsActivity extends FragmentActivity{
	private static final String GROUP_DETAILS_FRAGMENT = "groupDetails";
	private GroupdetailsFragment groupDetails;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_details2);
		
		GroupdetailsFragment fragment = new GroupdetailsFragment();
		getSupportFragmentManager().beginTransaction().add(R.id.groupDetailsFragment, fragment, "groupDetails").commit();
		
		FragmentManager fm = getSupportFragmentManager();
		groupDetails = (GroupdetailsFragment) fm.findFragmentByTag(GROUP_DETAILS_FRAGMENT);

	    // If the Fragment is non-null, then it is currently being
	    // retained across a configuration change.
		if (groupDetails == null) {
			groupDetails = new GroupdetailsFragment();
			fm.beginTransaction().add(R.id.groupDetailsFragment, groupDetails, GROUP_DETAILS_FRAGMENT).commit();
	    }
	}
	
	
	
	
	
	
}

