package id.co.hope.app.lupapassword;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.uny.riset.ride.utils.dialogs.DialogLupaPassword.DialogLupaPasswordPresenter;
import id.ac.uny.riset.ride.utils.dialogs.DialogLupaPassword.DialogLupaPasswordView;
import id.co.hope.R;
import lib.almuwahhid.utils.DialogBuilder;
import lib.almuwahhid.utils.LibUi;

public class LupaPasswordDialog extends DialogBuilder implements DialogLupaPasswordView.View{

    @BindView(R.id.btn_kirim)
    Button btn_kirim;
    @BindView(R.id.edt_email)
    EditText edt_email;
    @BindView(R.id.lay_dialog)
    RelativeLayout lay_dialog;

    DialogLupaPasswordPresenter presenter;


    public LupaPasswordDialog(Context context) {
        super(context, R.layout.dialog_lupa_password);
        ButterKnife.bind(this, getDialog());

        presenter = new DialogLupaPasswordPresenter(getContext(), this);

        setAnimation(R.style.DialogBottomAnimation);
        setFullWidth(lay_dialog);
        setGravity(Gravity.BOTTOM);

        btn_kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edt_email.getText().toString().equals("")){
                    edt_email.setError("Isi kolom email terlebih dahulu");
                } else {
                    presenter.requestLupaPassword(edt_email.getText().toString());
                }
            }
        });

        show();
    }

    @Override
    public void onRequestLupaPassword(boolean isExist) {
        if(isExist){
            LibUi.ToastShort(getContext(), "Pesan sudah terkirim ke email Anda, silahkan cek email");
            dismiss();
        } else {
            LibUi.ToastShort(getContext(), "Email tidak tersedia");
        }
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
        LibUi.ToastShort(getContext(), "Ada yang bermasalah dengan server");
    }
}
