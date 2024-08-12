package com.example.fadrinotes.Activity.AuthentUser.fragment;

import static android.content.Context.MODE_PRIVATE;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.fadrinotes.Activity.AuthentUser.DataModels.Users;
import com.example.fadrinotes.Activity.AuthentUser.Repository.UserManager;
import com.example.fadrinotes.Activity.ActivityMain.MainActivity;
import com.example.fadrinotes.R;
import com.example.fadrinotes.Activity.AuthentUser.ViewModel.UserModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SingUpAuth#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingUpAuth extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText signUpUsername, signUpEmail, signUpPassword;
    Button buttonSignUp;
    TextView textRedirectToSignIn;
    String username, email, password;
    SharedPreferences sharedPreferences;
    Users users;
    UserModel userViewModel;
    UserManager userManager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SingUpAuth() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SingUpFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static SingUpAuth newInstance(String param1, String param2) {
        SingUpAuth fragment = new SingUpAuth();
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
        return inflater.inflate(R.layout.fragment_sing_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signUpUsername = view.findViewById(R.id.editTextUsername);
        signUpEmail = view.findViewById(R.id.editTextEmail);
        signUpPassword = view.findViewById(R.id.editTextPassword);
        textRedirectToSignIn = view.findViewById(R.id.textViewLogin);
        buttonSignUp = view.findViewById(R.id.btnSingUp);
        sharedPreferences = getContext().getSharedPreferences("LocalData", MODE_PRIVATE);
        userViewModel  = new UserModel(requireActivity());

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = signUpUsername.getText().toString();
                email = signUpEmail.getText().toString();
                password = signUpPassword.getText().toString();
                userViewModel.createUsers(username ,email, password);

                userViewModel.getRegistrationStatus().observe(requireActivity(), registrasiStatus -> {
                    if (registrasiStatus) {
                        Toast.makeText(requireActivity(), "Berhasil Registrasi", Toast.LENGTH_SHORT).show();

                        userManager = new UserManager(requireContext());
                        int userID  = userManager.getUserByEmail(email).getId();
                        System.out.println("Check IDUser: " + userID);
                        if (userID != -1) {
                            users = new Users(userID, email, password);
                            sharedPreferences.edit().putInt("userId", userID).apply();
                            sharedPreferences.edit().putString("userName", username).apply();
                            Intent intent = new Intent(requireActivity(), MainActivity.class);
                            startActivity(intent);
                            requireActivity().finish();
                        }

                    } else {
                        Toast.makeText(requireActivity(), "Registrasi Gagal mohon masukan field yang benar", Toast.LENGTH_SHORT).show();
                    }

                });
            }
        });

        textRedirectToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInAuth fragment = new SignInAuth();
                getParentFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_MATCH_ACTIVITY_OPEN)
                        .replace(R.id.myFrame, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}