package org.linphone.groupchat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.linphone.LinphoneActivity;
import org.linphone.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NewGroupFragment extends Fragment implements OnClickListener {
	private static NewGroupFragment instance;
	
	
	private static final int REQUEST_CAMERA = 2;
	private static final int SELECT_FILE = 1;
	private TextView back, next;
	private TextWatcher textWatcher;
	private EditText group_name;
	private ImageView im;
	private String uploadImagePath = "";
	
	public static boolean isInstanciated() {
		return instance != null;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		View view = inflater.inflate(R.layout.new_group, container, false);
		
		// Retain the fragment across configuration changes
		setRetainInstance(true);
		
		group_name = (EditText)view.findViewById(R.id.groupName);

		back = (TextView) view.findViewById(R.id.back);
		if (back != null) {
			back.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					getActivity().finish();
				}
			});
		}
		
		textWatcher = new TextWatcher() {
			public void afterTextChanged(Editable arg0) {}

			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				if (group_name.getText().toString().equals("")) {
					next.setEnabled(false);
				} else {
					next.setEnabled(true);
				}
			}
		};
		
		next = (TextView) view.findViewById(R.id.next);
		next.setOnClickListener(this);
		
		//select group icon
		im = (ImageView) view.findViewById(R.id.GroupIcon);
		im.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				selectImage();
			}
		});
		
		return view;
	}

	public static NewGroupFragment instance() {
		return instance;
	}
	
	@Override
	public void onClick(View v) {
		if (group_name.getText().toString().equals("")) {
			LinphoneActivity.instance().displayCustomToast("Please provide group name", Toast.LENGTH_SHORT);
		} else {
			LinphoneActivity.instance().addMembers(group_name.getText().toString());
			
			getActivity().finish();
		}
	}
	
	//select Image
	private void selectImage(){
		final CharSequence[] items = { "Take Photo", "Choose from Gallery", "Cancel" };
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Add Group Icon!");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			
		@Override
		public void onClick(DialogInterface dialog, int item) {
			if (items[item].equals("Take Photo")) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intent, REQUEST_CAMERA);
			} 
			else if (items[item].equals("Choose from Gallery")) {
				Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				intent.setType("image/*");
				startActivityForResult(
				Intent.createChooser(intent, "Select File"),SELECT_FILE);
			} 
			else if (items[item].equals("Cancel")) {
				dialog.dismiss();
				}
		}
		});
		builder.show();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == REQUEST_CAMERA) {
				Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
				ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
				File destination = new File(Environment.getExternalStorageDirectory(),System.currentTimeMillis() + ".jpg");
				
				FileOutputStream fo;
				try {
					destination.createNewFile();
					fo = new FileOutputStream(destination);
					fo.write(bytes.toByteArray());
					fo.close();
					} 
				catch (FileNotFoundException e) {
					e.printStackTrace();
					} 
				catch (IOException e) {
					e.printStackTrace();
					}
				
				im.setImageBitmap(thumbnail);
				} 
			else if(requestCode == SELECT_FILE){
				Uri selectedImageUri = data.getData();
		    String tempPath = getPath(selectedImageUri, getActivity());
		    Bitmap bm;
		    
		    BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
		    bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
		    im.setImageBitmap(bm);
		    uploadImagePath = tempPath;
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public String getPath(Uri uri, Activity activity) {
	    String[] projection = { MediaColumns.DATA };
	    Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
	    int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
	    cursor.moveToFirst();
	    return cursor.getString(column_index);

	}
}
