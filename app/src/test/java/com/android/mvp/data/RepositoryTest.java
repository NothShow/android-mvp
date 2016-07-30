package com.android.mvp.data;


import com.android.mvp.data.impl.TaskRepository;
import com.android.mvp.injection.Injection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RepositoryTest {

    private Repository mRepository;


    @Before
    public void setUp() throws Exception {
        mRepository = new TaskRepository(
                Injection.provideLocalProxy(),
                Injection.provideRemoteProxy());
    }

    @Test
    public void testRepository() throws Exception {
        mRepository.getTasks();
        mRepository.refreshTasks();
    }

    @After
    public void tearDown() throws Exception {
        mRepository = null;
    }
}