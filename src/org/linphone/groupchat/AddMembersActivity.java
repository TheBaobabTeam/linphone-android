package org.linphone.groupchat;

import java.util.ArrayList;
import java.util.List;

import org.linphone.Contact;
import org.linphone.ContactsManager;
import org.linphone.LinphoneActivity;
import org.linphone.R;
import android.app.ListActivity;

import android.content.Context;
import android.graphics.Color;
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

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_members_activity);
		
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
		List<Contact> contactsList = new ArrayList<Contact>();
		contactsList = ContactsManager.getSIPContacts();
		dataAdapter = new MyAdapter(this,R.layout.add_members,contactsList);
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
						Toast.makeText(getApplicationContext(), "Clicked on: " +c.getText(), Toast.LENGTH_LONG).show();
						con.setSelected(c.isChecked());
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
	}
	
}

