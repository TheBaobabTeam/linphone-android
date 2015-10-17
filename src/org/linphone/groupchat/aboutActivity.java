package org.linphone.groupchat;

import org.linphone.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;


/**
 * @author Mpedi Mello
 */
public class aboutActivity extends FragmentActivity{
	private static final String ABOUT_FRAGMENT = "about";
	private aboutFragment fragDetails;
	String msg = "AboutActivity : ";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_activity);
		
		aboutFragment fragment = new aboutFragment();
		getSupportFragmentManager().beginTransaction().add(R.id.AboutContainer, fragment, "about").commit();
		
		FragmentManager fm = getSupportFragmentManager();
		fragDetails = (aboutFragment) fm.findFragmentByTag(ABOUT_FRAGMENT);
		
	    // If the Fragment is non-null, then it is currently being
	    // retained across a configuration change.
		if (fragDetails == null) {
			fragDetails = new aboutFragment();
			fm.beginTransaction().add(R.id.AboutContainer, fragDetails, ABOUT_FRAGMENT).commit();
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

