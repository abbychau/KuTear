package com.kutear.app.fragment;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.machinarius.preferencefragment.PreferenceFragment;
import com.kutear.app.R;

/**
 * Created by kutear.guo on 2015/8/18.
 * 设置页面
 */
public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    private Toolbar mToolBar;
    private ListPreference mListPreference;
    private static final String THEME_PREFERENCE = "theme_preference";

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

    private void initPreference(){
        mListPreference = (ListPreference) getPreferenceScreen().findPreference(THEME_PREFERENCE);
        mListPreference.setOnPreferenceChangeListener(this);
    }

    private void initToolBar(){
        mToolBar.setTitle(R.string.setting);
        mToolBar.setNavigationIcon(getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));
    }

    private void initSummary(){

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        mListPreference.setSummary(newValue.toString());
        if(preference == mListPreference){
        }

        return false;
    }
}
