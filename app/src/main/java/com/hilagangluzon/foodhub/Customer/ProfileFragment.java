package com.hilagangluzon.foodhub.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hilagangluzon.foodhub.Classes.User;
import com.hilagangluzon.foodhub.LoginActivity;
import com.hilagangluzon.foodhub.R;



public class ProfileFragment extends Fragment
{
    FirebaseFirestore db;

    EditText txfUser, txfPass, txfFullname, txfEmail;

    Button btnEdit, btnSignOut;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /*@Override
    public void onActivityCreated(Bundle bundle)
    {
        super.onActivityCreated(bundle);

        txfUser = getActivity().findViewById(R.id.txfUser);
        txfPass = getActivity().findViewById(R.id.txfPass);
        txfFullname = getActivity().findViewById(R.id.txfFullname);
        txfEmail = getActivity().findViewById(R.id.txfEmail);

        Log.d("redrawn!", "Rderawin!");

        User u = Dashboard.loggedInUserInfo;
        txfUser.setText(u.getUsername());
        txfPass.setText(u.getPassword());
        txfFullname.setText(u.pickFullname());
        txfEmail.setText(u.getEmail());

        txfUser.setKeyListener(null);
        txfPass.setKeyListener(null);
        txfFullname.setKeyListener(null);
        txfEmail.setKeyListener(null);

    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        
        db = FirebaseFirestore.getInstance();

        txfUser = view.findViewById(R.id.txfUser);
        txfPass = view.findViewById(R.id.txfPass);
        txfFullname = view.findViewById(R.id.txfFullname);
        txfEmail = view.findViewById(R.id.txfEmail);
        
        txfUser.setText("");
        txfPass.setText("");
        txfFullname.setText("");
        txfEmail.setText("");

        txfUser.setKeyListener(null);
        txfPass.setKeyListener(null);
        txfFullname.setKeyListener(null);
        txfEmail.setKeyListener(null);

        db.collection("users").document(Dashboard.loggedInUserId).get().addOnSuccessListener(
                new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User u = documentSnapshot.toObject(User.class);
                        Dashboard.loggedInUserInfo = u;
                        txfUser.setText(u.getUsername());
                        txfPass.setText(u.getPassword());
                        txfFullname.setText(u.pickFullname());
                        txfEmail.setText(u.getEmail());
                    }
                }
        );

        btnEdit = view.findViewById(R.id.btnEdit);
        btnSignOut = view.findViewById(R.id.btnSignOut);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toEdit = new Intent(getContext(), UserUserView.class);
                startActivity(toEdit);
            }
        });
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLogin = new Intent(getContext(), LoginActivity.class);
                startActivity(toLogin);
            }
        });
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(
                isVisibleToUser);

        // Refresh tab data:
        if (getFragmentManager() != null) {

            getFragmentManager()
                    .beginTransaction()
                    .detach(this)
                    .attach(this)
                    .commit();
        }
    }
}
