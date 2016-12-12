package com.esprit.android.inart;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.esprit.android.inart.Utils.Utils;
import com.esprit.android.inart.adapters.FillablePagesAdapter;
import com.esprit.android.inart.Utils.ResettableView;
import com.esprit.android.inart.ui.BaseActivity;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author jorge
 * @since 7/08/15
 */
public class PageV extends BaseActivity {

  public String tex;

  @Bind(R.id.pager2)
  ViewPager pager;
  @Bind(R.id.clippingTransformMode)
  TextView clippingModeText;
  @Bind(R.id.fab)
  FloatingActionButton fab;
  @Bind(R.id.imagepv)
  ImageView mImageView;


Button go;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.page_v);
    ButterKnife.bind(this);

  // go = (Button) findViewById(R.id.letsgo2);
 /*  go.setOnTouchListener(new View.OnTouchListener() {

      @Override
      public boolean onTouch(View v, MotionEvent event) {
        Intent intent = new Intent(PageV.this, MainActivity.class);
      //  intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);

        //overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_in_left);


        return false;
      }
    });*/
    mBackground = mImageView;
    moveBackground();
    setupPagination();
    setupDisableTraceButton();
  }

  private void setupPagination() {
    pager = (ViewPager) findViewById(R.id.pager2);
    final FillablePagesAdapter adapter = new FillablePagesAdapter(getSupportFragmentManager());
    pager.setAdapter(adapter);
    CirclePageIndicator titleIndicator = (CirclePageIndicator)findViewById(R.id.titles);
    titleIndicator.setViewPager(pager);


  pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
      @Override
      public void onPageSelected(int position) {
        super.onPageSelected(position);
        ((ResettableView) adapter.getItem(position)).reset();
        clippingModeText.setText(adapter.getPageTitle(position));
      }
    });

    pager.post(new Runnable() {
      @Override
      public void run() {
        ((ResettableView) adapter.getItem(0)).reset();
        clippingModeText.setText(adapter.getPageTitle(0));
      }
    });
  }

  private void setupDisableTraceButton() {
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

      }
    });
  }

  public void showStateHint(int state) {
    Snackbar.make(fab, "State changed callback: " + state, Snackbar.LENGTH_SHORT).show();
  }
  public void writeData ( String data ) {
    try {
      FileOutputStream fOut = openFileOutput ( "settings.dat" , MODE_WORLD_READABLE ) ;
      OutputStreamWriter osw = new OutputStreamWriter( fOut ) ;
      osw.write ( data ) ;
      osw.flush ( ) ;
      osw.close ( ) ;
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    if (Utils.hasHoneycomb()) {
      View demoContainerView = findViewById(R.id.imagepv);
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