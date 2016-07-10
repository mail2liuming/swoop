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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.vc.swoop.R.id;
import com.vc.swoop.R.layout;
import org.androidannotations.api.builder.ActivityIntentBuilder;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class RegisterStep1Activity_
    extends RegisterStep1Activity
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(layout.activity_register_step1);
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

    public static RegisterStep1Activity_.IntentBuilder_ intent(Context context) {
        return new RegisterStep1Activity_.IntentBuilder_(context);
    }

    public static RegisterStep1Activity_.IntentBuilder_ intent(Fragment supportFragment) {
        return new RegisterStep1Activity_.IntentBuilder_(supportFragment);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        tvBaseTitle = ((TextView) hasViews.findViewById(id.tvBaseTitle));
        tvBaseRight = ((TextView) hasViews.findViewById(id.tvBaseRight));
        btnBaseLeft = ((Button) hasViews.findViewById(id.btnBaseLeft));
        imgBaseLogo = ((ImageView) hasViews.findViewById(id.imgBaseLogo));
        btnBaseRight = ((Button) hasViews.findViewById(id.btnBaseRight));
        et_mobile = ((EditText) hasViews.findViewById(id.et_mobile));
        tvCountry = ((TextView) hasViews.findViewById(id.tvCountry));
        tvNum = ((TextView) hasViews.findViewById(id.tvNum));
        if (tvBaseRight!= null) {
            tvBaseRight.setOnClickListener(new OnClickListener() {


                @Override
                public void onClick(View view) {
                    RegisterStep1Activity_.this.rightTextClick();
                }

            }
            );
        }
        if (btnBaseLeft!= null) {
            btnBaseLeft.setOnClickListener(new OnClickListener() {


                @Override
                public void onClick(View view) {
                    RegisterStep1Activity_.this.goBack();
                }

            }
            );
        }
        if (btnBaseRight!= null) {
            btnBaseRight.setOnClickListener(new OnClickListener() {


                @Override
                public void onClick(View view) {
                    RegisterStep1Activity_.this.rightClick();
                }

            }
            );
        }
        {
            View view = hasViews.findViewById(id.tv_terms);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        RegisterStep1Activity_.this.onTearm(view);
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.btn_ok_account);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        RegisterStep1Activity_.this.account();
                    }

                }
                );
            }
        }
        if (tvCountry!= null) {
            tvCountry.setOnClickListener(new OnClickListener() {


                @Override
                public void onClick(View view) {
                    RegisterStep1Activity_.this.onSelectCountry();
                }

            }
            );
        }
        InitBase();
        init();
    }

    public static class IntentBuilder_
        extends ActivityIntentBuilder<RegisterStep1Activity_.IntentBuilder_>
    {

        private Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            super(context, RegisterStep1Activity_.class);
        }

        public IntentBuilder_(Fragment fragment) {
            super(fragment.getActivity(), RegisterStep1Activity_.class);
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
