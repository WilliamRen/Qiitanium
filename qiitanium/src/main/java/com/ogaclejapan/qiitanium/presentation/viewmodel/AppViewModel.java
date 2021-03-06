package com.ogaclejapan.qiitanium.presentation.viewmodel;

import com.ogaclejapan.qiitanium.domain.core.EntityModel;
import com.ogaclejapan.qiitanium.util.DateTimeUtils;

import java.util.Date;

import rx.Subscription;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

abstract class AppViewModel implements Subscription {

    static abstract class AppModelMapper<E extends EntityModel, V extends AppViewModel>
            implements Func1<E, V> {

        @Override
        public abstract V call(final E entityModel);

    }

    static final Func1<Date, String> TIMEAGO =
            new Func1<Date, String>() {
                @Override
                public String call(final Date source) {
                    return DateTimeUtils.timeAgo(source);
                }
            };


    private final CompositeSubscription mSubscriptions;

    protected AppViewModel() {
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.unsubscribe();
    }

    @Override
    public boolean isUnsubscribed() {
        return mSubscriptions.isUnsubscribed();
    }

    public void add(Subscription s) {
        mSubscriptions.add(s);
    }



    public void setParent(AppViewModel parent) {
        parent.add(this);
    }

}
