package com.ringsize.app.data.local.dao;

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
import com.ringsize.app.data.local.entity.MeasurementEntity;
import com.ringsize.app.data.model.MeasurementType;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.IllegalArgumentException;
import java.lang.Long;
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
public final class MeasurementDao_Impl implements MeasurementDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MeasurementEntity> __insertionAdapterOfMeasurementEntity;

  private final EntityDeletionOrUpdateAdapter<MeasurementEntity> __deletionAdapterOfMeasurementEntity;

  private final EntityDeletionOrUpdateAdapter<MeasurementEntity> __updateAdapterOfMeasurementEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteMeasurementById;

  public MeasurementDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMeasurementEntity = new EntityInsertionAdapter<MeasurementEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `measurements` (`id`,`userId`,`name`,`type`,`diameterMm`,`circumferenceMm`,`sizeEu`,`sizeUs`,`createdAt`,`lastSyncedAt`,`isSynced`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MeasurementEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getUserId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindLong(2, entity.getUserId());
        }
        statement.bindString(3, entity.getName());
        statement.bindString(4, __MeasurementType_enumToString(entity.getType()));
        if (entity.getDiameterMm() == null) {
          statement.bindNull(5);
        } else {
          statement.bindDouble(5, entity.getDiameterMm());
        }
        if (entity.getCircumferenceMm() == null) {
          statement.bindNull(6);
        } else {
          statement.bindDouble(6, entity.getCircumferenceMm());
        }
        if (entity.getSizeEu() == null) {
          statement.bindNull(7);
        } else {
          statement.bindDouble(7, entity.getSizeEu());
        }
        if (entity.getSizeUs() == null) {
          statement.bindNull(8);
        } else {
          statement.bindDouble(8, entity.getSizeUs());
        }
        statement.bindLong(9, entity.getCreatedAt());
        if (entity.getLastSyncedAt() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getLastSyncedAt());
        }
        final int _tmp = entity.isSynced() ? 1 : 0;
        statement.bindLong(11, _tmp);
      }
    };
    this.__deletionAdapterOfMeasurementEntity = new EntityDeletionOrUpdateAdapter<MeasurementEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `measurements` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MeasurementEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfMeasurementEntity = new EntityDeletionOrUpdateAdapter<MeasurementEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `measurements` SET `id` = ?,`userId` = ?,`name` = ?,`type` = ?,`diameterMm` = ?,`circumferenceMm` = ?,`sizeEu` = ?,`sizeUs` = ?,`createdAt` = ?,`lastSyncedAt` = ?,`isSynced` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MeasurementEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getUserId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindLong(2, entity.getUserId());
        }
        statement.bindString(3, entity.getName());
        statement.bindString(4, __MeasurementType_enumToString(entity.getType()));
        if (entity.getDiameterMm() == null) {
          statement.bindNull(5);
        } else {
          statement.bindDouble(5, entity.getDiameterMm());
        }
        if (entity.getCircumferenceMm() == null) {
          statement.bindNull(6);
        } else {
          statement.bindDouble(6, entity.getCircumferenceMm());
        }
        if (entity.getSizeEu() == null) {
          statement.bindNull(7);
        } else {
          statement.bindDouble(7, entity.getSizeEu());
        }
        if (entity.getSizeUs() == null) {
          statement.bindNull(8);
        } else {
          statement.bindDouble(8, entity.getSizeUs());
        }
        statement.bindLong(9, entity.getCreatedAt());
        if (entity.getLastSyncedAt() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getLastSyncedAt());
        }
        final int _tmp = entity.isSynced() ? 1 : 0;
        statement.bindLong(11, _tmp);
        statement.bindLong(12, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteMeasurementById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM measurements WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertMeasurement(final MeasurementEntity measurement,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfMeasurementEntity.insertAndReturnId(measurement);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteMeasurement(final MeasurementEntity measurement,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfMeasurementEntity.handle(measurement);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateMeasurement(final MeasurementEntity measurement,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfMeasurementEntity.handle(measurement);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteMeasurementById(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteMeasurementById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
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
          __preparedStmtOfDeleteMeasurementById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<MeasurementEntity>> getAllMeasurements() {
    final String _sql = "SELECT * FROM measurements ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"measurements"}, new Callable<List<MeasurementEntity>>() {
      @Override
      @NonNull
      public List<MeasurementEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfDiameterMm = CursorUtil.getColumnIndexOrThrow(_cursor, "diameterMm");
          final int _cursorIndexOfCircumferenceMm = CursorUtil.getColumnIndexOrThrow(_cursor, "circumferenceMm");
          final int _cursorIndexOfSizeEu = CursorUtil.getColumnIndexOrThrow(_cursor, "sizeEu");
          final int _cursorIndexOfSizeUs = CursorUtil.getColumnIndexOrThrow(_cursor, "sizeUs");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastSyncedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSyncedAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<MeasurementEntity> _result = new ArrayList<MeasurementEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MeasurementEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Long _tmpUserId;
            if (_cursor.isNull(_cursorIndexOfUserId)) {
              _tmpUserId = null;
            } else {
              _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            }
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final MeasurementType _tmpType;
            _tmpType = __MeasurementType_stringToEnum(_cursor.getString(_cursorIndexOfType));
            final Double _tmpDiameterMm;
            if (_cursor.isNull(_cursorIndexOfDiameterMm)) {
              _tmpDiameterMm = null;
            } else {
              _tmpDiameterMm = _cursor.getDouble(_cursorIndexOfDiameterMm);
            }
            final Double _tmpCircumferenceMm;
            if (_cursor.isNull(_cursorIndexOfCircumferenceMm)) {
              _tmpCircumferenceMm = null;
            } else {
              _tmpCircumferenceMm = _cursor.getDouble(_cursorIndexOfCircumferenceMm);
            }
            final Double _tmpSizeEu;
            if (_cursor.isNull(_cursorIndexOfSizeEu)) {
              _tmpSizeEu = null;
            } else {
              _tmpSizeEu = _cursor.getDouble(_cursorIndexOfSizeEu);
            }
            final Double _tmpSizeUs;
            if (_cursor.isNull(_cursorIndexOfSizeUs)) {
              _tmpSizeUs = null;
            } else {
              _tmpSizeUs = _cursor.getDouble(_cursorIndexOfSizeUs);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpLastSyncedAt;
            if (_cursor.isNull(_cursorIndexOfLastSyncedAt)) {
              _tmpLastSyncedAt = null;
            } else {
              _tmpLastSyncedAt = _cursor.getLong(_cursorIndexOfLastSyncedAt);
            }
            final boolean _tmpIsSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp != 0;
            _item = new MeasurementEntity(_tmpId,_tmpUserId,_tmpName,_tmpType,_tmpDiameterMm,_tmpCircumferenceMm,_tmpSizeEu,_tmpSizeUs,_tmpCreatedAt,_tmpLastSyncedAt,_tmpIsSynced);
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
  public Object getMeasurementById(final long id,
      final Continuation<? super MeasurementEntity> $completion) {
    final String _sql = "SELECT * FROM measurements WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<MeasurementEntity>() {
      @Override
      @Nullable
      public MeasurementEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfDiameterMm = CursorUtil.getColumnIndexOrThrow(_cursor, "diameterMm");
          final int _cursorIndexOfCircumferenceMm = CursorUtil.getColumnIndexOrThrow(_cursor, "circumferenceMm");
          final int _cursorIndexOfSizeEu = CursorUtil.getColumnIndexOrThrow(_cursor, "sizeEu");
          final int _cursorIndexOfSizeUs = CursorUtil.getColumnIndexOrThrow(_cursor, "sizeUs");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastSyncedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSyncedAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final MeasurementEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Long _tmpUserId;
            if (_cursor.isNull(_cursorIndexOfUserId)) {
              _tmpUserId = null;
            } else {
              _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            }
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final MeasurementType _tmpType;
            _tmpType = __MeasurementType_stringToEnum(_cursor.getString(_cursorIndexOfType));
            final Double _tmpDiameterMm;
            if (_cursor.isNull(_cursorIndexOfDiameterMm)) {
              _tmpDiameterMm = null;
            } else {
              _tmpDiameterMm = _cursor.getDouble(_cursorIndexOfDiameterMm);
            }
            final Double _tmpCircumferenceMm;
            if (_cursor.isNull(_cursorIndexOfCircumferenceMm)) {
              _tmpCircumferenceMm = null;
            } else {
              _tmpCircumferenceMm = _cursor.getDouble(_cursorIndexOfCircumferenceMm);
            }
            final Double _tmpSizeEu;
            if (_cursor.isNull(_cursorIndexOfSizeEu)) {
              _tmpSizeEu = null;
            } else {
              _tmpSizeEu = _cursor.getDouble(_cursorIndexOfSizeEu);
            }
            final Double _tmpSizeUs;
            if (_cursor.isNull(_cursorIndexOfSizeUs)) {
              _tmpSizeUs = null;
            } else {
              _tmpSizeUs = _cursor.getDouble(_cursorIndexOfSizeUs);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpLastSyncedAt;
            if (_cursor.isNull(_cursorIndexOfLastSyncedAt)) {
              _tmpLastSyncedAt = null;
            } else {
              _tmpLastSyncedAt = _cursor.getLong(_cursorIndexOfLastSyncedAt);
            }
            final boolean _tmpIsSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp != 0;
            _result = new MeasurementEntity(_tmpId,_tmpUserId,_tmpName,_tmpType,_tmpDiameterMm,_tmpCircumferenceMm,_tmpSizeEu,_tmpSizeUs,_tmpCreatedAt,_tmpLastSyncedAt,_tmpIsSynced);
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

  @Override
  public Object getUnsyncedMeasurements(
      final Continuation<? super List<MeasurementEntity>> $completion) {
    final String _sql = "SELECT * FROM measurements WHERE isSynced = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<MeasurementEntity>>() {
      @Override
      @NonNull
      public List<MeasurementEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfDiameterMm = CursorUtil.getColumnIndexOrThrow(_cursor, "diameterMm");
          final int _cursorIndexOfCircumferenceMm = CursorUtil.getColumnIndexOrThrow(_cursor, "circumferenceMm");
          final int _cursorIndexOfSizeEu = CursorUtil.getColumnIndexOrThrow(_cursor, "sizeEu");
          final int _cursorIndexOfSizeUs = CursorUtil.getColumnIndexOrThrow(_cursor, "sizeUs");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastSyncedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSyncedAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<MeasurementEntity> _result = new ArrayList<MeasurementEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MeasurementEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Long _tmpUserId;
            if (_cursor.isNull(_cursorIndexOfUserId)) {
              _tmpUserId = null;
            } else {
              _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            }
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final MeasurementType _tmpType;
            _tmpType = __MeasurementType_stringToEnum(_cursor.getString(_cursorIndexOfType));
            final Double _tmpDiameterMm;
            if (_cursor.isNull(_cursorIndexOfDiameterMm)) {
              _tmpDiameterMm = null;
            } else {
              _tmpDiameterMm = _cursor.getDouble(_cursorIndexOfDiameterMm);
            }
            final Double _tmpCircumferenceMm;
            if (_cursor.isNull(_cursorIndexOfCircumferenceMm)) {
              _tmpCircumferenceMm = null;
            } else {
              _tmpCircumferenceMm = _cursor.getDouble(_cursorIndexOfCircumferenceMm);
            }
            final Double _tmpSizeEu;
            if (_cursor.isNull(_cursorIndexOfSizeEu)) {
              _tmpSizeEu = null;
            } else {
              _tmpSizeEu = _cursor.getDouble(_cursorIndexOfSizeEu);
            }
            final Double _tmpSizeUs;
            if (_cursor.isNull(_cursorIndexOfSizeUs)) {
              _tmpSizeUs = null;
            } else {
              _tmpSizeUs = _cursor.getDouble(_cursorIndexOfSizeUs);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpLastSyncedAt;
            if (_cursor.isNull(_cursorIndexOfLastSyncedAt)) {
              _tmpLastSyncedAt = null;
            } else {
              _tmpLastSyncedAt = _cursor.getLong(_cursorIndexOfLastSyncedAt);
            }
            final boolean _tmpIsSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp != 0;
            _item = new MeasurementEntity(_tmpId,_tmpUserId,_tmpName,_tmpType,_tmpDiameterMm,_tmpCircumferenceMm,_tmpSizeEu,_tmpSizeUs,_tmpCreatedAt,_tmpLastSyncedAt,_tmpIsSynced);
            _result.add(_item);
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

  private String __MeasurementType_enumToString(@NonNull final MeasurementType _value) {
    switch (_value) {
      case RING: return "RING";
      case FINGER: return "FINGER";
      case BRACELET: return "BRACELET";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private MeasurementType __MeasurementType_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "RING": return MeasurementType.RING;
      case "FINGER": return MeasurementType.FINGER;
      case "BRACELET": return MeasurementType.BRACELET;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}
