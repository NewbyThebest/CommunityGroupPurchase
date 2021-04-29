package com.lwj.cgp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout mTabHome;
    private LinearLayout mTabGroup;
    private LinearLayout mTabCar;
    private LinearLayout mTabChat;
    private LinearLayout mTabPerson;

    private ImageButton mIBHome;
    private ImageButton mIBGroup;
    private ImageButton mIBCar;
    private ImageButton mIBChat;
    private ImageButton mIBPerson;

    private TextView mTvHome;
    private TextView mTvGroup;
    private TextView mTvCar;
    private TextView mTvChat;
    private TextView mTvPerson;

    private ViewPager mViewPager;
    private TabFragmentViewPagerAdapter mAdapter;
    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initClickListener();
    }

    private void initView() {

        mViewPager = (ViewPager) findViewById(R.id.tab_main_viewpager);

        mTabHome = (LinearLayout) findViewById(R.id.tab_home);
        mTabGroup = (LinearLayout) findViewById(R.id.tab_group);
        mTabCar = (LinearLayout) findViewById(R.id.tab_car);
        mTabChat = (LinearLayout) findViewById(R.id.tab_chat);
        mTabPerson = (LinearLayout) findViewById(R.id.tab_person);

        mIBHome = (ImageButton) findViewById(R.id.ib_home);
        mIBGroup = (ImageButton) findViewById(R.id.ib_group);
        mIBCar = (ImageButton) findViewById(R.id.ib_car);
        mIBChat = (ImageButton) findViewById(R.id.ib_chat);
        mIBPerson = (ImageButton) findViewById(R.id.ib_person);

        mTvHome = (TextView) findViewById(R.id.tv_home);
        mTvGroup = (TextView) findViewById(R.id.tv_group);
        mTvCar = (TextView) findViewById(R.id.tv_car);
        mTvChat = (TextView) findViewById(R.id.tv_chat);
        mTvPerson = (TextView) findViewById(R.id.tv_person);

        mFragments = new ArrayList<Fragment>();
        mFragments.add(new HomeFragment());
        mFragments.add(new GroupFragment());
        mFragments.add(new CarFragment());
        mFragments.add(new ChatFragment());
        mFragments.add(new PersonFragment());

        mAdapter = new TabFragmentViewPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            //滑动时 改变图标状态
            @Override
            public void onPageSelected(int position) {
                int currentItem = mViewPager.getCurrentItem();
                initTabImage();
                switch (currentItem) {
                    case 0:
                        mIBHome.setBackgroundResource(R.drawable.home_red_900_24dp);
                        mTvHome.setTextColor(getResources().getColor(R.color.red_B71C1C));
                        break;
                    case 1:
                        mIBGroup.setBackgroundResource(R.drawable.group_red_900_24dp);
                        mTvGroup.setTextColor(getResources().getColor(R.color.red_B71C1C));
                        break;
                    case 2:
                        mIBCar.setBackgroundResource(R.drawable.add_shopping_cart_red_900_24dp);
                        mTvCar.setTextColor(getResources().getColor(R.color.red_B71C1C));
                        break;
                    case 3:
                        mIBChat.setBackgroundResource(R.drawable.chat_red_900_24dp);
                        mTvChat.setTextColor(getResources().getColor(R.color.red_B71C1C));
                        break;
                    case 4:
                        mIBPerson.setBackgroundResource(R.drawable.person_red_900_24dp);
                        mTvPerson.setTextColor(getResources().getColor(R.color.red_B71C1C));
                        break;
                    default:
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initClickListener() {
        mTabHome.setOnClickListener(this);
        mTabGroup.setOnClickListener(this);
        mTabCar.setOnClickListener(this);
        mTabChat.setOnClickListener(this);
        mTabPerson.setOnClickListener(this);
    }

    private void initTabImage() {
        mIBHome.setBackgroundResource(R.drawable.home_black_24dp);
        mIBGroup.setBackgroundResource(R.drawable.group_black_24dp);
        mIBCar.setBackgroundResource(R.drawable.add_shopping_cart_black_24dp);
        mIBChat.setBackgroundResource(R.drawable.chat_black_24dp);
        mIBPerson.setBackgroundResource(R.drawable.person_black_24dp);

        mTvHome.setTextColor(getResources().getColor(R.color.black));
        mTvGroup.setTextColor(getResources().getColor(R.color.black));
        mTvCar.setTextColor(getResources().getColor(R.color.black));
        mTvChat.setTextColor(getResources().getColor(R.color.black));
        mTvPerson.setTextColor(getResources().getColor(R.color.black));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_home:
                setSelect(0);
                break;
            case R.id.tab_group:
                setSelect(1);
                break;
            case R.id.tab_car:
                setSelect(2);
                break;
            case R.id.tab_chat:
                setSelect(3);
                break;
            case R.id.tab_person:
                setSelect(4);
                break;
            default:
        }
    }

    private void setSelect(int i) {

        initTabImage();
        switch (i) {
            case 0:
                mIBHome.setBackgroundResource(R.drawable.home_red_900_24dp);
                mTvHome.setTextColor(getResources().getColor(R.color.red_B71C1C));
                break;
            case 1:
                mIBGroup.setBackgroundResource(R.drawable.group_red_900_24dp);
                mTvGroup.setTextColor(getResources().getColor(R.color.red_B71C1C));
                break;
            case 2:
                mIBCar.setBackgroundResource(R.drawable.add_shopping_cart_red_900_24dp);
                mTvCar.setTextColor(getResources().getColor(R.color.red_B71C1C));
                break;
            case 3:
                mIBChat.setBackgroundResource(R.drawable.chat_red_900_24dp);
                mTvChat.setTextColor(getResources().getColor(R.color.red_B71C1C));
                break;
            case 4:
                mIBPerson.setBackgroundResource(R.drawable.person_red_900_24dp);
                mTvPerson.setTextColor(getResources().getColor(R.color.red_B71C1C));
                break;
            default:
                break;
        }
        mViewPager.setCurrentItem(i);
    }

    public class TabFragmentViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        TabFragmentViewPagerAdapter(FragmentManager fm, List<Fragment> fragments){
            super(fm);
            this.fragments = fragments;
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}