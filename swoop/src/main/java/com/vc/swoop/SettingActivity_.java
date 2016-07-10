//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.1.
//


package com.vc.swoop;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.vc.swoop.R.id;
import com.vc.swoop.R.layout;
import org.androidannotations.api.builder.ActivityIntentBuilder;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class SettingActivity_
    extends SettingActivity
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(layout.activity_setting);
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

    public static SettingActivity_.IntentBuilder_ intent(Context context) {
        return new SettingActivity_.IntentBuilder_(context);
    }

    public static SettingActivity_.IntentBuilder_ intent(Fragment supportFragment) {
        return new SettingActivity_.IntentBuilder_(supportFragment);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        tvBaseTitle = ((TextView) hasViews.findViewById(id.tvBaseTitle));
        btnBaseLeft = ((Button) hasViews.findViewById(id.btnBaseLeft));
        tvBaseRight = ((TextView) hasViews.findViewById(id.tvBaseRight));
        btnBaseRight = ((Button) hasViews.findViewById(id.btnBaseRight));
        imgBaseLogo = ((ImageView) hasViews.findViewById(id.imgBaseLogo));
        ll_login = ((LinearLayout) hasViews.findViewById(id.ll_login));
        tv_mobile = ((TextView) hasViews.findViewById(id.tv_mobile));
        ll_unlogin = ((LinearLayout) hasViews.findViewById(id.ll_unlogin));
        tv_username = ((TextView) hasViews.findViewById(id.tv_username));
        if (btnBaseRight!= null) {
            btnBaseRight.setOnClickListener(new OnClickListener() {


                @Override
                public void onClick(View view) {
                    SettingActivity_.this.rightClick();
                }

            }
            );
        }
        if (tvBaseRight!= null) {
            tvBaseRight.setOnClickListener(new OnClickListener() {


                @Override
                public void onClick(View view) {
                    SettingActivity_.this.rightTextClick();
                }

            }
            );
        }
        if (btnBaseLeft!= null) {
            btnBaseLeft.setOnClickListener(new OnClickListener() {


                @Override
                public void onClick(View view) {
                    SettingActivity_.this.goBack();
                }

            }
            );
        }
        {
            View view = hasViews.findViewById(id.ll_tolog);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        SettingActivity_.this.tolog();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ll_feedback1);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        SettingActivity_.this.feedback1();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ll_feedback2);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        SettingActivity_.this.feedback2();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ll_privacy2);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        SettingActivity_.this.privacy2();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ll_about2);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        SettingActivity_.this.about2();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ll_terms1);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        SettingActivity_.this.terms1();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ll_logout);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        SettingActivity_.this.logout();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ll_register);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        SettingActivity_.this.register();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ll_change_mobilenumber);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        SettingActivity_.this.change_mobilenumber();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ll_terms2);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        SettingActivity_.this.terms2();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ll_privacy1);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        SettingActivity_.this.privacy1();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ll_change_pass);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        SettingActivity_.this.change_pass();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ll_about1);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        SettingActivity_.this.about1();
                    }

                }
                );
            }
        }
        InitBase();
        init();
    }

    public static class IntentBuilder_
        extends ActivityIntentBuilder<SettingActivity_.IntentBuilder_>
    {

        private Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            super(context, SettingActivity_.class);
        }

        public IntentBuilder_(Fragment fragment) {
            super(fragment.getActivity(), SettingActivity_.class);
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