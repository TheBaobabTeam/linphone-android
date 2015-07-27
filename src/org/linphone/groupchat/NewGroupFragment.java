package org.linphone.groupchat;

import org.linphone.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class NewGroupFragment extends Fragment implements OnClickListener {
	private static NewGroupFragment instance;
	
	private TextView back;
	
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

		back = (TextView) view.findViewById(R.id.back);
		if (back != null) {
			back.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					getActivity().finish();
				}
			});
		}
		
		return view;
	}

	public static NewGroupFragment instance() {
		return instance;
	}
	
	@Override
	public void onClick(View v) {
		//
	}
}
