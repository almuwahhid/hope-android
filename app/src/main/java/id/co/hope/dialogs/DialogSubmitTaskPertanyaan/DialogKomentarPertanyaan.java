package id.co.hope.dialogs.DialogSubmitTaskPertanyaan;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;


import id.co.hope.R;
import lib.almuwahhid.utils.DialogBuilder;

public class DialogKomentarPertanyaan extends DialogBuilder {
    RelativeLayout lay_dialog;
    EditText edt_komentar;
    Button btn_submit;
    OnDialogKomentarPertanyaan onDialogKomentarPertanyaan;

    public DialogKomentarPertanyaan(Context context, final OnDialogKomentarPertanyaan onDialogKomentarPertanyaan) {
        super(context, R.layout.dialog_komentar_pertanyaan);
        this.onDialogKomentarPertanyaan = onDialogKomentarPertanyaan;

        initComponent(new OnInitComponent() {
            @Override
            public void initComponent(Dialog dialog) {
                lay_dialog = dialog.findViewById(R.id.lay_dialog);
                edt_komentar = dialog.findViewById(R.id.edt_komentar);
                btn_submit = dialog.findViewById(R.id.btn_submit);

                setFullWidth(lay_dialog);
                setGravity(Gravity.BOTTOM);
                setAnimation(R.style.DialogBottomAnimation);
                setCancellable(false);

                btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(edt_komentar.getText().toString().equals("")){
                            edt_komentar.setError("Tanggapan harus diisi");
                        } else {
                            dismiss();
                            onDialogKomentarPertanyaan.onSubmitKomentar(edt_komentar.getText().toString());
                        }
                    }
                });

            }
        });
        show();
    }

    public interface OnDialogKomentarPertanyaan{
        void onSubmitKomentar(String komentar);
    }
}
