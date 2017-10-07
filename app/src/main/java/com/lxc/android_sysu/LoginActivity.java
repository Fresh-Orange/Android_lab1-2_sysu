package com.lxc.android_sysu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
	//存放头像的地址，网络图片
	String[] imgUrls = {"http://p2.ifengimg.com/fck/2017_26/35b0e434ba33e60_w600_h465.jpg"
			, "http://cms-bucket.nosdn.127.net/catchpic/7/72/7282e1d729f9524061337bdf83655c10.jpg?imageView&thumbnail=550x0"
			,"http://p2.ifengimg.com/fck/2017_26/ebc3ea03851eaa6_w550_h407.jpg"
			,"http://p2.ifengimg.com/fck/2017_26/4eb5a3d5ea6b6aa_w600_h430.jpg"
			,"http://p2.ifengimg.com/fck/2017_26/9bea9ca06a97992_w495_h374.jpg"
			};
	int nextUrl = 0;
	ImageView avatarImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		avatarImageView = (ImageView) findViewById(R.id.iv_avatar);
		avatarImageView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.iv_avatar:
				//使用glide动态加载图片
				Glide.with(this).load(imgUrls[nextUrl]).into(avatarImageView);
				nextUrl = nextUrl + 1 < imgUrls.length ? nextUrl + 1 : 0;
		}
	}
}
