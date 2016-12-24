package by.famcs.pasevinapolina.buyandsell;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;


import by.famcs.pasevinapolina.buyandsell.fragments.CatalogFragment;
import by.famcs.pasevinapolina.buyandsell.fragments.HomeFragment;
import by.famcs.pasevinapolina.buyandsell.fragments.ProfileFragment;
import by.famcs.pasevinapolina.buyandsell.fragments.ViewPagerAdapter;
import by.famcs.pasevinapolina.buyandsell.models.User;

public class MainActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener {

    public static final String USER = "user";

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private User user;

    private int[] tabIcons = {
            R.drawable.ic_home,
            R.drawable.ic_catalog,
            R.drawable.ic_profile
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null) {
            user = (User)savedInstanceState.get(USER);

        }

        if(user == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            user = (User) intent.getSerializableExtra(USER);
            if(user == null) {
                user = new User();
            }
        }

        final ActionBar actionBar = getSupportActionBar();

        //actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setHomeButtonEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager();

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), getResources().getString(R.string.home_fragment));
        adapter.addFragment(new CatalogFragment(), getResources().getString(R.string.catalog));
        adapter.addFragment(new ProfileFragment(), getApplication().getString(R.string.profile));
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(USER, user);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        if (searchView != null) {
            searchView.setOnQueryTextListener(this);

            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(
                    new ComponentName(this, MainActivity.class)));
            searchView.setIconifiedByDefault(false);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.action_cart:
                Intent intent = new Intent(this, CartActivity.class);
                intent.putExtra(USER, user);
                startActivity(intent);
                return true;
            case R.id.action_search:
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
