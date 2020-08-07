package com.example.datemodi;

import java.io.File;

/*
        FileInfo: フォルダ内の各要素を扱う情報クラス
     */
static public class FileInfo implements Comparable<FileInfo> {
    private String m_strName;    // 表示名
    private File m_file;    // ファイルオブジェクト

    // コンストラクタ
    public FileInfo(String strName, File file ) {
        m_strName = strName;
        m_file = file;
    }

    public String getName() {
        return m_strName;
    }
    public File getFile() {
        return m_file;
    }

    // 比較
    public int compareTo(FileInfo another) {
        // ディレクトリ < ファイル の順
        if (m_file.isDirectory() && !another.getFile().isDirectory()) {
            return -1;
        }
        if (!m_file.isDirectory() && another.getFile().isDirectory()) {
            return 1;
        }

        // ファイル同士、ディレクトリ同士の場合は、ファイル名（ディレクトリ名）の大文字小文字区別しない辞書順
        return m_file.getName().toLowerCase().compareTo(
                another.getFile().getName().toLowerCase()
        );
    }
}

