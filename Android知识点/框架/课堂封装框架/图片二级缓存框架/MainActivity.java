package cn.ucai.a4imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cn.ucai.a4imageloader.bean.AppBean;
import cn.ucai.a4imageloader.utils.MyImageLoader;
import cn.ucai.a4imageloader.utils.OkHttpUtils;


public class MainActivity extends AppCompatActivity {
    static final String ROOT_URL = "http://10.0.2.2";
    RecyclerView mrvApp;
    ArrayList<AppBean> mApplist;
    AppAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();

    }

    private void initData() {
        OkHttpUtils<AppBean[]> utils = new OkHttpUtils<>();
        utils.url(ROOT_URL + "/app.json")
                .targetClass(AppBean[].class)
                .execute(new OkHttpUtils.OnCompleteListener<AppBean[]>() {
                    @Override
                    public void onSuccess(AppBean[] result) {
                        ArrayList<AppBean> appList = OkHttpUtils.array2List(result);
                        mAdapter.addApps(appList);
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initView() {
        mrvApp = (RecyclerView) findViewById(R.id.rvApp);
        mApplist = new ArrayList<>();
        mAdapter = new AppAdapter(this, mApplist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mrvApp.setLayoutManager(layoutManager);
        mrvApp.setAdapter(mAdapter);
    }

    class AppViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumb;
        TextView tvName, tvVersion, tvFileSize;

        public AppViewHolder(View itemView) {
            super(itemView);
            ivThumb = (ImageView) itemView.findViewById(R.id.ivThumb);
            tvName = (TextView) itemView.findViewById(R.id.tvAppName);
            tvVersion = (TextView) itemView.findViewById(R.id.tvVersion);
            tvFileSize = (TextView) itemView.findViewById(R.id.tvFileSize);
        }
    }

    class AppAdapter extends RecyclerView.Adapter<AppViewHolder> {
        Context context;
        ArrayList<AppBean> appList;
        ViewGroup parent;

        public AppAdapter(Context context, ArrayList<AppBean> appList) {
            this.context = context;
            this.appList = appList;
        }

        public void addApps(ArrayList<AppBean> appList) {
            this.appList.addAll(appList);
            notifyDataSetChanged();
        }

        @Override
        public AppViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            this.parent = parent;
            return new AppViewHolder(LayoutInflater.from(context).inflate(R.layout.item_app, parent, false));
        }

        @Override
        public void onBindViewHolder(AppViewHolder holder, int position) {
            final AppBean app = appList.get(position);
            holder.tvName.setText(app.getName());
            holder.tvVersion.setText(app.getVersion());
            holder.tvFileSize.setText(app.getFileSize() + "kb");
            //将图片的下载地址保存在ImageView.mTag属性中
            holder.ivThumb.setTag(ROOT_URL + app.getThumb());
            String fileName = app.getThumb();
            fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
            Bitmap bitmap = MyImageLoader.build().url(ROOT_URL + app.getThumb())
                    .width(80)
                    .height(80)
                    .setFileName(fileName)
                    .listener(new MyImageLoader.OnImageLoadOkListener() {
                        @Override
                        public void onSuccess(String url, Bitmap bitmap) {
                            ImageView iv = (ImageView) parent.findViewWithTag(url);
                            if (iv != null) {
                                iv.setImageBitmap(bitmap);
                            }
                        }

                        @Override
                        public void onError(String error) {
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }).loadImage(context);

            if (bitmap == null) {
                holder.ivThumb.setImageResource(R.drawable.nopic);
            } else {
                holder.ivThumb.setImageBitmap(bitmap);
            }
        }

        @Override
        public int getItemCount() {
            return appList == null ? 0 : appList.size();
        }
    }
}
