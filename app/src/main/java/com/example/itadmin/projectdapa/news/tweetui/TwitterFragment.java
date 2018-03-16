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

package com.example.itadmin.projectdapa.news.tweetui;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.itadmin.projectdapa.R;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * TwitterFragment pages between different timeline Fragments.
 */
public class TwitterFragment extends Fragment {
    private static final int PAGE_NDRRMC = 0;
    private static final int PAGE_PAGASA = 1;
    private static final int PAGE_PHIVOLCS = 2;
    private static final int PAGE_IMREADY = 3;
    private static final int PAGE_WALANGPASOK = 4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.twitter_activity_pager, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());

        Twitter.initialize(getActivity());

        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        final OkHttpClient customClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor).build();

        final TwitterSession activeSession = TwitterCore.getInstance()
                .getSessionManager().getActiveSession();

        final TwitterApiClient customApiClient;
        if (activeSession != null) {
            customApiClient = new TwitterApiClient(activeSession, customClient);
            TwitterCore.getInstance().addApiClient(activeSession, customApiClient);
        } else {
            customApiClient = new TwitterApiClient(customClient);
            TwitterCore.getInstance().addGuestApiClient(customApiClient);
        }

        final FragmentManager fm = getActivity().getSupportFragmentManager();
        final FragmentPagerAdapter pagerAdapter = new TimelinePagerAdapter(fm, getResources());
        final ViewPager viewPager = getView().findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);

        final TabLayout tabLayout = getView().findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    public static class TimelinePagerAdapter extends FragmentPagerAdapter {
        // titles for timeline fragments, in order
        private static final int[] PAGE_TITLE_RES_IDS = {
                R.string.search_timeline_title,
                R.string.user_timeline_title,
                R.string.user_recycler_view_timeline_title,
                R.string.collection_timeline_title,
                R.string.list_timeline_title,
        };
        private Resources resources;

        public TimelinePagerAdapter(FragmentManager fm, Resources resources) {
            super(fm);
            this.resources = resources;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case PAGE_NDRRMC:
                    return NDRRMCFragment.newInstance();
                case PAGE_PAGASA:
                    return PAGASAFragment.newInstance();
                case PAGE_PHIVOLCS:
                    return PHIVOLCSFragment.newInstance();
                case PAGE_IMREADY:
                    return IMReadyFragment.newInstance();
                case PAGE_WALANGPASOK:
                    return WalangPasokFragment.newInstance();
                default:
                    throw new IllegalStateException("Unexpected Fragment page item requested.");
            }
        }

        @Override
        public int getCount() {
            return PAGE_TITLE_RES_IDS.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case PAGE_NDRRMC:
                    return resources.getString(PAGE_TITLE_RES_IDS[PAGE_NDRRMC]);
                case PAGE_PAGASA:
                    return resources.getString(PAGE_TITLE_RES_IDS[PAGE_PAGASA]);
                case PAGE_PHIVOLCS:
                    return resources.getString(PAGE_TITLE_RES_IDS[PAGE_PHIVOLCS]);
                case PAGE_IMREADY:
                    return resources.getString(PAGE_TITLE_RES_IDS[PAGE_IMREADY]);
                case PAGE_WALANGPASOK:
                    return resources.getString(PAGE_TITLE_RES_IDS[PAGE_WALANGPASOK]);
                default:
                    throw new IllegalStateException("Unexpected Fragment page title requested.");
            }
        }
    }
}
