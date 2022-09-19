/**
 * Copyright (c) 2022 The Brave Authors. All rights reserved.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.chromium.chrome.browser.rewards;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.IntDef;
import androidx.annotation.VisibleForTesting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.HashMap;

/**
 * BraveRewardsNativeWorker.GetPublisherBanner json response converted to this pojo class
 * */
public class BraveRewardsBannerInfo implements Parcelable {
    public static final String PUBLISHER_KEY = "publisher_key";
    public static final String TITLE = "title";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String BACKGROUND = "background";
    public static final String LOGO = "logo";
    public static final String PROVIDER = "provider";
    public static final String LINKS = "links";
    public static final String STATUS = "status";

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({NOT_CONNECTED, CONNECTED, VERIFIED, DISCONNECTED_NOT_VERIFIED, DISCONNECTED_VERIFIED,
            PENDING})
    public @interface WalletStatus {}

    public static final int NOT_CONNECTED = 0;
    public static final int CONNECTED = 1;
    public static final int VERIFIED = 2;
    public static final int DISCONNECTED_NOT_VERIFIED = 3;
    public static final int DISCONNECTED_VERIFIED = 4;
    public static final int PENDING = 5;

    private String mPublisherKey;
    private String mTitle;
    private String mName;
    private String mDescription;
    private String mBackground;

    private String mLogo;
    private String mProvider;
    private HashMap<String, String> mLinks;
    @WalletStatus
    private int mStatus;

    protected BraveRewardsBannerInfo(Parcel in) {
        mPublisherKey = in.readString();
        mTitle = in.readString();
        mName = in.readString();
        mDescription = in.readString();
        mBackground = in.readString();
        mLogo = in.readString();
        mProvider = in.readString();
        mStatus = in.readInt();
    }

    public static final Creator<BraveRewardsBannerInfo> CREATOR =
            new Creator<BraveRewardsBannerInfo>() {
                @Override
                public BraveRewardsBannerInfo createFromParcel(Parcel in) {
                    return new BraveRewardsBannerInfo(in);
                }

                @Override
                public BraveRewardsBannerInfo[] newArray(int size) {
                    return new BraveRewardsBannerInfo[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPublisherKey);
        dest.writeString(mTitle);
        dest.writeString(mName);
        dest.writeString(mDescription);
        dest.writeString(mBackground);
        dest.writeString(mLogo);
        dest.writeString(mProvider);
        dest.writeInt(mStatus);
    }

    public String getPublisherKey() {
        return mPublisherKey;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getBackground() {
        return mBackground;
    }

    public String getLogo() {
        return mLogo;
    }

    public String getProvider() {
        return mProvider;
    }

    public HashMap<String, String> getLinks() {
        return mLinks;
    }

    public int getStatus() {
        return mStatus;
    }

    public BraveRewardsBannerInfo(String json_external_wallet) throws JSONException {
        fromJson(json_external_wallet);
    }

    private void fromJson(String json_external_wallet) throws JSONException {
        JSONObject jsonObj = new JSONObject(json_external_wallet);
        mPublisherKey = jsonObj.getString(PUBLISHER_KEY);
        mTitle = jsonObj.getString(TITLE);
        mName = jsonObj.getString(NAME);
        mDescription = jsonObj.getString(DESCRIPTION);
        mBackground = jsonObj.getString(BACKGROUND);
        mLogo = jsonObj.getString(LOGO);

        mProvider = jsonObj.getString(PROVIDER);
        JSONObject linksJsonObject = jsonObj.getJSONObject(LINKS);
        if (linksJsonObject != null) {
            JSONArray array = linksJsonObject.names();
            if (array != null) {
                mLinks = new HashMap<>();
                for (int index = 0; index < array.length(); index++) {
                    mLinks.put(array.getString(index),
                            linksJsonObject.getString(array.getString(index)));
                }
            }
        }

        mStatus = jsonObj.getInt(STATUS);
    }

    @VisibleForTesting
    @Override
    public String toString() {
        return "BraveRewardsBannerInfo{"
                + "mPublisherKey='" + mPublisherKey + '\'' + ", mTitle='" + mTitle + '\''
                + ", mName='" + mName + '\'' + ", mDescription='" + mDescription + '\''
                + ", mBackground='" + mBackground + '\'' + ", mLogo='" + mLogo + '\''
                + ", mProvider='" + mProvider + '\'' + ", mLinks=" + mLinks + ", mStatus=" + mStatus
                + '}';
    }
}
