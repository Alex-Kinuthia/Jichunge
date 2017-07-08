package com.example.alex.jichunge.fragments;


import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.alex.jichunge.R;
import com.example.alex.jichunge.ui.AddContact;
import com.example.alex.jichunge.ui.ContactHelper;
import com.example.alex.jichunge.ui.DeleteContacts;
import com.example.alex.jichunge.ui.MainActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends ListActivity {

    private ArrayList<String> conNames;
    private ArrayList<String> conNumbers;
    private Cursor crContacts;


    public ContactsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_contacts);

        conNames = new ArrayList<String>();
        conNumbers = new ArrayList<String>();

        crContacts = ContactHelper.getContactCursor(getContentResolver(), "");
        crContacts.moveToFirst();

        while (!crContacts.isAfterLast()) {
            conNames.add(crContacts.getString(1));
            conNumbers.add(crContacts.getString(2));
            crContacts.moveToNext();
        }

        setListAdapter(new MyAdapter(this, android.R.layout.simple_list_item_1,
                R.id.tvNameMain, conNames));

    }

    private class MyAdapter extends ArrayAdapter<String> {

        public MyAdapter(Context context, int resource, int textViewResourceId,
                         ArrayList<String> conNames) {
            super(context, resource, textViewResourceId, conNames);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View row = setList(position, parent);
            return row;
        }

        private View setList(int position, ViewGroup parent) {
            LayoutInflater inf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = inf.inflate(R.layout.liststyle, parent, false);

            TextView tvName = (TextView) row.findViewById(R.id.tvNameMain);
            TextView tvNumber = (TextView) row.findViewById(R.id.tvNumberMain);

            tvName.setText(conNames.get(position));
            tvNumber.setText("No: " + conNumbers.get(position));

            return row;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater imf = getMenuInflater();
        imf.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item1) {
            Intent intent = new Intent(ContactsFragment.this, AddContact.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.item2) {
            Intent intent = new Intent(ContactsFragment.this, DeleteContacts.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}


