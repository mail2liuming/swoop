//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.1.
//


package com.vc.swoop;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.vc.swoop.R.id;
import com.vc.swoop.R.layout;
import org.androidannotations.api.builder.ActivityIntentBuilder;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class SearchActivity_
    extends SearchActivity
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(layout.activity_search);
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    public static SearchActivity_.IntentBuilder_ intent(Context context) {
        return new SearchActivity_.IntentBuilder_(context);
    }

    public static SearchActivity_.IntentBuilder_ intent(Fragment supportFragment) {
        return new SearchActivity_.IntentBuilder_(supportFragment);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        et_search = ((TextView) hasViews.findViewById(id.et_search));
        tvMsg = ((TextView) hasViews.findViewById(id.tvMsg));
        swipeRefreshLayout = ((SwipeRefreshLayout) hasViews.findViewById(id.swipeRefreshLayout));
        llEmpty = ((LinearLayout) hasViews.findViewById(id.llEmpty));
        listview = ((ListView) hasViews.findViewById(id.listview));
        {
            View view = hasViews.findViewById(id.iv_cancel);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        SearchActivity_.this.clearText();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.btnBaseLeft);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        SearchActivity_.this.goback();
                    }

                }
                );
            }
        }
        init();
    }

    public static class IntentBuilder_
        extends ActivityIntentBuilder<SearchActivity_.IntentBuilder_>
    {

        private Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            super(context, SearchActivity_.class);
        }

        public IntentBuilder_(Fragment fragment) {
            super(fragment.getActivity(), SearchActivity_.class);
            fragmentSupport_ = fragment;
        }

        @Override
        public void startForResult(int requestCode) {
            if (fragmentSupport_!= null) {
                fragmentSupport_.startActivityForResult(intent, requestCode);
            } else {
                super.startForResult(requestCode);
            }
        }

    }

}
