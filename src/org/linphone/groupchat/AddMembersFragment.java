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

public class AddMembersFragment extends Fragment implements OnClickListener {
	
	private static AddMembersFragment instance;
	
	private MyAdapter dataAdapter = null;
	private TextView back, next;
	private Button myBtn; 
	protected boolean result;
	private String sipUri;
	
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
		return view;
	}
	
	@Override
	 public void onActivityCreated(Bundle savedInstanceState) {
	  super.onActivityCreated(savedInstanceState);
	  
	//next button
	next = (TextView)getView().findViewById(R.id.create);
	next.setOnClickListener(this);
	  
	//back button
			back = (TextView)getView().findViewById(R.id.backToGroupName);
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
	   
	  //Checks which checkbox was selected
	  checkButtonClick();
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
			CheckBox name;
			}
		
		@Override
		public View getView(int position, View v, ViewGroup parent){
			ViewHolder holder = null;

			if(v == null){
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
						}
						else{
							FalseResult();
						}
						}
					});
				}
			else{
				holder = (ViewHolder)v.getTag();
				}
			
			Contact contact = contactsList.get(position);
			holder.name.setText(contact.getName());
			holder.name.setChecked(contact.isSelected());
			holder.name.setTag(contact);
			
			return v;
			}
		}
		
	private void displayListView() {
		List<Contact> contactsList = new ArrayList<Contact>();
		contactsList = ContactsManager.getSIPContacts();
		dataAdapter = new MyAdapter(getActivity(),R.layout.add_members,contactsList);
		ListView listview = (ListView)getActivity().findViewById(R.id.contactList);
		listview.setAdapter(dataAdapter);
		
		listview.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos,long id) {
				Contact con = (Contact)parent.getItemAtPosition(pos);
				//Toast.makeText(getApplicationContext(), "Selected: " + con.getName(), Toast.LENGTH_SHORT).show();
				}
			});
	}
	
	
	
	
	public static AddMembersFragment instance() {
		return instance;
	}
	
	private void checkButtonClick() {
	
		final List<String> sipAdress = new ArrayList<String>();
		myBtn = (Button)getActivity().findViewById(R.id.findSelected);
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
	
    private void returnPictureSip()
    {
    	
    	
    }

	@Override
	public void onClick(View arg0) {
		//if(getResult()){
			//String name = "Baobab";
			//Toast.makeText(getActivity().getApplicationContext(),"Result is: "+ result, Toast.LENGTH_SHORT).show();
			LinphoneActivity.instance().createGroupChat();
			//}
		//else{
			//Toast.makeText(getActivity().getApplicationContext(),"Result is: "+ result, Toast.LENGTH_SHORT).show();
			//LinphoneActivity.instance().displayCustomToast("At least one must be selected.", Toast.LENGTH_SHORT);
			//}
		
	}
}
