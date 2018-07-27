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

package com.example.itadmin.projectdapa.news.controller;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.itadmin.projectdapa.R;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthException;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import java.lang.ref.WeakReference;

public class WalangPasokFragment extends Fragment {

    public static WalangPasokFragment newInstance() {
        return new WalangPasokFragment();
    }
    final WeakReference<Activity> activityRef = new WeakReference<Activity>(getActivity());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.twitter_timeline_recyclerview, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        final SwipeRefreshLayout swipeLayout = view.findViewById(R.id.swipe_layout);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final UserTimeline userTimeline = new UserTimeline.Builder().screenName("walangpasok").build();

        // launch the app login activity when a guest user tries to favorite a Tweet
        final Callback<Tweet> actionCallback = new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> result) {
                // Intentionally blank
            }

            @Override
            public void failure(TwitterException exception) {
                if (exception instanceof TwitterAuthException) {
                    startActivity(TwitterLoginFragment.newIntent(getActivity()));
                }
            }
        };

        final TweetTimelineRecyclerViewAdapter adapter =
                new TweetTimelineRecyclerViewAdapter.Builder(getContext())
                        .setTimeline(userTimeline)
                        .setViewStyle(R.style.tw__TweetDarkWithActionsStyle)
                        .setOnActionCallback(actionCallback)
                        .build();

        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean enableRefresh = false;
            @Override
            public void onScrollStateChanged(RecyclerView view, int scrollState) {}
            @Override
            public void onScrolled(RecyclerView view, int dx, int dy) {
                if (recyclerView != null && recyclerView.getChildCount() > 0) {
                    // check that the first item is visible and that its top matches the parent
                    enableRefresh = recyclerView.getChildAt(0).getTop() >= 0;
                } else {
                    enableRefresh = false;
                }
                swipeLayout.setEnabled(enableRefresh);
            }
        });

        // specify action to take on swipe refresh
        swipeLayout.setOnRefreshListener(() -> {
            swipeLayout.setRefreshing(true);
            adapter.refresh(new Callback<TimelineResult<Tweet>>() {
                @Override
                public void success(Result<TimelineResult<Tweet>> result) {
                    swipeLayout.setRefreshing(false);
                }

                @Override
                public void failure(TwitterException exception) {
                    swipeLayout.setRefreshing(false);
                    final Activity activity = activityRef.get();
                    if (activity != null && !activity.isFinishing()) {
                        Toast.makeText(activity, exception.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });

        return view;
    }

}