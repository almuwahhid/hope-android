package id.co.hope.dialogs.MinatPenggunaDialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.uny.riset.ride.data.model.PickerModel;
import id.co.hope.R;
import id.co.hope.data.StaticData;
import id.co.hope.data.model.UserModel;
import id.co.hope.dialogs.DialogPicker.DialogPicker;
import id.co.hope.utils.HopeFunction;
import lib.almuwahhid.utils.DialogBuilder;
import lib.almuwahhid.utils.LibUi;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DialogMinatPengguna extends DialogBuilder implements DialogMinatPenggunaView.View {
    @BindView(R.id.lay_dialog)
    RelativeLayout lay_dialog;

    @BindView(R.id.edt_programstudi)
    EditText edt_programstudi;
    @BindView(R.id.edt_pekerjaanimpian)
    EditText edt_pekerjaanimpian;
    @BindView(R.id.edt_semester)
    EditText edt_semester;
    @BindView(R.id.btn_ok)
    Button btn_ok;

    DialogMinatPenggunaPresenter presenter;

    public DialogMinatPengguna(Context context) {
        super(context, R.layout.dialog_minat_pengguna);
        ButterKnife.bind(this, getDialog());

        setAnimation(R.style.DialogBottomAnimation);
        setFullWidth(lay_dialog);
        setGravity(Gravity.BOTTOM);
        setForms();

        presenter = new DialogMinatPenggunaPresenter(getContext(), this);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

        edt_pekerjaanimpian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogPicker(context, StaticData.dataPekerjaan(), edt_pekerjaanimpian.getText().toString(), new DialogPicker.OnDialogPicker() {
                    @Override
                    public void onDialogClicked(PickerModel model) {
                        edt_pekerjaanimpian.setText(model.getName());
                        edt_pekerjaanimpian.setError(null);
                    }
                });
            }
        });

        show();
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

    @Override
    public void onSubmitUpdate(@NotNull String userModel) {
        LibUi.ToastShort(getContext(), "Berhasil update biodata");
        HopeFunction.setUserPreference(getContext(), userModel);
        dismiss();
    }

    private void validate(){
        if (LibUi.isFormValid(getContext(), getDialog().getWindow().getDecorView(), forms)) {
            UserModel userModel = HopeFunction.getUserPreference(getContext());
            userModel.setUserModel(
                    edt_programstudi.getText().toString(),
                    edt_semester.getText().toString(),
                    edt_pekerjaanimpian.getText().toString());

            presenter.submitUpdate(userModel);
        }
    }

    ArrayList<Integer> forms = new ArrayList<>();
    private void setForms(){
        forms.add(R.id.edt_programstudi);
        forms.add(R.id.edt_semester);
        forms.add(R.id.edt_pekerjaanimpian);
    }
}
