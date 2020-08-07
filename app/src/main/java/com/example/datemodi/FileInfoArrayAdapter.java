package com.example.datemodi;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/*
   FileInfoArrayAdapter: ファイル/フォルダ情報を集約するクラス
 */
static public class FileInfoArrayAdapter extends BaseAdapter {
    private Context m_context;
    private List<FileInfo> m_listFileInfo; // ファイル情報リスト

    // コンストラクタ
    public FileInfoArrayAdapter(Context context, List<FileInfo> list) {
        super();
        m_context = context;
        m_listFileInfo = list;
    }

    @Override
    public int getCount() {
        return m_listFileInfo.size();
    }

    @Override
    public FileInfo getItem(int position) {
        return m_listFileInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView textviewFileName;
        TextView textviewFileSize;
    }

    // 一要素のビューの生成
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            // レイアウト: ファイル名、サイズをまとめる
            LinearLayout layout = new LinearLayout(m_context);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            // ファイル名テキスト
            TextView textviewFileName = new TextView(m_context);
            textviewFileName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
            layout.addView(textviewFileName, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            // ファイルサイズテキスト
            TextView textviewFileSize = new TextView(m_context);
            textviewFileSize.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            layout.addView(textviewFileSize, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            convertView = layout;
            viewHolder = new ViewHolder();
            viewHolder.textviewFileName = textviewFileName;
            viewHolder.textviewFileSize = textviewFileSize;
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        FileInfo fileinfo = m_listFileInfo.get(position);
        if (fileinfo.getFile().isDirectory()) {
            // ディレクトリの場合は、名前の後ろに「/」を付ける
            viewHolder.textviewFileName.setText(fileinfo.getName() + "/");
            viewHolder.textviewFileSize.setText("(directory)");
        } else { // ファイルの場合はサイズ
            viewHolder.textviewFileName.setText(fileinfo.getName());
            viewHolder.textviewFileSize.setText(String.valueOf(fileinfo.getFile().length() / 1024) + " [KB]");
        }

        return convertView;
    }
}

