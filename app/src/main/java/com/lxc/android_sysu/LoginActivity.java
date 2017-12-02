package com.lxc.android_sysu;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener {

	View mContentView;
	ImageView avatarImageView;
	TextInputLayout inputLayoutID;
	TextInputLayout inputLayoutPassword;
	EditText etID, etPassword;
	RadioGroup radioGroup;
	RadioButton studentRadio,teacherRadio;
	public static final int ALBUM_REQUEST_CODE = 666;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mContentView = findViewById(android.R.id.content);
		avatarImageView = (ImageView) findViewById(R.id.iv_avatar);
		avatarImageView.setOnClickListener(this);
		radioGroup = (RadioGroup) findViewById(R.id.rg);
		radioGroup.setOnCheckedChangeListener(this);
		inputLayoutID = (TextInputLayout) findViewById(R.id.tilo_id_num);
		inputLayoutPassword = (TextInputLayout) findViewById(R.id.tilo_password);
		etID = (EditText) findViewById(R.id.et_id_num);
		etPassword = (EditText) findViewById(R.id.et_password);
		studentRadio = (RadioButton) findViewById(R.id.rb_student);
		teacherRadio = (RadioButton) findViewById(R.id.rb_teacher);
		Button btLogin = (Button) findViewById(R.id.btn_login);
		btLogin.setOnClickListener(this);
		Button btResg = (Button) findViewById(R.id.btn_register);
		btResg.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.iv_avatar:
				showUploadDialog();
				break;
			case R.id.btn_login:
				checkInput();
				break;
			case R.id.btn_register:
				showRegisterMsg();
		}
	}

	private void showRegisterMsg(){
		if (studentRadio.isChecked()){
			showSnackbar("学生注册功能尚未启用");
		}
		if (teacherRadio.isChecked()){
			Toast.makeText(LoginActivity.this,"教职工注册功能尚未启用",Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 检查学号密码的输入情况
	 */
	private void checkInput(){
		if (etID.getText().toString().equals("")){
			inputLayoutID.setError("学号不能为空");
		}
		else{
			inputLayoutID.setError("");//错误信息重置为空
		}
		if (etPassword.getText().toString().equals("")){
			inputLayoutPassword.setError("密码不能为空");
		}
		else{
			inputLayoutPassword.setError("");//错误信息重置为空
		}
		//账号密码都匹配的话
		if (etID.getText().toString().equals("123456") && etPassword.getText().toString().equals("6666")){
			showSnackbar("登录成功");
		}
		else {
			showSnackbar("学号或密码错误");
		}
	}

	/**
	 * radioGroup选项切换事件
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
		switch (checkedId) {
			case R.id.rb_student:
				showSnackbar("您选择了学生");
				break;
			case R.id.rb_teacher:
				showSnackbar("您选择了教职工");
				break;
		}
	}

	/**
	 * 弹出snackbar
	 * @param msg 要弹出的snackbar信息
	 */
	private void showSnackbar(String msg) {
		Snackbar.make(mContentView, msg, Snackbar.LENGTH_SHORT).setAction("确定", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(LoginActivity.this,"Snackbar 的确定按钮被点击了",Toast.LENGTH_SHORT).show();
			}
		}).show();
	}

	/**
	 * 展示上传头像的dialog
	 */
	private void showUploadDialog(){
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.upload));
		String[] items = {getString(R.string.photo), getString(R.string.album)};
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (which == 0){
					Toast.makeText(LoginActivity.this, getString(R.string.photo_hint), Toast.LENGTH_SHORT).show();
				}
				else if (which == 1){
					Toast.makeText(LoginActivity.this, getString(R.string.album_hint), Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(intent, ALBUM_REQUEST_CODE);
				}
				dialog.dismiss();
			}
		});

		builder.setNegativeButton(this.getString(R.string.cancel), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(LoginActivity.this, getString(R.string.cancel_hint), Toast.LENGTH_SHORT).show();
				dialog.cancel();
			}
		});

		builder.show();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/*获得相册的返回数据，将图片设置成头像*/
		switch (requestCode) {
			case ALBUM_REQUEST_CODE:
				if (resultCode == RESULT_OK) {
					Uri uri = data.getData();
					if (uri != null) {
						Bitmap image;
						try {
							image = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
							avatarImageView.setImageBitmap(image);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						Bundle extras = data.getExtras();
						if (extras != null) {
							Bitmap image = extras.getParcelable("data");
							if (image != null) {
								avatarImageView.setImageBitmap(image);
							}
						}
					}
				}
				break;
		}
	}
}
