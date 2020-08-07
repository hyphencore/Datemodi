package com.example.datemodi;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileSelectionDialog implements AdapterView.OnItemClickListener {
    private Context              m_contextParent;    // 呼び出し元
    private OnFileSelectListener m_listener;    // 結果受取先
    private AlertDialog m_dialog;    // ダイアログ
    private FileInfoArrayAdapter m_fileinfoarrayadapter; // ファイル情報配列アダプタ

    // コンストラクタ
    public FileSelectionDialog(Context context, OnFileSelectListener listener) {
        m_contextParent = context;
        m_listener = listener;
    }

    // ダイアログの作成と表示
    public void show(File fileDirectory) {
        // タイトル
        String strTitle = fileDirectory.getAbsolutePath();

        // リストビュー
        ListView listview = new ListView( m_contextParent );
        listview.setScrollingCacheEnabled( false );
        listview.setOnItemClickListener((AdapterView.OnItemClickListener)this);
        // ファイルリスト
        File[]         aFile        = fileDirectory.listFiles();
        List<FileInfo> listFileInfo = new ArrayList<>();
        if (null != aFile) {
            for (File fileTemp : aFile) {
                listFileInfo.add( new FileInfo( fileTemp.getName(), fileTemp ) );
            }
            Collections.sort( listFileInfo );
        }
        // 親フォルダに戻るパスの追加
        if(null != fileDirectory.getParent()) {
            listFileInfo.add(0, new FileInfo( "..", new File( fileDirectory.getParent())));
        }
        m_fileinfoarrayadapter = new FileInfoArrayAdapter(m_contextParent, listFileInfo);
        listview.setAdapter(m_fileinfoarrayadapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(m_contextParent);
        builder.setTitle(strTitle);
        builder.setNegativeButton("Cancel", null);
        builder.setView(listview);
        m_dialog = builder.show();
    }

    // ListView内の項目をクリックしたときの処理
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != m_dialog) {
            m_dialog.dismiss();
            m_dialog = null;
        }

        FileInfo fileinfo = m_fileinfoarrayadapter.getItem(position);

        if (fileinfo.getFile().isDirectory()) {
            show(fileinfo.getFile());
        } else {
            // ファイルが選ばれた：リスナーのハンドラを呼び出す
            m_listener.onFileSelect(fileinfo.getFile());
        }
    }

    // 選択したファイルの情報を取り出すためのリスナーインターフェース
    public interface OnFileSelectListener {
        // ファイルが選択されたときに呼び出される関数
        void onFileSelect(File file);
    }
}
