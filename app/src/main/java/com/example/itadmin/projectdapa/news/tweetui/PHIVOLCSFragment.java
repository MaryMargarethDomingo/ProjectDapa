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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.itadmin.projectdapa.R;
import com.example.itadmin.projectdapa.news.twittercore.TwitterCoreMainActivity;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthException;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

public class PHIVOLCSFragment extends Fragment {

    public static PHIVOLCSFragment newInstance() {
        return new PHIVOLCSFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.twitter_fragment_phivolc, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final UserTimeline userTimeline = new UserTimeline.Builder().screenName("phivolcs_dost").build();

        // launch the app login activity when a guest user tries to favorite a Tweet
        final Callback<Tweet> actionCallback = new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> result) {
                // Intentionally blank
            }

            @Override
            public void failure(TwitterException exception) {
                if (exception instanceof TwitterAuthException) {
                    startActivity(TwitterCoreMainActivity.newIntent(getActivity()));
                }
            }
        };

        final TweetTimelineRecyclerViewAdapter adapter =
                new TweetTimelineRecyclerViewAdapter.Builder(getContext())
                        .setTimeline(userTimeline)
                        .setViewStyle(R.style.tw__TweetLightWithActionsStyle)
                        .setOnActionCallback(actionCallback)
                        .build();

        recyclerView.setAdapter(adapter);

        return view;
    }

}