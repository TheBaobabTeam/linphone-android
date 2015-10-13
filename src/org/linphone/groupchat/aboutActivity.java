package org.linphone.groupchat;



import org.linphone.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class aboutActivity extends FragmentActivity{
	private static final String ABOUT_FRAGMENT = "aboutFragment";
	private aboutFragment aboutFragment;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_container);
		
		aboutFragment fragment = new aboutFragment();
		getSupportFragmentManager().beginTransaction().add(R.id.containerAbout, fragment, "aboutFragment").commit();
		
		FragmentManager fm = getSupportFragmentManager();
		aboutFragment = (aboutFragment) fm.findFragmentByTag(ABOUT_FRAGMENT);

	    // If the Fragment is non-null, then it is currently being
	    // retained across a configuration change.
		if (aboutFragment == null) {
			aboutFragment = new aboutFragment();
			fm.beginTransaction().add(R.id.containerAbout, aboutFragment, ABOUT_FRAGMENT).commit();
	    }
	}}