package org.linphone.groupchat;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import org.linphone.R;
/**
 * @Author Patience Mtsweni
 */
public class VoiceRecordActivity extends FragmentActivity {
	private static final String VOICE_FRAGMENT = "VoiceRecordFragment";
	private VoiceRecordFragment voiceRecordFragment;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.voice_record);

		VoiceRecordFragment fragment = new VoiceRecordFragment();
		getSupportFragmentManager().beginTransaction().add(R.id.voiceRecordFrag, fragment, "VoiceRecordFragment").commit();
		
		FragmentManager fm = getSupportFragmentManager();
		voiceRecordFragment = (VoiceRecordFragment) fm.findFragmentByTag(VOICE_FRAGMENT);

	    // If the Fragment is non-null, then it is currently being
	    // retained across a configuration change.
		if (voiceRecordFragment == null) {
			voiceRecordFragment = new VoiceRecordFragment();
			fm.beginTransaction().add(R.id.voiceRecordFrag, voiceRecordFragment, VOICE_FRAGMENT).commit();
	    }
	}
}
