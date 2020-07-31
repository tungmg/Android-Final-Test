package com.example.android_final_test.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {
    private int num_pages = 2;
    public FragmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.num_pages = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                EditFragment editFragment = new EditFragment();
                return editFragment;
            case 0:
                InformationFragment informationFragment = new InformationFragment();
                return informationFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return num_pages;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "User Information";
            case 1:
                return "Edit Information";
        }
        return null;
    }
}
