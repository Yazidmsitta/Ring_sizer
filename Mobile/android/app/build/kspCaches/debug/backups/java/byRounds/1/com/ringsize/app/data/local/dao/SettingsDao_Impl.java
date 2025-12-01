package com.ringsize.app.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.ringsize.app.data.local.entity.SettingsEntity;
import com.ringsize.app.data.model.Language;
import com.ringsize.app.data.model.PreferredUnit;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SettingsDao_Impl implements SettingsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SettingsEntity> __insertionAdapterOfSettingsEntity;

  private final EntityDeletionOrUpdateAdapter<SettingsEntity> __updateAdapterOfSettingsEntity;

  public SettingsDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSettingsEntity = new EntityInsertionAdapter<SettingsEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `settings` (`userId`,`preferredUnit`,`language`) VALUES (?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SettingsEntity entity) {
        statement.bindLong(1, entity.getUserId());
        statement.bindString(2, __PreferredUnit_enumToString(entity.getPreferredUnit()));
        statement.bindString(3, __Language_enumToString(entity.getLanguage()));
      }
    };
    this.__updateAdapterOfSettingsEntity = new EntityDeletionOrUpdateAdapter<SettingsEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `settings` SET `userId` = ?,`preferredUnit` = ?,`language` = ? WHERE `userId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SettingsEntity entity) {
        statement.bindLong(1, entity.getUserId());
        statement.bindString(2, __PreferredUnit_enumToString(entity.getPreferredUnit()));
        statement.bindString(3, __Language_enumToString(entity.getLanguage()));
        statement.bindLong(4, entity.getUserId());
      }
    };
  }

  @Override
  public Object insertSettings(final SettingsEntity settings,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSettingsEntity.insert(settings);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSettings(final SettingsEntity settings,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfSettingsEntity.handle(settings);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<SettingsEntity> getSettings(final long userId) {
    final String _sql = "SELECT * FROM settings WHERE userId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"settings"}, new Callable<SettingsEntity>() {
      @Override
      @Nullable
      public SettingsEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfPreferredUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "preferredUnit");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final SettingsEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            final PreferredUnit _tmpPreferredUnit;
            _tmpPreferredUnit = __PreferredUnit_stringToEnum(_cursor.getString(_cursorIndexOfPreferredUnit));
            final Language _tmpLanguage;
            _tmpLanguage = __Language_stringToEnum(_cursor.getString(_cursorIndexOfLanguage));
            _result = new SettingsEntity(_tmpUserId,_tmpPreferredUnit,_tmpLanguage);
          } else {
            _result = null;
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private String __PreferredUnit_enumToString(@NonNull final PreferredUnit _value) {
    switch (_value) {
      case MM: return "MM";
      case INCH: return "INCH";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private String __Language_enumToString(@NonNull final Language _value) {
    switch (_value) {
      case FR: return "FR";
      case EN: return "EN";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private PreferredUnit __PreferredUnit_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "MM": return PreferredUnit.MM;
      case "INCH": return PreferredUnit.INCH;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }

  private Language __Language_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "FR": return Language.FR;
      case "EN": return Language.EN;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}
