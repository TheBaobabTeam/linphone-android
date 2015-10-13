package org.linphone.groupchat;

import java.util.ArrayList;

import java.util.List;

import org.linphone.ChatFragment;
import org.linphone.Contact;
import org.linphone.ContactsFragment;
import org.linphone.ContactsManager;
import org.linphone.LinphoneActivity;
import org.linphone.R;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class aboutFragment extends Fragment implements OnClickListener {
	
	private static aboutFragment instance;
	private TextView back;
	private TextView next;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		View view = inflater.inflate(R.layout.welcome, container, false);
		
		// Retain the fragment across configuration changes
		setRetainInstance(true);
		next = (TextView) view.findViewById(R.id.textView1);
		next.setOnClickListener(this);
		return view;
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		  super.onActivityCreated(savedInstanceState);
		  back = (TextView)getView().findViewById(R.id.back_from_welcome);
		  if (back != null) {
				back.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						getActivity().finish();
					}
				});
			}
		  
		  
		  
		//Generate list View from ArrayList
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		LinphoneActivity.instance().nextScreenAfterWelcome();
		//nextScreenAbout
		
	}
	
	
}
