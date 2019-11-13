package id.co.hope.dialogs.DialogPicker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

import id.ac.uny.riset.ride.data.model.PickerModel;
import id.co.hope.R;

import java.util.List;

public class AdapterDialogPicker extends RecyclerView.Adapter<AdapterDialogPicker.Holder> {
    Context context;
    List<PickerModel> list;
    String keyword;
    OnClickPicker onClickPicker;

    public AdapterDialogPicker(Context context, List<PickerModel> list, String keyword, OnClickPicker onClickPicker) {
        this.context = context;
        this.list = list;
        this.keyword = keyword;
        this.onClickPicker = onClickPicker;
    }

    @NonNull
    @Override
    public AdapterDialogPicker.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View layoutView;
        layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_dialog_picker, viewGroup, false);
        AdapterDialogPicker.Holder rcv = new AdapterDialogPicker.Holder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDialogPicker.Holder holder, int position) {
        final PickerModel pickerModel = list.get(position);
        holder.tv.setText(pickerModel.getName());
        if(pickerModel.getName().equalsIgnoreCase(keyword)){
            holder.lay_picker.setBackgroundColor(context.getResources().getColor(R.color.grey_100));
            holder.checkbox.setChecked(true);
        } else {
            holder.lay_picker.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.checkbox.setChecked(false);
        }

        holder.lay_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickPicker.onClickPicker(pickerModel);
            }
        });
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    onClickPicker.onClickPicker(pickerModel);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.lay_picker)
        RelativeLayout lay_picker;
        @BindView(R.id.tv)
        TextView tv;
        @BindView(R.id.checkbox)
        CheckBox checkbox;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    interface OnClickPicker{
        void onClickPicker(PickerModel model);
    }
}
