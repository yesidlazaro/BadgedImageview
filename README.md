# BadgedImageview
BadgedImageview allow you show a badge into a Imageview. I just extracted the widgets from Plaid app developed by Nick Butcher(https://github.com/nickbutcher) and add some adjustments, all credits for Nick.
# minSdk 15
# API java and xml
- set badge color
- set badge padding
- set badge text
- set badge gravity
- set foreground
- show and hide the badge programmatically

**Gradle via jitpack**

```groovy
 repositories {
        // ...
        maven { url "https://jitpack.io" }
 }
```
```groovy
 dependencies {
	        compile 'com.github.yesidlazaro:BadgedImageview:1.0.2'
	}
```

# Demo
![Alt text](https://github.com/yesidlazaro/BadgedImageview/blob/master/art/demo.png)
# example
```java
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    BadgedFourThreeImageView badgedImageViewDog;
    BadgedSquareImageView badgedImageViewPersonVideo;
    BadgedSquareImageView badgedImageViewPersonGif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        badgedImageViewDog = (BadgedFourThreeImageView) findViewById(R.id.badge_dog);
        badgedImageViewPersonVideo = (BadgedSquareImageView) findViewById(R.id.badge_person_video);
        badgedImageViewPersonGif = (BadgedSquareImageView) findViewById(R.id.badge_person_gif);
        badgedImageViewDog.showBadge(true);
        badgedImageViewPersonVideo.showBadge(true);
        badgedImageViewPersonGif.showBadge(true);
        badgedImageViewPersonGif.setBadgeText("JPG");
        badgedImageViewPersonGif.setBadgeColor(getResources().getColor(R.color.gray_50));
        badgedImageViewPersonGif.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (badgedImageViewPersonGif.isBadgeVisible()) {
            badgedImageViewPersonGif.showBadge(false);
        } else {
            badgedImageViewPersonGif.showBadge(true);
        }
    }

}
```
```xml
 <com.creativityapps.badgedimageviews.BadgedFourThreeImageView
            android:id="@+id/badge_dog"
            android:layout_width="300dp"
            android:layout_height="200dp"
            android:src="@drawable/dog"
            app:badgeGravity="end|bottom"
            app:badgePadding="@dimen/padding_normal"
            app:badgeText="@string/lab_gif" />

        <com.creativityapps.badgedimageviews.BadgedSquareImageView
            android:id="@+id/badge_person_video"
            android:layout_width="200dp"
            android:layout_height="200dp"
            android:layout_marginTop="@dimen/padding_normal"
            android:src="@drawable/me"
            app:badgeColor="@color/colorAccent"
            app:badgeGravity="top|right"
            app:badgePadding="@dimen/padding_normal"
            app:badgeText="@string/lab_video" />

        <com.creativityapps.badgedimageviews.BadgedSquareImageView
            android:id="@+id/badge_person_gif"
            android:layout_width="200dp"
            android:layout_height="200dp"
            android:layout_marginTop="@dimen/padding_normal"
            android:src="@drawable/me"
            android:foreground="?selectableItemBackground"
            app:badgeGravity="top|left"
            app:badgePadding="@dimen/padding_normal"
            app:badgeText="@string/lab_gif" />
```

#license 
Copyright 2015 Yesid Lazaro

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements. See the NOTICE file distributed with this work for
additional information regarding copyright ownership. The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License. You may obtain a copy of
the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
License for the specific language governing permissions and limitations under
the License.

 
