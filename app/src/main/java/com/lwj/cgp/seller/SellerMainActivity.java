package com.lwj.cgp.seller;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.lwj.cgp.R;
import com.lwj.cgp.base.BaseActivity;
import com.lwj.cgp.common.ChatFragment;
import com.lwj.cgp.buyer.BuyerGroupFragment;
import com.lwj.cgp.buyer.BuyerHomeFragment;
import com.lwj.cgp.common.PersonFragment;

import java.util.ArrayList;
import java.util.List;

public class SellerMainActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout mTabHome;
    private LinearLayout mTabDeal;
    private LinearLayout mTabChat;
    private LinearLayout mTabPerson;

    private ImageButton mIBHome;
    private ImageButton mIBDeal;
    private ImageButton mIBChat;
    private ImageButton mIBPerson;

    private TextView mTvHome;
    private TextView mTvDeal;
    private TextView mTvChat;
    private TextView mTvPerson;

    private ViewPager mViewPager;
    private TabFragmentViewPagerAdapter mAdapter;
    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_main);
        initView();
        initClickListener();
    }

    private void initView() {

        mViewPager = (ViewPager) findViewById(R.id.tab_main_viewpager);

        mTabHome = (LinearLayout) findViewById(R.id.tab_home);
        mTabDeal = (LinearLayout) findViewById(R.id.tab_group);
        mTabPerson = (LinearLayout) findViewById(R.id.tab_person);
        mTabChat = (LinearLayout) findViewById(R.id.tab_chat);

        mIBHome = (ImageButton) findViewById(R.id.ib_home);
        mIBDeal = (ImageButton) findViewById(R.id.ib_group);
        mIBChat = (ImageButton) findViewById(R.id.ib_chat);
        mIBPerson = (ImageButton) findViewById(R.id.ib_person);


        mTvHome = (TextView) findViewById(R.id.tv_home);
        mTvDeal = (TextView) findViewById(R.id.tv_group);
        mTvChat = (TextView) findViewById(R.id.tv_chat);
        mTvPerson = (TextView) findViewById(R.id.tv_person);

        mFragments = new ArrayList<Fragment>();
        mFragments.add(new SellerHomeFragment());
        mFragments.add(new BuyerGroupFragment());
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
                        mIBDeal.setBackgroundResource(R.drawable.assessment_red_900_24dp);
                        mTvDeal.setTextColor(getResources().getColor(R.color.red_B71C1C));
                        break;
                    case 2:
                        mIBChat.setBackgroundResource(R.drawable.chat_red_900_24dp);
                        mTvChat.setTextColor(getResources().getColor(R.color.red_B71C1C));
                        break;
                    case 3:
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
        mTabDeal.setOnClickListener(this);
        mTabChat.setOnClickListener(this);
        mTabPerson.setOnClickListener(this);
    }

    private void initTabImage() {
        mIBHome.setBackgroundResource(R.drawable.home_black_24dp);
        mIBDeal.setBackgroundResource(R.drawable.assessment_black_24dp);
        mIBChat.setBackgroundResource(R.drawable.chat_black_24dp);
        mIBPerson.setBackgroundResource(R.drawable.person_black_24dp);

        mTvHome.setTextColor(getResources().getColor(R.color.black));
        mTvDeal.setTextColor(getResources().getColor(R.color.black));
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
            case R.id.tab_chat:
                setSelect(2);
                break;
            case R.id.tab_person:
                setSelect(3);
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
                mIBDeal.setBackgroundResource(R.drawable.assessment_red_900_24dp);
                mTvDeal.setTextColor(getResources().getColor(R.color.red_B71C1C));
                break;
            case 2:
                mIBChat.setBackgroundResource(R.drawable.chat_red_900_24dp);
                mTvChat.setTextColor(getResources().getColor(R.color.red_B71C1C));
                break;
            case 3:
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