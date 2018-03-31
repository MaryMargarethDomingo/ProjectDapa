/*
 * Copyright (C) 2015 Twitter, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.itadmin.projectdapa.survival.controller.survivalContentPagers;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.itadmin.projectdapa.R;
import com.example.itadmin.projectdapa.survival.controller.survivalWebPages.WebViewEarthquakeFragment;
import com.example.itadmin.projectdapa.survival.controller.survivalWebPages.WebViewVolcanoFragment;

/**
 * TwitterFragment pages between different timeline Fragments.
 */
public class SurvivalContentPagerVolcano extends Fragment {
    private static final int PAGE_BEFORE = 0;
    private static final int PAGE_DURING = 1;
    private static final int PAGE_AFTER = 2;

    private static WebViewVolcanoFragment webViewVolcanoFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.survival_content_pager, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        final FragmentManager fm = getActivity().getSupportFragmentManager();
        final FragmentPagerAdapter pagerAdapter = new TimelinePagerAdapter(fm, getResources());
        final ViewPager viewPager = getView().findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);

        final TabLayout tabLayout = getView().findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    public static class TimelinePagerAdapter extends FragmentPagerAdapter {
        /*// titles for timeline fragments, in order
        private static final int[] PAGE_TITLE_RES_IDS = {
                R.string.search_timeline_title,
                R.string.user_timeline_title,
                R.string.user_recycler_view_timeline_title,

        };*/
        private Resources resources;

        public TimelinePagerAdapter(FragmentManager fm, Resources resources) {
            super(fm);
            this.resources = resources;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case PAGE_BEFORE:
                   webViewVolcanoFragment = new WebViewVolcanoFragment(0);
                   return webViewVolcanoFragment;

                case PAGE_DURING:
                    webViewVolcanoFragment = new WebViewVolcanoFragment(1);
                    return webViewVolcanoFragment;

                case PAGE_AFTER:
                    webViewVolcanoFragment = new WebViewVolcanoFragment(2);
                    return webViewVolcanoFragment;

                default:
                    throw new IllegalStateException("Unexpected Fragment page item requested.");
            }

        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case PAGE_BEFORE:
                    return "BEFORE";
                case PAGE_DURING:
                    return "DURING";
                case PAGE_AFTER:
                    return "AFTER";
                default:
                    throw new IllegalStateException("Unexpected Fragment page title requested.");
            }
        }
    }
}
