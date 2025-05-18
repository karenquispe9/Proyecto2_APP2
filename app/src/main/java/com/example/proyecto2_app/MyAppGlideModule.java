package com.example.proyecto2_app;

import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

@GlideModule
public final class MyAppGlideModule extends AppGlideModule {
    // No necesitamos implementar registerComponents
    // Glide usará su cliente HTTP por defecto

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}