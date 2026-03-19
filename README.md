# FloatingBottomNavBar

A beautiful, customizable, and floating bottom navigation bar for Android, inspired by modern designs.

## Features

- **Floating Design**: Elevated bar with rounded corners that floats above your content.
- **Customizable Appearance**: Easily change background colors, corner radius, and icon colors.
- **Active Item Highlighting**: Smoothly highlights the selected icon with a background shape and animation.
- **Simple Integration**: Clean API to set items and handle selection events.

## Preview

![Floating Bottom Nav Bar Preview](assets/preview.gif)

## Installation

Add the JitPack repository to your root `build.gradle` (or `settings.gradle` for newer Gradle versions):

```gradle
// settings.gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}

// or build.gradle (old method)
allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency to your app's `build.gradle` file:

```gradle
dependencies {
    implementation 'com.github.Willow7737:floating-bottom-navbar-android:1.0.0'
}
```

## Usage

### In XML Layout

Integrate the `FloatingBottomNavBar` into your layout file, typically `activity_main.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#E7F3FF"
    tools:context=".MainActivity">

    <!-- Your main content here -->

    <com.github.floatingnavbar.FloatingBottomNavBar
        android:id="@+id/bottomNavBar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="24dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:navBarBackgroundColor="#1C1B1F"
        app:navBarCornerRadius="40dp"
        app:selectedIconBackground="#3894F0"
        app:selectedIconColor="#FFFFFF"
        app:unselectedIconColor="#E6E1E5" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

### In Kotlin Code

Set up the navigation items and handle selection events in your `Activity` or `Fragment`:

```kotlin
package com.github.floatingnavbar.sample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.floatingnavbar.FloatingBottomNavBar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavBar = findViewById<FloatingBottomNavBar>(R.id.bottomNavBar)

        val items = listOf(
            FloatingBottomNavBar.NavBarItem(android.R.drawable.ic_menu_today, "Home"),
            FloatingBottomNavBar.NavBarItem(android.R.drawable.ic_menu_view, "Insights"),
            FloatingBottomNavBar.NavBarItem(android.R.drawable.ic_menu_camera, "Profile"),
            FloatingBottomNavBar.NavBarItem(android.R.drawable.ic_menu_compass, "Discover"),
            FloatingBottomNavBar.NavBarItem(android.R.drawable.ic_menu_search, "Search")
        )

        bottomNavBar.setItems(items)

        bottomNavBar.onItemSelectedListener = { index ->
            Toast.makeText(this, "Selected: ${items[index].title}", Toast.LENGTH_SHORT).show()
        }
    }
}
```

## Custom Attributes

The `FloatingBottomNavBar` offers several customizable attributes:

| Attribute                 | Format    | Description                                      |
|---------------------------|-----------|--------------------------------------------------|
| `navBarBackgroundColor`   | `color`   | Background color of the navigation bar.          |
| `navBarCornerRadius`      | `dimension` | Radius for the rounded corners of the bar.       |
| `navBarElevation`         | `dimension` | Elevation of the navigation bar, creating a floating effect. |
| `selectedIconColor`       | `color`   | Color of the icon when it is selected.           |
| `unselectedIconColor`     | `color`   | Color of the icon when it is not selected.       |
| `selectedIconBackground`  | `color`   | Background color for the selected icon.          |

## License

This library is released under the MIT License. See the [LICENSE](LICENSE) file for more details.
