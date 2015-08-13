package org.linphone.groupchat;

import java.util.ArrayList;
import java.util.List;

import org.linphone.Contact;
import org.linphone.ContactsManager;
import org.linphone.LinphoneActivity;
import org.linphone.R;
import android.app.ListActivity;

import android.content.Context;
import android.os.Bundle;
import android.provider.Contacts;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * @author Mpedi Mello
 */
public class AddMembersActivity extends FragmentActivity{
	MyAdapter dataAdapter = null;
	private TextView back, next;
	
	//private static final String ADD_MEMBERS_FRAGMENT = "addMembersFragment";

	//private AddMembersFragment addMembersFragment;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_members_activity);
		
		/*AddMembersFragment fragment = new AddMembersFragment();
		getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, fragment, "AddMembersFragment").commit();
		
		FragmentManager fm = getSupportFragmentManager();
		addMembersFragment = (AddMembersFragment) fm.findFragmentByTag(ADD_MEMBERS_FRAGMENT);

	    // If the Fragment is non-null, then it is currently being
	    // retained across a configuration change.
		if (addMembersFragment == null) {
			addMembersFragment = new AddMembersFragment();
			fm.beginTransaction().add(R.id.fragmentContainer, addMembersFragment, ADD_MEMBERS_FRAGMENT).commit();
	    }*/
		
		back = (TextView)findViewById(R.id.back);
		if (back != null) {
			back.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}
		
		next = (TextView)findViewById(R.id.next);
		next.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				LinphoneActivity.instance().displayCustomToast("At least one contact must be selected.", Toast.LENGTH_SHORT);
			}
		});
		
		displayListView();
		checkButtonClick();
	}
	
	private void displayListView(){
		List<Contact> contactList = new ArrayList<Contact>();
		contactList = ContactsManager.getSIPContacts();
		//add contactsmanager.getSipcontact()
		dataAdapter = new MyAdapter(this,R.layout.add_members,contactList);
		ListView listview = (ListView)findViewById(R.id.contactList);
		listview.setAdapter(dataAdapter);
		
		listview.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos,long id) {
				Contact con = (Contact)parent.getItemAtPosition(pos);
				Toast.makeText(getApplicationContext(), "Clicked on row: " + con.getName(), Toast.LENGTH_LONG).show();
				}
			});
	}
	
	
	private class MyAdapter extends ArrayAdapter<Contact>{
		private List<Contact> contactList2;
		
		public MyAdapter(Context context, int textViewResourceId, List<Contact> contactList2){
			super(context, textViewResourceId,contactList2);
			this.contactList2 = new ArrayList<Contact>();
			this.contactList2.addAll(contactList2);
		}
		
		private class ViewHolder{
			TextView code;
			CheckBox name;
		}
		
		@Override
		public View getView(int position, View v, ViewGroup parent){
			ViewHolder holder = null;
			
			if(v == null){
				LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.add_members, null);
				
				holder = new ViewHolder();
				holder.code = (TextView)v.findViewById(R.id.code);
				holder.name = (CheckBox)v.findViewById(R.id.contactList);
				v.setTag(holder);
				
				/*holder.name.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						CheckBox c = (CheckBox)v;
						Contact con = (Contact)c.getTag();
						Toast.makeText(getApplicationContext(), "Clicked on: " +c.getTag(), Toast.LENGTH_LONG).show();
						Contact.setSelected(c.isSelected());
					}
				});*/
			}
			else{
				holder = (ViewHolder)v.getTag();
			}
			
			/*Contact contact = contactList2.get(position);
			holder.code.setText(" (" + contact.getID() + ")");
			//holder.name.setText(contact.getName());
			holder.name.setChecked(contact.isSelected());
			holder.name.setTag(contact);*/
			
			return v;
		}
	}
	
	private void checkButtonClick(){
		Button myBtn = (Button)findViewById(R.id.findSelected);
		myBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				StringBuffer rep = new StringBuffer();
				rep.append("The followong were selected....");
				
				List<Contact> con = dataAdapter.contactList2;
				for(int i = 0; i < con.size(); i++){
					Contact c = con.get(i);
					
					if(c.isSelected()){
						rep.append("\n" + c.getName());
					}
				}
				Toast.makeText(getApplicationContext(), rep, Toast.LENGTH_LONG).show();
			}
			
		});
	}
	
}

