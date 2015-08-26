package org.linphone.groupchat;

import org.linphone.LinphoneActivity;
import org.linphone.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddMembersFragment extends Fragment implements OnClickListener {
	private static AddMembersFragment instance;
	
	private TextView back, next;
	
	public static boolean isInstanciated() {
		return instance != null;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		View view = inflater.inflate(R.layout.add_members, container, false);
		
		// Retain the fragment across configuration changes
		setRetainInstance(true);

		back = (TextView) view.findViewById(R.id.back);
		if (back != null) {
			back.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					getActivity().finish();
				}
			});
		}
		
		next = (TextView) view.findViewById(R.id.next);
		next.setOnClickListener(this);
		
		return view;
	}

	public static AddMembersFragment instance() {
		return instance;
	}
	
	@Override
	public void onClick(View v) {
		LinphoneActivity.instance().displayCustomToast("Atleast one contact must be selected.", Toast.LENGTH_SHORT);
	}
}
