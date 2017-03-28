package com.magicwindow.deeplink.base;

import android.support.v4.app.Fragment;

import com.zxinsight.TrackAgent;

public abstract class BaseFragment extends Fragment {

//    protected BackHandledInterface mBackHandledInterface;

    /**
     */
//    protected abstract boolean onBackPressed();


    @Override
    public void onStart() {
        super.onStart();
//        mBackHandledInterface.setSelectedFragment(this);
    }

    //added by aaron.liu add fragment and addToBackStack for back function 20140723
    /*public void switchFragment(BaseFragment from, BaseFragment to) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.stay, R.anim.nothing, R.anim.slide_left_out);
        if (!to.isAdded()) {
            transaction.hide(from).add(R.id.fragment_layout, to).addToBackStack(null).commit();
        } else {
            transaction.hide(from).show(to).addToBackStack(null).commit();
        }
    }*/


    @Override
    public void onPause() {
        TrackAgent.currentEvent().onPause(this.getActivity(), null);
        super.onPause();
    }

    @Override
    public void onResume() {
        TrackAgent.currentEvent().onResume(this.getActivity(), null);
        super.onResume();
    }

}
