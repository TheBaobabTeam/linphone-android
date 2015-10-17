package org.linphone.groupchat;

/**
 * @Author TeamBaobab
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import android.graphics.Matrix;
import java.util.ArrayList;
import java.util.List;


import org.linphone.ChatStorage;
import org.linphone.Contact;
import org.linphone.ContactsManager;
import org.linphone.LinphoneActivity;
import org.linphone.LinphoneManager;
import org.linphone.LinphoneService;
import org.linphone.LinphoneUtils;
import org.linphone.R;
import org.linphone.compatibility.Compatibility;
import org.linphone.core.LinphoneAddress;
import org.linphone.core.LinphoneBuffer;
import org.linphone.core.LinphoneChatMessage;
import org.linphone.core.LinphoneChatMessage.LinphoneChatMessageListener;
import org.linphone.core.LinphoneChatRoom;
import org.linphone.core.LinphoneContent;
import org.linphone.core.LinphoneCore;
import org.linphone.core.LinphoneChatMessage.State;
import org.linphone.core.LinphoneCoreException;
import org.linphone.core.LinphoneCoreListenerBase;
import org.linphone.mediastream.Log;
import org.linphone.ui.AvatarWithShadow;
import org.linphone.ui.BubbleChat;
import org.linphone.core.LinphoneProxyConfig;
import org.linphone.core.LinphoneCoreFactory;
import android.media.ExifInterface;
import android.support.v4.content.CursorLoader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupMenu;
import android.widget.PopupWindow;

public class GroupChatRoomFragment extends Fragment implements OnClickListener, LinphoneChatMessageListener {
	
	private static GroupChatRoomFragment instance;
	
	LinearLayout layoutOfPopup;
	PopupWindow popup;
	ImageView img1;
	ImageView img2;

	private static final int ADD_PHOTO = 1337;
	private static final int MENU_DELETE_MESSAGE = 0;
	private static final int MENU_COPY_TEXT = 6;
	private static final int MENU_RESEND_MESSAGE = 7;
	private static final int SIZE_MAX = 2048;

	private LinphoneChatRoom chatRoom;
	private String sipUri;
	private String displayName;
	private String pictureUri;
	private EditText message;
	private ImageView cancelUpload;
	private LinearLayout topBar;
	private TextView sendImage, sendMessage, contactName, remoteComposing, back, groupNameView, participantsView;
	private ImageView contactPicture;
	private RelativeLayout uploadLayout, textLayout;
	private ListView messagesList;
	private ImageView attach;
	private ImageView moreOptions;
	private ProgressBar progressBar;
	private Uri imageToUploadUri;
	private TextWatcher textWatcher;
	private ViewTreeObserver.OnGlobalLayoutListener keyboardListener;
	private ChatMessageAdapter adapter;
	
	private LinphoneCoreListenerBase mListener;
	private ByteArrayOutputStream mDownloadedImageStream;
	private ByteArrayInputStream mUploadingImageStream;
	private int mDownloadedImageStreamSize;
	private LinphoneChatMessage currentMessageInFileTransferUploadState;

	public static boolean isInstanciated() {
		return instance != null;
	}
//It works!
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		View view = inflater.inflate(R.layout.groupchatroom, container, false);
	
		// Retain the fragment across configuration changes
		setRetainInstance(true);

		//Retrieve parameter from intent
		sipUri = getArguments().getString("SipUri");
		displayName = getArguments().getString("DisplayName");
		pictureUri = getArguments().getString("PictureUri");

		//Initialize UI
		contactName = (TextView) view.findViewById(R.id.contactName);
		contactPicture = (ImageView) view.findViewById(R.id.GroupChatIcon);
		messagesList = (ListView) view.findViewById(R.id.groupchatMessageList);
		textLayout = (RelativeLayout) view.findViewById(R.id.groupmessageLayout);
		progressBar = (ProgressBar) view.findViewById(R.id.groupprogressbar);
		groupNameView = (TextView) view.findViewById(R.id.groupname);
		groupNameView.setText(displayName);
		participantsView = (TextView) view.findViewById(R.id.grouppartic);
		
		String members = "";
		List<Contact> list = new ArrayList<Contact>();
		list = AddMembersFragment.instance().getListOfMembers();
		
		for(int i = 0; i < list.size(); i++){
			members += list.get(i).getName() + ",";
		}
		
		participantsView.setText(members);
		//Set OnClickListeners and Initialize UI
		topBar = (LinearLayout) view.findViewById(R.id.grouptopbar);
		//topBar.setOnClickListener(this);

		attach = (ImageView) view.findViewById(R.id.image_attach);
		//attach.setOnClickListener(this);

		moreOptions = (ImageView) view.findViewById(R.id.image_more);
		//moreOptions.setOnClickListener(this);

		attach.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				PopupMenu pop = new PopupMenu(getActivity(),attach);
				pop.getMenuInflater().inflate(R.menu.popup_menu_attach, pop.getMenu());
				
				pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
					
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						switch(item.getItemId()){
						
						case R.id.attachPic :
							pickImage();
							return true;
						case R.id.attachAudio :
							Intent intent = new Intent(getActivity(),VoiceRecordActivity.class);
							getActivity().startActivity(intent);
							return true;
						default : return false;
						}
					
					}
				});
				
				pop.show();
			}
		});

		//works
		topBar.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent = new Intent(getActivity(),GroupDetailsActivity.class);
				getActivity().startActivity(intent);
			}
		});
		
		//works
		moreOptions.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				PopupMenu pop = new PopupMenu(getActivity(),moreOptions);
				pop.getMenuInflater().inflate(R.menu.group_popup_menu, pop.getMenu());
				
				pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
					
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						switch(item.getItemId()){
						
						case R.id.groupdetailsmenu :
							Intent intent = new Intent(getActivity(),GroupDetailsActivity.class);
							getActivity().startActivity(intent);
							return true;
						default : return false;
						}
					
					}
				});
				
				pop.show();
			}
		});

		sendMessage = (TextView) view.findViewById(R.id.groupsendMessage);
		sendMessage.setOnClickListener(this);

		remoteComposing = (TextView) view.findViewById(R.id.groupremoteComposing);
		remoteComposing.setVisibility(View.GONE);

		uploadLayout = (RelativeLayout) view.findViewById(R.id.groupuploadLayout);
		uploadLayout.setVisibility(View.GONE);

		//displayChatHeader(displayName, pictureUri);

		//Manage multiline
		message = (EditText) view.findViewById(R.id.groupmessage);
		if (!getResources().getBoolean(R.bool.allow_chat_multiline)) {
			message.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE);
			message.setMaxLines(1);
		}

		sendImage = (TextView) view.findViewById(R.id.groupsendPicture);
		if (!getResources().getBoolean(R.bool.disable_chat_send_file)) {
			sendImage.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					pickImage();
				}
			});
		} else {
			sendImage.setEnabled(false);
		}

		//Go Add the back button in xml
		/*back = (TextView) view.findViewById(R.id.back);
		if (back != null) {
			back.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					getActivity().finish();
				}
			});
		}*/

		/*cancelUpload = (ImageView) view.findViewById(R.id.cancelUpload);
		cancelUpload.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (currentMessageInFileTransferUploadState != null) {
					uploadLayout.setVisibility(View.GONE);
					textLayout.setVisibility(View.VISIBLE);
					progressBar.setProgress(0);
					currentMessageInFileTransferUploadState.cancelFileTransfer();
					currentMessageInFileTransferUploadState = null;
				}
			}
		});*/

		
		LinphoneCore lc = LinphoneManager.getLcIfManagerNotDestroyedOrNull();
		if (lc != null) {
			//if (getArguments().getInt("ChatType", 0) == 1) {
				/*String groupName = getArguments().getString("GroupName");
				String [] groupMembers = getArguments().getStringArray("GroupMembers");
				int groupSize = getArguments().getInt("GroupSize");
				
				//chatRoom = lc.getOrCreateGroupChatRoom(groupName, groupMembers, groupSize, 0, 0);
				
				if (chatRoom != null) {
					LinphoneAddress la = chatRoom.getPeerAddress();
					
					sipUri = la.asStringUriOnly();
					displayName = la.getDisplayName();
				}
				
				//String toDisplay = "Group Name : " + groupName + "\nGroup Members: " + Arrays.toString(groupMembers) + "\nGroup Size: " + (sipAdress.size() + 1);
				//Toast.makeText(getActivity().getApplicationContext(), "ChatFragment", Toast.LENGTH_LONG).show();
				chatRoom = lc.getOrCreateChatRoom(sipUri);
				
				LinphoneProxyConfig pc = lc.getDefaultProxyConfig();
				String identity = pc.getIdentity();
				
				LinphoneAddress la;
				String id = "";
				try {
					la = LinphoneCoreFactory.instance().createLinphoneAddress(identity);
					id = la.getUserName();
				} catch (LinphoneCoreException e) {
					Log.e("Cannot display chat",e);
					//return;
				}
				
				String groupName = getArguments().getString("GroupName");
				
				String message = id + " created group " + groupName;
				chatRoom.sendGroupMessage(message);
				
				if (LinphoneActivity.isInstanciated()) {
					LinphoneActivity.instance().onMessageSent(sipUri, message);
				}
				
				invalidate();
				//Log.i("Sent message current status: " + message.getStatus());*/
			//} else {
				chatRoom = lc.getOrCreateChatRoom(sipUri);
			//}
			//Only works if using liblinphone storage
			chatRoom.markAsRead();
		}

		mListener = new LinphoneCoreListenerBase(){
			@Override
			public void messageReceived(LinphoneCore lc, LinphoneChatRoom cr, LinphoneChatMessage message) {
				System.out.println("Message: [" + message.getText() + "]");
				
				LinphoneAddress from = cr.getPeerAddress();
				if (from.asStringUriOnly().equals(sipUri)) {
					invalidate();
				}
			}
			
			//Presence
			@Override
			public void isComposingReceived(LinphoneCore lc, LinphoneChatRoom room) {
				if (chatRoom != null && room != null && chatRoom.getPeerAddress().asStringUriOnly().equals(room.getPeerAddress().asStringUriOnly())) {
					remoteComposing.setVisibility(chatRoom.isRemoteComposing() ? View.VISIBLE : View.GONE);
				}
			}
		};

		 textWatcher = new TextWatcher() {
			public void afterTextChanged(Editable arg0) {}

			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				if (message.getText().toString().equals("")) {
					sendMessage.setEnabled(false);
				} else {
					if (chatRoom != null)
						chatRoom.compose();
					sendMessage.setEnabled(true);
				}
			}
		};
		
		

		// Force hide keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		return view;
	}
