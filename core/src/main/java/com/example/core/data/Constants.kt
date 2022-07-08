package com.example.core.data

object Constants {

    /**
     * NETWORK MODULO
     */
    const val TIMEOUT_SECONDS = 15L


    /**
     * BD ROOM
     */
    const val FAVORITES_TABLE_NAME = "favorites"
    const val FAVORITES_COLUMN_TABLE_ID = "id"
    const val FAVORITES_COLUMN_TABLE_NAME = "name"
    const val FAVORITES_COLUMN_TABLE_IMAGE_URL = "image_url"
    const val APP_DATABASE_NAME = "app_database"

    const val CHARACTER_TABLE_NAME = "characters"
    const val CHARACTER_COLUMN_INFO_ID = "id"
    const val CHARACTER_COLUMN_INFO_NAME = "name"
    const val CHARACTER_COLUMN_INFO_IMAGE_URL = "image_url"

    const val REMOTE_KEYS_TABLE_NAME = "remote_keys"
    const val REMOTE_KEYS_COLUMN_INFO_OFFSET = "next_offset"

    /**
     * DATA STORE
     */
    const val DAY_NIGHT = "day_night"

    /**
     * Characters Fragment
     */
    const val MINIMUM_FETCH_FIREBASE = 60L
    const val MENU_SORTING_FIREBASE = "show_toolbar_menu_sorting"
    const val MENU_DARK_LIGHT_FIREBASE = "show_toolbar_menu_dark_light"
}