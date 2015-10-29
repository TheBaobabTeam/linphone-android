package org.linphone.groupchat;
//Please note that the this class is a Fragment of Group details
import java.util.ArrayList;
import java.util.List;

import org.linphone.Contact;
import org.linphone.R;
import org.linphone.LinphoneManager;
//import org.linphone.LinphoneUtils;

import org.linphone.core.LinphoneCore;
import org.linphone.core.LinphoneChatRoom;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class GroupdetailsFragment extends Fragment implements OnClickListener {
	
	private static GroupdetailsFragment instance;
	private TextView back;
	private MyAdapter dataAdapter = null;
	
	private LinphoneChatRoom chatRoom;
	private String sipUri;
	
	//Check if its instantiated
	   
		public static boolean isInstanciated() {
			return instance != null;
		}
	
	//Loads the list on the listView
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		View view = inflater.inflate(R.layout.activity_group_details, container, false);
		
		// Retain the fragment across configuration changes
		setRetainInstance(true);
		
		//Retrieve parameter from intent
		sipUri = getArguments().getString("SipUri");
		
		LinphoneCore lc = LinphoneManager.getLcIfManagerNotDestroyedOrNull();
		if (lc != null) {
			chatRoom = lc.getOrCreateChatRoom(sipUri);
			
			//String members = "";
			
			//String[] mem = chatRoom.getMembers();
			
			
			//Only works if using liblinphone storage
			chatRoom.markAsRead();
		}
		
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		  super.onActivityCreated(savedInstanceState);
		  back = (TextView)getView().findViewById(R.id.back);
		  if (back != null) {
				back.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						getActivity().finish();
					}
				});
			}
		//Generate list View from ArrayList
		  displayListView();
	}
	
	//Custom Adapter
		private class MyAdapter extends ArrayAdapter<String>{
			private List<String> contactsList;
			
			public MyAdapter(Context context, int textViewResourceId, List<String> contactsList){
				super(context, textViewResourceId,contactsList);
				this.contactsList = new ArrayList<String>();
				this.contactsList.addAll(contactsList);
				}
			
			private class ViewHolder{
				TextView name;
				}
			
			@Override
			public View getView(int position, View v, ViewGroup parent){
				ViewHolder holder = null;
				
				if(v == null){
					LayoutInflater vi = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					v = vi.inflate(R.layout.group_details_list_item, null);
					
					holder = new ViewHolder();
					holder.name = (TextView)v.findViewById(R.id.name);
					holder.name.setTextColor(Color.BLACK);
					v.setTag(holder);
					}
				else{
					holder = (ViewHolder)v.getTag();
					}
				
				String contact = contactsList.get(position);
				holder.name.setText(contact);
				holder.name.setTag(contact);
				
				return v;
				}
			}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	public void displayListView() {
		List<String> contactsList = new ArrayList<String>();
		//contactsList = AddMembersFragment.instance().getListOfMembers();
		
		String[] mem = chatRoom.getMembers();
		if (mem != null) {
			for (int i = 0; i < mem.length; i++) {
				contactsList.add(mem[i]);
			}
		}
		
		dataAdapter = new MyAdapter(getActivity(),R.layout.group_details_list_item,contactsList);
		ListView listview = (ListView)getActivity().findViewById(R.id.groupDetails);
		listview.setAdapter(dataAdapter);

	}
}