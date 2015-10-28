package org.linphone.groupchat;

import org.linphone.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutFragment extends Fragment implements OnClickListener {
	private static AboutFragment instance;
	
	private TextView close;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		View view = inflater.inflate(R.layout.about_container, container, false);
		
		// Retain the fragment across configuration changes
		setRetainInstance(true);
		
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		int width = dm.widthPixels;
		int height = dm.heightPixels;
		
		getActivity().getWindow().setLayout((int)(width*0.8),(int)(height*0.6));
		
		return view;
	}

	@Override
	public void onClick(View v) {
		
	}
}
