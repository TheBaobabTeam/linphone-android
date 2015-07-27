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

public class NewGroupFragment extends Fragment implements OnClickListener {
	private static NewGroupFragment instance;
	
	private TextView back, next;
	private TextWatcher textWatcher;
	private EditText group_name;
	
	public static boolean isInstanciated() {
		return instance != null;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		View view = inflater.inflate(R.layout.new_group, container, false);
		
		// Retain the fragment across configuration changes
		setRetainInstance(true);
		
		group_name = (EditText)view.findViewById(R.id.groupName);

		back = (TextView) view.findViewById(R.id.back);
		if (back != null) {
			back.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					getActivity().finish();
				}
			});
		}
		
		textWatcher = new TextWatcher() {
			public void afterTextChanged(Editable arg0) {}

			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				if (group_name.getText().toString().equals("")) {
					next.setEnabled(false);
				} else {
					next.setEnabled(true);
				}
			}
		};
		
		next = (TextView) view.findViewById(R.id.next);
		next.setOnClickListener(this);
		
		return view;
	}

	public static NewGroupFragment instance() {
		return instance;
	}
	
	@Override
	public void onClick(View v) {
		if (group_name.getText().toString().equals("")) {
			LinphoneActivity.instance().displayCustomToast("Please provide group name", Toast.LENGTH_SHORT);
		} else {
			LinphoneActivity.instance().addMembers(group_name.getText().toString());
		}
	}
}
