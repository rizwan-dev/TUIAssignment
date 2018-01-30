package com.systemplus.tuiassignment.ui.textinput;

import android.arch.lifecycle.ViewModelProviders;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.systemplus.tuiassignment.BaseActivity;
import com.systemplus.tuiassignment.R;
import com.systemplus.tuiassignment.networking.NetworkService;
import com.systemplus.tuiassignment.util.JokeDisplayDialogFragment;
import com.systemplus.tuiassignment.viewmodel.Response;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.systemplus.tuiassignment.util.TUIConstants.DIALOG_TAG;
import static com.systemplus.tuiassignment.util.TUIConstants.MESSAGE_BODY;

public class TextInputActivity extends BaseActivity {

    private static final String WHITE_SPACE_REGEX = "\\s+";
    @Inject
    NetworkService mNetworkService;
    private TextInputViewModel mViewModel;

    @BindView(R.id.progress)
    ProgressBar progress;

    @BindView(R.id.inputLayoutUserName)
    TextInputLayout inputLayoutUserName;

    @BindView(R.id.edtUserName)
    EditText edtUserName;

    FragmentManager fm = getSupportFragmentManager();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_input);
        findViewById(R.id.flParent).requestFocus();
        ButterKnife.bind(this);
        getDeps().inject(this);

        mViewModel = ViewModelProviders.of(this).get(TextInputViewModel.class);
        mViewModel.response().observe(this, response -> processResponse(response));

        edtUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                inputLayoutUserName.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void processResponse(Response response) {
        switch (response.status) {
            case LOADING:
                progress.setVisibility(View.VISIBLE);
                break;

            case SUCCESS:
                progress.setVisibility(View.GONE);
                showDialog(response.data);
                break;

            case ERROR:
                progress.setVisibility(View.GONE);
                Toast.makeText(this, response.error.getMessage(), Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @OnClick(R.id.btnSubmit)
    public void onSubmitClick(View view) {
        String userFullName = edtUserName.getText().toString().trim();
        if (TextUtils.isEmpty(userFullName)) {
            inputLayoutUserName.setError(getString(R.string.first_last_name_missing));
            edtUserName.requestFocus();
            return;
        }
        String[] userNameParts = userFullName.split(WHITE_SPACE_REGEX);
        if (userNameParts.length < 2) {
            inputLayoutUserName.setError(getString(R.string.last_name_missing));
            edtUserName.requestFocus();
            return;
        }

        String firstName = userNameParts[0];
        String lastName = userNameParts[1].trim();

        mViewModel.requestRandomJoke(mNetworkService, firstName, lastName);

    }

    public void showDialog(String message) {
        Fragment prevJokeDialogFragment = fm.findFragmentByTag(DIALOG_TAG);
        if (prevJokeDialogFragment == null) {
            JokeDisplayDialogFragment jokeDialogFragment = JokeDisplayDialogFragment.newInstance(message);
            Bundle bundle = new Bundle();
            bundle.putString(MESSAGE_BODY, message);
            jokeDialogFragment.setArguments(bundle);
            // Show DialogFragment
            jokeDialogFragment.show(fm, DIALOG_TAG);
        }
    }

}
