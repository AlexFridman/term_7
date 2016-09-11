	//----------------------------------------
	//------------Акселерометр----------------
	//----------------------------------------

	//Файл main.xml:
	<?xml version="1.0" encoding="utf-8"?>
	<RelativeLayout
	 xmlns:android="http://schemas.android.com/apk/res/android"
	 xmlns:tools="http://schemas.android.com/tools"
	 android:layout_width="match_parent"
	 android:layout_height="match_parent">
	<TextView
	 android:id="@+id/tvText"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:textAppearance="?android:attr/textAppearanceLarge">
	</TextView>
	</RelativeLayout>

	//Файл MainActivity.java:
	package ru.startandroid.develop.p1372acceleration;

	import java.util.Timer;
	import java.util.TimerTask;

	import android.app.Activity;
	import android.hardware.Sensor;
	import android.hardware.SensorEvent;
	import android.hardware.SensorEventListener;
	import android.hardware.SensorManager;
	import android.os.Bundle;
	import android.widget.TextView;

	public class MainActivity extends Activity {

	  TextView tvText;
	  SensorManager sensorManager;
	  Sensor sensorAccel;
	  Sensor sensorLinAccel;
	  Sensor sensorGravity;

	  StringBuilder sb = new StringBuilder();

	  Timer timer;

	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		tvText = (TextView) findViewById(R.id.tvText);
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensorAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorLinAccel = sensorManager
			.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		sensorGravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

	  }

	  @Override
	  protected void onResume() {
		super.onResume();
		sensorManager.registerListener(listener, sensorAccel,
			SensorManager.SENSOR_DELAY_NORMAL);
		sensorManager.registerListener(listener, sensorLinAccel,
			SensorManager.SENSOR_DELAY_NORMAL);
		sensorManager.registerListener(listener, sensorGravity,
			SensorManager.SENSOR_DELAY_NORMAL);

		timer = new Timer();
		TimerTask task = new TimerTask() {
		  @Override
		  public void run() {
			runOnUiThread(new Runnable() {
			  @Override
			  public void run() {
				showInfo();
			  }
			});
		  }
		};
		timer.schedule(task, 0, 400);
	  }

	  @Override
	  protected void onPause() {
		super.onPause();
		sensorManager.unregisterListener(listener);
		timer.cancel();
	  }

	  String format(float values[]) {
		return String.format("%1$.1f\t\t%2$.1f\t\t%3$.1f", values[0], values[1],
			values[2]);
	  }

	  void showInfo() {
		sb.setLength(0);
		sb.append("Accelerometer: " + format(valuesAccel))
			.append("\n\nAccel motion: " + format(valuesAccelMotion))
			.append("\nAccel gravity : " + format(valuesAccelGravity))
			.append("\n\nLin accel : " + format(valuesLinAccel))
			.append("\nGravity : " + format(valuesGravity));
		tvText.setText(sb);
	  }

	  float[] valuesAccel = new float[3];
	  float[] valuesAccelMotion = new float[3];
	  float[] valuesAccelGravity = new float[3];
	  float[] valuesLinAccel = new float[3];
	  float[] valuesGravity = new float[3];

	  SensorEventListener listener = new SensorEventListener() {

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}

		@Override
		public void onSensorChanged(SensorEvent event) {
		  switch (event.sensor.getType()) {
		  case Sensor.TYPE_ACCELEROMETER:
			for (int i = 0; i < 3; i++) {
			  valuesAccel[i] = event.values[i];
			  valuesAccelGravity[i] = (float) (0.1 * event.values[i] + 0.9 * valuesAccelGravity[i]);
			  valuesAccelMotion[i] = event.values[i]
				  - valuesAccelGravity[i];
			}
			break;
		  case Sensor.TYPE_LINEAR_ACCELERATION:
			for (int i = 0; i < 3; i++) {
			  valuesLinAccel[i] = event.values[i];
			}
			break;
		  case Sensor.TYPE_GRAVITY:
			for (int i = 0; i < 3; i++) {
			  valuesGravity[i] = event.values[i];
			}
			break;
		  }

		}

	  };

	}


	//----------------------------------------
	//------------Ориентация------------------
	//----------------------------------------

	//Файл mail.xml:
	<?xml version="1.0" encoding="utf-8"?>
	<RelativeLayout
	 xmlns:android="http://schemas.android.com/apk/res/android"
	 xmlns:tools="http://schemas.android.com/tools"
	 android:layout_width="match_parent"
	 android:layout_height="match_parent">
	<TextView
	 android:id="@+id/tvText"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:textAppearance="?android:attr/textAppearanceLarge">
	</TextView>
	</RelativeLayout>

	//Файл MainActivity.java:
	package ru.startandroid.develop.p1373orientation;

	import java.util.Timer;
	import java.util.TimerTask;

	import android.app.Activity;
	import android.content.Context;
	import android.hardware.Sensor;
	import android.hardware.SensorEvent;
	import android.hardware.SensorEventListener;
	import android.hardware.SensorManager;
	import android.os.Bundle;
	import android.view.Display;
	import android.view.Surface;
	import android.view.WindowManager;
	import android.widget.TextView;

	public class MainActivity extends Activity {

	  TextView tvText;
	  SensorManager sensorManager;
	  Sensor sensorAccel;
	  Sensor sensorMagnet;
	  
	  StringBuilder sb = new StringBuilder();
	  
	  Timer timer;
	  
	  int rotation;
	  
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		tvText = (TextView) findViewById(R.id.tvText);
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensorAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorMagnet = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
	  }
	  
	  @Override
	  protected void onResume() {
		super.onResume();
		sensorManager.registerListener(listener, sensorAccel, SensorManager.SENSOR_DELAY_NORMAL);
		sensorManager.registerListener(listener, sensorMagnet, SensorManager.SENSOR_DELAY_NORMAL);
		
		timer = new Timer();
		TimerTask task = new TimerTask() {
		  @Override
		  public void run() {
			runOnUiThread(new Runnable() {
			  @Override
			  public void run() {
				getDeviceOrientation();
				getActualDeviceOrientation();
				showInfo();
			  }
			});
		  }
		};
		timer.schedule(task, 0, 400);
		
		WindowManager windowManager = ((WindowManager) getSystemService(Context.WINDOW_SERVICE));
		Display display = windowManager.getDefaultDisplay();
		rotation = display.getRotation();

	  }

	  @Override
	  protected void onPause() {
		super.onPause();
		sensorManager.unregisterListener(listener);
		timer.cancel();
	  }
	  
	  String format(float values[]) {
		return String.format("%1$.1f\t\t%2$.1f\t\t%3$.1f", values[0], values[1], values[2]);
	  }
	  
	  void showInfo() {
		sb.setLength(0);
		sb.append("Orientation : " + format(valuesResult))
		.append("\nOrientation 2: " + format(valuesResult2))
		;
		tvText.setText(sb);
	  }
	  
	  float[] r = new float[9];
	  
	  void getDeviceOrientation() {
		SensorManager.getRotationMatrix(r, null, valuesAccel, valuesMagnet);
		SensorManager.getOrientation(r, valuesResult);

		valuesResult[0] = (float) Math.toDegrees(valuesResult[0]); 
		 valuesResult[1] = (float) Math.toDegrees(valuesResult[1]);
		 valuesResult[2] = (float) Math.toDegrees(valuesResult[2]);
		return;
	  }
	  
	  float[] inR = new float[9];
	  float[] outR = new float[9];
	  
	  void getActualDeviceOrientation() {
		SensorManager.getRotationMatrix(inR, null, valuesAccel, valuesMagnet);
		int x_axis = SensorManager.AXIS_X;
		int y_axis = SensorManager.AXIS_Y;
		switch (rotation) {
		case (Surface.ROTATION_0): break;
		case (Surface.ROTATION_90):
		x_axis = SensorManager.AXIS_Y;
		y_axis = SensorManager.AXIS_MINUS_X;
		break;
		case (Surface.ROTATION_180):
		y_axis = SensorManager.AXIS_MINUS_Y;
		break;
		case (Surface.ROTATION_270):
		x_axis = SensorManager.AXIS_MINUS_Y;
		y_axis = SensorManager.AXIS_X;
		break;
		default: break;
		}
		SensorManager.remapCoordinateSystem(inR, x_axis, y_axis, outR);
		SensorManager.getOrientation(outR, valuesResult2);
		valuesResult2[0] = (float) Math.toDegrees(valuesResult2[0]); 
		valuesResult2[1] = (float) Math.toDegrees(valuesResult2[1]); 
		valuesResult2[2] = (float) Math.toDegrees(valuesResult2[2]); 
		return;
	  }  
	  
	  float[] valuesAccel = new float[3];
	  float[] valuesMagnet = new float[3];
	  float[] valuesResult = new float[3];
	  float[] valuesResult2 = new float[3];
	  
	  
	  SensorEventListener listener = new SensorEventListener() {

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}

		@Override
		public void onSensorChanged(SensorEvent event) {
		  switch (event.sensor.getType()) {
		  case Sensor.TYPE_ACCELEROMETER:
			for (int i=0; i < 3; i++){
			  valuesAccel[i] = event.values[i];
			}        
			break;
		  case Sensor.TYPE_MAGNETIC_FIELD:
			for (int i=0; i < 3; i++){
			  valuesMagnet[i] = event.values[i];
			}  
			break;
		  }
		}
	  };
	 
	}

	//----------------------------------------
	//------------Камера----------------------
	//----------------------------------------

	//Добавить в файл strings.xml:
	<string name="photo">Photo</string>
	<string name="video">Video</string>

	//Файл main.xml:
	//На экране только кнопки для получения фото и видео, и ImageView, в котором будем отображать полученное фото.
	<?xml version="1.0" encoding="utf-8"?>
	<RelativeLayout
	 xmlns:android="http://schemas.android.com/apk/res/android"
	 android:layout_width="match_parent"
	 android:layout_height="match_parent">
	<Button
	 android:id="@+id/btnPhoto"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:onClick="onClickPhoto"
	 android:text="@string/photo">
	</Button>
	<Button
	 android:id="@+id/btnVideo"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:layout_below="@+id/btnPhoto"
	 android:onClick="onClickVideo"
	 android:text="@string/video">
	</Button>
	<ImageView
	 android:id="@+id/ivPhoto"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:layout_below="@+id/btnVideo">
	</ImageView>
	</RelativeLayout>

	//Файл MainActivity.java:
	package ru.startandroid.develop.p1311cameraintent;

	import java.io.File;

	import android.app.Activity;
	import android.content.Intent;
	import android.graphics.Bitmap;
	import android.net.Uri;
	import android.os.Bundle;
	import android.os.Environment;
	import android.provider.MediaStore;
	import android.util.Log;
	import android.view.View;
	import android.widget.ImageView;

	public class MainActivity extends Activity {

	  File directory;
	  final int TYPE_PHOTO = 1;
	  final int TYPE_VIDEO = 2;

	  final int REQUEST_CODE_PHOTO = 1;
	  final int REQUEST_CODE_VIDEO = 2;

	  final String TAG = "myLogs";

	  ImageView ivPhoto;

	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		createDirectory();
		ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
	  }

	  public void onClickPhoto(View view) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, generateFileUri(TYPE_PHOTO));
		startActivityForResult(intent, REQUEST_CODE_PHOTO);
	  }

	  public void onClickVideo(View view) {
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, generateFileUri(TYPE_VIDEO));
		startActivityForResult(intent, REQUEST_CODE_VIDEO);
	  }

	  @Override
	  protected void onActivityResult(int requestCode, int resultCode,
		  Intent intent) {
		if (requestCode == REQUEST_CODE_PHOTO) {
		  if (resultCode == RESULT_OK) {
			if (intent == null) {
			  Log.d(TAG, "Intent is null");
			} else {
			  Log.d(TAG, "Photo uri: " + intent.getData());
			  Bundle bndl = intent.getExtras();
			  if (bndl != null) {
				Object obj = intent.getExtras().get("data");
				if (obj instanceof Bitmap) {
				  Bitmap bitmap = (Bitmap) obj;
				  Log.d(TAG, "bitmap " + bitmap.getWidth() + " x "
					  + bitmap.getHeight());
				  ivPhoto.setImageBitmap(bitmap);
				}
			  }
			}
		  } else if (resultCode == RESULT_CANCELED) {
			Log.d(TAG, "Canceled");
		  }
		}

		if (requestCode == REQUEST_CODE_VIDEO) {
		  if (resultCode == RESULT_OK) {
			if (intent == null) {
			  Log.d(TAG, "Intent is null");
			} else {
			  Log.d(TAG, "Video uri: " + intent.getData());
			}
		  } else if (resultCode == RESULT_CANCELED) {
			Log.d(TAG, "Canceled");
		  }
		}
	  }

	  private Uri generateFileUri(int type) {
		File file = null;
		switch (type) {
		case TYPE_PHOTO:
		  file = new File(directory.getPath() + "/" + "photo_"
			  + System.currentTimeMillis() + ".jpg");
		  break;
		case TYPE_VIDEO:
		  file = new File(directory.getPath() + "/" + "video_"
			  + System.currentTimeMillis() + ".mp4");
		  break;
		}
		Log.d(TAG, "fileName = " + file);
		return Uri.fromFile(file);
	  }

	  private void createDirectory() {
		directory = new File(
			Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
			"MyFolder");
		if (!directory.exists())
		  directory.mkdirs();
	  }

	}

	//Дописать в манифест
	<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/> - право на запись на SD карту
	<uses-feature android:name="android.hardware.camera" /> - ваше приложение в маркете будет видно только устройствам с камерой

	//----------------------------------------
	//-----Обработка поворота камеры----------
	//----------------------------------------

	//Файл main.xml:
	<?xml version="1.0" encoding="utf-8"?>
	<FrameLayout
	 xmlns:android="http://schemas.android.com/apk/res/android"
	 xmlns:tools="http://schemas.android.com/tools"
	 android:layout_width="match_parent"
	 android:layout_height="match_parent">
	<SurfaceView
	 android:id="@+id/surfaceView"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:layout_gravity="center">
	</SurfaceView>
	</FrameLayout>
	//SurfaceView по центру экрана.
	//В манифест добавьте права на камеру: <uses-permission an-droid:name="android.permission.CAMERA"/>

	//Файл MainActivity.java:
	package ru.startandroid.develop.p1321camerascreen;

	import java.io.IOException;

	import android.app.Activity;
	import android.graphics.Matrix;
	import android.graphics.RectF;
	import android.hardware.Camera;
	import android.hardware.Camera.CameraInfo;
	import android.hardware.Camera.Size;
	import android.os.Bundle;
	import android.view.Display;
	import android.view.Surface;
	import android.view.SurfaceHolder;
	import android.view.SurfaceView;
	import android.view.Window;
	import android.view.WindowManager;

	public class MainActivity extends Activity {

	  SurfaceView sv;
	  SurfaceHolder holder;
	  HolderCallback holderCallback;
	  Camera camera;

	  final int CAMERA_ID = 0;
	  final boolean FULL_SCREEN = true;

	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
			WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);

		sv = (SurfaceView) findViewById(R.id.surfaceView);
		holder = sv.getHolder();
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		holderCallback = new HolderCallback();
		holder.addCallback(holderCallback);
	  }

	  @Override
	  protected void onResume() {
		super.onResume();
		camera = Camera.open(CAMERA_ID);
		setPreviewSize(FULL_SCREEN);
	  }

	  @Override
	  protected void onPause() {
		super.onPause();
		if (camera != null)
		  camera.release();
		camera = null;
	  }

	  class HolderCallback implements SurfaceHolder.Callback {

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
		  try {
			camera.setPreviewDisplay(holder);
			camera.startPreview();
		  } catch (IOException e) {
			e.printStackTrace();
		  }
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		  camera.stopPreview();
		  setCameraDisplayOrientation(CAMERA_ID);
		  try {
			camera.setPreviewDisplay(holder);
			camera.startPreview();
		  } catch (Exception e) {
			e.printStackTrace();
		  }
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {

		}

	  }

	  void setPreviewSize(boolean fullScreen) {

		// получаем размеры экрана
		Display display = getWindowManager().getDefaultDisplay();
		boolean widthIsMax = display.getWidth() > display.getHeight();

		// определяем размеры превью камеры
		Size size = camera.getParameters().getPreviewSize();
			
		RectF rectDisplay = new RectF();
		RectF rectPreview = new RectF();
		
		// RectF экрана, соотвествует размерам экрана
		rectDisplay.set(0, 0, display.getWidth(), display.getHeight());
		
		// RectF первью 
		if (widthIsMax) {
		  // превью в горизонтальной ориентации
		  rectPreview.set(0, 0, size.width, size.height);
		} else {
		  // превью в вертикальной ориентации
		  rectPreview.set(0, 0, size.height, size.width);
		}

		Matrix matrix = new Matrix();
		// подготовка матрицы преобразования
		if (!fullScreen) {
		  // если превью будет "втиснут" в экран
		  matrix.setRectToRect(rectPreview, rectDisplay,
			  Matrix.ScaleToFit.START);
		} else {
		  // если экран будет "втиснут" в превью
		  matrix.setRectToRect(rectDisplay, rectPreview,
			  Matrix.ScaleToFit.START);
		  matrix.invert(matrix);
		}
		// преобразование
		matrix.mapRect(rectPreview);

		// установка размеров surface из получившегося преобразования
		sv.getLayoutParams().height = (int) (rectPreview.bottom);
		sv.getLayoutParams().width = (int) (rectPreview.right);
	  }

	  void setCameraDisplayOrientation(int cameraId) {
		// определяем насколько повернут экран от нормального положения
		int rotation = getWindowManager().getDefaultDisplay().getRotation();
		int degrees = 0;
		switch (rotation) {
		case Surface.ROTATION_0:
		  degrees = 0;
		  break;
		case Surface.ROTATION_90:
		  degrees = 90;
		  break;
		case Surface.ROTATION_180:
		  degrees = 180;
		  break;
		case Surface.ROTATION_270:
		  degrees = 270;
		  break;
		}
		
		int result = 0;
		
		// получаем инфо по камере cameraId
		CameraInfo info = new CameraInfo();
		Camera.getCameraInfo(cameraId, info);

		// задняя камера
		if (info.facing == CameraInfo.CAMERA_FACING_BACK) {
		  result = ((360 - degrees) + info.orientation);
		} else
		// передняя камера
		if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
		  result = ((360 - degrees) - info.orientation);
		  result += 360;
		}
		result = result % 360;
		camera.setDisplayOrientation(result);
	  }
	}

	//----------------------------------------
	//------------GPS и Network---------------
	//----------------------------------------

	//Дописать в strings.xml:
	<string name="provider_gps">GPS</string>
	<string name="provider_network">Network</string>
	<string name="location_settings">Location settings</string>

	//Файл main.xml:
	//Несколько TextView, в которые мы будем выводить данные, и кнопка для открытия настроек местоположения.
	<?xml version="1.0" encoding="utf-8"?>
	<LinearLayout
	 xmlns:android="http://schemas.android.com/apk/res/android"
	 xmlns:tools="http://schemas.android.com/tools"
	 android:layout_width="match_parent"
	 android:layout_height="match_parent"
	 android:orientation="vertical"
	 android:padding="5dp">
	<TextView
	 android:id="@+id/tvTitleGPS"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:text="@string/provider_gps"
	 android:textSize="30sp">
	</TextView>
	<TextView
	 android:id="@+id/tvEnabledGPS"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:textSize="24sp">
	</TextView>
	<TextView
	 android:id="@+id/tvStatusGPS"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:textSize="24sp">
	</TextView>
	<TextView
	 android:id="@+id/tvLocationGPS"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:textSize="24sp">
	</TextView>
	<TextView
	 android:id="@+id/tvTitleNet"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:layout_marginTop="10dp"
	 android:text="@string/provider_network"
	 android:textSize="30sp">
	</TextView>
	<TextView
	 android:id="@+id/tvEnabledNet"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:textSize="24sp">
	</TextView>
	<TextView
	 android:id="@+id/tvStatusNet"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:textSize="24sp">
	</TextView>
	<TextView
	 android:id="@+id/tvLocationNet"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:textSize="24sp">
	</TextView>
	<Button
	 android:id="@+id/btnLocationSettings"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:layout_marginTop="10dp"
	 android:onClick="onClickLocationSettings"
	 android:text="@string/location_settings">
	</Button>
	</LinearLayout>

	//Файл MainActivity.java:
	package ru.startandroid.develop.p1381location;

	import java.util.Date;

	import android.app.Activity;
	import android.content.Intent;
	import android.location.Location;
	import android.location.LocationListener;
	import android.location.LocationManager;
	import android.os.Bundle;
	import android.view.View;
	import android.widget.TextView;

	public class MainActivity extends Activity {

	  TextView tvEnabledGPS;
	  TextView tvStatusGPS;
	  TextView tvLocationGPS;
	  TextView tvEnabledNet;
	  TextView tvStatusNet;
	  TextView tvLocationNet;

	  private LocationManager locationManager;
	  StringBuilder sbGPS = new StringBuilder();
	  StringBuilder sbNet = new StringBuilder();

	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		tvEnabledGPS = (TextView) findViewById(R.id.tvEnabledGPS);
		tvStatusGPS = (TextView) findViewById(R.id.tvStatusGPS);
		tvLocationGPS = (TextView) findViewById(R.id.tvLocationGPS);
		tvEnabledNet = (TextView) findViewById(R.id.tvEnabledNet);
		tvStatusNet = (TextView) findViewById(R.id.tvStatusNet);
		tvLocationNet = (TextView) findViewById(R.id.tvLocationNet);

		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
	  }

	  @Override
	  protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
			1000 * 10, 10, locationListener);
		locationManager.requestLocationUpdates(
			LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
			locationListener);
		checkEnabled();
	  }

	  @Override
	  protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(locationListener);
	  }

	  private LocationListener locationListener = new LocationListener() {

		@Override
		public void onLocationChanged(Location location) {
		  showLocation(location);
		}

		@Override
		public void onProviderDisabled(String provider) {
		  checkEnabled();
		}

		@Override
		public void onProviderEnabled(String provider) {
		  checkEnabled();
		  showLocation(locationManager.getLastKnownLocation(provider));
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		  if (provider.equals(LocationManager.GPS_PROVIDER)) {
			tvStatusGPS.setText("Status: " + String.valueOf(status));
		  } else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
			tvStatusNet.setText("Status: " + String.valueOf(status));
		  }
		}
	  };

	  private void showLocation(Location location) {
		if (location == null)
		  return;
		if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
		  tvLocationGPS.setText(formatLocation(location));
		} else if (location.getProvider().equals(
			LocationManager.NETWORK_PROVIDER)) {
		  tvLocationNet.setText(formatLocation(location));
		}
	  }

	  private String formatLocation(Location location) {
		if (location == null)
		  return "";
		return String.format(
			"Coordinates: lat = %1$.4f, lon = %2$.4f, time = %3$tF %3$tT",
			location.getLatitude(), location.getLongitude(), new Date(
				location.getTime()));
	  }

	  private void checkEnabled() {
		tvEnabledGPS.setText("Enabled: "
			+ locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER));
		tvEnabledNet.setText("Enabled: "
			+ locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
	  }

	  public void onClickLocationSettings(View view) {
		startActivity(new Intent(
			android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
	  };

	}

	//----------------------------------------
	//------------Google Maps-----------------
	//----------------------------------------

	//В манифесте необходимо добавить следующее в тег application:
	<meta-data
	 android:name="com.google.android.maps.v2.API_KEY"
	 android:value="AIzaSyComUhEqr9BL4JjqJE05Lck4j1uABIU08Y">
	</meta-data>
	<meta-data
	 android:name="com.google.android.gms.version"
	 android:value="@integer/google_play_services_version">
	</meta-data>
	//Первые данные, это наш ключ из гугл-консоли. Здесь вам надо в android:value поставить ваше значение API key. Этот ключ нужен, чтобы карта работала.
	//Вторые данные – это версия Google Play services.
	//Также, в манифесте в тег manifest нам надо добавить следующие разрешения:
	<uses-permission android:name="android.permission.INTERNET"></uses-permission>
	<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>
	<uses-permission an-droid:name="android.permission.WRITE_EXTERNAL_STORAGE"></uses-permission>
	<uses-permission an-droid:name="com.google.android.providers.gsf.permission.READ_GSERVICES"></uses-permission>

	//Это доступ в интернет, проверка доступности инета, сохранение кэша карт и доступ к гугл-веб-сервисам.

	//Если думаете работать с определением местоположения, то не забывайте про:
	<uses-permission an-droid:name="android.permission.ACCESS_COARSE_LOCATION"></uses-permission>
	<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"></uses-permission>
	//И туда же, в тег manifest такое требование:
	<uses-feature
	 android:glEsVersion="0x00020000"
	 android:required="true">
	</uses-feature>
	//Гугл-карты используют OpenGL ES версии 2. На девайсах, которые это не поддержи-вают, карта просто не отобразится. Поэтому ставим ограничение.

	//Теперь все. Далее продолжаем работу, как с обычным проектом.

	//В strings.xml добавим строку:
	<string name="test">Test</string>

	//Файл main.xml:
	<?xml version="1.0" encoding="utf-8"?>
	<LinearLayout
	 xmlns:android="http://schemas.android.com/apk/res/android"
	 xmlns:tools="http://schemas.android.com/tools"
	 android:layout_width="match_parent"
	 android:layout_height="match_parent"
	 android:orientation="vertical">
	<Button
	 android:id="@+id/btnTest"
	 android:layout_width="wrap_content"
	 android:layout_height="wrap_content"
	 android:onClick="onClickTest"
	 android:text="@string/test">
	</Button>
	<fragment
	 android:id="@+id/map"
	 android:name="com.google.android.gms.maps.SupportMapFragment"
	 android:layout_width="match_parent"
	 android:layout_height="match_parent">
	</fragment>
	</LinearLayout>
	//Кнопка и фрагмент-карта

	//Файл MainActivity.java:
	package ru.startandroid.develop.p1391googlemaps;

	import android.os.Bundle;
	import android.support.v4.app.FragmentActivity;
	import android.view.View;

	import com.google.android.gms.maps.GoogleMap;
	import com.google.android.gms.maps.SupportMapFragment;

	public class MainActivity extends FragmentActivity {

	  SupportMapFragment mapFragment;
	  GoogleMap map;
	  final String TAG = "myLogs";

	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mapFragment = (SupportMapFragment) getSupportFragmentManager()
			.findFragmentById(R.id.map);
		map = mapFragment.getMap();
		if (map == null) {
		  finish();
		  return;
		}
		init();
	  }

	  private void init() {
		}


	  public void onClickTest(View view) {
		map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
	  }
	}