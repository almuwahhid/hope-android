package id.co.hope.dialogs.DialogDetailAchievement;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import id.ac.uny.riset.ride.data.model.SurveyModel;
import id.co.hope.R;
import id.co.hope.dialogs.DialogDetailAchievement.adapter.DialogDetailAchievementAdapter;
import lib.almuwahhid.utils.DialogBuilder;
import lib.almuwahhid.utils.LibUi;
import lib.almuwahhid.utils.UiLibRequest;
import lib.almuwahhid.utils.downloader.Exception.UiLibDownloadException;
import lib.almuwahhid.utils.downloader.UiLibDownloadCallback;
import lib.almuwahhid.utils.downloader.UiLibDownloadManager;
import lib.almuwahhid.utils.downloader.UiLibDownloadRequest;

public class DialogDetailAchievement extends DialogBuilder {
    RelativeLayout lay_dialog;
    ImageView img_close, img_badges;
    TextView tv_title, tv_description;
    DialogDetailAchievementAdapter adapter;
    RecyclerView rv;
    Uri destination;
    CardView card_download;

    public DialogDetailAchievement(Context context, SurveyModel surveyModel) {
        super(context, R.layout.dialog_detail_acievement);
        destination = Uri.withAppendedPath(Uri.fromFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)), "Hope");

        initComponent(new OnInitComponent() {
            @Override
            public void initComponent(Dialog dialog) {
                lay_dialog = dialog.findViewById(R.id.lay_dialog);
                img_close = dialog.findViewById(R.id.img_close);
                img_badges = dialog.findViewById(R.id.img_badges);
                tv_title = dialog.findViewById(R.id.tv_title);
                tv_description = dialog.findViewById(R.id.tv_description);
                rv = dialog.findViewById(R.id.rv);
                card_download = dialog.findViewById(R.id.card_download);

                setFullWidth(lay_dialog);
                setGravity(Gravity.BOTTOM);
                setAnimation(R.style.DialogBottomAnimation);

                adapter = new DialogDetailAchievementAdapter(getContext(), surveyModel);
                rv.setLayoutManager(new LinearLayoutManager(getContext()));
                rv.setAdapter(adapter);

                img_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });

                tv_title.setText(surveyModel.getNama_status());
                tv_description.setText(surveyModel.getDeskripsi_status());

                switch (surveyModel.getId_status_identitas_religius()){
                    case "1":
                        img_badges.setImageResource(R.drawable.ic_medal_gold);
                        break;
                    case "2":
                        img_badges.setImageResource(R.drawable.ic_medal_silver);
                        break;
                    case "3":
                        img_badges.setImageResource(R.drawable.ic_medal_platinum);
                        break;
                    case "4":
                        img_badges.setImageResource(R.drawable.ic_medal_bronze);
                        break;
                    default:
                        img_badges.setImageResource(R.drawable.ic_medal_gold);
                        break;
                }

                card_download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            new UiLibDownloadManager(context, new UiLibDownloadCallback(){
                                @Override
                                public void onProcess(UiLibDownloadRequest request) {
//                            onChatRoomClick.onDownloadingAttachment(true, position);
                                    LibUi.ToastShort(context, "Sedang mendownload dokumen");

                                }

                                @Override
                                public void onCancel(UiLibDownloadRequest request) {

                                }

                                @Override
                                public void onSuccess(UiLibDownloadRequest request) {
                                    LibUi.ToastShort(context, "Sukses mendownload dokumen");
//                            onChatRoomClick.onDownloadingAttachment(false, position);
//                                        onChatRoomClick.onClickAttachment(chat, request.getDestinationUri());
                                }
                            }).startRequest(new UiLibDownloadRequest(Uri.parse("asd")).setDestinationUri(destination));
                        } catch (UiLibDownloadException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

        show();
    }
}
