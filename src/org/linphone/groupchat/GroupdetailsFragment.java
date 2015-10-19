package org.linphone.groupchat;
//Please note that the this class is a Fragment of Group details
import java.util.ArrayList;
import java.util.List;

import org.linphone.Contact;
import org.linphone.LinphoneActivity;
import org.linphone.R;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class GroupdetailsFragment extends Fragment implements OnClickListener {
	
	private static GroupdetailsFragment instance;
	private TextView back;
	private Button AddParticipant, ExitGroup;
	private MyAdapter dataAdapter = null;
	
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
		
		//Add more members to a group
		AddParticipant = (Button)view.findViewById(R.id.AddParticipant);
		AddParticipant.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//Exit or delete group
		ExitGroup = (Button)view.findViewById(R.id.exitGroup);
		ExitGroup.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
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
		private class MyAdapter extends ArrayAdapter<Contact>{
			private List<Contact> contactsList;
			
			public MyAdapter(Context context, int textViewResourceId, List<Contact> contactsList){
				super(context, textViewResourceId,contactsList);
				this.contactsList = new ArrayList<Contact>();
				this.contactsList.addAll(contactsList);
				}
			
			private class ViewHolder{
				TextView name;
				TextView delete;
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
					
					holder.delete = (TextView)v.findViewById(R.id.deleteParticipant);
					
					v.setTag(holder);
					}
				else{
					holder = (ViewHolder)v.getTag();
					}
				
				Contact contact = contactsList.get(position);
				holder.name.setText(contact.getName());
				holder.name.setTag(contact);
				
				
				//delete contact
				holder.delete.setOnClickListener(new View.OnClickListener(){

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						
					}
					
				});
				
				return v;
				}
			}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	public void displayListView() {
		List<Contact> contactsList = new ArrayList<Contact>();
		contactsList = AddMembersFragment.instance().getListOfMembers();
		
		dataAdapter = new MyAdapter(getActivity(),R.layout.group_details_list_item,contactsList);
		ListView listview = (ListView)getActivity().findViewById(R.id.groupDetails);
		listview.setAdapter(dataAdapter);

	}
}