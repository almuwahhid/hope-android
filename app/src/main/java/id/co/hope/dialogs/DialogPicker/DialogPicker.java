package id.co.hope.dialogs.DialogPicker;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import id.ac.uny.riset.ride.data.model.PickerModel;
import id.co.hope.R;
import lib.almuwahhid.utils.DialogBuilder;

import java.util.List;

public class DialogPicker extends DialogBuilder {
    List<PickerModel> pickerModelList;
    RecyclerView rv;
    ImageView img_close;
    AdapterDialogPicker adapterDialogPicker;
    RelativeLayout lay_picker;
    OnDialogPicker onDialogPicker;

    public DialogPicker(Context context, final List<PickerModel> pickerModelList, String keyword, final OnDialogPicker onDialogPicker) {
        super(context, R.layout.dialog_picker);
        this.pickerModelList = pickerModelList;
        this.onDialogPicker = onDialogPicker;

        initComponent(new OnInitComponent() {
            @Override
            public void initComponent(Dialog dialog) {
                img_close = dialog.findViewById(R.id.img_close);
                lay_picker = dialog.findViewById(R.id.lay_picker);
                rv = dialog.findViewById(R.id.rv);
                setFullWidth(lay_picker);
                setGravity(Gravity.BOTTOM);
                setAnimation(R.style.DialogBottomAnimation);

                rv.setLayoutManager(new LinearLayoutManager(getContext()));
                adapterDialogPicker = new AdapterDialogPicker(getContext(), pickerModelList, keyword, new AdapterDialogPicker.OnClickPicker() {
                    @Override
                    public void onClickPicker(PickerModel model) {
//                        setCheckedData(model);
                        onDialogPicker.onDialogClicked(model);
                        dismiss();
                    }
                });
                rv.setAdapter(adapterDialogPicker);

                img_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });
            }
        });

        show();
    }

    public void setCheckedData(PickerModel model){
        for (int i = 0; i < pickerModelList.size(); i++) {
            if(pickerModelList.get(i).getId().equals(model.getId())){
                pickerModelList.get(i).setClicked(true);
            } else {
                pickerModelList.get(i).setClicked(false);
            }
        }
        adapterDialogPicker.notifyDataSetChanged();
    }

    public interface OnDialogPicker{
        void onDialogClicked(PickerModel model);
    }
}
