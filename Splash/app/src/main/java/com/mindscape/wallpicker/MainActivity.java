package com.mindscape.wallpicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

//import com.android.wallpicker.R;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1000;
    private int page = 1;
    private final String pixabayUrl = "https://pixabay.com/";
    private final String wallAbyssUrl = "https://wall.alphacoders.com/";
    private Call<JsonFetchPojo> call;
    private List<HitsResult> results;
    private Call<AbyssPojo> callAbyss;
    private List<WallpaperResults> wallResults;
    private MainViewAdapter mAdapter;
    private AbyssViewAdapter mAbyssAdapter;
    private int totalPages;
    private int FIRST_PAGE = 1;
    private ProgressBar progressBar;
    private int controller = 1;
    private int categorySelected = 1;
    private String categoryPix;
    private String query = null;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    public static final int RC_SIGN_IN = 1;
    private String mUsername, ANONYMOUS;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        mFirebaseAuth = FirebaseAuth.getInstance();

        toolbar.setBackground(getResources().getDrawable(R.color.colorPrimary));
        setSupportActionBar(toolbar);

        mAuthStateListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                onSignedInInitialized(user.getDisplayName());
                if (FIRST_PAGE == 1) {
                    loadPixabay(FIRST_PAGE);
                    FIRST_PAGE = 2;
                }
            } else {
                onSIgnedOutCleanup();
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setIsSmartLockEnabled(false)
                                .setAvailableProviders(Arrays.asList(
                                        new AuthUI.IdpConfig.EmailBuilder().build(),
                                        new AuthUI.IdpConfig.AnonymousBuilder().build()
                                ))
                                .setLogo(R.drawable.new_login_logo)
                                .setTheme(R.style.LoginTheme)
                                .build(),
                        RC_SIGN_IN);
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, PERMISSION_REQUEST_CODE);
        }

        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .addProfiles(new ProfileDrawerItem().withName(R.string.app_name).withIcon(getResources().getDrawable(R.drawable.logo)))
                .withTranslucentStatusBar(true)
                .withSelectionListEnabledForSingleProfile(false)
                .build();

        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Home").withIcon(R.drawable.ic_home_24dp);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName("Search").withIcon(R.drawable.ic_search_24dp);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName("Categories").withIcon(R.drawable.ic_list_24dp);
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(4).withName("Favourites").withIcon(R.drawable.ic_favorite_black_24dp);
        PrimaryDrawerItem item5 = new PrimaryDrawerItem().withIdentifier(5).withName("Settings").withIcon(FontAwesome.Icon.faw_cog);
        PrimaryDrawerItem item6 = new PrimaryDrawerItem().withIdentifier(6).withName("About").withIcon(FontAwesome.Icon.faw_info_circle);
        final Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(accountHeader)
                .withSelectedItem(-1)
                .addDrawerItems(
                        item1,
                        item2,
                        item3,
                        item4,
                        item5,
                        item6
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (position == 1) {
                            page = 1;
                            categoryPix = null;
                            loadPixabay(page);
                            controller = 1;
                        } else if (position == 2) {
                            Intent i = new Intent(MainActivity.this, SearchActivity.class);
                            startActivity(i);
                        } else if (position == 3) {
                            Intent i = new Intent(MainActivity.this, CategoryWallpaper.class);
                            startActivity(i);
                        } else if (position == 4) {
                            Intent i = new Intent(MainActivity.this, FavouritesActivity.class);
                            startActivity(i);
                            Toast.makeText(MainActivity.this, "Retrieving favourite images.", Toast.LENGTH_SHORT).show();
                        } else if (position == 5) {
                            Intent i = new Intent(MainActivity.this, SettingActivity.class);
                            startActivity(i);
                        } else if (position == 6) {
                            Intent i = new Intent(MainActivity.this, AboutActivity.class);
                            startActivity(i);
                        }
                        return false;
                    }
                })
                .build();
        result.addStickyFooterItem(new PrimaryDrawerItem().withName("Sign Out").withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                AuthUI.getInstance().signOut(MainActivity.this);
                return false;
            }
        }).withSelectable(false));

        ButterKnife.bind(this);
        GridLayoutManager manager = new GridLayoutManager(this, 2);

        recyclerView.setLayoutManager(manager);
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if ((page + 1) <= 10) {
                    Toast.makeText(MainActivity.this, "Loading more images...", Toast.LENGTH_SHORT).show();
                    if (controller == 1) {
                        loadPixabay(page + 1);
                    } else if (controller == 2) {
                        loadAbyss(page + 1);
                    } else if (controller == 3) {
                        loadSearch(page + 1);
                    }
                }
            }
        };
        recyclerView.addOnScrollListener(scrollListener);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            categorySelected = bundle.getInt("theCategory");
            categoryPix = bundle.getString("pixCategory");
            controller = bundle.getInt("theController");
            query = bundle.getString("Query");
            if (controller == 1) {
                loadPixabay(page);
            } else if (controller == 2) {
                loadAbyss(page);
            } else {
                loadSearch(page);
                controller = 3;
            }
        }
    }

    private void loadSearch(final int page) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(pixabayUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SplashApiService splashApiService = retrofit.create(SplashApiService.class);

        call = splashApiService.getSearch(getString(R.string.key), query, page, "photo", "vertical", "true");
        call.enqueue(new Callback<JsonFetchPojo>() {
            @Override
            public void onResponse(Call<JsonFetchPojo> call, Response<JsonFetchPojo> response) {

                if (page == 1) {
                    Toast.makeText(MainActivity.this, "Loading images", Toast.LENGTH_SHORT).show();
                    assert response.body() != null;
                    results = response.body().getHitsResults();
                    assert response.body() != null;
                    totalPages = response.body().getTotalHits();
                    mAdapter = new MainViewAdapter(results);
                    recyclerView.setAdapter(mAdapter);
                    progressBar.setVisibility(View.GONE);

                } else {
                    assert response.body() != null;
                    List<HitsResult> thepageResults = response.body().getHitsResults();
                    for (HitsResult movie : thepageResults) {
                        results.add(movie);
                        mAdapter.notifyItemInserted(results.size() - 1);
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonFetchPojo> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed : Check your internet connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadPixabay(final int page) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(pixabayUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SplashApiService splashApiService = retrofit.create(SplashApiService.class);

        call = splashApiService.getPhotos(getString(R.string.key), page, "photo", "vertical", categoryPix, "true");
        call.enqueue(new Callback<JsonFetchPojo>() {
            @Override
            public void onResponse(Call<JsonFetchPojo> call, Response<JsonFetchPojo> response) {

                if (page == 1) {
                    Toast.makeText(MainActivity.this, "Loading images", Toast.LENGTH_SHORT).show();
                    assert response.body() != null;
                    results = response.body().getHitsResults();
                    assert response.body() != null;
                    totalPages = response.body().getTotalHits();
                    mAdapter = new MainViewAdapter(results);
                    recyclerView.setAdapter(mAdapter);
                    progressBar.setVisibility(View.GONE);

                } else {
                    assert response.body() != null;
                    List<HitsResult> thepageResults = response.body().getHitsResults();
                    for (HitsResult movie : thepageResults) {
                        results.add(movie);
                        mAdapter.notifyItemInserted(results.size() - 1);
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonFetchPojo> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed : Check your internet connection.", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void loadAbyss(final int page) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(wallAbyssUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SplashApiService splashApiService = retrofit.create(SplashApiService.class);

        callAbyss = splashApiService.getWallAbyss(getString(R.string.abyss_key), "category", categorySelected, page);
        callAbyss.enqueue(new Callback<AbyssPojo>() {
            @Override
            public void onResponse(Call<AbyssPojo> call, Response<AbyssPojo> response) {
                if (page == 1) {
                    Toast.makeText(MainActivity.this, "Loading images", Toast.LENGTH_SHORT).show();
                    assert response.body() != null;
                    wallResults = response.body().getWallpaperResults();
                    mAbyssAdapter = new AbyssViewAdapter(wallResults);
                    recyclerView.setAdapter(mAbyssAdapter);
                    progressBar.setVisibility(View.GONE);

                } else {
                    assert response.body() != null;
                    List<WallpaperResults> thepageResults = response.body().getWallpaperResults();
                    for (WallpaperResults movie : thepageResults) {
                        wallResults.add(movie);
                        mAbyssAdapter.notifyItemInserted(wallResults.size() - 1);
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<AbyssPojo> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed : Check your internet connection.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                else Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Sign in success.", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Sign in cancelled.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    private void onSignedInInitialized(String username) {
        mUsername = username;
    }

    private void onSIgnedOutCleanup() {
        mUsername = ANONYMOUS;
    }
}
