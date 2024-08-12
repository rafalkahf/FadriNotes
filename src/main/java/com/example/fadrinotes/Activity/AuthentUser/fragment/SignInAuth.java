package com.example.fadrinotes.Activity.AuthentUser.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fadrinotes.Activity.AuthentUser.DataModels.Users;
import com.example.fadrinotes.Activity.ActivityMain.MainActivity;
import com.example.fadrinotes.R;
import com.example.fadrinotes.Activity.AuthentUser.ViewModel.UserModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignInAuth#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInAuth extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText emailEditText, passwordEditText;
    Button signInButton, singUpButton;
    String email, password;
    SharedPreferences sharedPreferences;
    UserModel userViewModel;
    com.example.fadrinotes.Activity.AuthentUser.Repository.UserManager userManager;
    Users users;

    public SignInAuth() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignInAuth.
     */
    // TODO: Rename and change types and number of parameters
    public static SignInAuth newInstance(String param1, String param2) {
        SignInAuth fragment = new SignInAuth();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userManager = new com.example.fadrinotes.Activity.AuthentUser.Repository.UserManager(requireContext());
        userViewModel = new UserModel(requireContext());
        emailEditText = view.findViewById(R.id.editTextEmail);
        passwordEditText = view.findViewById(R.id.editTextPassword);
        signInButton = view.findViewById(R.id.buttonSignin);
        singUpButton = view.findViewById(R.id.buttonSignUp);
        sharedPreferences = getContext().getSharedPreferences("LocalData", Context.MODE_PRIVATE);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailEditText.getText().toString();
                password = passwordEditText.getText().toString();
                userViewModel.loginUser(email, password);
                userViewModel.getLoginStatus().observe(requireActivity(), loginStatus -> {
                    if (loginStatus) {
                        int userId = userManager.getUserByEmail(email).getId();
                        System.out.println("Check IDUser: " + userId);
                        if (userId != -1){
                            Toast.makeText(requireActivity(), "Berhasil Login", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("userId", userId);
                            editor.apply();

                            Intent intent = new Intent(requireActivity(), MainActivity.class);
                            intent.putExtra("userID", userId);
                            startActivity(intent);
                            requireActivity().finish();
                        }

                    } else {
                        Toast.makeText(requireActivity(), "Login gagal, pastikan Anda memasukkan email dan password yang benar", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        singUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SingUpAuth fragment = new SingUpAuth();
                getParentFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_MATCH_ACTIVITY_CLOSE)
                        .replace(R.id.myFrame, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}