package id.co.hope.app.ubahpassword;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.co.hope.R;
import id.co.hope.app.login.LoginActivity;
import id.co.hope.data.Preferences;
import id.co.hope.data.model.UserModel;
import id.co.hope.utils.HopeFunction;
import lib.almuwahhid.utils.DialogBuilder;
import lib.almuwahhid.utils.LibUi;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class UbahPasswordDialog extends DialogBuilder implements UbahPasswordView.View{

    @BindView(R.id.lay_dialog)
    RelativeLayout lay_dialog;
    @BindView(R.id.edt_passwordlama)
    EditText edt_passwordlama;
    @BindView(R.id.edt_passwordbaru)
    EditText edt_passwordbaru;
    @BindView(R.id.edt_passwordulangi)
    EditText edt_passwordulangi;
    @BindView(R.id.btn_ok)
    Button btn_ok;

    UbahPasswordPresenter presenter;

    public UbahPasswordDialog(Context context) {
        super(context, R.layout.dialog_ubah_password);
        ButterKnife.bind(this, getDialog());

        setAnimation(R.style.DialogBottomAnimation);
        setFullWidth(lay_dialog);
        setGravity(Gravity.BOTTOM);
        setForms();

        presenter = new UbahPasswordPresenter(getContext(), this);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

        show();
    }

    @Override
    public void onRequestUbahPassword() {
        LibUi.ToastShort(getContext(), "Berhasil mengubah password, silahkan login kembali");
        dismiss();
        LibUi.setSPString(getContext(), Preferences.USER_INTRO, "");
        HopeFunction.logoutUser(getContext());
        getActivity().startActivity(new Intent(getContext(), LoginActivity.class));
        getActivity().finish();
    }

    @Override
    public void onLoading() {
        LibUi.showLoadingDialog(getContext(), R.drawable.ic_hope);
    }

    @Override
    public void onHideLoading() {
        LibUi.hideLoadingDialog(getContext());
    }

    @Override
    public void onErrorConnection() {

    }

    private void validate(){
        if (LibUi.isFormValid(getContext(), getDialog().getWindow().getDecorView(), forms)) {
            if(edt_passwordbaru.getText().toString().equals(edt_passwordulangi.getText().toString())){
                UserModel userModel = HopeFunction.getUserPreference(getContext());
                userModel.setPassword(edt_passwordlama.getText().toString());
                presenter.requestUbahPassword(userModel, edt_passwordbaru.getText().toString());
            } else {
                edt_passwordulangi.setError("Password belum sesuai");
            }
        }
    }

    ArrayList<Integer> forms = new ArrayList<>();
    private void setForms(){
        forms.add(R.id.edt_passwordlama);
        forms.add(R.id.edt_passwordbaru);
        forms.add(R.id.edt_passwordulangi);
    }

    @Override
    public void onFailedRequestUbahPassword(@NotNull String message) {
        LibUi.ToastShort(getContext(), message);
    }
}
