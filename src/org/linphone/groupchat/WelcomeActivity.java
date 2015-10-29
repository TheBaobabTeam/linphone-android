package org.linphone.groupchat;

import org.linphone.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;


/**
 * @author Mpedi Mello
 */
public class WelcomeActivity extends FragmentActivity{
	private static final String WELCOME_FRAGMENT = "welcomeFragment";
	private WelcomeFragment welcomeFragment;
	String msg = "WelcomeActivity : ";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_fragment);
		
		WelcomeFragment fragment = new WelcomeFragment();
		getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainerWelcome, fragment, "WelcomeFragment").commit();
		
		FragmentManager fm = getSupportFragmentManager();
		welcomeFragment = (WelcomeFragment) fm.findFragmentByTag(WELCOME_FRAGMENT);
		
		// If the Fragment is non-null, then it is currently being
		// retained across a configuration change.
		if (welcomeFragment == null) {
			welcomeFragment = new WelcomeFragment();
			fm.beginTransaction().add(R.id.fragmentContainerWelcome, welcomeFragment, WELCOME_FRAGMENT).commit();
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Log.d(msg, "The onPause() event");
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		Log.d(msg, "The onStop() event");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(msg, "The onDestroy() event");
	}
}

