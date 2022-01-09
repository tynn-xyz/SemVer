//  Copyright 2022 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.semver.example;

import static xyz.tynn.semver.example.BuildConfig.VERSION_NAME;
import static xyz.tynn.semver.example.MutableSemVerKt.setSemVerString;
import static xyz.tynn.semver.example.MutableSemVerKt.toMutableSemVer;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import kotlin.Unit;

public class MainActivity extends AppCompatActivity {

    public static final String STATE_VERSION = "version";
    private final MutableSemVer version = toMutableSemVer(VERSION_NAME);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            String versionString = savedInstanceState.getString(STATE_VERSION);
            if (versionString != null) {
                setSemVerString(version, versionString);
            }
        }
        final TextView buttonMajor = findViewById(R.id.button_major);
        buttonMajor.setOnClickListener(v -> version.incrementMajorRelease());
        final TextView buttonMinor = findViewById(R.id.button_minor);
        buttonMinor.setOnClickListener(v -> version.incrementMinorRelease());
        final TextView buttonPatch = findViewById(R.id.button_patch);
        buttonPatch.setOnClickListener(v -> version.incrementPatchRelease());
        findViewById(R.id.button_alpha).setOnClickListener(v -> version.incrementAlphaPreRelease());
        findViewById(R.id.button_beta).setOnClickListener(v -> version.incrementBetaPreRelease());
        final TextView textPreRelease = findViewById(R.id.text_prerelease);
        version.setUpdateListener(version -> {
            buttonMajor.setText(version.getMajor());
            buttonMinor.setText(version.getMinor());
            buttonPatch.setText(version.getPatch());
            textPreRelease.setText(version.getPreRelease());
            return Unit.INSTANCE;
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_VERSION, version.toString());
    }

    @Override
    protected void onDestroy() {
        version.setUpdateListener(null);
        super.onDestroy();
    }
}
