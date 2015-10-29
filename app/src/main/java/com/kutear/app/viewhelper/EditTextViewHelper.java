package com.kutear.app.viewhelper;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.callback.IUploadCallBack;
import com.kutear.app.fragment.BaseFragment;
import com.kutear.app.fragment.KDialogFragment;
import com.kutear.app.upload.QiniuUpload;
import com.kutear.app.utils.ImageCompressUtil;
import com.kutear.app.utils.L;
import com.kutear.app.utils.SaveData;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by kutear on 15-10-11.
 * 用于文章/页面编辑的EditText
 **/
public class EditTextViewHelper implements View.OnClickListener, IUploadCallBack {
    private Activity mActivity;
    private BaseFragment mFragment;
    private View mMainView;
    private EditText mEditText;
    private static final String IMAGE_TYPE = "image/*";
    private static final int IMAGE_CODE = 0;
    private static final String TAG = EditTextViewHelper.class.getSimpleName();

    public EditTextViewHelper(BaseFragment fragment) {
        this.mActivity = fragment.getActivity();
        this.mFragment = fragment;
        initView();
    }

    /**
     * 初始化整体布局
     */
    private void initView() {
        mMainView = mActivity.getLayoutInflater().inflate(R.layout.viewhelp_edit_text, null);
        mMainView.findViewById(R.id.view_helper_text_add_img).setOnClickListener(this);
        mEditText = (EditText) mMainView.findViewById(R.id.view_helper_edit_text);
    }

    public View getMainView() {
        return mMainView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_helper_text_add_img:
                chooseImgFromAlbum();
                break;
            default:
                break;
        }
    }

    /**
     * 从相册选取图片
     */
    private void chooseImgFromAlbum() {
        Intent getAlbum = new Intent(Intent.ACTION_PICK);
        getAlbum.setType(IMAGE_TYPE);
        mFragment.startActivityForResult(getAlbum, IMAGE_CODE);
    }

    /**
     * 选取图片文件后的返回
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            try {
                uploadImg(getRealPathFromURI(uri));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 上传文件
     *
     * @param path
     */
    private void uploadImg(String path) throws IOException {
        if (path != null) {
            File file = new File(path);
            Bitmap bmp = ImageCompressUtil.compressBySize(file.getAbsolutePath(), 480, 480);
            String absPath = SaveData.saveImage(bmp, file.getName());
            if (absPath != null) {
                file = new File(absPath);
            }
            QiniuUpload.upload((AppApplication) mActivity.getApplication(),file, this);
            //显示上传对话框
            showUploadDialog();
        } else {
            Snackbar.make(mMainView, R.string.please_choose_from_album, Snackbar.LENGTH_SHORT).show();
        }
    }

    /**
     * 根据Uri获取到完整的路径
     *
     * @param contentUri
     * @return
     */
    private String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = mActivity.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    @Override
    public void onSuccess(final String url) {
        String[] tempIndex = url.split("/");
        String tempName = tempIndex[tempIndex.length - 1];
        KDialogFragment.showEditText(mFragment.getFragmentManager(), mActivity.getString(R.string.edit_link_name), tempName,
                new KDialogFragment.IEditTextCallBack() {
                    @Override
                    public void onTextInput(String str) {
                        //转化为MarkDown格式
                        str = "![" + str + "](" + url + ")";
                        insertTextToSelection(str);
                    }
                });
    }

    /**
     * 向光标位置插入文字
     *
     * @param input
     */
    private void insertTextToSelection(String input) {
        int index = mEditText.getSelectionStart();//获取光标所在位置
        Editable edit = mEditText.getEditableText();//获取EditText的文字
        if (index < 0 || index >= edit.length()) {
            edit.append(input);
        } else {
            edit.insert(index, input);//光标所在位置插入文字
        }
    }

    /**
     * 返回EditText
     *
     * @return
     */
    public String getContent() {
        return mEditText.getText().toString();
    }

    /**
     * 设置文本
     *
     * @param input
     */
    public void setContent(String input) {
        mEditText.setText(input);
    }

    @Override
    public void onError(String error) {
        closeUploadDialog();
    }

    @Override
    public void onProcess(double process) {
        if (process == 1.0) {
            closeUploadDialog();
        }
    }

    private void showUploadDialog() {
        KDialogFragment.showLoadingDialog(mFragment.getFragmentManager(), "上传中...");
    }

    private void closeUploadDialog() {
        KDialogFragment.hiddenDialog(mFragment.getFragmentManager());
    }
}
