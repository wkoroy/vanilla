/*
 * Copyright (C) 2016 Oleg Chernovskiy <adonai@xaker.ru>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package ru.smartinterestapps.android.vanilla;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * Common plugin utilities and constants reside here.
 * Note that public `action` and `extra` strings are common over all plugins, so please keep them in sync.
 */

public class PluginUtils {

    private PluginUtils() {
    }

    // these actions are for passing between main player and plugins
    public static final String ACTION_REQUEST_PLUGIN_PARAMS = "ru.smartinterestapps.android.vanilla.action.REQUEST_PLUGIN_PARAMS"; // broadcast
    public static final String ACTION_HANDLE_PLUGIN_PARAMS = "ru.smartinterestapps.android.vanilla.action.HANDLE_PLUGIN_PARAMS"; // answer
    public static final String ACTION_WAKE_PLUGIN = "ru.smartinterestapps.android.vanilla.action.WAKE_PLUGIN"; // targeted at each found
    public static final String ACTION_LAUNCH_PLUGIN = "ru.smartinterestapps.android.vanilla.action.LAUNCH_PLUGIN"; // targeted at selected by user

    // these are used by plugins to describe themselves
    public static final String EXTRA_PARAM_PLUGIN_NAME = "ru.smartinterestapps.android.vanilla.extra.PLUGIN_NAME";
    public static final String EXTRA_PARAM_PLUGIN_APP = "ru.smartinterestapps.android.vanilla.extra.PLUGIN_APP";
    public static final String EXTRA_PARAM_PLUGIN_DESC = "ru.smartinterestapps.android.vanilla.extra.PLUGIN_DESC";

    // this is passed to plugin when it is selected by user
    public static final String EXTRA_PARAM_URI = "ru.smartinterestapps.android.vanilla.extra.URI";
    public static final String EXTRA_PARAM_SONG_TITLE = "ru.smartinterestapps.android.vanilla.extra.SONG_TITLE";
    public static final String EXTRA_PARAM_SONG_ALBUM = "ru.smartinterestapps.android.vanilla.extra.SONG_ALBUM";
    public static final String EXTRA_PARAM_SONG_ARTIST = "ru.smartinterestapps.android.vanilla.extra.SONG_ARTIST";

    static final String EXTRA_PLUGIN_MAP = "ru.smartinterestapps.android.vanilla.internal.extra.PLUGIN_MAP";

    public static boolean checkPlugins(Context ctx) {
        List<ResolveInfo> resolved = ctx.getPackageManager().queryBroadcastReceivers(new Intent(ACTION_REQUEST_PLUGIN_PARAMS), 0);
        if(!resolved.isEmpty()) {
            // If plugin is just installed, Android will not deliver intents to its receiver
            // until it's started at least one time
            for (ResolveInfo ri : resolved) {
                Intent awaker = new Intent(ACTION_WAKE_PLUGIN);
                awaker.setPackage(ri.activityInfo.packageName);
                ctx.startService(awaker);
            }
            return true;
        }

        return false;
    }
}
