package com.esprit.android.inart.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.esprit.android.inart.FillableLoaderPage;

/**
 * As a fast workaround to make this work, i am going to retain all three pages in memory in order
 * to avoid problems when trying to call the resettable callback from the
 * OnPageChangeListener. (When it is called, the fragment could not be totally
 * created as its documentation says so.)
 *
 * @author jorge
 * @since 11/08/15
 */
public class FillablePagesAdapter extends FragmentStatePagerAdapter {

  private FillableLoaderPage firstPage;
  private FillableLoaderPage secondPage;
  private FillableLoaderPage thirdPage;
  private FillableLoaderPage fourthPage;
  private FillableLoaderPage fifthPage;
  private FillableLoaderPage sixthPage;

  public FillablePagesAdapter(FragmentManager fm) {
    super(fm);
    firstPage = FillableLoaderPage.newInstance(0);
    secondPage = FillableLoaderPage.newInstance(1);
    thirdPage = FillableLoaderPage.newInstance(2);
    fourthPage = FillableLoaderPage.newInstance(3);
    fifthPage = FillableLoaderPage.newInstance(4);
    sixthPage = FillableLoaderPage.newInstance(5);
  }

  @Override
  public Fragment getItem(int position) {
    return getFragmentForPosition(position);
  }

  @Override
  public int getCount() {
    return 6;
  }

  private Fragment getFragmentForPosition(int position) {
    switch (position) {
      case 0:
        return firstPage;
      case 1:
        return secondPage;
      case 2:
        return thirdPage;
      case 3:
        return fourthPage;
      case 4:
        return fifthPage;
      default:
        return sixthPage;
    }
  }

  @Override
  public CharSequence getPageTitle(int position) {
    switch (position) {
      case 0:
        return "Cinema";
      case 1:
        return "Music";
      case 2:
        return "Photography";
      case 3:
        return "Painting";
      case 4:
        return "Theatre";
      default:
        return "";
    }
  }
}