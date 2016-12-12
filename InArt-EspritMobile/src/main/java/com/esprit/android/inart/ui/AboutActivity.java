package com.esprit.android.inart.ui;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.TextView;


import com.esprit.android.inart.Utils.ImageUtil;
import com.esprit.android.inart.Utils.Utils;
import com.esprit.android.inart.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import butterknife.ButterKnife;
import butterknife.Bind;

/**
 * Created by jpardogo on 23/02/2014.
 */
public class AboutActivity extends BaseActivity {

    @Bind(R.id.about_body)
    TextView mTextView;

    @Bind(R.id.image)
    ImageView mImageView;

    @Bind(R.id.hassanphoto)
    ImageView Hassan;

    @Bind(R.id.mariemphoto)
    ImageView mariem;

    protected ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        ButterKnife.bind(this);
        mBackground = mImageView;
        moveBackground();
       // mTextView.setText(Html.fromHtml(getString(R.string.about_cody, Utils.getVersionName(this))));
        mTextView.setMovementMethod(new LinkMovementMethod());
        Hassan.setImageResource(R.drawable.hassan2);
        mariem.setImageResource(R.drawable.mariem2);
        ImageUtil.displayRoundImage(Hassan, "http://img11.hostingpics.net/pics/828862hassan2.jpg", null);
        ImageUtil.displayRoundImage(mariem, "http://img11.hostingpics.net/pics/629126mariem2.jpg", null);

    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (Utils.hasHoneycomb()) {
            View demoContainerView = findViewById(R.id.image);
            demoContainerView.setAlpha(0);
            ViewPropertyAnimator animator = demoContainerView.animate();
            animator.alpha(1);
            if (Utils.hasICS()) {
                animator.setStartDelay(250);
            }
            animator.setDuration(1000);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}

