package com.finnvocab.app;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class WordDao_Impl implements WordDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Word> __insertionAdapterOfWord;

  private final EntityDeletionOrUpdateAdapter<Word> __deletionAdapterOfWord;

  private final EntityDeletionOrUpdateAdapter<Word> __updateAdapterOfWord;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllWords;

  public WordDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWord = new EntityInsertionAdapter<Word>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `words` (`id`,`finnish`,`english`,`category`,`sentence`,`sentenceEnglish`,`mastery`,`isFavorite`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Word entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getFinnish());
        statement.bindString(3, entity.getEnglish());
        statement.bindString(4, entity.getCategory());
        statement.bindString(5, entity.getSentence());
        statement.bindString(6, entity.getSentenceEnglish());
        statement.bindLong(7, entity.getMastery());
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(8, _tmp);
      }
    };
    this.__deletionAdapterOfWord = new EntityDeletionOrUpdateAdapter<Word>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `words` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Word entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfWord = new EntityDeletionOrUpdateAdapter<Word>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `words` SET `id` = ?,`finnish` = ?,`english` = ?,`category` = ?,`sentence` = ?,`sentenceEnglish` = ?,`mastery` = ?,`isFavorite` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Word entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getFinnish());
        statement.bindString(3, entity.getEnglish());
        statement.bindString(4, entity.getCategory());
        statement.bindString(5, entity.getSentence());
        statement.bindString(6, entity.getSentenceEnglish());
        statement.bindLong(7, entity.getMastery());
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindLong(9, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteAllWords = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM words";
        return _query;
      }
    };
  }

  @Override
  public Object insertWord(final Word word, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfWord.insert(word);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteWord(final Word word, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfWord.handle(word);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateWord(final Word word, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfWord.handle(word);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllWords(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllWords.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAllWords.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Word>> getAllWords() {
    final String _sql = "SELECT * FROM words ORDER BY category, finnish";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"words"}, new Callable<List<Word>>() {
      @Override
      @NonNull
      public List<Word> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfFinnish = CursorUtil.getColumnIndexOrThrow(_cursor, "finnish");
          final int _cursorIndexOfEnglish = CursorUtil.getColumnIndexOrThrow(_cursor, "english");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfSentence = CursorUtil.getColumnIndexOrThrow(_cursor, "sentence");
          final int _cursorIndexOfSentenceEnglish = CursorUtil.getColumnIndexOrThrow(_cursor, "sentenceEnglish");
          final int _cursorIndexOfMastery = CursorUtil.getColumnIndexOrThrow(_cursor, "mastery");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final List<Word> _result = new ArrayList<Word>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Word _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpFinnish;
            _tmpFinnish = _cursor.getString(_cursorIndexOfFinnish);
            final String _tmpEnglish;
            _tmpEnglish = _cursor.getString(_cursorIndexOfEnglish);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpSentence;
            _tmpSentence = _cursor.getString(_cursorIndexOfSentence);
            final String _tmpSentenceEnglish;
            _tmpSentenceEnglish = _cursor.getString(_cursorIndexOfSentenceEnglish);
            final int _tmpMastery;
            _tmpMastery = _cursor.getInt(_cursorIndexOfMastery);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            _item = new Word(_tmpId,_tmpFinnish,_tmpEnglish,_tmpCategory,_tmpSentence,_tmpSentenceEnglish,_tmpMastery,_tmpIsFavorite);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Word>> getWordsByCategory(final String category) {
    final String _sql = "SELECT * FROM words WHERE category = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, category);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"words"}, new Callable<List<Word>>() {
      @Override
      @NonNull
      public List<Word> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfFinnish = CursorUtil.getColumnIndexOrThrow(_cursor, "finnish");
          final int _cursorIndexOfEnglish = CursorUtil.getColumnIndexOrThrow(_cursor, "english");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfSentence = CursorUtil.getColumnIndexOrThrow(_cursor, "sentence");
          final int _cursorIndexOfSentenceEnglish = CursorUtil.getColumnIndexOrThrow(_cursor, "sentenceEnglish");
          final int _cursorIndexOfMastery = CursorUtil.getColumnIndexOrThrow(_cursor, "mastery");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final List<Word> _result = new ArrayList<Word>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Word _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpFinnish;
            _tmpFinnish = _cursor.getString(_cursorIndexOfFinnish);
            final String _tmpEnglish;
            _tmpEnglish = _cursor.getString(_cursorIndexOfEnglish);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpSentence;
            _tmpSentence = _cursor.getString(_cursorIndexOfSentence);
            final String _tmpSentenceEnglish;
            _tmpSentenceEnglish = _cursor.getString(_cursorIndexOfSentenceEnglish);
            final int _tmpMastery;
            _tmpMastery = _cursor.getInt(_cursorIndexOfMastery);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            _item = new Word(_tmpId,_tmpFinnish,_tmpEnglish,_tmpCategory,_tmpSentence,_tmpSentenceEnglish,_tmpMastery,_tmpIsFavorite);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<String>> getAllCategories() {
    final String _sql = "SELECT DISTINCT category FROM words ORDER BY category";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"words"}, new Callable<List<String>>() {
      @Override
      @NonNull
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final String _item;
            _item = _cursor.getString(0);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getWordById(final int wordId, final Continuation<? super Word> $completion) {
    final String _sql = "SELECT * FROM words WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, wordId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Word>() {
      @Override
      @Nullable
      public Word call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfFinnish = CursorUtil.getColumnIndexOrThrow(_cursor, "finnish");
          final int _cursorIndexOfEnglish = CursorUtil.getColumnIndexOrThrow(_cursor, "english");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfSentence = CursorUtil.getColumnIndexOrThrow(_cursor, "sentence");
          final int _cursorIndexOfSentenceEnglish = CursorUtil.getColumnIndexOrThrow(_cursor, "sentenceEnglish");
          final int _cursorIndexOfMastery = CursorUtil.getColumnIndexOrThrow(_cursor, "mastery");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final Word _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpFinnish;
            _tmpFinnish = _cursor.getString(_cursorIndexOfFinnish);
            final String _tmpEnglish;
            _tmpEnglish = _cursor.getString(_cursorIndexOfEnglish);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpSentence;
            _tmpSentence = _cursor.getString(_cursorIndexOfSentence);
            final String _tmpSentenceEnglish;
            _tmpSentenceEnglish = _cursor.getString(_cursorIndexOfSentenceEnglish);
            final int _tmpMastery;
            _tmpMastery = _cursor.getInt(_cursorIndexOfMastery);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            _result = new Word(_tmpId,_tmpFinnish,_tmpEnglish,_tmpCategory,_tmpSentence,_tmpSentenceEnglish,_tmpMastery,_tmpIsFavorite);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
