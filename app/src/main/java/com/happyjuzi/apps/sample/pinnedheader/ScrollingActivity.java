package com.happyjuzi.apps.sample.pinnedheader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final CollapsingToolbarLayout collapsingToolbarLayout = ((CollapsingToolbarLayout) findViewById(R.id.toolbar_layout));
        final ImageView blurImage = (ImageView) findViewById(R.id.blur);
        final TextView infoView = (TextView) findViewById(R.id.custom_info);
        setSupportActionBar(toolbar);

        Bitmap originBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_welcome_4);

        Bitmap blurBitmap = FastBlur.doBlur(originBitmap, 100, false);

        Palette palette = Palette.from(originBitmap).generate();
        int statusBarScrimColor = palette.getDarkVibrantColor(Color.GRAY);
        int contentScrimColor = palette.getVibrantColor(Color.RED);

        int color = (contentScrimColor) & 0x5f000000;

        collapsingToolbarLayout.setStatusBarScrimColor(statusBarScrimColor);
        collapsingToolbarLayout.setContentScrimColor(color);

        if (blurImage != null) {
            blurImage.setImageBitmap(blurBitmap);
        }

        AppBarLayout appBarLayout = ((AppBarLayout) findViewById(R.id.app_bar));
        if (appBarLayout != null) {
            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    int height = appBarLayout.getTotalScrollRange();
                    float percent = -verticalOffset * 1.0f / height;
                    blurImage.setAlpha(percent);
                    infoView.setAlpha(1 - percent * 2);

                    if (percent == 1) {
                        toolbar.animate().alpha(1).setDuration(200).start();
                    } else if (toolbar.getAlpha() == 1) {
                        toolbar.animate().alpha(0).setDuration(200).start();
                    }

                }
            });

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
        Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
