package org.linphone.groupchat;

import org.linphone.R;
import org.linphone.Contact;
import org.linphone.LinphoneManager;
import org.linphone.LinphoneActivity;
import org.linphone.ContactsManager;

import org.linphone.core.LinphoneProxyConfig;
import org.linphone.core.LinphoneCore;
import org.linphone.core.LinphoneAddress;

import android.support.v4.app.Fragment;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.CheckBox;
import android.graphics.Color;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.List;
import java.util.ArrayList;

public class AddMembersFragment extends Fragment implements OnClickListener {
	private static AddMembersFragment instance;
	
	private MyAdapter dataAdapter = null;
	private TextView back, next;
	private Button myBtn; 
	protected boolean result;
	private String sipUri;
	private String groupName;
	private ListView listview;
	
	private void TrueResult() {
		result = true;
	}
	
	private void FalseResult() {
		result = false;
	}
    
	private boolean getResult(){
		return result;
	}
    
	public static boolean isInstanciated() {
		return instance != null;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		View view = inflater.inflate(R.layout.add_members_container, container, false);
		
		// Retain the fragment across configuration changes
		setRetainInstance(true);
		
		groupName = getArguments().getString("GroupName");
		
		//back button
		back = (TextView) view.findViewById(R.id.backToGroupName);
		if (back != null) {
			back.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					getActivity().finish();
				}
			});
		}
		
		//next button
		next = (TextView) view.findViewById(R.id.create);
		next.setOnClickListener(this);
		
		//Generate list View from ArrayList
		listview = (ListView) view.findViewById(R.id.contactList);
		if (listview != null) {
			displayListView();
		}
		
		//Checks which checkbox was selected
		/*myBtn = (Button) view.findViewById(R.id.findSelected);
		if (myBtn != null) {
			checkButtonClick();
		}*/
		
		return view;
	}
	
	public static AddMembersFragment instance() {
		return instance;
	}
	
	@Override
	public void onClick(View arg0) {
		final List<String> sipAdress = new ArrayList<String>();
		List<Contact> contactsList = dataAdapter.contactsList;
		
		LinphoneCore lc = LinphoneManager.getLcIfManagerNotDestroyedOrNull();
		LinphoneProxyConfig pc = lc.getDefaultProxyConfig();
		String identity = pc.getIdentity();
		sipAdress.add(identity);	// very important
		
		boolean flag = false;
		for (int i = 0; i < contactsList.size(); i++) {
			Contact contact = contactsList.get(i);
			if(contact.isSelected()) {
				sipAdress.add(contactsList.get(i).getSipAdress(contact));
				
				flag = true;
			}
		}
		
		if (flag == true) {
			String [] groupMembers = sipAdress.toArray(new String[sipAdress.size()]);
			int groupSize = sipAdress.size();
			
			LinphoneActivity.instance().createGroupChat(groupName, groupMembers, groupSize);
			
			getActivity().finish();	// close the current activity
		} else {
			LinphoneActivity.instance().displayCustomToast("At least one contact must be selected.", Toast.LENGTH_SHORT);
		}
	}
	
	private void displayListView() {
		List<Contact> contactsList = new ArrayList<Contact>();
		contactsList = ContactsManager.getSIPContacts();
		dataAdapter = new MyAdapter(getActivity(), R.layout.add_members, contactsList);
		listview.setAdapter(dataAdapter);
		
		listview.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos,long id) {
				Contact con = (Contact)parent.getItemAtPosition(pos);
				//Toast.makeText(getApplicationContext(), "Selected: " + con.getName(), Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	//Custom Adapter
	private class MyAdapter extends ArrayAdapter<Contact> {
		private List<Contact> contactsList;
		
		public MyAdapter(Context context, int textViewResourceId, List<Contact> contactsList) {
			super(context, textViewResourceId,contactsList);
			this.contactsList = new ArrayList<Contact>();
			this.contactsList.addAll(contactsList);
		}
		
		private class ViewHolder {
			CheckBox name;
		}
		
		@Override
		public View getView(int position, View v, ViewGroup parent) {
			ViewHolder holder = null;

			if(v == null) {
				LayoutInflater vi = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.add_members, null);
				
				holder = new ViewHolder();
				holder.name = (CheckBox)v.findViewById(R.id.checkBox1);
				holder.name.setTextColor(Color.BLACK);
				v.setTag(holder);
				
				holder.name.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						CheckBox c = (CheckBox) v;
						Contact con = (Contact)c.getTag();
						//Toast.makeText(getActivity().getApplicationContext(),"SipUri: "+ cf.getSipUri(), Toast.LENGTH_SHORT).show();
						Toast.makeText(getActivity().getApplicationContext(), c.getText() + " selected", Toast.LENGTH_SHORT).show();
						con.setSelected(c.isChecked());
						
						if(c.isChecked()){
							TrueResult();
						} else {
							FalseResult();
						}
					}
				});
			} else {
				holder = (ViewHolder)v.getTag();
			}
			
			Contact contact = contactsList.get(position);
			holder.name.setText(contact.getName());
			holder.name.setChecked(contact.isSelected());
			holder.name.setTag(contact);
			
			return v;
		}
	}
	
	private void checkButtonClick() {
		final List<String> sipAdress = new ArrayList<String>();
		myBtn.setTextColor(Color.BLACK);
		myBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				StringBuffer repsonseText = new StringBuffer();
				repsonseText.append("The following were selected....");
				
				List<Contact> contactsList = dataAdapter.contactsList;
				for(int i = 0; i < contactsList.size(); i++){
					Contact contact = contactsList.get(i);
					sipAdress.add(contactsList.get(i).getSipAdress(contact));
					if(contact.isSelected()){
						repsonseText.append("\n" + contact.getName());
					}
				}
				Toast.makeText(getActivity().getApplicationContext(), repsonseText, Toast.LENGTH_LONG).show();
			}
		});
	}
	
	public List<Contact> getListOfMembers() {
		List<Contact> list = new ArrayList<Contact>();
		List<Contact> contactsList = dataAdapter.contactsList;
		
		for(int i = 0; i < contactsList.size(); i++) {
			Contact contact = contactsList.get(i);
			if(contact.isSelected()){
				list.add(contact);
			}
		}
		return list;
	}
	
	private void returnPictureSip() {
		//hold on for now
	}
}
