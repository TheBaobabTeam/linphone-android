package org.linphone.groupchat;

import org.linphone.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import org.linphone.core.LinphoneChatRoom;
import org.linphone.core.LinphoneCore;

import android.content.Intent;


/**
 * @author Mpedi Mello
 */
public class AddMembersActivity extends FragmentActivity{
	private static final String ADD_MEMBERS_FRAGMENT = "addMembersFragment";
	private AddMembersFragment addMembersFragment;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_members_activity);
		
		Bundle extras = new Bundle();
		extras.putString("GroupName", getIntent().getExtras().getString("GroupName"));
		
		AddMembersFragment fragment = new AddMembersFragment();
		fragment.setArguments(extras);
		getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainerMembers, fragment, "addMembersFragment").commit();
		
		FragmentManager fm = getSupportFragmentManager();
		addMembersFragment = (AddMembersFragment) fm.findFragmentByTag(ADD_MEMBERS_FRAGMENT);

	    // If the Fragment is non-null, then it is currently being
	    // retained across a configuration change.
		if (addMembersFragment == null) {
			addMembersFragment = new AddMembersFragment();
			addMembersFragment.setArguments(extras);
			fm.beginTransaction().add(R.id.fragmentContainerMembers, addMembersFragment, ADD_MEMBERS_FRAGMENT).commit();
	    }
	}
	
	
	
	
	
	/*MyAdapter dataAdapter = null;
	private TextView back, next;
	private boolean result;
	private String groupName;
	private LinphoneChatRoom chatRoom;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_members_activity);
		
		Intent intent = getIntent();
		
		groupName = intent.getStringExtra("GroupName");
		
		back = (TextView)findViewById(R.id.back);
		if (back != null) {
			back.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}
		
		displayListView();
		checkButtonClick();
		
		next = (TextView)findViewById(R.id.create);
		next.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				if(result){
					/*String name = "Baobab";
					LinphoneActivity.instance().createGroupChat(name.toString());
					
					List<Contact> contactsList = dataAdapter.contactsList;
					
					String [] groupMembers = new String[contactsList.size()];
					int groupSize = contactsList.size();
					
					for(int i = 0; i < contactsList.size(); i++){
						Contact contact = contactsList.get(i);
						if(contact.isSelected()){
							//repsonseText.append("\n" + contact.getName());
							for (String numberOrAddress : contact.getNumbersOrAddresses()) {
								String displayednumberOrAddress = numberOrAddress;
								if (numberOrAddress.startsWith("sip:")) {
									groupMembers[i] = displayednumberOrAddress;
								}
							}
						}
					}
					
					LinphoneCore lc = LinphoneManager.getLcIfManagerNotDestroyedOrNull();
					if (lc != null) {
						chatRoom = lc.getOrCreateGroupChatRoom(groupName, groupMembers, groupSize, 0, 0);
						
						if (chatRoom != null) {
							chatRoom.sendGroupMessage(lc.getPrimaryContactUsername() + ": created this group.");
							
							//if (LinphoneActivity.isInstanciated()) {
								LinphoneActivity.instance().onMessageSent(chatRoom.getPeerAddress().asStringUriOnly(), lc.getPrimaryContactUsername() + ": created this group.");
							//}
							
							LinphoneActivity.instance().goToChatList();
						}
					}
				}
				else{
					LinphoneActivity.instance().displayCustomToast("At least one must be selected.", Toast.LENGTH_SHORT);
				}
			}
		});
	}
	
	private void displayListView(){
		List<Contact> contactsList = new ArrayList<Contact>();
		contactsList = ContactsManager.getSIPContacts();
		dataAdapter = new MyAdapter(this,R.layout.add_members,contactsList);
		ListView listview = (ListView)findViewById(R.id.contactList);
		listview.setAdapter(dataAdapter);
		
		listview.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos,long id) {
				Contact con = (Contact)parent.getItemAtPosition(pos);
				//Toast.makeText(getApplicationContext(), "Selected: " + con.getName(), Toast.LENGTH_SHORT).show();
				}
			});
	}
	
	
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
				LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
						Toast.makeText(getApplicationContext(), "Selected: " +c.getText(), Toast.LENGTH_SHORT).show();
						con.setSelected(c.isChecked());
						
						if(c.isChecked()){
							doOnTrueResult();
						}
						else{
							doOnFalseResult();
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
	
	private void doOnTrueResult() {
        result = true;
    }

    private void doOnFalseResult() {
        result = false;
    }
    
    
    private void returnPictureSip()
    {
    	
    	List<Contact> contactsList = dataAdapter.contactsList;
		for(int i = 0; i < contactsList.size(); i++){
			Contact contact = contactsList.get(i);
			if(contact.isSelected()){
				
			}
		}
    	
    }
	
	private void checkButtonClick(){
		Button myBtn = (Button)findViewById(R.id.findSelected);
		myBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				StringBuffer repsonseText = new StringBuffer();
				repsonseText.append("The following were selected....\n");
				
				List<Contact> contactsList = dataAdapter.contactsList;
				for(int i = 0; i < contactsList.size(); i++){
					Contact contact = contactsList.get(i);
					if(contact.isSelected()){
						repsonseText.append("\n" + contact.getName());
					}
				}
				Toast.makeText(getApplicationContext(), repsonseText, Toast.LENGTH_LONG).show();
			}
		});
	}*/
	
}

