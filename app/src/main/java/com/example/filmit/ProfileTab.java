package com.example.filmit;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class ProfileTab extends Fragment {

    private EditText edtProfileName, edtProfilePassion, edtProfileProfession, edtProfileHobbies, edtProfileFavSport;
    private Button btnUpdateInfo;

    public ProfileTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);
        edtProfileName = view.findViewById(R.id.edtProfileName);
        edtProfilePassion = view.findViewById(R.id.edtProfilePassion);
        edtProfileProfession = view.findViewById(R.id.edtProfileProfession);
        edtProfileHobbies = view.findViewById(R.id.edtProfileHobbies);
        edtProfileFavSport = view.findViewById(R.id.edtProfileFavouriteSports);

        btnUpdateInfo = view.findViewById(R.id.btnUpdateInfo);

        final ParseUser parseUser = ParseUser.getCurrentUser();

        if (parseUser.get("ProfileName") == null) {
            edtProfileName.setHint("Please enter a profile name");
        } if (parseUser.get("ProfilePassion") == null) {
            edtProfilePassion.setHint("Please enter your passion");
        } if (parseUser.get("ProfileProfession") == null) {
            edtProfileProfession.setHint("Please enter your profession");
        } if (parseUser.get("ProfileHobbies") == null) {
            edtProfileHobbies.setHint("Please enter your hobbies");
        } if (parseUser.get("ProfileFavSport") == null) {
            edtProfileFavSport.setHint("Please enter your favourite sport");
        } else {
            edtProfileName.setText(parseUser.get("ProfileName").toString());
            edtProfilePassion.setText(parseUser.get("ProfilePassion").toString());
            edtProfileProfession.setText(parseUser.get("ProfileProfession").toString());
            edtProfileHobbies.setText(parseUser.get("ProfileHobbies").toString());
            edtProfileFavSport.setText(parseUser.get("ProfileFavSport").toString());
        }

        btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parseUser.put("ProfileName", edtProfileName.getText().toString());
                parseUser.put("ProfilePassion", edtProfilePassion.getText().toString());
                parseUser.put("ProfileProfession", edtProfileProfession.getText().toString());
                parseUser.put("ProfileHobbies", edtProfileHobbies.getText().toString());
                parseUser.put("ProfileFavSport", edtProfileFavSport.getText().toString());

                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setMessage("Updating Info");
                        progressDialog.show();
                        if (e == null) {
                            FancyToast.makeText(getContext(), "Info Updated", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                            progressDialog.dismiss();
                        } else {
                            progressDialog.dismiss();
                            FancyToast.makeText(getContext(), "There was an error " + e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                        }
                    }
                });
            }
        });

        return view;
    }
}