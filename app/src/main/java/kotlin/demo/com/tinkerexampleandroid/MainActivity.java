package kotlin.demo.com.tinkerexampleandroid;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mTestBtn;
    private Button mRepairBtn;
    private File patchFileApk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        patchFileApk = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "patch.apk");
    }

    private void initView() {
        mTestBtn = findViewById(R.id.btn_test);
        mTestBtn.setOnClickListener(this);
        mRepairBtn = findViewById(R.id.Btn_repair);
        mRepairBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test:
                // TODO 18/05/14

                //  int num = 1 / 0;//错误测试
                int num = 1 / 1;//修复后的代码
                Toast.makeText(MainActivity.this, "bug已经被修复", Toast.LENGTH_LONG).show();//修复后的代码
                break;
            case R.id.Btn_repair:
                // TODO 18/05/14
                loadPathApk();

            default:
                break;
        }
    }

    private void loadPathApk() {

        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.STORAGE)
                .onGranted(permissions -> {
                    // TODO ...
                    if (patchFileApk.exists()) {
                        TinkerInstaller.onReceiveUpgradePatch(MainActivity.this, patchFileApk.getAbsolutePath());
                    } else {
                        Toast.makeText(MainActivity.this, "文件不存在", Toast.LENGTH_LONG).show();
                    }
                })
                .onDenied(permissions -> {
                    // TODO ...
                })
                .start();


    }
}
