package id.co.hope.dialogs.DialogImagePicker;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import id.co.hope.R;
import lib.almuwahhid.utils.DialogBuilder;


public class DialogImagePicker extends DialogBuilder {

    RelativeLayout lay_dialogimagepicker;
    LinearLayout lay_camera;
    LinearLayout lay_folder;

    OnDialogImagePicker onDialogImagePicker;

    public DialogImagePicker(Context context, OnDialogImagePicker dialogImagePicker) {
        super(context, R.layout.dialog_image_picker);

        initComponent(new OnInitComponent() {
            @Override
            public void initComponent(Dialog dialog) {
                lay_dialogimagepicker = dialog.findViewById(R.id.lay_dialogimagepicker);
                lay_camera = dialog.findViewById(R.id.lay_camera);
                lay_folder = dialog.findViewById(R.id.lay_folder);

                setFullWidth(lay_dialogimagepicker);
                setGravity(Gravity.BOTTOM);
                setAnimation(R.style.DialogBottomAnimation);

                onDialogImagePicker = dialogImagePicker;

                lay_camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                        onDialogImagePicker.onCameraClick();
                    }
                });


                lay_folder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                        onDialogImagePicker.onFileManagerClick();
                    }
                });

                show();
            }
        });

    }

    public interface OnDialogImagePicker{
        void onCameraClick();
        void onFileManagerClick();
    }
}
