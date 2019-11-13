package id.co.hope.app.hubungikonseler;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.daimajia.easing.linear.Linear;
import id.co.hope.R;
import lib.almuwahhid.utils.DialogBuilder;
import lib.almuwahhid.utils.LibUi;

public class HubungiConselerDialog extends DialogBuilder {
    @BindView(R.id.lay_dialog)
    RelativeLayout lay_dialog;
    @BindView(R.id.lay_callphone)
    LinearLayout lay_callphone;
    @BindView(R.id.lay_email)
    LinearLayout lay_email;

    public HubungiConselerDialog(Context context) {
        super(context, R.layout.dialog_hubungiconseler);
        ButterKnife.bind(this, getDialog());

        setAnimation(R.style.DialogBottomAnimation);
        setFullWidth(lay_dialog);
        setGravity(Gravity.BOTTOM);

        lay_callphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:085729626666"));
                getContext().startActivity(intent);
            }
        });

        lay_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"" + view.getTag(view.getId())});
                i.putExtra(Intent.EXTRA_SUBJECT, "HOPE");
                i.putExtra(Intent.EXTRA_TEXT, "");
                try {
                    getActivity().startActivity(Intent.createChooser(i, "Kirim email"));
                } catch (android.content.ActivityNotFoundException ex) {
                    LibUi.ToastShort(getActivity(), "Tidak ada aplikasi email terpasang");
                }
            }
        });

        show();
    }
}
