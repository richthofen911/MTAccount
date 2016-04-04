package net.callofdroidy.sharedaccount;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseRecyclerAdapter;

public class ActivityMain extends AppCompatActivity {
    private static final String TAG = "ActivityMain";

    RecyclerView recyclerView;
    TextView tvTotal;
    FirebaseRecyclerAdapter mAdapter;
    FloatingActionButton fab;

    Firebase refRoot;
    Firebase refConnected;
    Firebase refPresence;

    public float currentTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        refRoot = new Firebase("https://mtsharedexpense.firebaseio.com/");
        refRoot.keepSynced(true);

        refPresence = new Firebase("https://mtsharedexpense.firebaseio.com/disconnectmessage");
        refPresence.onDisconnect().setValue("I disconnected!");

        refConnected = new Firebase("https://mtsharedexpense.firebaseio.com/.info/connected");
        refConnected.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = dataSnapshot.getValue(Boolean.class);
                if(connected){
                    Log.e(TAG, "connected");
                    refPresence.onDisconnect().cancel();
                    refPresence.onDisconnect().removeValue();
                }else{
                    Log.e(TAG, "not connected");
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e(TAG, "onCancelled: Connection Listener was canceled");
            }
        });

        tvTotal = (TextView) findViewById(R.id.tv_total);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_entries);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new FirebaseRecyclerAdapter<AccountEntry, AccountEntryViewHolder>(AccountEntry.class, R.layout.account_entry, AccountEntryViewHolder.class, refRoot){
            @Override
            public void populateViewHolder(AccountEntryViewHolder accountEntryViewHolder, AccountEntry accountEntry, int position){
                accountEntryViewHolder.tvTitle.setText(accountEntry.getTitle());
                accountEntryViewHolder.tvDate.setText(accountEntry.getDate());
                accountEntryViewHolder.tvValue.setText(accountEntry.getValue());
            }
        };
        recyclerView.setAdapter(mAdapter);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        /*
        refRoot.child("total").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e(TAG, "onChildAdded: " + dataSnapshot.getValue());
                //currentTotal = (float) dataSnapshot.getValue();
                //String totalInfo = "Total: " + currentTotal;
                //tvTotal.setText(totalInfo);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.e(TAG, "onChildChanged: " + dataSnapshot.getValue());
                //currentTotal = (float) dataSnapshot.getValue();
                //String totalInfo = "Total: " + currentTotal;
                //tvTotal.setText(totalInfo);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        */
    }


    public void showDialog() {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("fragmentAddEntry");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        FragmentAddEntry newFragment = FragmentAddEntry.newInstance();
        newFragment.show(ft, "fragmentAddEntry");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }
}
