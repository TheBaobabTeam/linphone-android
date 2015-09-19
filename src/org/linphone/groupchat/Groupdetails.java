package org.linphone.groupchat;
//Please note that the this class is a Fragment of Group details
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

public class Groupdetails extends Fragment implements OnClickListener {
	
	private static Groupdetails instance;
	private TextView back;
	private myArrayAdapter dataAdapter = null;
	
	//Check if its instantiated
	   
		public static boolean isInstanciated() {
			return instance != null;
		}
	
	//Loads the list on the listView
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		View view = inflater.inflate(R.layout.activity_group_details, container, false);
		
		// Retain the fragment across configuration changes
		setRetainInstance(true);
		return view;
	}
	
	//Create a custom Adaapter

	
	
	public void onActivityCreated(Bundle savedInstanceState) {
		  super.onActivityCreated(savedInstanceState);
		  back = (TextView)getView().findViewById(R.id.back_to_main);
		  if (back != null) {
				back.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						getActivity().finish();
					}
				});
			}
		//Generate list View from ArrayList
		displayListFromArrayList();
	}
	private class myArrayAdapter extends ArrayAdapter<String>
	{
		private List<String> contactsList;
		
		public myArrayAdapter(Context context, int textViewResourceId, List<String> contactsList){
			super(context, textViewResourceId,contactsList);
			this.contactsList = new ArrayList<String>();
			this.contactsList.add("Martha");
			this.contactsList.add("Potego");
			this.contactsList.add("Nomzamo");
			this.contactsList.add("Lerato");
			}
		

		
		@Override
		public View getView(int position, View v, ViewGroup parent)
		{
			LayoutInflater vi = null;
			if(v == null){
				vi = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			}
			
			
				v = vi.inflate(R.layout.activity_group_details, null);
			
			return v;
		}

	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	private void displayListFromArrayList() {
		
		ArrayList<String> contactsList = new ArrayList<String>();
		
		dataAdapter = new myArrayAdapter(getActivity(),R.layout.activity_group_details,contactsList);
		ListView listview = (ListView)getActivity().findViewById(R.id.groupDetails);
		listview.setAdapter(dataAdapter);
		
	}

}