package com.techmighty.baseplayer.activities;

import android.support.annotation.Nullable;

import com.afollestad.appthemeengine.ATEActivity;
import com.techmighty.baseplayer.utils.Helpers;

/**
 * Created by ankit on 31/12/15.
 */
public class BaseThemedActivity extends ATEActivity {

    @Nullable
    @Override
    public String getATEKey() {
        return Helpers.getATEKey(this);
    }
}
