package org.linphone.groupchat;
//Please note that the this class is a Fragment of Group details
import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
import org.linphone.ChatFragment;
import org.linphone.Contact;
import org.linphone.ContactsFragment;
import org.linphone.ContactsManager;
import org.linphone.LinphoneActivity;
import org.linphone.R;




=======
import org.linphone.Contact;
import org.linphone.R;

>>>>>>> create_group_chat
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
<<<<<<< HEAD
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
=======
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
>>>>>>> create_group_chat

public class GroupdetailsFragment extends Fragment implements OnClickListener {
	
	private static GroupdetailsFragment instance;
	private TextView back;
<<<<<<< HEAD
	private myArrayAdapter dataAdapter = null;
=======
	private MyAdapter dataAdapter = null;
>>>>>>> create_group_chat
	
	//Check if its instantiated
	   
		public static boolean isInstanciated() {
			return instance != null;
		}
	
	//Loads the list on the listView
<<<<<<< HEAD
=======
	@Override
>>>>>>> create_group_chat
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		View view = inflater.inflate(R.layout.activity_group_details, container, false);
		
		// Retain the fragment across configuration changes
		setRetainInstance(true);
		return view;
	}
<<<<<<< HEAD
	
	//Create a custom Adaapter

	
	
	public void onActivityCreated(Bundle savedInstanceState) {
		  super.onActivityCreated(savedInstanceState);
		  back = (TextView)getView().findViewById(R.id.back_to_main);
=======

	public void onActivityCreated(Bundle savedInstanceState) {
		  super.onActivityCreated(savedInstanceState);
		  back = (TextView)getView().findViewById(R.id.back);
>>>>>>> create_group_chat
		  if (back != null) {
				back.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						getActivity().finish();
					}
				});
			}
		//Generate list View from ArrayList
<<<<<<< HEAD
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
=======
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
				
				Contact contact = contactsList.get(position);
				holder.name.setText(contact.getName());
				holder.name.setTag(contact);
				
				return v;
				}
			}
>>>>>>> create_group_chat
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
<<<<<<< HEAD
	private void displayListFromArrayList() {
		
		ArrayList<String> contactsList = new ArrayList<String>();
		
		dataAdapter = new myArrayAdapter(getActivity(),R.layout.activity_group_details,contactsList);
		ListView listview = (ListView)getActivity().findViewById(R.id.groupDetails);
		listview.setAdapter(dataAdapter);
		
	}

=======
	
	public void displayListView() {
		List<Contact> contactsList = new ArrayList<Contact>();
		contactsList = AddMembersFragment.instance().getListOfMembers();
		
		dataAdapter = new MyAdapter(getActivity(),R.layout.group_details_list_item,contactsList);
		ListView listview = (ListView)getActivity().findViewById(R.id.groupDetails);
		listview.setAdapter(dataAdapter);

	}
>>>>>>> create_group_chat
}