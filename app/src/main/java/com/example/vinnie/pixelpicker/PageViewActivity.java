package com.example.vinnie.pixelpicker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import java.util.ArrayList;
import java.util.List;

public class PageViewActivity extends FragmentActivity implements Communicator {

    private CustomPageAdapter pageAdapter;
    private ViewPager viewPager;
    private List<Fragment> fList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_view);

        // Set the page
        List<Fragment> fragments = getFragments();
        pageAdapter = new CustomPageAdapter(getSupportFragmentManager(), fragments);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(pageAdapter);

        // Start in the middle (ColorDropper class)
        viewPager.setCurrentItem(1);

        // Keep memory of the left side and right side
        viewPager.setOffscreenPageLimit(2);
    }

    private List<Fragment> getFragments() {
        // Add the fragments to the list
        fList = new ArrayList();
        fList.add(Fragment.instantiate(this, LayoutGeneratorFragment.class.getName()));
        fList.add(Fragment.instantiate(this, ColorDropperFragment.class.getName()));
        fList.add(Fragment.instantiate(this, DoodleFragment.class.getName()));
        return fList;
    }

    @Override
    public void sendColor(String hex) {
        LayoutGeneratorFragment lgf = (LayoutGeneratorFragment) fList.get(0);
        lgf.changeColors(hex);

        DoodleFragment df = (DoodleFragment) fList.get(2);
        df.changeDoodleColor(hex);
    }

    @Override
    public void updateBankColorList(DataBaseHandler dbh) {
        DoodleFragment df = (DoodleFragment) fList.get(2);
        df.updateList(dbh);
    }
}