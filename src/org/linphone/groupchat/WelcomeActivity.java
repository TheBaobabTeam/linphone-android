package org.linphone.groupchat;

import org.linphone.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;


/**
 * @author Mpedi Mello
 */
public class WelcomeActivity extends FragmentActivity{
	private static final String WELCOME_FRAGMENT = "welcome";
	private WelcomeFragment fragDetails;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_fragment);
		
		WelcomeFragment fragment = new WelcomeFragment();
		getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainerWelcome, fragment, "welcome").commit();
		
		FragmentManager fm = getSupportFragmentManager();
		fragDetails = (WelcomeFragment) fm.findFragmentByTag(WELCOME_FRAGMENT);
		
	    // If the Fragment is non-null, then it is currently being
	    // retained across a configuration change.
		if (fragDetails == null) {
			fragDetails = new WelcomeFragment();
			fm.beginTransaction().add(R.id.fragmentContainerWelcome, fragDetails, WELCOME_FRAGMENT).commit();
	    }
	}
	
	
	
	
	
	
}

