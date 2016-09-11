	//-----------------------------------
	//----Хранение данных. SQLite--------
	//-----------------------------------

	//Пара полей для ввода и кнопки добавления записи, вывода существующих записей и очистки таблицы.
	//Нарисуем экран для ввода записей и очистки таблицы. Открываем main.xml и пишем:
	<?xml version="1.0" encoding="utf-8"?>
	<LinearLayout
	 xmlns:android="http://schemas.android.com/apk/res/android"
	 android:layout_width="fill_parent"
	 android:layout_height="fill_parent"
	 android:orientation="vertical">
	<LinearLayout
	 android:id="@+id/linearLayout1"
	 android:layout_width="match_parent"
	 android:layout_height="wrap_content">
	<TextView
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:text="Name"
	 android:layout_marginLeft="5dp"
	 android:layout_marginRight="5dp">
	</TextView>
	<EditText
	 android:id="@+id/etName"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:layout_weight="1">
	<requestFocus>
	</requestFocus>
	</EditText>
	</LinearLayout>
	<LinearLayout
	 android:id="@+id/linearLayout3"
	 android:layout_width="match_parent"
	 android:layout_height="wrap_content">
	<TextView
	 android:id="@+id/textView2"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:text="Email"
	 android:layout_marginLeft="5dp"
	 android:layout_marginRight="5dp">
	</TextView>
	<EditText
	 android:id="@+id/etEmail"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:layout_weight="1">
	</EditText>
	</LinearLayout>
	<LinearLayout
	 android:id="@+id/linearLayout2"
	 android:layout_width="match_parent"
	 android:layout_height="wrap_content">
	<Button
	 android:id="@+id/btnAdd"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:text="Add">
	</Button>
	<Button
	 android:id="@+id/btnRead"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:text="Read">
	</Button>
	<Button
	 android:id="@+id/btnClear"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:text="Clear">
	</Button>
	</LinearLayout>
	</LinearLayout>

	//Открываем MainActivity.java и пишем:
	package ru.startandroid.develop.p0341simplesqlite;

	import android.app.Activity;
	import android.content.ContentValues;
	import android.content.Context;
	import android.database.Cursor;
	import android.database.sqlite.SQLiteDatabase;
	import android.database.sqlite.SQLiteOpenHelper;
	import android.os.Bundle;
	import android.util.Log;
	import android.view.View;
	import android.view.View.OnClickListener;
	import android.widget.Button;
	import android.widget.EditText;

	public class MainActivity extends Activity implements OnClickListener {

	  final String LOG_TAG = "myLogs";

	  Button btnAdd, btnRead, btnClear;
	  EditText etName, etEmail;

	  DBHelper dbHelper;

	  /** Called when the activity is first created. */
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btnAdd = (Button) findViewById(R.id.btnAdd);
		btnAdd.setOnClickListener(this);

		btnRead = (Button) findViewById(R.id.btnRead);
		btnRead.setOnClickListener(this);

		btnClear = (Button) findViewById(R.id.btnClear);
		btnClear.setOnClickListener(this);

		etName = (EditText) findViewById(R.id.etName);
		etEmail = (EditText) findViewById(R.id.etEmail);
		
		// создаем объект для создания и управления версиями БД
		dbHelper = new DBHelper(this);
	  }


	  @Override
	  public void onClick(View v) {
		
		// создаем объект для данных
		ContentValues cv = new ContentValues();
		
		// получаем данные из полей ввода
		String name = etName.getText().toString();
		String email = etEmail.getText().toString();

		// подключаемся к БД
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		

		switch (v.getId()) {
		case R.id.btnAdd:
		  Log.d(LOG_TAG, "--- Insert in mytable: ---");
		  // подготовим данные для вставки в виде пар: наименование столбца - значение
		  
		  cv.put("name", name);
		  cv.put("email", email);
		  // вставляем запись и получаем ее ID
		  long rowID = db.insert("mytable", null, cv);
		  Log.d(LOG_TAG, "row inserted, ID = " + rowID);
		  break;
		case R.id.btnRead:
		  Log.d(LOG_TAG, "--- Rows in mytable: ---");
		  // делаем запрос всех данных из таблицы mytable, получаем Cursor 
		  Cursor c = db.query("mytable", null, null, null, null, null, null);

		  // ставим позицию курсора на первую строку выборки
		  // если в выборке нет строк, вернется false
		  if (c.moveToFirst()) {

			// определяем номера столбцов по имени в выборке
			int idColIndex = c.getColumnIndex("id");
			int nameColIndex = c.getColumnIndex("name");
			int emailColIndex = c.getColumnIndex("email");

			do {
			  // получаем значения по номерам столбцов и пишем все в лог
			  Log.d(LOG_TAG,
				  "ID = " + c.getInt(idColIndex) + 
				  ", name = " + c.getString(nameColIndex) + 
				  ", email = " + c.getString(emailColIndex));
			  // переход на следующую строку 
			  // а если следующей нет (текущая - последняя), то false - выходим из цикла
			} while (c.moveToNext());
		  } else
			Log.d(LOG_TAG, "0 rows");
		  c.close();
		  break;
		case R.id.btnClear:
		  Log.d(LOG_TAG, "--- Clear mytable: ---");
		  // удаляем все записи
		  int clearCount = db.delete("mytable", null, null);
		  Log.d(LOG_TAG, "deleted rows count = " + clearCount);
		  break;
		}
		// закрываем подключение к БД
		dbHelper.close();
	  }

	  class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
		  // конструктор суперкласса
		  super(context, "myDB", null, 1);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
		  Log.d(LOG_TAG, "--- onCreate database ---");
		  // создаем таблицу с полями
		  db.execSQL("create table mytable ("
			  + "id integer primary key autoincrement," 
			  + "name text,"
			  + "email text" + ");");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}
	  }

	}


	//-------------------------------------------------------
	//---Метод query. Условие, сортировка, группировка-------
	//-------------------------------------------------------

	//Открываем layout-файл main.xml и пишем:
	<?xml version="1.0" encoding="utf-8"?>
	<LinearLayout
	 xmlns:android="http://schemas.android.com/apk/res/android"
	 android:layout_width="fill_parent"
	 android:layout_height="fill_parent"
	 android:orientation="vertical">
	<TextView
	 android:layout_width="fill_parent"
	 android:layout_height="wrap_content"
	 android:text="Справочник стран"
	 android:textSize="14sp"
	 android:gravity="center_horizontal"
	 android:layout_marginBottom="5dp"
	 android:layout_marginTop="5dp">
	</TextView>
	<Button
	 android:id="@+id/btnAll"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:text="Все записи"
	 android:layout_marginTop="5dp">
	</Button>
	<LinearLayout
	 android:id="@+id/linearLayout1"
	 android:layout_width="match_parent"
	 android:layout_height="wrap_content"
	 android:layout_marginTop="5dp">
	<Button
	 android:id="@+id/btnFunc"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:text="Функция">
	</Button>
	<EditText
	 android:id="@+id/etFunc"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:layout_weight="1">
	<requestFocus>
	</requestFocus>
	</EditText>
	</LinearLayout>
	<LinearLayout
	 android:id="@+id/linearLayout2"
	 android:layout_width="match_parent"
	 android:layout_height="wrap_content"
	 android:layout_marginTop="5dp">
	<Button
	 android:id="@+id/btnPeople"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:text="Население >">
	</Button>
	<EditText
	 android:id="@+id/etPeople"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:layout_weight="1"
	 android:inputType="number">
	</EditText>
	</LinearLayout>
	<Button
	 android:id="@+id/btnGroup"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:text="Население по региону"
	 android:layout_marginTop="5dp">
	</Button>
	<LinearLayout
	 android:id="@+id/linearLayout4"
	 android:layout_width="match_parent"
	 android:layout_height="wrap_content"
	 android:layout_marginTop="5dp">
	<Button
	 android:id="@+id/btnHaving"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:text="Население по региону >">
	</Button>
	<EditText
	 android:id="@+id/etRegionPeople"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:layout_weight="1"
	 android:inputType="number">
	</EditText>
	</LinearLayout>
	<LinearLayout
	 android:id="@+id/linearLayout3"
	 android:layout_width="match_parent"
	 android:layout_height="wrap_content"
	 android:layout_marginTop="5dp">
	<Button
	 android:id="@+id/btnSort"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:text="Сортировка">
	</Button>
	<RadioGroup
	 android:id="@+id/rgSort"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content">
	<RadioButton
	 android:id="@+id/rName"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:checked="true"
	 android:text="Наименование">
	</RadioButton>
	<RadioButton
	 android:id="@+id/rPeople"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:text="Население">
	</RadioButton>
	<RadioButton
	 android:id="@+id/rRegion"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:text="Регион">
	</RadioButton>
	</RadioGroup>
	</LinearLayout>
	</LinearLayout>

	//6 кнопок – 6 функций, которые мы планируем реализовать. Поля для ввода значений, где это необходимо. Для сортировки используем RadioGroup.

	//Код для MainActivity.java:
	package ru.startandroid.develop.p0361sqlitequery;

	import android.app.Activity;
	import android.content.ContentValues;
	import android.content.Context;
	import android.database.Cursor;
	import android.database.sqlite.SQLiteDatabase;
	import android.database.sqlite.SQLiteOpenHelper;
	import android.os.Bundle;
	import android.util.Log;
	import android.view.View;
	import android.view.View.OnClickListener;
	import android.widget.Button;
	import android.widget.EditText;
	import android.widget.RadioGroup;

	public class MainActivity extends Activity implements OnClickListener {

	  final String LOG_TAG = "myLogs";

	  String name[] = { "Китай", "США", "Бразилия", "Россия", "Япония",
		  "Германия", "Египет", "Италия", "Франция", "Канада" };
	  int people[] = { 1400, 311, 195, 142, 128, 82, 80, 60, 66, 35 };
	  String region[] = { "Азия", "Америка", "Америка", "Европа", "Азия",
		  "Европа", "Африка", "Европа", "Европа", "Америка" };

	  Button btnAll, btnFunc, btnPeople, btnSort, btnGroup, btnHaving;
	  EditText etFunc, etPeople, etRegionPeople;
	  RadioGroup rgSort;

	  DBHelper dbHelper;
	  SQLiteDatabase db;

	  /** Called when the activity is first created. */

	  public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btnAll = (Button) findViewById(R.id.btnAll);
		btnAll.setOnClickListener(this);

		btnFunc = (Button) findViewById(R.id.btnFunc);
		btnFunc.setOnClickListener(this);

		btnPeople = (Button) findViewById(R.id.btnPeople);
		btnPeople.setOnClickListener(this);

		btnSort = (Button) findViewById(R.id.btnSort);
		btnSort.setOnClickListener(this);

		btnGroup = (Button) findViewById(R.id.btnGroup);
		btnGroup.setOnClickListener(this);

		btnHaving = (Button) findViewById(R.id.btnHaving);
		btnHaving.setOnClickListener(this);

		etFunc = (EditText) findViewById(R.id.etFunc);
		etPeople = (EditText) findViewById(R.id.etPeople);
		etRegionPeople = (EditText) findViewById(R.id.etRegionPeople);

		rgSort = (RadioGroup) findViewById(R.id.rgSort);

		dbHelper = new DBHelper(this);
		// подключаемся к базе
		db = dbHelper.getWritableDatabase();

		// проверка существования записей
		Cursor c = db.query("mytable", null, null, null, null, null, null);
		if (c.getCount() == 0) {
		  ContentValues cv = new ContentValues();
		  // заполним таблицу
		  for (int i = 0; i < 10; i++) {
			cv.put("name", name[i]);
			cv.put("people", people[i]);
			cv.put("region", region[i]);
			Log.d(LOG_TAG, "id = " + db.insert("mytable", null, cv));
		  }
		}
		c.close();
		dbHelper.close();
		// эмулируем нажатие кнопки btnAll
		onClick(btnAll);

	  }

	  public void onClick(View v) {

		// подключаемся к базе
		db = dbHelper.getWritableDatabase();

		// данные с экрана
		String sFunc = etFunc.getText().toString();
		String sPeople = etPeople.getText().toString();
		String sRegionPeople = etRegionPeople.getText().toString();

		// переменные для query
		String[] columns = null;
		String selection = null;
		String[] selectionArgs = null;
		String groupBy = null;
		String having = null;
		String orderBy = null;

		// курсор
		Cursor c = null;

		// определяем нажатую кнопку
		switch (v.getId()) {
		// Все записи
		case R.id.btnAll:
		  Log.d(LOG_TAG, "--- Все записи ---");
		  c = db.query("mytable", null, null, null, null, null, null);
		  break;
		// Функция
		case R.id.btnFunc:
		  Log.d(LOG_TAG, "--- Функция " + sFunc + " ---");
		  columns = new String[] { sFunc };
		  c = db.query("mytable", columns, null, null, null, null, null);
		  break;
		// Население больше, чем
		case R.id.btnPeople:
		  Log.d(LOG_TAG, "--- Население больше " + sPeople + " ---");
		  selection = "people > ?";
		  selectionArgs = new String[] { sPeople };
		  c = db.query("mytable", null, selection, selectionArgs, null, null,
			  null);
		  break;
		// Население по региону
		case R.id.btnGroup:
		  Log.d(LOG_TAG, "--- Население по региону ---");
		  columns = new String[] { "region", "sum(people) as people" };
		  groupBy = "region";
		  c = db.query("mytable", columns, null, null, groupBy, null, null);
		  break;
		// Население по региону больше чем
		case R.id.btnHaving:
		  Log.d(LOG_TAG, "--- Регионы с населением больше " + sRegionPeople
			  + " ---");
		  columns = new String[] { "region", "sum(people) as people" };
		  groupBy = "region";
		  having = "sum(people) > " + sRegionPeople;
		  c = db.query("mytable", columns, null, null, groupBy, having, null);
		  break;
		// Сортировка
		case R.id.btnSort:
		  // сортировка по
		  switch (rgSort.getCheckedRadioButtonId()) {
		  // наименование
		  case R.id.rName:
			Log.d(LOG_TAG, "--- Сортировка по наименованию ---");
			orderBy = "name";
			break;
		  // население
		  case R.id.rPeople:
			Log.d(LOG_TAG, "--- Сортировка по населению ---");
			orderBy = "people";
			break;
		  // регион
		  case R.id.rRegion:
			Log.d(LOG_TAG, "--- Сортировка по региону ---");
			orderBy = "region";
			break;
		  }
		  c = db.query("mytable", null, null, null, null, null, orderBy);
		  break;
		}

		if (c != null) {
		  if (c.moveToFirst()) {
			String str;
			do {
			  str = "";
			  for (String cn : c.getColumnNames()) {
				str = str.concat(cn + " = "
					+ c.getString(c.getColumnIndex(cn)) + "; ");
			  }
			  Log.d(LOG_TAG, str);

			} while (c.moveToNext());
		  }
		  c.close();
		} else
		  Log.d(LOG_TAG, "Cursor is null");

		dbHelper.close();
	  }

	  class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
		  // конструктор суперкласса
		  super(context, "myDB", null, 1);
		}

		public void onCreate(SQLiteDatabase db) {
		  Log.d(LOG_TAG, "--- onCreate database ---");
		  // создаем таблицу с полями
		  db.execSQL("create table mytable ("
			  + "id integer primary key autoincrement," + "name text,"
			  + "people integer," + "region text" + ");");
		}

		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}
	  }

	}

	//------------------------------------------------------------------
	//-Запросы из связанных таблиц. INNER JOIN в SQLite. Метод rawQuery-
	//------------------------------------------------------------------

	//Экран вообще использовать не будем, поэтому main.xml даже не трогаем. Открываем MainActivity.java и пишем код:
	package ru.startandroid.develop.p0371sqliteinnerjoin;

	import android.app.Activity;
	import android.content.ContentValues;
	import android.content.Context;
	import android.database.Cursor;
	import android.database.sqlite.SQLiteDatabase;
	import android.database.sqlite.SQLiteOpenHelper;
	import android.os.Bundle;
	import android.util.Log;

	public class MainActivity extends Activity {

	  final String LOG_TAG = "myLogs";

	  // данные для таблицы должностей
	  int[] position_id = { 1, 2, 3, 4 };
	  String[] position_name = { "Директор", "Программер", "Бухгалтер", "Охранник" };
	  int[] position_salary = { 15000, 13000, 10000, 8000 };

	  // данные для таблицы людей
	  String[] people_name = { "Иван", "Марья", "Петр", "Антон", "Даша", "Борис", "Костя", "Игорь" };
	  int[] people_posid = { 2, 3, 2, 2, 3, 1, 2, 4 };

	  /** Called when the activity is first created. */
	  public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Подключаемся к БД
		DBHelper dbh = new DBHelper(this);
		SQLiteDatabase db = dbh.getWritableDatabase();

		// Описание курсора
		Cursor c;

		// выводим в лог данные по должностям
		Log.d(LOG_TAG, "--- Table position ---");
		c = db.query("position", null, null, null, null, null, null);
		logCursor(c);
		c.close();
		Log.d(LOG_TAG, "--- ---");

		// выводим в лог данные по людям
		Log.d(LOG_TAG, "--- Table people ---");
		c = db.query("people", null, null, null, null, null, null);
		logCursor(c);
		c.close();
		Log.d(LOG_TAG, "--- ---");

		// выводим результат объединения
		// используем rawQuery
		Log.d(LOG_TAG, "--- INNER JOIN with rawQuery---");
		String sqlQuery = "select PL.name as Name, PS.name as Position, salary as Salary "
			+ "from people as PL "
			+ "inner join position as PS "
			+ "on PL.posid = PS.id " 
			+ "where salary > ?";
		c = db.rawQuery(sqlQuery, new String[] {"12000"});
		logCursor(c);
		c.close();
		Log.d(LOG_TAG, "--- ---");

		// выводим результат объединения
		// используем query
		Log.d(LOG_TAG, "--- INNER JOIN with query---");
		String table = "people as PL inner join position as PS on PL.posid = PS.id";
		String columns[] = { "PL.name as Name", "PS.name as Position", "salary as Salary" };
		String selection = "salary < ?";
		String[] selectionArgs = {"12000"};
		c = db.query(table, columns, selection, selectionArgs, null, null, null);
		logCursor(c);
		c.close();
		Log.d(LOG_TAG, "--- ---");
		
		// закрываем БД
		dbh.close();
	  }

	  // вывод в лог данных из курсора
	  void logCursor(Cursor c) {
		if (c != null) {
		  if (c.moveToFirst()) {
			String str;
			do {
			  str = "";
			  for (String cn : c.getColumnNames()) {
				str = str.concat(cn + " = " + c.getString(c.getColumnIndex(cn)) + "; ");
			  }
			  Log.d(LOG_TAG, str);
			} while (c.moveToNext());
		  }
		} else
		  Log.d(LOG_TAG, "Cursor is null");
	  }

	  // класс для работы с БД
	  class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
		  super(context, "myDB", null, 1);
		}

		public void onCreate(SQLiteDatabase db) {
		  Log.d(LOG_TAG, "--- onCreate database ---");

		  ContentValues cv = new ContentValues();

		  // создаем таблицу должностей
		  db.execSQL("create table position (" 
			  + "id integer primary key,"
			  + "name text," + "salary integer" 
			  + ");");

		  // заполняем ее
		  for (int i = 0; i < position_id.length; i++) {
			cv.clear();
			cv.put("id", position_id[i]);
			cv.put("name", position_name[i]);
			cv.put("salary", position_salary[i]);
			db.insert("position", null, cv);
		  }

		  // создаем таблицу людей
		  db.execSQL("create table people ("
			  + "id integer primary key autoincrement," 
			  + "name text,"
			  + "posid integer" 
			  + ");");

		  // заполняем ее
		  for (int i = 0; i < people_name.length; i++) {
			cv.clear();
			cv.put("name", people_name[i]);
			cv.put("posid", people_posid[i]);
			db.insert("people", null, cv);
		  }
		}

		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}
	  }

	}

	//------------------------------------------------------------------
	//-----------------------Транзакции в SQLite------------------------
	//------------------------------------------------------------------

	//Открываем MainActivity.java и пишем:
	package ru.startandroid.develop.p0381sqlitetransaction;

	import android.app.Activity;
	import android.content.ContentValues;
	import android.content.Context;
	import android.database.Cursor;
	import android.database.sqlite.SQLiteDatabase;
	import android.database.sqlite.SQLiteOpenHelper;
	import android.os.Bundle;
	import android.util.Log;

	public class MainActivity extends Activity {

	  final String LOG_TAG = "myLogs";

	  DBHelper dbh;
	  SQLiteDatabase db;

	  /** Called when the activity is first created. */
	  public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Log.d(LOG_TAG, "--- onCreate Activity ---");
		dbh = new DBHelper(this);
		myActions();
	  }

	  void myActions() {
		db = dbh.getWritableDatabase();
		delete(db, "mytable");
		insert(db, "mytable", "val1");
		read(db, "mytable");
		dbh.close();
	  }

	  void insert(SQLiteDatabase db, String table, String value) {
		Log.d(LOG_TAG, "Insert in table " + table + " value = " + value);
		ContentValues cv = new ContentValues();
		cv.put("val", value);
		db.insert(table, null, cv);
	  }

	  void read(SQLiteDatabase db, String table) {
		Log.d(LOG_TAG, "Read table " + table);
		Cursor c = db.query(table, null, null, null, null, null, null);
		if (c != null) {
		  Log.d(LOG_TAG, "Records count = " + c.getCount());
		  if (c.moveToFirst()) {
			do {
			  Log.d(LOG_TAG, c.getString(c.getColumnIndex("val")));
			} while (c.moveToNext());
		  }
		  c.close();
		}
	  }

	  void delete(SQLiteDatabase db, String table) {
		Log.d(LOG_TAG, "Delete all from table " + table);
		db.delete(table, null, null);
	  }

	  // класс для работы с БД
	  class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
		  super(context, "myDB", null, 1);
		}

		public void onCreate(SQLiteDatabase db) {
		  Log.d(LOG_TAG, "--- onCreate database ---");

		  db.execSQL("create table mytable ("
			  + "id integer primary key autoincrement," 
			  + "val text"
			  + ");");
		}

		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}
	  }

	}
	//смотрим лекцию, чтобы понять причины постоянного исправления кода в myActions
	/*
	поправляем код 
	void myActions() {
		db = dbh.getWritableDatabase();
		delete(db, "mytable");
		db.beginTransaction();
		insert(db, "mytable", "val1");
		db.endTransaction();
		insert(db, "mytable", "val2");
		read(db, "mytable");
		dbh.close();
	  }
	*/

	/*
	снова поправляем код
	void myActions() {
		db = dbh.getWritableDatabase();
		delete(db, "mytable");
		db.beginTransaction();
		insert(db, "mytable", "val1");
		db.setTransactionSuccessful();
		insert(db, "mytable", "val2");
		db.endTransaction();
		insert(db, "mytable", "val3");
		read(db, "mytable");
		dbh.close();
	  }
	*/

	/*
	снова перепишем
	void myActions() {
		try {
		  db = dbh.getWritableDatabase();
		  delete(db, "mytable");

		  db.beginTransaction();
		  insert(db, "mytable", "val1");

		  Log.d(LOG_TAG, "create DBHelper");
		  DBHelper dbh2 = new DBHelper(this);
		  Log.d(LOG_TAG, "get db");
		  SQLiteDatabase db2 = dbh2.getWritableDatabase();
		  read(db2, "mytable");
		  dbh2.close();

		  db.setTransactionSuccessful();
		  db.endTransaction();

		  read(db, "mytable");
		  dbh.close();

		} catch (Exception ex) {
		  Log.d(LOG_TAG, ex.getClass() + " error: " + ex.getMessage());
		}
	  }
	*/

	//------------------------------------------------------------------
	//---------------Обновление базы данных в SQLite--------------------
	//------------------------------------------------------------------

	//Файл MainActivity.java
	package ru.startandroid.develop.p0391sqliteonupgradedb;

	import android.app.Activity;
	import android.content.ContentValues;
	import android.content.Context;
	import android.database.Cursor;
	import android.database.sqlite.SQLiteDatabase;
	import android.database.sqlite.SQLiteOpenHelper;
	import android.os.Bundle;
	import android.util.Log;

	public class MainActivity extends Activity {

	  final String LOG_TAG = "myLogs";

	  final String DB_NAME = "staff"; // имя БД
	  final int DB_VERSION = 1; // версия БД

	  /** Called when the activity is first created. */
	  @Override
	  public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		DBHelper dbh = new DBHelper(this);
		SQLiteDatabase db = dbh.getWritableDatabase();
		Log.d(LOG_TAG, " --- Staff db v." + db.getVersion() + " --- ");
		writeStaff(db);
		dbh.close();
	  }

	  // запрос данных и вывод в лог
	  private void writeStaff(SQLiteDatabase db) {
		Cursor c = db.rawQuery("select * from people", null);
		logCursor(c, "Table people");
		c.close();
	  }

	  // вывод в лог данных из курсора
	  void logCursor(Cursor c, String title) {
		if (c != null) {
		  if (c.moveToFirst()) {
			Log.d(LOG_TAG, title + ". " + c.getCount() + " rows");
			StringBuilder sb = new StringBuilder();
			do {
			  sb.setLength(0);
			  for (String cn : c.getColumnNames()) {
				sb.append(cn + " = "
					+ c.getString(c.getColumnIndex(cn)) + "; ");
			  }
			  Log.d(LOG_TAG, sb.toString());
			} while (c.moveToNext());
		  }
		} else
		  Log.d(LOG_TAG, title + ". Cursor is null");
	  }

	  // класс для работы с БД
	  class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
		  super(context, DB_NAME, null, DB_VERSION);
		}

		public void onCreate(SQLiteDatabase db) {
		  Log.d(LOG_TAG, " --- onCreate database --- ");

		  String[] people_name = { "Иван", "Марья", "Петр", "Антон", "Даша",
			  "Борис", "Костя", "Игорь" };
		  String[] people_positions = { "Программер", "Бухгалтер",
			  "Программер", "Программер", "Бухгалтер", "Директор",
			  "Программер", "Охранник" };

		  ContentValues cv = new ContentValues();

		  // создаем таблицу людей
		  db.execSQL("create table people ("
			  + "id integer primary key autoincrement,"
			  + "name text, position text);");

		  // заполняем ее
		  for (int i = 0; i < people_name.length; i++) {
			cv.clear();
			cv.put("name", people_name[i]);
			cv.put("position", people_positions[i]);
			db.insert("people", null, cv);
		  }
		}

		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}
	  }
	}

	//Давайте менять MainActivity.java. Наше приложение теперь будет ориентировано на БД версии 2. Укажем это, изменив значение константы DB_VERSION на 2:
	  final int DB_VERSION = 2; // версия БД

	//Метод writeStaff перепишем таким образом:
	  private void writeStaff(SQLiteDatabase db) {
		  Cursor c = db.rawQuery("select * from people", null);
		  logCursor(c, "Table people");
		  c.close();
		  
		  c = db.rawQuery("select * from position", null);
		  logCursor(c, "Table position");
		  c.close();
		  
		  String sqlQuery = "select PL.name as Name, PS.name as Position, salary as Salary "
			+ "from people as PL "
			+ "inner join position as PS "
			+ "on PL.posid = PS.id ";
		  c = db.rawQuery(sqlQuery, null);
		  logCursor(c, "inner join");
		  c.close();
		}

	//Будем выводить в лог данные из таблиц people, position и их объединения.

	//Реализуем метод обновления - onUpgrade в DBHelper:
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		  Log.d(LOG_TAG, " --- onUpgrade database from " + oldVersion
			  + " to " + newVersion + " version --- ");

		  if (oldVersion == 1 && newVersion == 2) {

			ContentValues cv = new ContentValues();

			// данные для таблицы должностей
			int[] position_id = { 1, 2, 3, 4 };
			String[] position_name = { "Директор", "Программер",
				"Бухгалтер", "Охранник" };
			int[] position_salary = { 15000, 13000, 10000, 8000 };

			db.beginTransaction();
			try {
			  // создаем таблицу должностей
			  db.execSQL("create table position ("
				  + "id integer primary key,"
				  + "name text, salary integer);");

			  // заполняем ее
			  for (int i = 0; i < position_id.length; i++) {
				cv.clear();
				cv.put("id", position_id[i]);
				cv.put("name", position_name[i]);
				cv.put("salary", position_salary[i]);
				db.insert("position", null, cv);
			  }

			  db.execSQL("alter table people add column posid integer;");

			  for (int i = 0; i < position_id.length; i++) {
				cv.clear();
				cv.put("posid", position_id[i]);
				db.update("people", cv, "position = ?",
					new String[] { position_name[i] });
			  }

			  db.execSQL("create temporary table people_tmp ("
				  + "id integer, name text, position text, posid integer);");

			  db.execSQL("insert into people_tmp select id, name, position, posid from people;");
			  db.execSQL("drop table people;");

			  db.execSQL("create table people ("
				  + "id integer primary key autoincrement,"
				  + "name text, posid integer);");

			  db.execSQL("insert into people select id, name, posid from people_tmp;");
			  db.execSQL("drop table people_tmp;");

			  db.setTransactionSuccessful();
			} finally {
			  db.endTransaction();
			}
		  }
		}

	//Пишем onCreate в DBHelper:
		public void onCreate(SQLiteDatabase db) {
		  Log.d(LOG_TAG, " --- onCreate database --- ");

		  String[] people_name = { "Иван", "Марья", "Петр", "Антон", "Даша",
			  "Борис", "Костя", "Игорь" };
		  int[] people_posid = { 2, 3, 2, 2, 3, 1, 2, 4 };

		  // данные для таблицы должностей
		  int[] position_id = { 1, 2, 3, 4 };
		  String[] position_name = { "Директор", "Программер", "Бухгалтер",
			  "Охранник" };
		  int[] position_salary = { 15000, 13000, 10000, 8000 };

		  ContentValues cv = new ContentValues();

		  // создаем таблицу должностей
		  db.execSQL("create table position (" + "id integer primary key,"
			  + "name text, salary integer" + ");");

		  // заполняем ее
		  for (int i = 0; i < position_id.length; i++) {
			cv.clear();
			cv.put("id", position_id[i]);
			cv.put("name", position_name[i]);
			cv.put("salary", position_salary[i]);
			db.insert("position", null, cv);
		  }

		  // создаем таблицу людей
		  db.execSQL("create table people ("
			  + "id integer primary key autoincrement,"
			  + "name text, posid integer);");

		  // заполняем ее
		  for (int i = 0; i < people_name.length; i++) {
			cv.clear();
			cv.put("name", people_name[i]);
			cv.put("posid", people_posid[i]);
			db.insert("people", null, cv);
		  }
		}

	//Теперь можно все сохранить и запустить приложение.