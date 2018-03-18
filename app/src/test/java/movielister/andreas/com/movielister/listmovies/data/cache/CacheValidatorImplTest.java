package movielister.andreas.com.movielister.listmovies.data.cache;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.observers.TestObserver;
import movielister.andreas.com.movielister.core.data.LocalStorage;

import static org.mockito.Mockito.when;

public class CacheValidatorImplTest {

    private static final int CACHE_EXPIRY_DURATION = 600000;

    @Rule
    public MockitoRule mockito = MockitoJUnit.rule();

    @Mock
    private LocalStorage localStorageMock;
    @Mock
    private TimeProvider timeProviderMock;

    private CacheValidatorImpl cacheValidator;

    @Before
    public void setUp() throws Exception {
        cacheValidator = new CacheValidatorImpl(CACHE_EXPIRY_DURATION, localStorageMock, timeProviderMock);
    }

    @Test
    public void setCacheUpToDateCallsLocalStorage() throws Exception {
        TestObserver<Void> test = cacheValidator.setCacheUpToDate().test();

        test.assertNoErrors()
                .assertComplete();
    }

    @Test
    public void isCacheValidWhenLastValueIsZero() throws Exception {
        when(localStorageMock.getMovieCacheTime()).thenReturn(0L);
        when(timeProviderMock.getCurrentTimeInMillis()).thenReturn(100000000000L);

        TestObserver<Boolean> test = cacheValidator.isCacheValid().test();

        test.assertValue(false)
                .assertNoErrors();
    }

    @Test
    public void cacheInvalidWhenSameAsCurrentTime() throws Exception {
        when(localStorageMock.getMovieCacheTime()).thenReturn(100000000000L - CACHE_EXPIRY_DURATION);
        when(timeProviderMock.getCurrentTimeInMillis()).thenReturn(100000000000L);

        TestObserver<Boolean> test = cacheValidator.isCacheValid().test();

        test.assertValue(false)
                .assertNoErrors();
    }

    @Test
    public void cacheValidWhenCurrentTimeLessThanExpiryTime() throws Exception {
        when(localStorageMock.getMovieCacheTime()).thenReturn(100000000000L);
        when(timeProviderMock.getCurrentTimeInMillis()).thenReturn(100000000000L);

        TestObserver<Boolean> test = cacheValidator.isCacheValid().test();

        test.assertValue(true)
                .assertNoErrors();
    }

}