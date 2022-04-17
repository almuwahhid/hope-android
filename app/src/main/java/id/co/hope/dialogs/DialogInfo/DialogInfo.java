package id.co.hope.dialogs.DialogInfo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import id.co.hope.R;
import lib.almuwahhid.utils.DialogBuilder;

public class DialogInfo extends DialogBuilder {

    RelativeLayout lay_dialog;
    ImageView img_close;
    ImageView img_ilustrasi;
    TextView tv_konfirmasi;
    Button btn_konfirmasi;

    OnDialogConfirm onDialogConfirm;

    public DialogInfo(Context context, final String title, final String button_name, final int image_source, final OnDialogConfirm onDialogConfirm) {
        super(context, R.layout.dialog_info);
        this.onDialogConfirm = onDialogConfirm;

        initComponent(new OnInitComponent() {
            @Override
            public void initComponent(Dialog dialog) {
                lay_dialog = dialog.findViewById(R.id.lay_dialog);
                img_close = dialog.findViewById(R.id.img_close);
                img_ilustrasi = dialog.findViewById(R.id.img_ilustrasi);
                tv_konfirmasi = dialog.findViewById(R.id.tv_konfirmasi);
                btn_konfirmasi = dialog.findViewById(R.id.btn_konfirmasi);

                setFullWidth(lay_dialog);
                setGravity(Gravity.BOTTOM);
                setAnimation(R.style.DialogBottomAnimation);

                setComponent(title, button_name, image_source, onDialogConfirm);

                img_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });

                btn_konfirmasi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                        onDialogConfirm.onDialogConfirmClick();
                    }
                });
                show();

                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        onDialogConfirm.onCancelConfirmClick();
                    }
                });
            }
        });

        show();
    }

    public interface OnDialogConfirm{
        void onDialogConfirmClick();
        void onCancelConfirmClick();
    }

    public void setComponent(String title, String button_name, int image_source, OnDialogConfirm onDialogConfirm) {
        btn_konfirmasi.setText(button_name);
        this.onDialogConfirm = onDialogConfirm;
        tv_konfirmasi.setText(title);

        if(image_source != 0)
            img_ilustrasi.setImageResource(image_source);
    }
}