//Instance of this fragment
	public static GroupChatRoomFragment instance() {
		return instance;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putString("messageDraft", message.getText().toString());

		super.onSaveInstanceState(outState);
	}

	private void addVirtualKeyboardVisiblityListener() {
		keyboardListener = new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
			Rect visibleArea = new Rect();
			getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(visibleArea);

			int heightDiff = getActivity().getWindow().getDecorView().getRootView().getHeight() - (visibleArea.bottom - visibleArea.top);
				if (heightDiff > 200) {
					showKeyboardVisibleMode();
				} else {
					hideKeyboardVisibleMode();
				}
			}
		};
		getActivity().getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(keyboardListener);
	}
	public void showKeyboardVisibleMode() {
		boolean isOrientationLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
		if (isOrientationLandscape && topBar != null) {
			topBar.setVisibility(View.GONE);
		}
		contactPicture.setVisibility(View.GONE);
		//scrollToEnd();
	}

	public void hideKeyboardVisibleMode() {
		boolean isOrientationLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
		contactPicture.setVisibility(View.VISIBLE);
		if (isOrientationLandscape && topBar != null) {
			topBar.setVisibility(View.VISIBLE);
		}
		//scrollToEnd();
	}
	
	class ChatMessageAdapter extends BaseAdapter {
		LinphoneChatMessage[] history;
		Context context;

		public ChatMessageAdapter(Context context, LinphoneChatMessage[] history) {
			this.history = history;
			this.context = context;
		}

		public void refreshHistory() {
			this.history = chatRoom.getHistory();
		}

		@Override
		public int getCount() {
			return history.length;
		}

		@Override
		public LinphoneChatMessage getItem(int position) {
			return history[position];
		}

		@Override
		public long getItemId(int position) {
			return history[position].getStorageId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LinphoneChatMessage message = history[position];
			
			BubbleChat bubble = new BubbleChat(context, message, GroupChatRoomFragment.this);
			View v = bubble.getView();

			registerForContextMenu(v);
			RelativeLayout rlayout = new RelativeLayout(context);
			rlayout.addView(v);

			return rlayout;
		}
	}
	
	public void dispayMessageList() {
		adapter = new ChatMessageAdapter(getActivity(), chatRoom.getHistory());
		messagesList.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	/*private void displayChatHeader(String displayName, String pictureUri) {
		LinphoneAddress lAddress;
		try {
			lAddress = LinphoneCoreFactory.instance().createLinphoneAddress(sipUri);
			Contact contact = ContactsManager.getInstance().findContactWithAddress(getActivity().getContentResolver(), lAddress);
			/*if (contact != null) {
				LinphoneUtils.setImagePictureFromUri(getActivity(), contactPicture.getView(), contact.getPhotoUri(), contact.getThumbnailUri(), R.drawable.unknown_small);

			} else {
				contactPicture.setImageResource(R.drawable.unknown_small);
			}
		} catch (LinphoneCoreException e) {
			e.printStackTrace();
		}
		//use this to show participants in the list
		if (displayName == null && getResources().getBoolean(R.bool.only_display_username_if_unknown) && LinphoneUtils.isSipAddress(sipUri)) {
			contactName.setText(LinphoneUtils.getUsernameFromAddress(sipUri));
		} else if (displayName == null) {
			contactName.setText(sipUri);
		} else {
			contactName.setText(displayName);
		}

	}*/


	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		menu.add(v.getId(), MENU_DELETE_MESSAGE, 0, getString(R.string.delete));
		menu.add(v.getId(), MENU_COPY_TEXT, 0, getString(R.string.copy_text));

		LinphoneChatMessage msg = getMessageForId(v.getId());
		if (msg != null && msg.getStatus() == LinphoneChatMessage.State.NotDelivered) {
			menu.add(v.getId(), MENU_RESEND_MESSAGE, 0, getString(R.string.retry));
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case MENU_DELETE_MESSAGE:
				if (chatRoom != null) {
					LinphoneChatMessage message = getMessageForId(item.getGroupId());
					if (message != null) {
						chatRoom.deleteMessage(message);
						invalidate();
					}
				}
				break;
			case MENU_COPY_TEXT:
				//copyTextMessageToClipboard(item.getGroupId());
				break;
			case MENU_RESEND_MESSAGE:
				//resendMessage(item.getGroupId());
				break;
		}
		return true;
	}
	
	private LinphoneChatMessage getMessageForId(int id) {
		for (LinphoneChatMessage message : chatRoom.getHistory()) {
			if (message.getStorageId() == id) {
				return message;
			}
		}
		
		return null;
	}
	
	private void invalidate() {
		adapter.refreshHistory();
		adapter.notifyDataSetChanged();
		chatRoom.markAsRead();
	}

	/*private void resendMessage(int id) {
		LinphoneChatMessage message = getMessageForId(id);
		if (message == null)
			return;

		chatRoom.deleteMessage(getMessageForId(id));
		invalidate();

		if (message.getText() != null && message.getText().length() > 0) {
			sendTextMessage(message.getText());
		} else {
			sendImageMessage(message.getAppData());
		}
	}*/

	private void copyTextMessageToClipboard(int id) {
		String msg = LinphoneActivity.instance().getChatStorage().getTextMessageForId(chatRoom, id);
		if (msg != null) {
			Compatibility.copyTextToClipboard(getActivity(), msg);
			LinphoneActivity.instance().displayCustomToast(getString(R.string.text_copied_to_clipboard), Toast.LENGTH_SHORT);
		}
	}
	
	@Override
	public void onClick(View v) {
		sendTextMessage();
	}

	private void sendTextMessage() {
		sendTextMessage(message.getText().toString());
		message.setText("");
	}

	private void sendTextMessage(String messageToSend) {
		LinphoneCore lc = LinphoneManager.getLcIfManagerNotDestroyedOrNull();
		boolean isNetworkReachable = lc == null ? false : lc.isNetworkReachable();

		if (chatRoom != null && messageToSend != null && messageToSend.length() > 0 && isNetworkReachable) {
			if (chatRoom.getChatRoomType() == 0) {
				LinphoneChatMessage message = chatRoom.createLinphoneChatMessage(messageToSend);
				message.setListener(this);
				chatRoom.sendChatMessage(message);

				if (LinphoneActivity.isInstanciated()) {
					LinphoneActivity.instance().onMessageSent(sipUri, messageToSend);
				}

				invalidate();
				Log.i("Sent message current status: " + message.getStatus());
			} else if (chatRoom.getChatRoomType() == 1) {
				LinphoneProxyConfig pc = lc.getDefaultProxyConfig();
				String identity = pc.getIdentity();
				
				LinphoneAddress la;
				String id = "";
				try {
					la = LinphoneCoreFactory.instance().createLinphoneAddress(identity);
					id = la.getUserName() + ": ";
				} catch (LinphoneCoreException e) {
					Log.e("Cannot display chat",e);
					return;
				}
				
				chatRoom.sendGroupMessage(id + messageToSend);
				
				if (LinphoneActivity.isInstanciated()) {
					LinphoneActivity.instance().onMessageSent(sipUri, id + messageToSend);
				}
				
				invalidate();
				//Log.i("Sent message current status: " + message.getStatus());
			}
		} else if (!isNetworkReachable && LinphoneActivity.isInstanciated()) {
			LinphoneActivity.instance().displayCustomToast(getString(R.string.error_network_unreachable), Toast.LENGTH_LONG);
		}
	}
	
	private void pickImage() {
		List<Intent> cameraIntents = new ArrayList<Intent>();
		Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File file = new File(Environment.getExternalStorageDirectory(), getString(R.string.temp_photo_name_with_date).replace("%s", String.valueOf(System.currentTimeMillis())));
		imageToUploadUri = Uri.fromFile(file);
		captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageToUploadUri);
		cameraIntents.add(captureIntent);
		
		Intent galleryIntent = new Intent();
		galleryIntent.setType("image/*");
		galleryIntent.setAction(Intent.ACTION_PICK);
		
		Intent chooserIntent = Intent.createChooser(galleryIntent, getString(R.string.image_picker_title));
		chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
		
		startActivityForResult(chooserIntent, ADD_PHOTO);
	}

	private void sendImageMessage(String path) {
		LinphoneCore lc = LinphoneManager.getLcIfManagerNotDestroyedOrNull();
		boolean isNetworkReachable = lc == null ? false : lc.isNetworkReachable();

		if (chatRoom != null && path != null && path.length() > 0 && isNetworkReachable) {
			Bitmap bm = BitmapFactory.decodeFile(path);
			if (bm != null) {
				FileUploadPrepareTask task = new FileUploadPrepareTask(getActivity(), path);
				task.execute(bm);
			} else {
				Log.e("Error, bitmap factory can't read " + path);
			}
		} else if (!isNetworkReachable && LinphoneActivity.isInstanciated()) {
			LinphoneActivity.instance().displayCustomToast(getString(R.string.error_network_unreachable), Toast.LENGTH_LONG);
		}
	}
	
	class FileUploadPrepareTask extends AsyncTask<Bitmap, Void, byte[]> {
		private String path;
		private ProgressDialog progressDialog;
		
		public FileUploadPrepareTask(Context context, String fileToUploadPath) {
			path = fileToUploadPath;
			
			uploadLayout.setVisibility(View.VISIBLE);
			textLayout.setVisibility(View.GONE);
			
			progressDialog = new ProgressDialog(context);
			progressDialog.setIndeterminate(true);
			progressDialog.setMessage(getString(R.string.processing_image));
			progressDialog.show();
		}

		@Override
		protected byte[] doInBackground(Bitmap... params) {
			Bitmap bm = params[0];

			if (bm.getWidth() >= bm.getHeight() && bm.getWidth() > SIZE_MAX) {
				bm = Bitmap.createScaledBitmap(bm, SIZE_MAX, (SIZE_MAX * bm.getHeight()) / bm.getWidth(), false);
			} else if (bm.getHeight() >= bm.getWidth() && bm.getHeight() > SIZE_MAX) {
				bm = Bitmap.createScaledBitmap(bm, (SIZE_MAX * bm.getWidth()) / bm.getHeight(), SIZE_MAX, false);
			}

			// Rotate the bitmap if possible/needed, using EXIF data
			Log.w(path);
			try {
				if (path != null) {
					ExifInterface exif = new ExifInterface(path);
					int pictureOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
					Matrix matrix = new Matrix();
					if (pictureOrientation == 6) {
						matrix.postRotate(90);
					} else if (pictureOrientation == 3) {
						matrix.postRotate(180);
					} else if (pictureOrientation == 8) {
						matrix.postRotate(270);
					}
					bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
			byte[] byteArray = stream.toByteArray();
			return byteArray;
		}
		
		@Override
		protected void onPostExecute(byte[] result) {
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
			
			mUploadingImageStream = new ByteArrayInputStream(result);
			
			LinphoneContent content = LinphoneCoreFactory.instance().createLinphoneContent("image", "jpeg", result, null);
			String fileName = path.substring(path.lastIndexOf("/") + 1);
			content.setName(fileName);
			
			LinphoneChatMessage message = chatRoom.createFileTransferMessage(content);
			message.setListener(GroupChatRoomFragment.this);
			message.setAppData(path);
			
			chatRoom.sendChatMessage(message);
			currentMessageInFileTransferUploadState = message;
		}
	}

/*	private LinphoneChatMessage getMessageForId(int id) {
		for (LinphoneChatMessage message : chatRoom.getHistory()) {
			if (message.getStorageId() == id) {
				return message;
			}
		}
		
		return null;
	}*

	private void invalidate() {
		adapter.refreshHistory();
		adapter.notifyDataSetChanged();
		chatRoom.markAsRead();
	}


	private void copyTextMessageToClipboard(int id) {
		String msg = LinphoneActivity.instance().getChatStorage().getTextMessageForId(chatRoom, id);
		if (msg != null) {
			Compatibility.copyTextToClipboard(getActivity(), msg);
			LinphoneActivity.instance().displayCustomToast(getString(R.string.text_copied_to_clipboard), Toast.LENGTH_SHORT);
		}
	}*/

	public String getSipUri() {
		return sipUri;
	}

	
	/*private void pickAudio() {
		Intent intent = new Intent();
		intent.setType("audio/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent,"Select Audio"),2);
		
	}*/
	public String getRealPathFromURI(Uri contentUri) {
		String[] proj = {MediaStore.Images.Media.DATA};
		CursorLoader loader = new CursorLoader(getActivity(), contentUri, proj, null, null, null);
		Cursor cursor = loader.loadInBackground();
		if (cursor != null && cursor.moveToFirst()) {
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			String result = cursor.getString(column_index);
			cursor.close();
			return result;
		}
		return null;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ADD_PHOTO && resultCode == Activity.RESULT_OK) {
			String fileToUploadPath = null;
			
			if (data != null && data.getData() != null) {
				fileToUploadPath = getRealPathFromURI(data.getData());
			} else if (imageToUploadUri != null) {
				fileToUploadPath = imageToUploadUri.getPath();
			}
			
			if (fileToUploadPath != null) {
				sendImageMessage(fileToUploadPath);
			}
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

//context_menu_for_more_options
@Override
	public void onLinphoneChatMessageStateChanged(LinphoneChatMessage msg, State state) {
		if (LinphoneActivity.isInstanciated() && state != LinphoneChatMessage.State.InProgress) {
			if (msg != null) {
				LinphoneActivity.instance().onMessageStateChanged(sipUri, msg.getText(), state.toInt());
			}
			invalidate();
		}
		
		if (state == State.FileTransferDone) {
			if (mDownloadedImageStream != null) {
				byte[] bytes = mDownloadedImageStream.toByteArray();
				Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, mDownloadedImageStreamSize);
				
				String path = msg.getExternalBodyUrl();
				String fileName = path.substring(path.lastIndexOf("/") + 1);
				String url = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bm, fileName, null);
				if (url != null) {
					msg.setAppData(url);
				}
				
				mDownloadedImageStream = null;
				mDownloadedImageStreamSize = 0;
			} else if (mUploadingImageStream != null) {
				mUploadingImageStream = null;
			}
		} 
		
		if (state == State.FileTransferDone || state == State.FileTransferError) {
			uploadLayout.setVisibility(View.GONE);
			textLayout.setVisibility(View.VISIBLE);
			progressBar.setProgress(0);
			currentMessageInFileTransferUploadState = null;
		}
		invalidate();
	}

	@Override
	public void onLinphoneChatMessageFileTransferReceived(LinphoneChatMessage msg, LinphoneContent content, LinphoneBuffer buffer) {
		if (mDownloadedImageStream == null) {
			mDownloadedImageStream = new ByteArrayOutputStream();
			mDownloadedImageStreamSize = 0;
		}

		if (buffer != null && buffer.getSize() > 0) {
			try {
				mDownloadedImageStream.write(buffer.getContent());
				mDownloadedImageStreamSize += buffer.getSize();
			} catch (IOException e) {
				Log.e(e);
			}
		}
	}

	@Override
	public void onLinphoneChatMessageFileTransferSent(LinphoneChatMessage msg, LinphoneContent content, int offset, int size, LinphoneBuffer bufferToFill) {
		if (mUploadingImageStream != null && size > 0) {
			byte[] data = new byte[size];
			int read = mUploadingImageStream.read(data, 0, size);
			if (read > 0) {
				bufferToFill.setContent(data);
				bufferToFill.setSize(read);
			} else {
				Log.e("Error, upload task asking for more bytes(" + size + ") than available (" + mUploadingImageStream.available() + ")");
			}
		}
	}

	@Override
	public void onLinphoneChatMessageFileTransferProgressChanged(LinphoneChatMessage msg, LinphoneContent content, int offset, int total) {
		progressBar.setProgress(offset * 100 / total);
	}

}
