package com.ginomai.gino;

import java.util.HashMap;
import java.util.Locale;





import android.content.Intent;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements
		ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;
	ClientsFragment clientFragment;
	private ClientArrayAdapter clientArrayAdapter;
	
	public HashMap<String,ChatFragment> chatFragments=new HashMap<>();
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set up the action bar.
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setIcon(R.drawable.chat);
//		actionBar.setDisplayUseLogoEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setTitle("Bengi");
		actionBar.setSubtitle("Online");
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});
		clientFragment=new ClientsFragment();
//		Client client=new Client("benikod@gmail.com","egima");
//		clientFragment.addNewClient(client);
		ChatFragment frag= new ChatFragment("benikod@gmail.com");
		chatFragments.put("benikod@gmail.com", frag);
		chatFragments.put("active",frag);
		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}
	public class SimulateClients extends AsyncTask<String,String,String>{

		@Override
		protected String doInBackground(String... params) {
			for(final String s:params){
			
			MainActivity.this.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					Client client=new Client();
					
					String[] data=s.split(":");
					client.setEmail(data[1]);
					client.setUser(data[0]);
					System.out.println("setting client object: "+client.toString());
					clientFragment.addNewClient(client);
					
				}
			});;
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			}
			return null;
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			return getFragment(position + 1);
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_visitors).toUpperCase(l);
			case 1:
				return getString(R.string.title_chats).toUpperCase(l);

			}
			return null;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static Fragment newInstance(int sectionNumber) {
//			if(sectionNumber==1){
//			ClientsFragment fragment=new ClientsFragment();
//			String[] emails={"egima:benikod@gmail.com","ben:ben@cloudboost.com","timo:ekastimo@gmail.com"};
//
//			Bundle args = new Bundle();
//			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//			fragment.setArguments(args);
//			return fragment;
//			}
//			else{
//				PlaceholderFragment fragment=new PlaceholderFragment(); 
//
//						Bundle args = new Bundle();
//						args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//						fragment.setArguments(args);
//						return fragment;
//			}
			return null;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";
	public Fragment getFragment(int sectionNumber) {
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		if(sectionNumber==1){
			return clientFragment;
		}
		else if(sectionNumber==2){
			System.out.println("asked for chats");
			ChatFragment frag=chatFragments.get("active");
			TextView text=frag.header;
			System.out.println("text view is "+text);
			if(text!=null){
			String header=(String) text.getText();
			System.out.println("current header is: "+header);}
				return frag;
		}
		return sectionNumber==1?new NoClientsFragment():new NoChatsFragment();
	}
	public void setActiveUser(String email){
		
		System.out.println("keyset of maps is: "+chatFragments.keySet());
		if(chatFragments.containsKey(email))
		{	
			System.out.println("clicked item already in chatfrags");
			chatFragments.put("active", chatFragments.get(email));
		}else{
			System.out.println("clicked item not in chat frags");
			ChatFragment frag=new ChatFragment(email);
			chatFragments.put(email, frag);
			chatFragments.put("active", frag);
		}
	}

class NoClientsFragment extends Fragment{
		
	}
	class NoChatsFragment extends Fragment{
		
	}
	/**
	 * A placeholder fragment containing a simple view.
	 */
	
	class ChatFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
	    private ChatArrayAdapter chatArrayAdapter;
	    private ListView listView;
	    private EditText chatText;
	    private Button buttonSend;
	    private TextView header;
	    public String heading;
	    public String getHeading() {
			return heading;
		}
		public void setHeading(String heading) {
			this.heading = heading;
		}
		private String email;
	    Intent intent;
	    private boolean side = false;





		public ChatFragment(String email) {
			this.email=email;
		}
		public void setHeader(String text){
			header.setText(text);
		}
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
			View rootView = inflater.inflate(R.layout.activity_chat, container,
					false);
			header=(TextView) rootView.findViewById(R.id.current_client);
			header.setText(email);
	        buttonSend = (Button)rootView.findViewById(R.id.buttonSend);

	        listView = (ListView) rootView.findViewById(R.id.listView1);

	        chatArrayAdapter = new ChatArrayAdapter(MainActivity.this, R.layout.activity_chat_singlemessage);
	        listView.setAdapter(chatArrayAdapter);

	        chatText = (EditText) rootView.findViewById(R.id.chatText);
	        chatText.setOnKeyListener(new OnKeyListener() {
	            public boolean onKey(View v, int keyCode, KeyEvent event) {
	                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
	                    return sendChatMessage();
	                }
	                return false;
	            }
	        });
	        buttonSend.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View arg0) {
	                sendChatMessage();
	            }
	        });

	        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
	        listView.setAdapter(chatArrayAdapter);

	        //to scroll the list view to bottom on data change
	        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
	            @Override
	            public void onChanged() {
	                super.onChanged();
	                listView.setSelection(chatArrayAdapter.getCount() - 1);
	            }
	        });
	        return rootView;
		}
	    private boolean sendChatMessage(){
	        chatArrayAdapter.add(new ChatMessage(side, chatText.getText().toString()));
	        chatText.setText("");
	        side = !side;
	        return true;
	    }
	}
	public class ClientsFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";
	    
	    private ListView listView;
	    Intent intent;
	    private boolean side = false;





		public ClientsFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			
	        listView = (ListView) rootView.findViewById(R.id.client_list);

	        clientArrayAdapter = new ClientArrayAdapter(MainActivity.this, R.layout.row,MainActivity.this);
	        listView.setAdapter(clientArrayAdapter);
	        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
	        listView.setAdapter(clientArrayAdapter);

	        //to scroll the list view to bottom on data change
	        clientArrayAdapter.registerDataSetObserver(new DataSetObserver() {
	            @Override
	            public void onChanged() {
	                super.onChanged();
	                listView.setSelection(clientArrayAdapter.getCount() - 1);
	            }
	        });
	        SimulateClients cli=new SimulateClients();
			String[] emails={"egima:benikod@gmail.com","ben:ben@cloudboost.com","timo:ekastimo@gmail.com"};

	        cli.execute(emails);
	        return rootView;
		}
	    public void addNewClient(Client client){
	    	clientArrayAdapter.add(client);
	    }
	    public void removeClient(Client client){
	    	clientArrayAdapter.remove(client);
	    }
	}

}
