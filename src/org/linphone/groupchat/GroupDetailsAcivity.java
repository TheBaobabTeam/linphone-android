package org.linphone.groupchat;

import org.linphone.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;


/**
 * @author Mpedi Mello
 */
public class GroupDetailsAcivity extends FragmentActivity{
	private static final String GROUP_DETAILS_FRAGMENT = "groupDetails";
	private Groupdetails groupDetails;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_details);
		
		Groupdetails fragment = new Groupdetails();
		getSupportFragmentManager().beginTransaction().add(R.id.addgroupfragment, fragment, "groupDetails").commit();
		
		FragmentManager fm = getSupportFragmentManager();
		groupDetails = (Groupdetails) fm.findFragmentByTag(GROUP_DETAILS_FRAGMENT);

	    // If the Fragment is non-null, then it is currently being
	    // retained across a configuration change.
		if (groupDetails == null) {
			groupDetails = new Groupdetails();
			fm.beginTransaction().add(R.id.addgroupfragment, groupDetails, GROUP_DETAILS_FRAGMENT).commit();
	    }
	}
	
	
	
	
	
	
}

