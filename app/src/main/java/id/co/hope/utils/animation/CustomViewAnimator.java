package id.co.hope.utils.animation;

import android.view.View;
import com.daimajia.androidanimations.library.BaseViewAnimator;
import com.daimajia.androidanimations.library.attention.StandUpAnimator;

public class CustomViewAnimator extends StandUpAnimator {
    public void animate(View target) {
        this.reset(target);
        this.prepare(target);
        this.start();
    }
}
