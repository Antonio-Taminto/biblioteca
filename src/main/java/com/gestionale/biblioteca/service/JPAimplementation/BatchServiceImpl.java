package com.gestionale.biblioteca.service.JPAimplementation;

import com.gestionale.biblioteca.batch.BatchLauncher;
import com.gestionale.biblioteca.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchServiceImpl implements BatchService {

    @Autowired
    private BatchLauncher batchLauncher;

    @Override
    public void startBatch() throws Exception {
        batchLauncher.performBatchJob();
    }
}
