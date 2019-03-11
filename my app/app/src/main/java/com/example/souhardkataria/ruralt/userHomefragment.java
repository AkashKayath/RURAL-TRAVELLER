package com.example.souhardkataria.ruralt;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class userHomefragment extends Fragment {
    FirebaseAuth mAuth;
    DatabaseReference mRef;
    TextView name;
    ListView listPackage;
    ArrayList<PackageClass> array=new ArrayList<>();
    public userHomefragment() {
        // Required empty public constructor
    }
    PackageAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_user_homefragment, container, false);
        name=view.findViewById(R.id.nameString);
        mAuth = FirebaseAuth.getInstance();

        listPackage=view.findViewById(R.id.listPackages);

        adapter = new PackageAdapter();
        listPackage.setAdapter(adapter);

        mRef= FirebaseDatabase.getInstance().getReference("Packages");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        name.setText("Hi!, "+user.getEmail());
        try {
            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    array.clear();
                    for (DataSnapshot i : dataSnapshot.getChildren()) {
                        array.add(i.getValue(PackageClass.class));
                    }
                    //Toast.makeText(getContext(),array.get(2).name+"",Toast.LENGTH_LONG).show();
                    adapter.notifyDataSetChanged();
                   // Toast.makeText(getContext(),adapter.getCount()+"",Toast.LENGTH_LONG).show();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch (Exception e)
        {
            Log.i("None",e.getMessage());
        }

        return view;
    }

    class PackageAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return array.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Context context=getContext();
            view = ((FragmentActivity) context).getLayoutInflater().inflate(R.layout.package_element, viewGroup, false);
            TextView name=view.findViewById(R.id.name1);
            TextView duration=view.findViewById(R.id.duration);
            TextView rate=view.findViewById(R.id.rate);
            name.setText(array.get(i).name);
            duration.setText(array.get(i).Duration);
            rate.setText(array.get(i).Rate);
            Toast.makeText(getContext(),"Get View "+i+" "+array.get(i).name,Toast.LENGTH_LONG).show();

            return view;
        }
    }
}
