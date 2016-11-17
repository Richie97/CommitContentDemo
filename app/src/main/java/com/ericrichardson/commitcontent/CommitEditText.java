package com.ericrichardson.commitcontent;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v13.view.inputmethod.EditorInfoCompat;
import android.support.v13.view.inputmethod.InputConnectionCompat;
import android.support.v13.view.inputmethod.InputContentInfoCompat;
import android.support.v4.os.BuildCompat;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

/**
 * Created by ericrichardson on 11/17/16.
 */

public class CommitEditText extends AppCompatEditText {
    private CommitListener commitListener;

    public CommitEditText(Context context) {
        super(context);
    }

    public CommitEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CommitEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public InputConnection onCreateInputConnection(final EditorInfo info) {
        final InputConnection ic = super.onCreateInputConnection(info);
        EditorInfoCompat.setContentMimeTypes(info, new String[]{"image/gif"});
        final InputConnectionCompat.OnCommitContentListener callback =
                new InputConnectionCompat.OnCommitContentListener() {
                    @Override
                    public boolean onCommitContent(InputContentInfoCompat info, int flags, Bundle opts) {
                        if (BuildCompat.isAtLeastNMR1() && (flags & InputConnectionCompat.INPUT_CONTENT_GRANT_READ_URI_PERMISSION) != 0) {
                            try {
                                info.requestPermission();
                            } catch (Exception e) {
                                return false; // return false if failed
                            }
                        }
                        if(commitListener != null){
                            commitListener.onCommitContent(info.getContentUri());
                        }
                        info.releasePermission();
                        return true;  // return true if succeeded
                    }
                };
        return InputConnectionCompat.createWrapper(ic, info, callback);
    }

    public void setCommitListener(CommitListener listener) {
        this.commitListener = listener;
    }

    public interface CommitListener {
        void onCommitContent(Uri uri);
    }
}
