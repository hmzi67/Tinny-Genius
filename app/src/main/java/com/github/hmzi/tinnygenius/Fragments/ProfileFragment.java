package com.github.hmzi.tinnygenius.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.hmzi.tinnygenius.EditProfileActivity;
import com.github.hmzi.tinnygenius.GetStartedActivity;
import com.github.hmzi.tinnygenius.LoginActivity;
import com.github.hmzi.tinnygenius.Model.Users;
import com.github.hmzi.tinnygenius.R;
import com.github.hmzi.tinnygenius.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;

    private String userName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        // ready the firebase
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();


        // show userData
        // show the user name
        if (auth.getCurrentUser() != null) {
            firebaseDatabase.getReference().child("Users").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Users user = snapshot.getValue(Users.class);
                    userName = user.getUserName();
                    binding.userName.setText(user.getUserName());
                    binding.userEmail.setText(user.getUserEmail());
                    if (user.getUserImg().isEmpty())
                        binding.editUserPic.setImageResource(R.drawable.ic_profile);
                    else
                        Picasso.get().load(user.getUserImg()).placeholder(R.drawable.ic_profile).into(binding.editUserPic);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }

        // edit profile
        binding.eduEditProfile.setOnClickListener(view-> {
            startActivity(new Intent(getContext(), EditProfileActivity.class));
        });

        // reset password
        binding.eduResetPass.setOnClickListener(view-> {
            auth.sendPasswordResetEmail(auth.getCurrentUser().getEmail().toString()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Check your email for reset link!", Toast.LENGTH_SHORT).show();
                    auth.signOut();
                    startActivity(new Intent(getContext(), LoginActivity.class));
                } else {
                    Toast.makeText(getContext(), "Error : " + task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Delete account
        binding.eduDeactivateAccount.setOnClickListener(view -> {
            auth.getCurrentUser().delete();
            firebaseDatabase.getReference().child("Users").child(auth.getCurrentUser().getUid()).removeValue();
            startActivity(new Intent(getContext(), LoginActivity.class));
        });

        // log out
        binding.eduUserLogout.setOnClickListener(view -> {
            auth.signOut();
            startActivity(new Intent(getContext(), GetStartedActivity.class));
            requireActivity().finish();
        });
    }

}