package whot.what.hot.ui.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.HashMap;

import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;
import whot.what.hot.api.ApiServices;
import whot.what.hot.data.AntModel;
import whot.what.hot.data.MainModel;
import whot.what.hot.http.RetrofitManager;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** MAIN UNIT TEST
 * Created by Kevin on 18/10/2017.
 */
public class TestPresenterTest {

    private TestPresenter testPresenter;
    private TestView testView;
    private ApiServices apiServices;

    @BeforeClass
    public static void openRxTools(){
        RxAndroidSchedulersHook rxAndroidSchedulersHook = new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        };

        RxJavaSchedulersHook rxJavaSchedulersHook = new RxJavaSchedulersHook() {
            @Override
            public Scheduler getIOScheduler() {
                return Schedulers.immediate();
            }
        };

        // reset()不是必要，實踐中發現不寫reset()，偶爾會出錯，所以寫上保險
        RxAndroidPlugins.getInstance().reset();
        RxAndroidPlugins.getInstance().registerSchedulersHook(rxAndroidSchedulersHook);
        RxJavaPlugins.getInstance().reset();
        RxJavaPlugins.getInstance().registerSchedulersHook(rxJavaSchedulersHook);
    }

    @Before
    public void setUp() throws Exception {
        // 生成mock對象
        testView = mock(TestView.class);
        apiServices = RetrofitManager.getDefault("http://60.248.145.133:8164");
        apiServices = mock(ApiServices.class);
        testPresenter = new TestPresenter(apiServices, testView);
    }

    @Test
    public void testNotificationData() throws Exception {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("IMEI", "355693063092533");
        hashMap.put("CusID", "0005967");
        hashMap.put("TimeStamp", "1497235728");
        hashMap.put("Signature", "ce42ccc9a6da004321c8f4dab7632c6b849245669634c155a9497afdc6fdfe11");
        hashMap.put("AppID", "20170518A");

        MainModel mainModel = new MainModel();

        when(apiServices.getNotificationCountWithRxJava("api/Android/NotifiListCount", hashMap)).thenReturn(Observable.just(mainModel));
        testPresenter.loadDataByRetrofitRxJava(hashMap);

        ArgumentCaptor<MainModel> captor = ArgumentCaptor.forClass(MainModel.class);

        verify(testView).getNotificationData(captor.capture());
        mainModel = captor.getValue();

        Assert.assertEquals(true,mainModel.isResult());
    }

    @Test
    public void testAntResult() throws Exception {
        AntModel antModel = new AntModel();
        when(apiServices.getAntInfoWithGson("https://script.google.com/macros/s/AKfycbxxgTSWXbEiX8EHBSWrt6PVDnMAfmM3FLYDAhy-cqgDTRVY6hA/exec")).thenReturn(Observable.just(antModel));
        testPresenter.loadAntData();
        ArgumentCaptor<AntModel> captor = ArgumentCaptor.forClass(AntModel.class);
        verify(testView).getAntResult(captor.capture());
        antModel = captor.getValue();
        Assert.assertEquals("Ant",antModel.getName());
    }
}
