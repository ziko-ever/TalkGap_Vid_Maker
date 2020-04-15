package com.xheghun.vidit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.talkgap.R;
import com.xheghun.vidit.adapter.ClipEditRecyclerAdapter;
import com.xheghun.vidit.adapter.TabAdapter;
import com.xheghun.vidit.adapter.ThemeAdapter;
import com.xheghun.vidit.classes.Animate;
import com.xheghun.vidit.fragments.CamEffectsFragment;
import com.xheghun.vidit.fragments.CameraFilterFragment;
import com.xheghun.vidit.models.ClipEditItemModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoEditActivity extends AppCompatActivity {

    @BindView(R.id.image_thumb)
    ImageView image;

    @BindView(R.id.theme_layout)
    ViewGroup themeLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.theme_rc)
    RecyclerView theme_rc;

    @BindView(R.id.sound_layout)
    ViewGroup soundLayout;

    @BindView(R.id.stickers_rc)
    RecyclerView stickers_rc;

    @BindView(R.id.canvas_rc)
    RecyclerView canvas_rc;

    @BindView(R.id.cut_tools)
    BottomNavigationView bottom_nav;

    @BindView(R.id.edit_clip_tools)
    BottomNavigationView editClipToolbar;

    @BindView(R.id.add_text_layout)
    ViewGroup addTextLayout;

    @BindView(R.id.canvas_layout)
    ViewGroup canvasLayout;

    @BindView(R.id.stickers_layout)
    ViewGroup stickersLayout;

    @BindView(R.id.add_sound_layout)
    ViewGroup addSoundLayout;


    private XheghunViewPager filterViewPager;
    private TabLayout filterTab;
    private XheghunViewPager effectsViewPager;
    private TabLayout effectsTab;
    private ViewGroup filterLayout;
    private ViewGroup effectsLayout;
    private ViewGroup clipEditLayout;

    private ArrayList<ClipEditItemModel> canvasList;
    private ArrayList<ClipEditItemModel> stickerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_edit);
        ButterKnife.bind(this);


        filterTab = findViewById(R.id.filter_tab);
        filterLayout = findViewById(R.id.filter_layout);
        filterViewPager = findViewById(R.id.filter_viewpager);

        effectsTab = findViewById(R.id.effects_tab);
        effectsLayout = findViewById(R.id.effects_layout);
        effectsViewPager = findViewById(R.id.effects_viewpager);

        clipEditLayout = findViewById(R.id.clip_edit_layout);

        stickerList = new ArrayList<>();
        canvasList = new ArrayList<>();





        initEffects();
        initFilters();


        String[] canvasItem = {"none","9:10","1:1","3:4","4:3","16:9","4:6"};

        for (String s : canvasItem) {
            ClipEditItemModel item = new ClipEditItemModel();
            item.setTitle(s);
            item.setDrawable(getResources().getDrawable(R.drawable.ic_aspect_ratio));

            canvasList.add(item);
        }


        for (int x = 0; x < 16; x++) {
            ClipEditItemModel item = new ClipEditItemModel();
            item.setTitle("");
            item.setDrawable(getResources().getDrawable(R.drawable.ic_insert_emoticon));

            stickerList.add(item);
        }


        LinearLayoutManager stickerLayoutManger = new LinearLayoutManager(this);
        stickerLayoutManger.setOrientation(RecyclerView.HORIZONTAL);

        stickers_rc.setLayoutManager(stickerLayoutManger);
        stickers_rc.setAdapter(new ClipEditRecyclerAdapter(this,stickerList));

        LinearLayoutManager canvasLayoutManger = new LinearLayoutManager(this);
        canvasLayoutManger.setOrientation(RecyclerView.HORIZONTAL);

        canvas_rc.setLayoutManager(canvasLayoutManger);
        canvas_rc.setAdapter(new ClipEditRecyclerAdapter(this,canvasList));

        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
            finish();
        });

        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.done:
                    startActivity(new Intent(this, SelfieVideoEditActivity.class));
                    break;
                case R.id.undo:
                    Toast.makeText(this, "undo", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.redo:
                    Toast.makeText(this, "redo", Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        });


        bottom_nav.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.add_music:
                    if (soundLayout.getVisibility() == View.GONE) {
                        Animate.slideInUp(soundLayout, this);
                    } else {
                        Animate.slideOutDown(soundLayout, this);
                    }
                    if (filterLayout.getVisibility() == View.VISIBLE) {
                        Animate.slideOutDown(filterLayout,this);
                    } else if (effectsLayout.getVisibility() == View.VISIBLE) {
                        Animate.slideOutDown(effectsLayout,this);
                    } else if (themeLayout.getVisibility() == View.VISIBLE) {
                        Animate.slideOutDown(themeLayout,this);
                    } else if (clipEditLayout.getVisibility() == View.VISIBLE) {
                        Animate.slideOutDown(clipEditLayout,this);
                    }
                    break;
                case R.id.clip_edit:
                    if (clipEditLayout.getVisibility() == View.GONE) {
                        Animate.slideInUp(clipEditLayout, this);
                    } else {
                        Animate.slideOutDown(clipEditLayout, this);
                    }
                    if (filterLayout.getVisibility() == View.VISIBLE) {
                        Animate.slideOutDown(filterLayout,this);
                    } else if (effectsLayout.getVisibility() == View.VISIBLE) {
                        Animate.slideOutDown(effectsLayout,this);
                    } else if (themeLayout.getVisibility() == View.VISIBLE) {
                        Animate.slideOutDown(themeLayout,this);
                    } else if (soundLayout.getVisibility() == View.VISIBLE) {
                        Animate.slideOutDown(soundLayout,this);
                    }
                    break;
                case R.id.theme:
                    if (themeLayout.getVisibility() == View.GONE) {
                        Animate.slideInUp(themeLayout, this);
                    } else {
                        Animate.slideOutDown(themeLayout, this);
                    }
                    if (filterLayout.getVisibility() == View.VISIBLE) {
                        Animate.slideOutDown(filterLayout,this);
                    } else if (effectsLayout.getVisibility() == View.VISIBLE) {
                        Animate.slideOutDown(effectsLayout,this);
                    } else if (soundLayout.getVisibility() == View.VISIBLE) {
                        Animate.slideOutDown(soundLayout,this);
                    } else if (clipEditLayout.getVisibility() == View.VISIBLE) {
                        Animate.slideOutDown(clipEditLayout,this);
                    }
                    break;
                case R.id.filters:
                    if (filterLayout.getVisibility() == View.GONE) {
                        Animate.slideInUp(filterLayout, this);
                    } else {
                        Animate.slideOutDown(filterLayout, this);
                    }

                    if (effectsLayout.getVisibility() == View.VISIBLE) {
                        Animate.slideOutDown(effectsLayout,this);
                    } else if (themeLayout.getVisibility() == View.VISIBLE) {
                        Animate.slideOutDown(themeLayout,this);
                    } else if (soundLayout.getVisibility() == View.VISIBLE) {
                        Animate.slideOutDown(soundLayout,this);
                    } else if (clipEditLayout.getVisibility() == View.VISIBLE) {
                        Animate.slideOutDown(clipEditLayout,this);
                    }
                    break;
                case R.id.effects:
                    if (effectsLayout.getVisibility() == View.GONE) {
                        Animate.slideInUp(effectsLayout, this);
                    } else {
                        Animate.slideOutDown(effectsLayout, this);
                    }
                    if (filterLayout.getVisibility() == View.VISIBLE) {
                        Animate.slideOutDown(filterLayout,this);
                    } else if (themeLayout.getVisibility() == View.VISIBLE) {
                        Animate.slideOutDown(themeLayout,this);
                    } else if (soundLayout.getVisibility() == View.VISIBLE) {
                        Animate.slideOutDown(soundLayout,this);
                    } else if (clipEditLayout.getVisibility() == View.VISIBLE) {
                        Animate.slideOutDown(clipEditLayout,this);
                    }
                    break;
            }
            return true;
        });

        editClipToolbar.setOnNavigationItemSelectedListener(
                menuItem -> {
                    switch (menuItem.getItemId()) {
                        case R.id.add_text:
                            if (addTextLayout.getVisibility() == View.GONE) {
                                Animate.fadeInAnimation(addTextLayout, this);
                            }

                            if (addSoundLayout.getVisibility() == View.VISIBLE) {
                                Animate.fadeOutAnimation(addSoundLayout,this);
                            } else if (canvasLayout.getVisibility() == View.VISIBLE) {
                                Animate.fadeOutAnimation(canvasLayout,this);
                            } else if (stickersLayout.getVisibility() == View.VISIBLE) {
                                Animate.fadeOutAnimation(stickersLayout,this);
                            }
                            break;
                        case R.id.canvas:
                            if (canvasLayout.getVisibility() == View.GONE) {
                                Animate.fadeInAnimation(canvasLayout, this);
                            }

                            if (addSoundLayout.getVisibility() == View.VISIBLE) {
                                Animate.fadeOutAnimation(addSoundLayout,this);
                            } else if (addTextLayout.getVisibility() == View.VISIBLE) {
                                Animate.fadeOutAnimation(addTextLayout,this);
                            } else if (stickersLayout.getVisibility() == View.VISIBLE) {
                                Animate.fadeOutAnimation(stickersLayout,this);
                            }
                            break;
                        case R.id.stickers:
                            if (stickersLayout.getVisibility() == View.GONE) {
                                Animate.fadeInAnimation(stickersLayout, this);
                            }

                            if (addSoundLayout.getVisibility() == View.VISIBLE) {
                                Animate.fadeOutAnimation(addSoundLayout,this);
                            } else if (canvasLayout.getVisibility() == View.VISIBLE) {
                                Animate.fadeOutAnimation(canvasLayout,this);
                            } else if (addTextLayout.getVisibility() == View.VISIBLE) {
                                Animate.fadeOutAnimation(addTextLayout,this);
                            }
                            break;
                        case R.id.add_sound:
                            if (addSoundLayout.getVisibility() == View.GONE) {
                                Animate.fadeInAnimation(addSoundLayout, this);
                            }

                            if (addTextLayout.getVisibility() == View.VISIBLE) {
                                Animate.fadeOutAnimation(addTextLayout,this);
                            } else if (canvasLayout.getVisibility() == View.VISIBLE) {
                                Animate.fadeOutAnimation(canvasLayout,this);
                            } else if (stickersLayout.getVisibility() == View.VISIBLE) {
                                Animate.fadeOutAnimation(stickersLayout,this);
                            }
                            break;
                    }
                    return true;
                }
        );

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.HORIZONTAL);
        theme_rc.setLayoutManager(manager);
        theme_rc.setAdapter(new ThemeAdapter(this));

    }

    private void initFilters() {
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), 0);
        adapter.addFragment(new CameraFilterFragment("TG-"), "Smart");
        adapter.addFragment(new CameraFilterFragment("E-"), "Style-Leo");
        adapter.addFragment(new CameraFilterFragment("K-"), " Creative");
        adapter.notifyDataSetChanged();
        filterViewPager.setAdapter(adapter);

        filterTab.setupWithViewPager(filterViewPager);
    }

    private void initEffects() {
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), 1);
        adapter.addFragment(new CamEffectsFragment(), "Free");
        adapter.addFragment(new CamEffectsFragment(), "My Effects");
        adapter.addFragment(new CamEffectsFragment(), "Favourite");
        adapter.notifyDataSetChanged();
        effectsViewPager.setAdapter(adapter);

        effectsTab.setupWithViewPager(effectsViewPager);
    }

    public void gotoMusicSelect(View view) {
        startActivity(new Intent(this,AddMusicActivity.class));
    }
}
