package com.kusitms.kusitmsmarket.ui.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.kusitms.kusitmsmarket.R;
import com.kusitms.kusitmsmarket.RetrofitClient;
import com.kusitms.kusitmsmarket.databinding.FragmentMypageBinding;
import com.kusitms.kusitmsmarket.response.UserInfoResponse;
import com.naver.maps.map.LocationTrackingMode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPageFragment extends Fragment {

    String username;
    String token;
    String phone;
    String nickname;
    boolean alarm;

    Integer cnt;

    private MyPageViewModel myPageViewModel;
    private FragmentMypageBinding binding;

    Button btnNotice, btnUserInfo, btnCenter, btnReport, btnEvent;
    TextView tvNicknameMain;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myPageViewModel =
                new ViewModelProvider(this).get(MyPageViewModel.class);

        binding = FragmentMypageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        LinearLayout linearLayout = (LinearLayout) root.findViewById(R.id.myPageLayout);
        linearLayout.setPadding(0, getStatusBarHeight(), 0, 0);

        btnNotice = root.findViewById(R.id.btn_notice);
        btnUserInfo = root.findViewById(R.id.btn_mypage_user_info);
        btnCenter = root.findViewById(R.id.btn_mypage_center);
        btnReport = root.findViewById(R.id.report_btn);
        tvNicknameMain = root.findViewById(R.id.nickname);
        btnEvent = root.findViewById(R.id.btn_mypage_event);

        // ?????? ????????? ?????? ???????????? ????????????
        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        token = bundle.getString("user_token");

        Call<UserInfoResponse> callUser = RetrofitClient.getAPIService().getUserInfoData(token);

        callUser.enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                UserInfoResponse userInfoResponse = response.body();
                username = userInfoResponse.getUserInfo().getUsername();
                nickname = userInfoResponse.getUserInfo().getNickname();
                phone = userInfoResponse.getUserInfo().getPhone();

                cnt = userInfoResponse.getCount();

                tvNicknameMain.setText(nickname);

                if(response.isSuccessful()) {
                    Log.d("????????? ????????? : ", response.body().toString());

                } else {
                    Log.e("????????? ???????????? : ", "error code : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<UserInfoResponse> call, Throwable t) {
                Log.e("????????????", t.getMessage());
            }
        });

        Button reportButton = (Button) root.findViewById(R.id.report_btn);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReportLocationActivity.class);
                startActivity(intent);
            }
        });

        // ???????????? ????????? ???

        btnNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentNoticeActivity = new Intent(getActivity(), NoticeActivity.class);

                startActivity(intentNoticeActivity);
            }
        });

        // ???????????? ??????
        btnUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intentUserInfo = new Intent(getActivity(), UserInfoActivity.class);

                // ?????? ?????? ??????
                Intent mainActivityIntent = getActivity().getIntent();
                Bundle getBundle = mainActivityIntent.getExtras();
                String token = getBundle.getString("user_token");

                System.out.println("in fragment : " + token);

                // ?????? ????????? ??????
                Bundle sendBundle = new Bundle();
                sendBundle.putString("user_token", token);
                intentUserInfo.putExtras(sendBundle);

                startActivity(intentUserInfo);

            }
        });

        // ???????????? ?????? ????????? ???
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentReportLocationActivity = new Intent(getActivity(), ReportLocationActivity.class);
                Intent intentReportActivity = new Intent(getActivity(), ReportActivity.class);
//
//                // ?????? ?????? ??????
//                Intent mainActivityIntent = getActivity().getIntent();
//                Bundle getBundle = mainActivityIntent.getExtras();
//                String token = getBundle.getString("user_token");

                System.out.println("in fragment : " + token);

                // ?????? ????????? ??????
                Bundle sendBundle = new Bundle();
                sendBundle.putString("user_token", token);
                intentReportActivity.putExtras(sendBundle);


                startActivity(intentReportLocationActivity);
            }
        });

        // ???????????? ?????? ????????? ???
        btnCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCenterActivity = new Intent(getActivity(), CenterActivity.class);

                // ?????? ?????? ??????
                Intent mainActivityIntent = getActivity().getIntent();
                Bundle getBundle = mainActivityIntent.getExtras();
                String token = getBundle.getString("user_token");

                System.out.println("in fragment : " + token);

                // ?????? ????????? ??????
                Bundle sendBundle = new Bundle();
                sendBundle.putString("user_token", token);
                intentCenterActivity.putExtras(sendBundle);

                startActivity(intentCenterActivity);
            }
        });

        // ????????? ????????? ???
        btnEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent eventIntent = new Intent(getActivity(), EventActivity.class);

                startActivity(eventIntent);

            }
        });
//        myPageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    //status bar??? ?????? ??????
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            result = getResources().getDimensionPixelSize(resourceId);

        return result;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

}
