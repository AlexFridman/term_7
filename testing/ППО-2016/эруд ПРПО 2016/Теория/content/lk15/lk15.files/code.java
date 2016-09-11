	//------------------------------------
	//-------Canvas через View------------
	//------------------------------------

	//Файл MainActivity.java:
	package ru.startandroid.develop.p1411canvasview;

	import android.app.Activity;
	import android.content.Context;
	import android.graphics.Canvas;
	import android.graphics.Color;
	import android.os.Bundle;
	import android.view.View;

	public class MainActivity extends Activity {

	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new DrawView(this));
	  }

	  class DrawView extends View {

		public DrawView(Context context) {
		  super(context);
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
		  canvas.drawColor(Color.GREEN);
		}
		
	  }
	  
	}

	//------------------------------------
	//------Canvas через SurfaceView------
	//------------------------------------

	//Файл MainActivity.java:
	package ru.startandroid.develop.p1411canvasview;

	import android.app.Activity;
	import android.content.Context;
	import android.graphics.Canvas;
	import android.graphics.Color;
	import android.os.Bundle;
	import android.view.SurfaceHolder;
	import android.view.SurfaceView;

	public class MainActivity extends Activity {

	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new DrawView(this));
	  }

	  class DrawView extends SurfaceView implements SurfaceHolder.Callback {

		private DrawThread drawThread;

		public DrawView(Context context) {
		  super(context);
		  getHolder().addCallback(this);
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
		  drawThread = new DrawThread(getHolder());
		  drawThread.setRunning(true);
		  drawThread.start();
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
		  boolean retry = true;
		  drawThread.setRunning(false);
		  while (retry) {
			try {
			  drawThread.join();
			  retry = false;
			} catch (InterruptedException e) {
			}
		  }
		}

		class DrawThread extends Thread {

		  private boolean running = false;
		  private SurfaceHolder surfaceHolder;

		  public DrawThread(SurfaceHolder surfaceHolder) {
			this.surfaceHolder = surfaceHolder;
		  }

		  public void setRunning(boolean running) {
			this.running = running;
		  }

		  @Override
		  public void run() {
			Canvas canvas;
			while (running) {
			  canvas = null;
			  try {
				canvas = surfaceHolder.lockCanvas(null);
				if (canvas == null)
				  continue;
				canvas.drawColor(Color.GREEN);
			  } finally {
				if (canvas != null) {
				  surfaceHolder.unlockCanvasAndPost(canvas);
				}
			  }
			}
		  }
		}

	  }

	}

	//------------------------------------
	//------Matrix перемещение------------
	//------------------------------------

	//Файл MainActivity.java:
	package ru.startandroid.develop.p1441matrixtransform;

	import android.app.Activity;
	import android.content.Context;
	import android.graphics.Canvas;
	import android.graphics.Color;
	import android.graphics.Matrix;
	import android.graphics.Paint;
	import android.graphics.Path;
	import android.os.Bundle;
	import android.view.View;

	public class MainActivity extends Activity {

	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new DrawView(this));
	  }

	  class DrawView extends View {

		Paint p;
		Path path;
		Matrix matrix;

		public DrawView(Context context) {
		  super(context);
		  p = new Paint();
		  p.setStrokeWidth(3);
		  p.setStyle(Paint.Style.STROKE);

		  path = new Path();
		  matrix = new Matrix();
		}

		@Override
		protected void onDraw(Canvas canvas) {
		  canvas.drawARGB(80, 102, 204, 255);

		  // создаем крест в path
		  path.reset();
		  path.addRect(300, 150, 450, 200, Path.Direction.CW);
		  path.addRect(350, 100, 400, 250, Path.Direction.CW);

		  // рисуем path зеленым
		  p.setColor(Color.GREEN);
		  canvas.drawPath(path, p);

		  // настраиваем матрицу на перемещение на 300 вправо и 200 вниз
		  matrix.reset();
		  matrix.setTranslate(300, 200);
		  
		  // применяем матрицу к path
		  path.transform(matrix);

		  // рисуем path синим
		  p.setColor(Color.BLUE);
		  canvas.drawPath(path, p);

		}

	  }

	}

	//------------------------------------
	//------Matrix изменение размера------
	//------------------------------------

	//Файл MainActivity.java:
	package ru.startandroid.develop.p1441matrixtransform;

	import android.app.Activity;
	import android.content.Context;
	import android.graphics.Canvas;
	import android.graphics.Color;
	import android.graphics.Matrix;
	import android.graphics.Paint;
	import android.graphics.Path;
	import android.os.Bundle;
	import android.view.View;

	public class MainActivity extends Activity {

	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new DrawView(this));
	  }

	  class DrawView extends View {
		
		Paint p;
		Path path;
		Matrix matrix;

		public DrawView(Context context) {
		  super(context);
		  p = new Paint();
		  p.setStrokeWidth(3);
		  p.setStyle(Paint.Style.STROKE);

		  path = new Path();
		  matrix = new Matrix();
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
		  canvas.drawARGB(80, 102, 204, 255);

		  // создаем крест в path
		  path.reset();
		  path.addRect(300,150,450,200, Path.Direction.CW);
		  path.addRect(350,100,400,250, Path.Direction.CW);
		  
		  // рисуем path зеленым
		  p.setColor(Color.GREEN);
		  canvas.drawPath(path, p);
		  
		  // настраиваем матрицу на изменение размера:
		  // в 2 раза по горизонтали
		  // в 2,5 по вертикали
		  // относительно точки (375, 100)
		  matrix.reset();      
		  matrix.setScale(2f, 2.5f, 375, 100);
		  
		  // применяем матрицу к path
		  path.transform(matrix);
		  
		  // рисуем path синим
		  p.setColor(Color.BLUE);
		  canvas.drawPath(path, p);

		  // рисуем точку относительно которой было выполнено преобразование
		  p.setColor(Color.BLACK);
		  canvas.drawCircle(375, 100, 5, p);
		  
		}
		
	  }

	//------------------------------------
	//---3D Использование GLSurfaceView---
	//------------------------------------

	import javax.microedition.khronos.egl.EGLConfig;
	import javax.microedition.khronos.opengles.GL10;

	import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
	import static android.opengl.GLES20.glClear;
	import static android.opengl.GLES20.glClearColor;
	import static android.opengl.GLES20.glViewport;

	import android.opengl.GLSurfaceView.Renderer;

	public class OpenGLRenderer implements Renderer {

	  @Override
	  public void onDrawFrame(GL10 arg0) {
		glClear(GL_COLOR_BUFFER_BIT);
		
	  }

	  @Override
	  public void onSurfaceChanged(GL10 arg0, int width, int height) {
		glViewport(0, 0, width, height);

	  }

	  @Override
	  public void onSurfaceCreated(GL10 arg0, EGLConfig arg1) {
		glClearColor(0f, 1f, 0f, 1f);
	  }

	}

	//Файл MainActivity.java
	import android.app.Activity;
	import android.app.ActivityManager;
	import android.content.Context;
	import android.content.pm.ConfigurationInfo;
	import android.opengl.GLSurfaceView;
	import android.os.Bundle;
	import android.widget.Toast;

	public class MainActivity extends Activity {
	  
	  private GLSurfaceView glSurfaceView;

	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!supportES2()) {
		  Toast.makeText(this, "OpenGl ES 2.0 is not supported", Toast.LENGTH_LONG).show();
		  finish();
		  return;
		}
		glSurfaceView = new GLSurfaceView(this);
		glSurfaceView.setEGLContextClientVersion(2);
		glSurfaceView.setRenderer(new OpenGLRenderer());
		setContentView(glSurfaceView);
	  }
	  
	  @Override
	  protected void onPause() {
		super.onPause();
		glSurfaceView.onPause();
	  }
	  
	  @Override
	  protected void onResume() {
		super.onResume();
		glSurfaceView.onResume();
	  }
	  
	  private boolean supportES2() {
			ActivityManager activityManager =
					(ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
				ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
				return (configurationInfo.reqGlEsVersion >= 0x20000);
	  }

	}