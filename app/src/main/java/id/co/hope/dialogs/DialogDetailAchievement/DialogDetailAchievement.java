package id.co.hope.dialogs.DialogDetailAchievement;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class DialogDetailAchievement extends DialogBuilder {
    RelativeLayout lay_dialog;
    ImageView img_close, img_badges;
    TextView tv_title, tv_description;
    DialogDetailAchievementAdapter adapter;
    RecyclerView rv;

    public DialogDetailAchievement(Context context, SurveyModel surveyModel) {
        super(context, R.layout.dialog_detail_acievement);

        initComponent(new OnInitComponent() {
            @Override
            public void initComponent(Dialog dialog) {
                lay_dialog = dialog.findViewById(R.id.lay_dialog);
                img_close = dialog.findViewById(R.id.img_close);
                img_badges = dialog.findViewById(R.id.img_badges);
                tv_title = dialog.findViewById(R.id.tv_title);
                tv_description = dialog.findViewById(R.id.tv_description);
                rv = dialog.findViewById(R.id.rv);

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

                show();
            }
        });
    }
}
