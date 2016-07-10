//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.1.
//


package com.vc.swoop;

import android.content.Context;
import android.content.Intent;
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

public final class RegisterStep3Activity_
    extends RegisterStep3Activity
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();
    public final static String CODE_EXTRA = "code";
    public final static String PHONE_EXTRA = "phonecode";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(layout.activity_register_step3);
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        injectExtras_();
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

    public static RegisterStep3Activity_.IntentBuilder_ intent(Context context) {
        return new RegisterStep3Activity_.IntentBuilder_(context);
    }

    public static RegisterStep3Activity_.IntentBuilder_ intent(Fragment supportFragment) {
        return new RegisterStep3Activity_.IntentBuilder_(supportFragment);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        tvBaseRight = ((TextView) hasViews.findViewById(id.tvBaseRight));
        btnBaseRight = ((Button) hasViews.findViewById(id.btnBaseRight));
        btnBaseLeft = ((Button) hasViews.findViewById(id.btnBaseLeft));
        imgBaseLogo = ((ImageView) hasViews.findViewById(id.imgBaseLogo));
        tvBaseTitle = ((TextView) hasViews.findViewById(id.tvBaseTitle));
        password = ((EditText) hasViews.findViewById(id.password));
        et_nickname = ((EditText) hasViews.findViewById(id.et_nickname));
        password_again = ((EditText) hasViews.findViewById(id.password_again));
        if (tvBaseRight!= null) {
            tvBaseRight.setOnClickListener(new OnClickListener() {


                @Override
                public void onClick(View view) {
                    RegisterStep3Activity_.this.rightTextClick();
                }

            }
            );
        }
        if (btnBaseRight!= null) {
            btnBaseRight.setOnClickListener(new OnClickListener() {


                @Override
                public void onClick(View view) {
                    RegisterStep3Activity_.this.rightClick();
                }

            }
            );
        }
        if (btnBaseLeft!= null) {
            btnBaseLeft.setOnClickListener(new OnClickListener() {


                @Override
                public void onClick(View view) {
                    RegisterStep3Activity_.this.goBack();
                }

            }
            );
        }
        {
            View view = hasViews.findViewById(id.btn_ok_account);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        RegisterStep3Activity_.this.onSubmit();
                    }

                }
                );
            }
        }
        InitBase();
        init();
    }

    private void injectExtras_() {
        Bundle extras_ = getIntent().getExtras();
        if (extras_!= null) {
            if (extras_.containsKey(CODE_EXTRA)) {
                code = extras_.getString(CODE_EXTRA);
            }
            if (extras_.containsKey(PHONE_EXTRA)) {
                phone = extras_.getString(PHONE_EXTRA);
            }
        }
    }

    @Override
    public void setIntent(Intent newIntent) {
        super.setIntent(newIntent);
        injectExtras_();
    }

    public static class IntentBuilder_
        extends ActivityIntentBuilder<RegisterStep3Activity_.IntentBuilder_>
    {

        private Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            super(context, RegisterStep3Activity_.class);
        }

        public IntentBuilder_(Fragment fragment) {
            super(fragment.getActivity(), RegisterStep3Activity_.class);
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

        public RegisterStep3Activity_.IntentBuilder_ code(String code) {
            return super.extra(CODE_EXTRA, code);
        }

        public RegisterStep3Activity_.IntentBuilder_ phone(String phone) {
            return super.extra(PHONE_EXTRA, phone);
        }

    }

}