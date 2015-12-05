package com.ginomai.gino;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ClientArrayAdapter extends ArrayAdapter{
	private TextView username;
	private TextView email;
	private MainActivity main;

	private List<Client> clientList = new ArrayList<>();

	

	@Override
	public void add(Object object) {
		clientList.add((Client)object);
		super.add(object);
	}

	public ClientArrayAdapter(Context context, int textViewResourceId,MainActivity main) {
		super(context, textViewResourceId);
		this.main=main;

	}

	public int getCount() {
		return this.clientList.size();
	}

	public Client getItem(int index) {
		return this.clientList.get(index);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.row, parent, false);
		}
		Client client = getItem(position);
		username = (TextView) row.findViewById(R.id.user_name);
		email = (TextView) row.findViewById(R.id.email);
		
		username.setText(client.getUser());
		email.setText(client.getEmail());
		row.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String mail=(String) email.getText();
				System.out.println("setting active email="+mail);
				Log.d("ClientArrayAdapter", "setting active email="+mail);

				main.setActiveUser(mail);
				main.getSupportActionBar().setSelectedNavigationItem(1);
				
			}
		});
		return row;
	}

	public Bitmap decodeToBitmap(byte[] decodedByte) {
		return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
	}
}
