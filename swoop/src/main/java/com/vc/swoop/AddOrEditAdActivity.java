package com.vc.swoop;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vc.swoop.adapter.ImageAdapter;
import com.vc.swoop.common.BaseActivity;
import com.vc.swoop.common.DialogHelper;
import com.vc.swoop.common.ImageUtils;
import com.vc.swoop.common.JUtil;
import com.vc.swoop.common.PictureUtil;
import com.vc.swoop.model.AD;
import com.vc.swoop.model.CategoryMDL;
import com.vc.swoop.webservice.AdWS;
import com.vc.swoop.widgets.ComfrimDialog.OnConfirmListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

@EActivity(R.layout.activity_addad)
public class AddOrEditAdActivity extends BaseActivity {

    private static final int CAMERA_WITH_DATA = 1001;
    /* ������ʶ����gallery��activity */
    private static final int PHOTO_PICKED_WITH_DATA = 1002;
    private static final int PHOTO_REQUEST_CUT = 1003;
    /* ���յ���Ƭ�洢λ�� */
    private static final File PHOTO_DIR = new File(Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera");
    private static final int REQUEST_IMAGE = 2;
    public BDLocationListener myListener = new MyLocationListener();
    public File mCurrentPhotoFile;// ��������յõ���ͼƬ
    @ViewById
    ImageView iv_following, iv_price, iv_help;
    @ViewById
    TextView tv_price, tv_category;
    @ViewById
    EditText et_price, ad_title, ad_content, et_phone;
    @ViewById
    RelativeLayout rl_category, rl_swap, rl_price, rl_offer;
    @ViewById
    Button btn_confirm, btn_cancel, btn_addphone;
    @ViewById
    Gallery pic_gallery;
    @ViewById
    RadioGroup rbgPrice;
    ImageAdapter adapter;
    double longitude = 0;
    double latitude = 0;
    boolean isFree = false;
    @Extra("isEdit")
    boolean isEdit = false;
    @Extra("ad_json")
    String ad_json;
    AD ad = null;
    List<CategoryMDL> categoryMDLs = CategoryMDL.GetDatas();
    boolean isLoad = false;
    // byte[] imgStream;
    // Bitmap bitmap;
    DisplayImageOptions options;
    List<Map<String, Object>> imgs = new ArrayList<Map<String, Object>>();
    boolean isFollowing = true;
    Dialog dialog;
    String session_id = "";
    String pricetype = "";
    String price = "";
    String phone = "";
    String category = "";
    String title = "";
    String content = "";
    String lat = "";
    String lng = "";
    InputStream pic1 = null;
    InputStream pic2 = null;
    InputStream pic3 = null;
    InputStream pic4 = null;
    InputStream pic5 = null;
    String ad_id = "";
    String follow_me = "0";
    private String[] categorys = {"New Zealand"};
    private CategoryMDL selectCategory;
    private String theLarge;

    @AfterViews
    void init() {

        if (isEdit) {
            ad = JUtil.parseObject(ad_json, AD.class);
        }
        options = new DisplayImageOptions.Builder().showImageForEmptyUri(
                R.drawable.img).build();//

        categorys = new String[categoryMDLs.size()];
        for (int i = 0; i < categoryMDLs.size(); i++) {
            categorys[i] = categoryMDLs.get(i).getName();
        }
        tv_price.setVisibility(View.GONE);
        et_price.setVisibility(View.VISIBLE);
        iv_price.setImageResource(R.drawable.toogle_gray);
        isFree = false;

        initView();
        if (ad != null && ad.getIsFlow() == 0) {
            iv_following.setImageResource(R.drawable.toogle_gray);
            isFollowing = false;
        } else {
            iv_following.setImageResource(R.drawable.toogle_green);
            isFollowing = true;
        }
        // et_price.addTextChangedListener(new TextWatcher() {
        // private boolean isChanged = false;
        //
        // @Override
        // public void onTextChanged(CharSequence s, int start, int before,
        // int count) {
        // // TODO Auto-generated method stub
        // }
        //
        // @Override
        // public void beforeTextChanged(CharSequence s, int start, int count,
        // int after) {
        // // TODO Auto-generated method stub
        // }
        //
        // @Override
        // public void afterTextChanged(Editable s) {
        // // TODO Auto-generated method stub
        // if (isChanged) {// ----->����ַ�δ�ı��򷵻�
        // return;
        // }
        // String str = s.toString();
        //
        // isChanged = true;
        // String cuttedStr = str;
        // /* ɾ���ַ��е�dot */
        // for (int i = str.length() - 1; i >= 0; i--) {
        // char c = str.charAt(i);
        // if ('.' == c) {
        // cuttedStr = str.substring(0, i) + str.substring(i + 1);
        // break;
        // }
        // }
        // /* ɾ��ǰ������0 */
        // int NUM = cuttedStr.length();
        // int zeroIndex = -1;
        // for (int i = 0; i < NUM - 2; i++) {
        // char c = cuttedStr.charAt(i);
        // if (c != '0') {
        // zeroIndex = i;
        // break;
        // } else if (i == NUM - 3) {
        // zeroIndex = i;
        // break;
        // }
        // }
        // if (zeroIndex != -1) {
        // cuttedStr = cuttedStr.substring(zeroIndex);
        // }
        // /* ����3λ��0 */
        // if (cuttedStr.length() < 3) {
        // cuttedStr = "0" + cuttedStr;
        // }
        // /* ����dot������ʾС������λ */
        // cuttedStr = cuttedStr.substring(0, cuttedStr.length() - 2)
        // + "." + cuttedStr.substring(cuttedStr.length() - 2);
        //
        // et_price.setText(cuttedStr);
        //
        // et_price.setSelection(et_price.length());
        // isChanged = false;
        // }
        // });

    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        // initLocation(locationListener);
        // if(mLocationClient!=null)
        // mLocationClient.start();
        // else {
        // initLocation(myListener);
        // }
        super.onResume();
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub

        if (mLocationClient != null)
            mLocationClient.stop();
        isLoad = false;
        super.onPause();
    }

    private void checkGPSInfo() {
        LocationManager locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);
        boolean result = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!result) {

            pgsAlert();

        }

    }

    private void pgsAlert() {
        DialogHelper
                .showComfrimDialog(
                        mContext,
                        null,
                        "Swoop requires to access your location without which you will not be able you use most features in Swoop.Please enable location services in Settings.",
                        "Settings", new OnConfirmListener() {

                            @Override
                            public void confirm() {
                                // TODO Auto-generated method stub
                                openGPSSetting();
                            }
                        }, null, null);

    }

    private void openGPSSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            this.startActivity(intent);

        } catch (ActivityNotFoundException ex) {

            // The Android SDK doc says that the location settings activity
            // may not be found. In that case show the general settings.

            // General settings activity
            intent.setAction(Settings.ACTION_SETTINGS);
            try {
                this.startActivity(intent);
            } catch (Exception e) {
            }
        }
    }

    private void initView() {
        rbgPrice.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                rl_swap.setVisibility(View.GONE);
                rl_offer.setVisibility(View.GONE);
                rl_price.setVisibility(View.GONE);
                if (checkedId == R.id.rb_price) {
                    rl_price.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.rb_swap) {
                    rl_swap.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.rb_offer) {
                    rl_offer.setVisibility(View.VISIBLE);
                }
            }
        });
        adapter = new ImageAdapter(this);
        pic_gallery.setAdapter(adapter);
        pic_gallery.setSpacing(10);
        if (isEdit) {
            if (ad != null) {
                addPic(ad.getPic1());
                addPic(ad.getPic2());
                addPic(ad.getPic3());
                addPic(ad.getPic4());
                addPic(ad.getPic5());
                adapter.setImgs(imgs);
            }
        }

        if (isEdit && ad != null) {
            ad_title.setText(ad.getTitle());
            ad_content.setText(ad.getDescribe());
            et_phone.setText(ad.getPhone());
            if (ad.getPricetype() == 0) {
                rbgPrice.check(R.id.rb_price);
            } else if (ad.getPricetype() == 1) {
                rbgPrice.check(R.id.rb_swap);
            } else if (ad.getPricetype() == 2) {
                rbgPrice.check(R.id.rb_offer);
            }
            // if (ad.getPrice() == 0) {
            // isFree = true;
            // } else {
            et_price.setText(String.valueOf(ad.getPrice()));
            // }
            if (ad.getCategory() != 0) {
                for (int i = 0; i < categoryMDLs.size(); i++) {
                    if (categoryMDLs.get(i).getId() == ad.getCategory()) {
                        selectCategory = categoryMDLs.get(i);
                        tv_category.setText(selectCategory.getName());
                        break;
                    }
                }
            }
            updatePriceView();

        } else {
            et_phone.setText(GetApp().getUser().getPhone());
        }

        if (isEdit) {
            setTitleText("Edit");
        } else
            setTitleText("New Listing");

        // if (!isEdit) {
        // imageChooseItem();
        // }
    }

    // List<String> urls = new ArrayList<String>();
    //
    private void addPic(String imgUrl) {
        if (imgUrl != null && !imgUrl.equals("")) {
            Map<String, Object> url = new HashMap<String, Object>();
            url.put("isEdit", true);
            url.put("url", imgUrl);
            imgs.add(url);
        }
    }

    private void updatePriceView() {
        if (!isFree) {
            // toogle_gray
            tv_price.setVisibility(View.GONE);
            et_price.setVisibility(View.VISIBLE);
            iv_price.setImageResource(R.drawable.toogle_gray);
            isFree = false;
        } else {
            tv_price.setVisibility(View.VISIBLE);
            et_price.setVisibility(View.INVISIBLE);
            iv_price.setImageResource(R.drawable.toogle_green);
            isFree = true;
        }
    }

    // @Override
    // protected void onActivityResult(final int requestCode,
    // final int resultCode, final Intent data) {
    // L.i("requestCode", String.valueOf(requestCode));
    // L.i("resultCode", String.valueOf(resultCode));
    // if (resultCode != RESULT_OK)
    // return;
    //
    // final Handler handler = new Handler() {
    // public void handleMessage(Message msg) {
    // if (msg.what == 1 && msg.obj != null) {
    // // ��ʾͼƬ
    // if (imgs.size() >= 5) {
    // T.ShortToast(AddOrEditAd.this
    // .getString(R.string.pic_5_tickets));
    // return;
    // }
    // Map<String, Object> b = new HashMap<String, Object>();
    // b.put("isEdit", false);
    // b.put("bmp", (Bitmap) msg.obj);
    // imgs.add(b);
    // adapter.setImgs(imgs);
    // adapter.notifyDataSetChanged();
    // pic_gallery.setSelection(imgs.size()-1);
    // }
    // }
    // };
    //
    // new Thread() {
    // public void run() {
    // Bitmap bitmap = null;
    //
    // if (requestCode == ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD) {
    // if (data == null)
    // return;
    //
    // Uri thisUri = data.getData();
    // String thePath = ImageUtils
    // .getAbsolutePathFromNoStandardUri(thisUri);
    //
    // // ����Ǳ�׼Uri
    // if (StringUtils.isEmpty(thePath)) {
    // theLarge = ImageUtils.getAbsoluteImagePath(
    // AddOrEditAd.this, thisUri);
    // } else {
    // theLarge = thePath;
    // }
    //
    // String attFormat = FileUtils.getFileFormat(theLarge);
    // if (!"photo".equals(MediaUtils.getContentType(attFormat))) {
    // Toast.makeText(AddOrEditAd.this,
    // "Please select a picture type",
    // Toast.LENGTH_SHORT).show();
    // return;
    // }
    //
    // // if(bitmap == null && !StringUtils.isEmpty(theLarge))
    // // {
    // // bitmap = ImageUtils.loadImgThumbnail(theLarge, 100, 100);
    // // }
    // }
    // // ����ͼƬ
    // // else if(requestCode ==
    // // ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA)
    // // {
    // // if(bitmap == null && !StringUtils.isEmpty(theLarge))
    // // {
    // // bitmap = ImageUtils.loadImgThumbnail(theLarge, 100, 100);
    // // }
    // // }
    // try {
    // L.i("pic path:" + theLarge);
    // // Bitmap bmp =
    // // PictureUtil.ImageCrop(PictureUtil.getSmallBitmap(theLarge));
    // Bitmap bmp = PictureUtil.ImageCrop(PictureUtil
    // .picChange90Ratate(
    // PictureUtil.getSmallBitmap(theLarge),
    // PictureUtil.readPictureDegree(theLarge)));
    // Message msg = new Message();
    // msg.what = 1;
    // msg.obj = bmp;
    // handler.sendMessage(msg);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    //
    // // if(bitmap!=null)
    // // {
    // // //�����Ƭ���ļ���
    // // String savePath =
    // // Environment.getExternalStorageDirectory().getAbsolutePath() +
    // // "/Airshop/Camera/";
    // // File savedir = new File(savePath);
    // // if (!savedir.exists()) {
    // // savedir.mkdirs();
    // // }
    // //
    // // String largeFileName = FileUtils.getFileName(theLarge);
    // // String largeFilePath = savePath + largeFileName;
    // // //�ж��Ƿ��Ѵ�������ͼ
    // // if(largeFileName.startsWith("thumb_") && new
    // // File(largeFilePath).exists())
    // // {
    // // theThumbnail = largeFilePath;
    // // imgFile = new File(theThumbnail);
    // // }
    // // else
    // // {
    // // //����ϴ���800���ͼƬ
    // // String thumbFileName = "thumb_" + largeFileName;
    // // theThumbnail = savePath + thumbFileName;
    // // if(new File(theThumbnail).exists())
    // // {
    // // imgFile = new File(theThumbnail);
    // // }
    // // else
    // // {
    // // try {
    // // //ѹ���ϴ���ͼƬ
    // // ImageUtils.createImageThumbnail(TweetPub.this, theLarge,
    // // theThumbnail, 800, 80);
    // // imgFile = new File(theThumbnail);
    // // } catch (IOException e) {
    // // e.printStackTrace();
    // // }
    // // }
    // // }
    //
    // // Message msg = new Message();
    // // msg.what = 1;
    // // msg.obj = bitmap;
    // // handler.sendMessage(msg);
    // // }
    // };
    //
    // }.start();
    //
    // }

    @Click(R.id.btn_addphone)
    void addphone() {
        if (imgs.size() >= 5) {
            DialogHelper.showTost(mContext, "Add up to 5 photos");
        } else {
//            imageChooseItem();
            pickupImageItem();
        }
    }

    @Click(R.id.btn_cancel)
    void cancel() {
        this.finish();
    }

    @Click(R.id.rl_category)
    void showCategroy() {
        new AlertDialog.Builder(this).setTitle("Select category")
                .setItems(categorys, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        tv_category.setText(categorys[which]);
                        selectCategory = categoryMDLs.get(which);
                    }
                }).setNegativeButton("Cancel", null).show();
    }

    private void alertConfirm() {
        if (dialog == null) {
            dialog = new Dialog(this, R.style.activity_translucent);
            dialog.setContentView(R.layout.tip_confirm);
        }
        Button btn_confirm, btn_cancel;
        final TextView tv_title = (TextView) dialog.getWindow().findViewById(
                R.id.tv_title);
        tv_title.setText("What does this mean?");
        final TextView tv_message = (TextView) dialog.getWindow().findViewById(
                R.id.tv_message);
        tv_message
                .setText("Location of your listing will follow you no matter where you are");
        btn_confirm = (Button) dialog.getWindow()
                .findViewById(R.id.btn_confirm);
        btn_cancel = (Button) dialog.getWindow().findViewById(R.id.btn_cancel);

        btn_confirm.setVisibility(View.GONE);
        btn_cancel.setText("Dismiss");
        btn_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public void pickupImageItem() {
        MultiImageSelector.create(this)
                .count(5) // max select image size, 9 by default. used width #.multi()
                .start(this, REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                // Get the result list of select image paths
                List<String> paths = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                for (String path : paths) {
                    addImageBitmap(getBitmapUrl(path));
                }
            }
        }
    }

    /**
     * ����ѡ��
     *
     * @param items
     */
    public void imageChooseItem() {

        AlertDialog imageDialog = new AlertDialog.Builder(this)
                .setTitle("Select Image")
                .setItems(new String[]{"Gallery", "Camera"},
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                // �ֻ�ѡͼ
                                if (item == 0) {
                                    doPickPhotoFromGallery();
                                    // Intent intent = new Intent(
                                    // Intent.ACTION_GET_CONTENT);
                                    // intent.addCategory(Intent.CATEGORY_OPENABLE);
                                    // intent.setType("image/*");
                                    // startActivityForResult(
                                    // Intent.createChooser(intent,
                                    // "select picture"),
                                    // ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);
                                }
                                // ����
                                else if (item == 1) {
                                    doTakePhoto();
                                    // String savePath = "";
                                    // // �ж��Ƿ������SD��
                                    // String storageState = Environment
                                    // .getExternalStorageState();
                                    // if (storageState
                                    // .equals(Environment.MEDIA_MOUNTED)) {
                                    // savePath = Environment
                                    // .getExternalStorageDirectory()
                                    // .getAbsolutePath()
                                    // + "/Airshop/Camera/";// �����Ƭ���ļ���
                                    // File savedir = new File(savePath);
                                    // if (!savedir.exists()) {
                                    // savedir.mkdirs();
                                    // }
                                    // }
                                    //
                                    // // û�й���SD�����޷������ļ�
                                    // if (StringUtils.isEmpty(savePath)) {
                                    // T.ShortToast(AddOrEditAd.this
                                    // .getString(R.string.not_save_to_card));
                                    // return;
                                    // }
                                    //
                                    // String timeStamp = new SimpleDateFormat(
                                    // "yyyyMMddHHmmss")
                                    // .format(new Date());
                                    // String fileName = "air_" + timeStamp
                                    // + ".jpg";// ��Ƭ����
                                    // File out = new File(savePath, fileName);
                                    // Uri uri = Uri.fromFile(out);
                                    //
                                    // theLarge = savePath + fileName;//
                                    // ����Ƭ�ľ��·��
                                    //
                                    // Intent intent = new Intent(
                                    // MediaStore.ACTION_IMAGE_CAPTURE);
                                    // intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    // uri);
                                    // startActivityForResult(
                                    // intent,
                                    // ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA);
                                }
                            }
                        }).create();

        imageDialog.show();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        for (int i = 0; i < imgs.size(); i++) {
            Map<String, Object> bMap = imgs.get(i);
            if (!(Boolean) bMap.get("isEdit")) {
                Bitmap bmp = (Bitmap) bMap.get("bmp");
                if (bmp != null && !bmp.isRecycled()) {
                    bmp.recycle();
                    bmp = null;
                }
            }

        }
        System.gc();
        super.onDestroy();

    }

    protected void doPickPhotoFromGallery() {
        try {
            // Launch picker to choose photo for selected contact
            final Intent intent = getPhotoPickIntent();
            this.startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
        } catch (ActivityNotFoundException e) {
            DialogHelper.showTost(mContext, AddOrEditAdActivity.this
                    .getString(R.string.not_save_to_card));
        }
    }

    public Intent getPhotoPickIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        intent.putExtra("return-data", false);
        return intent;
    }

    // protected void getImageFromCamera() {
    // String state = Environment.getExternalStorageState();
    // if (state.equals(Environment.MEDIA_MOUNTED)) {
    // Intent getImageByCamera = new
    // Intent("android.media.action.IMAGE_CAPTURE");
    // String out_file_path = Constant.SAVED_IMAGE_DIR_PATH;
    // File dir = new File(out_file_path);
    // if (!dir.exists()) {
    // dir.mkdirs();
    // }
    // capturePath = Constant.SAVED_IMAGE_DIR_PATH + System.currentTimeMillis()
    // + ".jpg";
    // getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new
    // File(capturePath)));
    // getImageByCamera.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
    // startActivityForResult(getImageByCamera,
    // Constant.REQUEST_CODE_CAPTURE_CAMEIA);
    // }
    // else {
    // Toast.makeText(getApplicationContext(), "mark sure have sdcard",
    // Toast.LENGTH_LONG).show();
    // }
    // }
    protected void doTakePhoto() {

        try {
            PHOTO_DIR.mkdirs();// ������Ƭ�Ĵ洢Ŀ¼
            mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName());// �����յ���Ƭ�ļ�����
            if (!mCurrentPhotoFile.exists()) {
                try {
                    mCurrentPhotoFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Intent intent = getTakePickIntent(mCurrentPhotoFile);
            this.startActivityForResult(intent, CAMERA_WITH_DATA);
        } catch (ActivityNotFoundException e) {
            DialogHelper.showTost(mContext, AddOrEditAdActivity.this
                    .getString(R.string.not_save_to_card));
        }
    }

    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMddHHmmss");
        return dateFormat.format(date) + ".png";
    }

    public Intent getTakePickIntent(File f) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(mCurrentPhotoFile));
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

        intent.putExtra("return-data", false);
        return intent;
    }

    private void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // cropΪtrue�������ڿ�����intent��������ʾ��view���Լ���
        intent.putExtra("crop", "true");

        // aspectX aspectY �ǿ�ߵı���
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY �Ǽ���ͼƬ�Ŀ��
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    /*
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            switch (requestCode) {
            case PHOTO_PICKED_WITH_DATA: // ����Gallery���ص�
                if (data != null) {
                    Uri uri = data.getData();
                    String[] proj = { MediaStore.Images.Media.DATA };
                    Cursor actualimagecursor = managedQuery(uri, proj, null, null,
                            null);
                    String img_path = "";
                    if (actualimagecursor != null) {
                        int actual_image_column_index = actualimagecursor
                                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        actualimagecursor.moveToFirst();

                        img_path = actualimagecursor
                                .getString(actual_image_column_index);
                    }
                    if (img_path == null || img_path.equals("")) {

                        ContentResolver cr = this.getContentResolver();
                        try {
                            // BitmapFactory.d
                            InputStream is = cr.openInputStream(uri);

                            // ��Ӳü�
                            // startPhotoZoom(uri, 300);
                            Bitmap bitmap = ImageUtils.decodeStream(cr
                                    .openInputStream(uri));
                            // bitmap =
                            // ImageUtil.decodeStream(cr.openInputStream(uri));
                            addImageBitmap(bitmap);
                            // bitmap=ImageUtil.compBitmap(bitmap);
                            // bitmap =
                            // ImageUtil.decodeSampledBitmapFromByte(FileHelper.InputStreamToByte(cr.openInputStream(uri)),
                            // imgMember.getWidth(), imgMember.getHeight());
                            // imgStream=ImageUtil.Bitmap2Bytes(bitmap);
                            // bitmap=ImageUtil.CompressBitmapBySize(bitmap, 8);
                            // setImageBitmap(bitmap);
                            // } catch (FileNotFoundException e) {
                            // Log.e("Exception", e.getMessage(),e);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    } else {
                        // addImageBitmap(getBitmapUrl(mCurrentPhotoFile.getPath()));
                        Bitmap b = ImageUtils.decodeFile(img_path);
                        addImageBitmap(b);
                        // File tempFile = new File(img_path);
                        //
                        // startPhotoZoom(Uri.fromFile(tempFile), 300);
                        //
                        // setImageUrl(img_path);
                    }

                }
                break;
            case PHOTO_REQUEST_CUT: // �������򷵻ص�
                Bundle bundle = data.getExtras();
                Bitmap bitmap = bundle.getParcelable("data");
                if (bitmap != null) {
                    bitmap = ImageUtils.compBitmap(bitmap);
                    // Bitmap photo = bundle.getParcelable("data");
                    // imgStream=ImageUtils.Bitmap2Bytes(bitmap);
                    Map<String, Object> b = new HashMap<String, Object>();
                    b.put("isEdit", false);
                    b.put("bmp", bitmap);
                    imgs.add(b);
                    adapter.setImgs(imgs);
                    adapter.notifyDataSetChanged();
                    pic_gallery.setSelection(imgs.size() - 1);
                }

                // setImageBitmap(bitmap);
                break;
            case CAMERA_WITH_DATA: // �������򷵻ص�
                // DialogHelper.showTost(mContext, "resultCode=="+resultCode);
                if (resultCode == 0)
                    return;
                // DialogHelper.showTost(mContext, "������ͼƬ�·����"
                // + mCurrentPhotoFile.getPath());
                if (mCurrentPhotoFile != null
                        && mCurrentPhotoFile.getPath() != null)
                    addImageBitmap(getBitmapUrl(mCurrentPhotoFile.getPath()));
                // if (data != null) { //
                // ������δָ��intent.putExtra(MediaStore.EXTRA_OUTPUT,
                // // uri);
                // // ����������ͼ
                // DialogHelper.showTost(mContext, "data ��Ϊ��");
                // if (data.hasExtra("data")) {
                // DialogHelper.showTost(mContext, "������ͼ");
                // bitmap = data.getParcelableExtra("data");
                // addImageBitmap(bitmap);
                // // bitmap=ImageUtils.compBitmap(bitmap);
                // // //imgStream=ImageUtils.Bitmap2Bytes(bitmap);
                // // Map<String, Object> bb = new HashMap<String, Object>();
                // // bb.put("isEdit", false);
                // // bb.put("bmp", bitmap);
                // // imgs.add(bb);
                // // adapter.setImgs(imgs);
                // // adapter.notifyDataSetChanged();
                // // pic_gallery.setSelection(imgs.size()-1);
                // //
                // // �õ�bitmap��Ĳ���
                // } else {
                // DialogHelper.showTost(mContext, "������ͼƬ�·����"
                // + mCurrentPhotoFile.getPath());
                // addImageBitmap(getBitmapUrl(mCurrentPhotoFile.getPath()));
                // }
                // } else {
                // // startPhotoZoom(Uri.fromFile(mCurrentPhotoFile), 300);
                // DialogHelper.showTost(mContext, "������ͼƬ�·����"
                // + mCurrentPhotoFile.getPath());
                // addImageBitmap(getBitmapUrl(mCurrentPhotoFile.getPath()));
                // // ����ָ����Ŀ��uri���洢��Ŀ��uri��intent.putExtra(MediaStore.EXTRA_OUTPUT,
                // // uri);
                // // ͨ��Ŀ��uri���ҵ�ͼƬ
                // // ��ͼƬ�����Ŵ���
                // // ����
                // }

                break;

            }
        }
    */
    public Bitmap getBitmapUrl(String url) {
        // imgMember.setImageURI(Uri.parse(url));
        // imgMember.setVisibility(View.GONE);
        // circleImageView.setVisibility(View.VISIBLE);
        // imgStream = FileHelper.encodeByteFile(url);
        Bitmap bitmap = ImageUtils.decodeFile(url);
        if (bitmap == null) {
            // DialogHelper.showTost(mContext, "��ȡͼƬ���?bitmap is null");
        }
        ExifInterface exifInterface;
        try {
            exifInterface = new ExifInterface(url);
            int result = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            int rotate = 0;
            switch (result) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                default:
                    break;
            }

            Matrix m = new Matrix();
            m.setRotate(rotate);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            // DialogHelper.showTost(mContext, "��תͼƬ���?" + e.getMessage());
            e.printStackTrace();
        }
        // bitmap = BitmapFactory.decodeFile(url);
        // bitmap=ImageUtil.compBitmap(bitmap);
        // bitmap =
        // ImageUtil.decodeSampledBitmapFromByte(FileHelper.InputStreamToByte(cr.openInputStream(uri)),
        // imgMember.getWidth(), imgMember.getHeight());
        // factoryBalanceView.setImageBitmap(bitmap);
        return bitmap;
    }

    private void addImageBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            // DialogHelper.showTost(mContext, "��ʾͼƬ���?");
            return;
        }

        bitmap = ImageUtils.compBitmap(bitmap);
        // imgStream=ImageUtils.Bitmap2Bytes(bitmap);
        Map<String, Object> bb = new HashMap<String, Object>();
        bb.put("isEdit", false);
        bb.put("bmp", bitmap);
        imgs.add(bb);
        adapter.setImgs(imgs);
        adapter.notifyDataSetChanged();
        pic_gallery.setSelection(imgs.size() - 1);
    }

    @Override
    public void finish() {
        if (ad != null) {

            Intent i = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("ad", ad);
            bundle.putBoolean("isEdit", true);
            i.putExtras(bundle);
            setResult(2, i);
        }

        super.finish();
    }

    public InputStream returnInputStream(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        InputStream is = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }

    @Click(R.id.btn_confirm)
    void selingAd() {
        DialogHelper.showComfrimDialog(mContext, null, "Confirm your listing",
                null, new OnConfirmListener() {

                    @Override
                    public void confirm() {
                        // TODO Auto-generated method stub
                        if (!netCheck()) {
                            DialogHelper
                                    .showTost(
                                            mContext,
                                            AddOrEditAdActivity.this
                                                    .getString(R.string.No_Networks_Found));
                            return;
                        }

                        if (selectCategory == null) {
                            DialogHelper
                                    .showTost(
                                            mContext,
                                            AddOrEditAdActivity.this
                                                    .getString(R.string.categtoy_not_null));
                            return;
                        }
                        if (TextUtils.isEmpty(ad_title.getText().toString())) {
                            DialogHelper
                                    .showTost(
                                            mContext,
                                            AddOrEditAdActivity.this
                                                    .getString(R.string.title_not_empty));
                            return;
                        }
                        // location();
                        session_id = GetApp().getUser().getSession_id();
                        pricetype = "0";
                        price = et_price.getText().toString();
                        phone = et_phone.getText().toString();
                        category = selectCategory.getId() + "";
                        title = ad_title.getText().toString();

                        content = ad_content.getText().toString();
                        // if (TextUtils.isEmpty(content)) {
                        //
                        // DialogHelper
                        // .showTost(
                        // mContext,
                        // AddOrEditAdActivity.this
                        // .getString(R.string.content_not_null));
                        // return;
                        // }
                        if (TextUtils.isEmpty(price)) {

                            DialogHelper
                                    .showTost(
                                            mContext,
                                            AddOrEditAdActivity.this
                                                    .getString(R.string.price_not_null));
                            return;
                        }
                        if (title.length() > 50 || content.length() > 512) {
                            DialogHelper
                                    .showTost(
                                            mContext,
                                            AddOrEditAdActivity.this
                                                    .getString(R.string.title_too_long));
                            return;
                        }

                        if (checkLatAndLng()) {
                            DialogHelper
                                    .showTost(
                                            mContext,
                                            AddOrEditAdActivity.this
                                                    .getString(R.string.location_failure));
                            return;
                        }
                        lat = GetApp().getLatitude() + "";
                        lng = GetApp().getLongitude() + "";

                        ad_id = "";
                        if (isEdit) {
                            ad_id = ad.getAd_id() + "";
                        }
                        follow_me = "0";
                        new addADTask().execute("");
                    }
                }, null, null);

    }

    // // ����λ�ü�����
    // private LocationListener locationListener = new LocationListener() {
    // // λ�÷���ı�ʱ����
    // @Override
    // public void onLocationChanged(Location location) {
    // longitude = location.getLongitude();
    // latitude = location.getLatitude();
    // GetApp().setLatitude(latitude);
    // GetApp().setLongitude(longitude);
    // // if (!isLoad) {
    // // new GetDataTask().execute("");
    // // }
    // Log.d("Location", "onLocationChanged");
    // }
    //
    // // providerʧЧʱ����
    // @Override
    // public void onProviderDisabled(String provider) {
    // Log.d("Location", "onProviderDisabled");
    // }
    //
    // // provider����ʱ����
    // @Override
    // public void onProviderEnabled(String provider) {
    // Log.d("Location", "onProviderEnabled");
    // }
    //
    // // ״̬�ı�ʱ����
    // @Override
    // public void onStatusChanged(String provider, int status, Bundle extras) {
    // Log.d("Location", "onStatusChanged");
    // }
    // };
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            GetApp().setLatitude(latitude);
            GetApp().setLongitude(longitude);
            // if (!isLoad) {
            // new GetDataTask().execute("");
            // }
        }
    }

    class addADTask extends AsyncTask<Object, Integer, String> {

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            DialogHelper.CloseLoadingDialog();
            if (!TextUtils.isEmpty(result)) {
                DialogHelper.showTost(mContext, result);
            } else {
                if (isEdit) {
                    DialogHelper.showTost(mContext,
                            "Your listing has been updated");
                }
                AddOrEditAdActivity.this.finish();
            }
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

            DialogHelper.ShowLoadingDialog(mContext, "loading data");
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Object... arg0) {
            // TODO Auto-generated method stub
            AdWS ws = new AdWS(mContext);
            pic1 = null;
            pic2 = null;
            pic3 = null;
            pic4 = null;
            pic5 = null;
            for (int i = 0; i < imgs.size(); i++) {

                try {
                    InputStream pic = null;
                    if ((Boolean) imgs.get(i).get("isEdit")) {
                        pic = PictureUtil
                                .Bitmap2IS(ImageLoader
                                        .getInstance()
                                        .loadImageSync(
                                                (String) imgs.get(i).get("url")));
                    } else {
                        pic = PictureUtil.Bitmap2IS((Bitmap) imgs.get(i).get(
                                "bmp")); // �ύ�ֽ���
                    }
                    if (i == 0) {
                        pic1 = pic;
                    } else if (i == 1) {
                        pic2 = pic;
                    } else if (i == 2) {
                        pic3 = pic;
                    } else if (i == 3) {
                        pic4 = pic;
                    } else if (i == 4) {
                        pic5 = pic;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            JSONObject jsonObject = ws.selling_ad(session_id, ad_id, category,
                    lng, lat, pricetype, price, title, content, follow_me,
                    phone, pic1, pic2, pic3, pic4, pic5);
            if (JUtil.checkStaus(jsonObject)) {
                return "";
            } else {
                return JUtil.getError(jsonObject);
            }

        }

    }
}
