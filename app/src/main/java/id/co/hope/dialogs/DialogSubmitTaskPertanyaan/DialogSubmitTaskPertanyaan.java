package id.co.hope.dialogs.DialogSubmitTaskPertanyaan;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

import id.ac.uny.riset.ride.data.model.TaskPertanyaanModel;
import id.co.hope.R;
import id.co.hope.utils.HopeFunction;
import lib.almuwahhid.utils.DialogBuilder;
import lib.almuwahhid.utils.LibUi;

public class DialogSubmitTaskPertanyaan extends DialogBuilder {

    RelativeLayout lay_dialog;
    LinearLayout lay_action, lay_notyet;
    ImageView img_close;
    TextView tv_taskintervensi, tv_tanya, tv_notyet;
    Button btn_ok;

    Button btn_ya, btn_no;


    TaskPertanyaanModel taskIntervensiModel;
    OnDialogSubmitPertanyaan onDialogSubmitPertanyaan;


    public DialogSubmitTaskPertanyaan(Context context, final TaskPertanyaanModel taskIntervensiModel, OnDialogSubmitPertanyaan onDialogSubmitPertanyaan) {
        super(context, R.layout.dialog_submit_pertanyaan);
        this.taskIntervensiModel = taskIntervensiModel;
        this.onDialogSubmitPertanyaan = onDialogSubmitPertanyaan;

        initComponent(new OnInitComponent() {
            @Override
            public void initComponent(Dialog dialog) {
                img_close = dialog.findViewById(R.id.img_close);
                tv_taskintervensi = dialog.findViewById(R.id.tv_taskintervensi);
                btn_no = dialog.findViewById(R.id.btn_no);
                btn_ya = dialog.findViewById(R.id.btn_ya);
                tv_tanya = dialog.findViewById(R.id.tv_tanya);
                btn_ok = dialog.findViewById(R.id.btn_ok);
                lay_action = dialog.findViewById(R.id.lay_action);
                lay_notyet = dialog.findViewById(R.id.lay_notyet);
                tv_notyet = dialog.findViewById(R.id.tv_notyet);

                lay_dialog = dialog.findViewById(R.id.lay_dialog);

                setFullWidth(lay_dialog);
                setAnimation(R.style.DialogBottomAnimation);
                setGravity(Gravity.BOTTOM);

                img_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        taskIntervensiModel.setStatus_task("Y");
                        taskIntervensiModel.setKomentar_pertanyaan("");
                        onDialogSubmitPertanyaan.onSubmitTaskPertanyaan(taskIntervensiModel);
                        dismiss();
                    }
                });
                btn_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        onDialogSubmitPertanyaan.onSubmitTaskPertanyaan(taskIntervensiModel);
                        new DialogKomentarPertanyaan(getContext(), new DialogKomentarPertanyaan.OnDialogKomentarPertanyaan() {
                            @Override
                            public void onSubmitKomentar(String komentar) {
                                taskIntervensiModel.setStatus_task("T");
                                taskIntervensiModel.setKomentar_pertanyaan(komentar);
                                onDialogSubmitPertanyaan.onSubmitTaskPertanyaan(taskIntervensiModel);
                                dismiss();
                            }
                        });
                        dismiss();
                    }
                });

                btn_ya.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        taskIntervensiModel.setStatus_task("Y");
                        taskIntervensiModel.setKomentar_pertanyaan("");
                        onDialogSubmitPertanyaan.onSubmitTaskPertanyaan(taskIntervensiModel);
                        dismiss();
                    }
                });

                tv_taskintervensi.setText(taskIntervensiModel.getIntervensi_task());
//                tv_tanya.setText(taskIntervensiModel.getPertanyaan_intervensi());

                if(taskIntervensiModel.getStatus_task().equals("N")){
                    /*if(HopeFunction.isToday(taskIntervensiModel.getTanggal_task()).equals("today")){
                        lay_action.setVisibility(View.VISIBLE);
                        lay_notyet.setVisibility(View.GONE);
                    } else if(HopeFunction.isToday(taskIntervensiModel.getTanggal_task()).equals("yesterday")){
                        lay_action.setVisibility(View.GONE);
                        lay_notyet.setVisibility(View.VISIBLE);
                        tv_notyet.setText("*Intervensi sudah lewat, kamu tidak bisa mengerjakannya lagi");
                    } else if(HopeFunction.isToday(taskIntervensiModel.getTanggal_task()).equals("tomorrow")){
                        lay_action.setVisibility(View.GONE);
                        lay_notyet.setVisibility(View.VISIBLE);
                        tv_notyet.setText("*Intervensi belum bisa ditandai");
                        tv_notyet.setTextColor(getActivity().getResources().getColor(R.color.grey_500));
                    }*/
                    lay_action.setVisibility(View.VISIBLE);
                    lay_notyet.setVisibility(View.GONE);
                } else {
                    if (taskIntervensiModel.getStatus_task().equals("T")) {
                        lay_action.setVisibility(View.GONE);
                        lay_notyet.setVisibility(View.VISIBLE);
                        tv_notyet.setText("*Kamu tidak menyetujui intervensi ini");
                        tv_notyet.setTextColor(getActivity().getResources().getColor(R.color.grey_400));
                    } else {
                        lay_action.setVisibility(View.GONE);
                        lay_notyet.setVisibility(View.VISIBLE);
                        tv_notyet.setText("*Kamu telah menyetujui intervensi ini");
                        tv_notyet.setTextColor(getActivity().getResources().getColor(R.color.green_400));
                    }
                }
                show();
            }
        });
    }

//    @Override
//    public void onLoading() {
//        LibUi.showLoadingDialog(getContext(), R.drawable.ic_sand_clock);
//    }
//
//
//    @Override
//    public void onHideLoading() {
//        LibUi.hideLoadingDialog(getContext());
//    }
//
//    @Override
//    public void onErrorConnection() {
//        LibUi.ToastShort(getContext(), "Ada yang bermasalah dengan server");
//    }
//
//    @Override
//    public void onSubmitTaskPertanyaan(boolean isSuccess, String message, boolean isDone) {
//        LibUi.ToastShort(getContext(), message);
//
//        if(isSuccess){
//            onDialogSubmitPertanyaan.onFinishSubmit(isDone);
//            dismiss();
//        }
//    }

    public interface OnDialogSubmitPertanyaan{
        void onSubmitTaskPertanyaan(TaskPertanyaanModel taskPertanyaanModel);
    }
}
