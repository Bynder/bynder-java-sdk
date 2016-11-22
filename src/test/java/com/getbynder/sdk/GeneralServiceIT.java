package com.getbynder.sdk;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.getbynder.sdk.domain.Category;
import com.getbynder.sdk.domain.Tag;
import com.getbynder.sdk.service.GeneralService;

/**
 *
 * @author daniel.sequeira
 */
public class GeneralServiceIT {

    private static final Logger LOG = LoggerFactory.getLogger(GeneralServiceIT.class);

    private GeneralService generalService;

    @Before
    public void setUp() throws Exception {
        generalService = new GeneralService();
    }

    @Test
    public void getCategoriesSyncTest() {
        List<Category> categories = generalService.getCategories().execute();
        assertTrue(categories.size() > 0);
    }

    // @Test
    // public void getCategoriesAsyncTest() throws InterruptedException {
    // final List<Category> categories = new ArrayList<>();
    // generalService.getCategories().enqueue(new BynderServiceCallback<List<Category>>() {
    // @Override
    // public void onResponse(final List<Category> response) {
    // categories.addAll(response);
    // }
    //
    // @Override
    // public void onFailure(final Exception e) {
    // LOG.error(e.getMessage());
    // }
    // });
    //
    // Thread.sleep(1000);
    // assertTrue(categories.size() > 0);
    // }

    // @Test
    // public void getCategoriesRxTest() throws InterruptedException {
    // final List<Category> categories = new ArrayList<>();
    // generalService.getCategories().reactive().subscribe(new
    // Subscriber<Response<List<Category>>>() {
    // @Override
    // public void onCompleted() {
    // LOG.info("reactive request completed successfully");
    // }
    //
    // @Override
    // public void onError(final Throwable e) {
    // LOG.error(e.getMessage());
    // }
    //
    // @Override
    // public void onNext(final Response<List<Category>> response) {
    // categories.addAll(response.body());
    // }
    // });
    //
    // Thread.sleep(1000);
    // assertTrue(categories.size() > 0);
    // }

    @Test
    public void getTagsSyncTest() {
        List<Tag> tags = generalService.getTags().execute();
        assertTrue(tags.size() > 0);
    }

    // @Test
    // public void getTagsAsyncTest() throws InterruptedException {
    // final List<Tag> tags = new ArrayList<>();
    // generalService.getTags().enqueue(new BynderServiceCallback<List<Tag>>() {
    // @Override
    // public void onResponse(final List<Tag> response) {
    // tags.addAll(response);
    // }
    //
    // @Override
    // public void onFailure(final Exception e) {
    // LOG.error(e.getMessage());
    // }
    // });
    //
    // Thread.sleep(1000);
    // assertTrue(tags.size() > 0);
    // }

    // @Test
    // public void getTagsRxTest() throws InterruptedException {
    // final List<Tag> tags = new ArrayList<>();
    // generalService.getTags().reactive().subscribe(new Subscriber<Response<List<Tag>>>() {
    // @Override
    // public void onCompleted() {
    // LOG.info("reactive request completed successfully");
    // }
    //
    // @Override
    // public void onError(final Throwable e) {
    // LOG.error(e.getMessage());
    // }
    //
    // @Override
    // public void onNext(final Response<List<Tag>> response) {
    // tags.addAll(response.body());
    // }
    // });
    //
    // Thread.sleep(1000);
    // assertTrue(tags.size() > 0);
    // }
}
