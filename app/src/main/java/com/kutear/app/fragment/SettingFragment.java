package com.kutear.app.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.support.v4.content.IntentCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.machinarius.preferencefragment.PreferenceFragment;
import com.kutear.app.R;
import com.kutear.app.activity.MainActivity;
import com.kutear.app.utils.Constant;

/**
 * Created by kutear.guo on 2015/8/18.
 * 设置页面
 */
public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    private Toolbar mToolBar;
    private ListPreference mThemePreference;


    public static SettingFragment newInstance() {
        Bundle args = new Bundle();
        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_setting);
        mToolBar = (Toolbar) LayoutInflater.from(getActivity()).inflate(R.layout.toolbar, null, false);
        ViewGroup root = ((ViewGroup) getActivity().findViewById(R.id.action_bar_root));
        root.addView(mToolBar, 0);
        initToolBar();
        initPreference();
        initSummary();
    }

    private void initPreference() {
        mThemePreference = (ListPreference) getPreferenceScreen().findPreference(Constant.THEME_PREFERENCE);
        mThemePreference.setOnPreferenceChangeListener(this);
    }

    private void initToolBar() {
        mToolBar.setTitle(R.string.setting);
        mToolBar.setNavigationIcon(getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getActivity().finish();
                Intent mainIntent = new Intent();
                mainIntent.setClass(getContext(), MainActivity.class);
                startActivity(mainIntent);
                getActivity().finish();
            }
        });
    }

    private void initSummary() {
        SharedPreferences sharedPreferences = getPreferenceManager().getSharedPreferences();
        String themeSummary = sharedPreferences.getString(Constant.THEME_PREFERENCE, "0");
        setThemeSummary(themeSummary);
    }

    private void setThemeSummary(String value) {
        if (TextUtils.equals(value, "1")) {
            mThemePreference.setSummary(R.string.theme_black);
            mThemePreference.setValue("1");
        } else {
            mThemePreference.setSummary(R.string.theme_white);
            mThemePreference.setValue("0");
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mThemePreference) {
            setThemeSummary(newValue.toString());
            Intent themeIntent = getActivity().getIntent();
            themeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
            getActivity().startActivity(themeIntent);
            getActivity().finish();
        }

        return false;
    }
}
