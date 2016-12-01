package by.famcs.pasevinapolina.buyandsell;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import by.famcs.pasevinapolina.buyandsell.fragments.CatalogFragment;
import by.famcs.pasevinapolina.buyandsell.fragments.HomeFragment;
import by.famcs.pasevinapolina.buyandsell.fragments.ProfileFragment;
import by.famcs.pasevinapolina.buyandsell.fragments.ViewPagerAdapter;

public class MainActivity extends FragmentActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private int[] tabIcons = {
            R.drawable.ic_home,
            R.drawable.ic_catalog,
            R.drawable.ic_profile
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager();
//
//        tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(viewPager);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
