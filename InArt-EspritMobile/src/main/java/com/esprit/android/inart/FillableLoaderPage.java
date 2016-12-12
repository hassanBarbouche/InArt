/*
 * Copyright (C) 2015 Jorge Castillo Pérez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.esprit.android.inart;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.esprit.android.inart.Utils.Paths;
import com.esprit.android.inart.ui.MainActivity;
import com.github.jorgecastillo.FillableLoader;
import com.github.jorgecastillo.FillableLoaderBuilder;
import com.github.jorgecastillo.clippingtransforms.WavesClippingTransform;
import com.github.jorgecastillo.listener.OnStateChangeListener;
import com.esprit.android.inart.Utils.ResettableView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import butterknife.Bind;
import butterknife.ButterKnife;
//import butterknife.InjectViews;

/**
 * @author jorge
 * @since 11/08/15
 */
public class FillableLoaderPage extends Fragment implements OnStateChangeListener, ResettableView {

  @Bind(R.id.fillableLoader) @Nullable
  FillableLoader fillableLoader;
  private View rootView;
  private int pageNum;
    Button lets;

  public static FillableLoaderPage newInstance(int pageNum) {
    FillableLoaderPage page = new FillableLoaderPage();
    Bundle bundle = new Bundle();
    bundle.putInt("pageNum", pageNum);
    page.setArguments(bundle);


    return page;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    pageNum = getArguments().getInt("pageNum", 0);



    switch (pageNum) {
      case 0:
        rootView = inflater.inflate(R.layout.fragment_fillable_loader_first_page, container, false);
        break;
      case 1:
        rootView =
            inflater.inflate(R.layout.fragment_fillable_loader_second_page, container, false);
        break;
      case 2:
        rootView = inflater.inflate(R.layout.fragment_fillable_loader_third_page, container, false);
        break;
      case 3:
        rootView =
            inflater.inflate(R.layout.fragment_fillable_loader_fourth_page, container, false);
        break;
      case 4:
        rootView = inflater.inflate(R.layout.fragment_fillable_loader_fifth_page, container, false);
        break;
        case 5:
            rootView = inflater.inflate(R.layout.fragment_fillable_loader_sixth_page, container, false);
            lets = (Button) rootView.findViewById(R.id.letsgo);
            lets.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    Intent intent = new Intent(
                            getActivity(),
                            MainActivity.class);
                    startActivity(intent);

                    return false;
                }
            });

            break;

    }

    return rootView;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
   try{ ButterKnife.bind(this, rootView);}
   catch (Exception E){
       System.out.println(E.getMessage());
   }
      setupFillableLoader(pageNum);

  }

  private void setupFillableLoader(int pageNum) {
    if (pageNum == 3) {
      int viewSize = getResources().getDimensionPixelSize(R.dimen.fourthSampleViewSize);
      FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(viewSize, viewSize);
      params.gravity = Gravity.CENTER;

      FillableLoaderBuilder loaderBuilder = new FillableLoaderBuilder();
      fillableLoader = loaderBuilder.parentView((FrameLayout) rootView)
          .svgPath(Paths.pinceau)
          .layoutParams(params)
          .originalDimensions(1610, 1350)
          .strokeColor(Color.parseColor("#FFFFFF"))
          .fillColor(Color.parseColor("#FFFFFF"))
          .strokeDrawingDuration(1500)
          .clippingTransform(new WavesClippingTransform())
          .fillDuration(2000)

          .build();
    } else {
     try{ fillableLoader.setSvgPath(pageNum == 0 ? Paths.RONALDO : pageNum == 1 ? Paths.INDOMINUS_REX
          : pageNum == 2 ? Paths.Photo : pageNum == 4 ? Paths.GITHUB :pageNum == 5 ? Paths.in: Paths.GITHUB);}
     catch (Exception e){
         System.out.println(e.toString());
     }}


    fillableLoader.setOnStateChangeListener(this);
  }

  @Override
  public void onStateChange(int state) {
  // ((PageV) getActivity()).showStateHint(state);
  }

  @Override
  public void reset() {
    fillableLoader.reset();

    //We wait a little bit to start the animation, to not contaminate the drawing effect
    //by the activity creation animation.
    fillableLoader.postDelayed(new Runnable() {
        @Override
        public void run() {
            fillableLoader.start();
        }
    }, 250);
  }
    public String readSavedData ( ) {
        StringBuffer datax = new StringBuffer("");
        try {
            FileInputStream fIn = this.getActivity().openFileInput("loulou.txt") ;
            InputStreamReader isr = new InputStreamReader( fIn ) ;
            BufferedReader buffreader = new BufferedReader( isr ) ;

            String readString = buffreader.readLine ( ) ;
            while ( readString != null ) {
                datax.append(readString);
                readString = buffreader.readLine ( ) ;
            }

            isr.close ( ) ;
        } catch ( IOException ioe ) {
            ioe.printStackTrace ( ) ;
        }
        return datax.toString() ;
    }



}
