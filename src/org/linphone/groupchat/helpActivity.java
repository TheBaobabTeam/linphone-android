package org.linphone.groupchat;

import org.linphone.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;


/**
 * @author Mpedi Mello
 */
public class helpActivity extends FragmentActivity{
	private static final String HELP_FRAGMENT = "help";
	private helpFragment fragDetails;
	String msg = "HelpActivity : ";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help_activity);
		
		helpFragment fragment = new helpFragment();
		getSupportFragmentManager().beginTransaction().add(R.id.HelpfragmentContainer, fragment, "help").commit();
		
		FragmentManager fm = getSupportFragmentManager();
		fragDetails = (helpFragment) fm.findFragmentByTag(HELP_FRAGMENT);
		
	    // If the Fragment is non-null, then it is currently being
	    // retained across a configuration change.
		if (fragDetails == null) {
			fragDetails = new helpFragment();
			fm.beginTransaction().add(R.id.HelpfragmentContainer, fragDetails, HELP_FRAGMENT).commit();
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

