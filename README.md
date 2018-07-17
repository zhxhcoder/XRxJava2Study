
# Learning RxJava 2 for Android by example

## How to use RxJava 2 in Android Application
## How to migrate from RxJava 1.0 to RxJava 2.0

### This project is for : 
* who is migrating to RxJava 2 
* or just started with RxJava.

### Just Build the project and start learning RxJava by examples.

RxJava 2.0 has been completely rewritten from scratch on top of the Reactive-Streams specification. The specification itself has evolved out of RxJava 1.x and provides a common baseline for reactive systems and libraries.

Because Reactive-Streams has a different architecture, it mandates changes to some well known RxJava types.


# Migration From RxJava 1.0 to RxJava 2.0

To allow having RxJava 1 and RxJava 2 side-by-side, RxJava 2 is under the maven coordinates 
io.reactivex.rxjava2:rxjava:2.x.y and classes are accessible below io.reactivex.

Users switching from 1.x to 2.x have to re-organize their imports, but carefully.

### Using RxJava 2.0 Library in your application

Add this in your build.gradle
```groovy
compile 'io.reactivex.rxjava2:rxjava:2.1.1'
```
If you are using RxAndroid also, then add the following
```groovy
compile 'io.reactivex.rxjava2:rxandroid:2.0.1'
```

# RxJava 2 Examples present in this sample project

* RxJava 2.0 Example using `CompositeDisposable` as `CompositeSubscription` and `Subscription` have
been removed.

* RxJava 2 Example using `Flowable`.

* RxJava 2 Example using `SingleObserver`, `CompletableObserver`.

* RxJava 2 Example using RxJava2 operators such as `map, zip, take, reduce, flatMap, filter, buffer, skip, merge, concat, replay`, and much more:

* RxJava 2 Android Samples using `Function` as `Func1` has been removed.

* RxJava 2 Android Samples  using `BiFunction` as `Func2` has been removed.

* And many others android examples

# Quick Look on few changes done in RxJava2 over RxJava1

RxJava1 -> RxJava2

* `onCompleted` -> `onComplete` - without the trailing d
* `Func1` -> `Function`
* `Func2` -> `BiFunction`
* `CompositeSubscription` -> `CompositeDisposable`
* `limit` operator has been removed - Use `take` in RxJava2
* and much more.

# Operators :
* `Map` -> transform the items emitted by an Observable by applying a function to each item
* `Zip` -> combine the emissions of multiple Observables together via a specified function and emit single items for each combination based on the results of this function
* `Filter` -> emit only those items from an Observable that pass a predicate test
* `FlatMap` -> transform the items emitted by an Observable into Observables, then flatten the emissions from those into a single Observable
* `Take` -> emit only the first n items emitted by an Observable
* `Reduce` -> apply a function to each item emitted by an Observable, sequentially, and emit the final value
* `Skip` -> suppress the first n items emitted by an Observable
* `Buffer` -> periodically gather items emitted by an Observable into bundles and emit these bundles rather than emitting the items one at a time
* `Concat` -> emit the emissions from two or more Observables without interleaving them
* `Replay` -> ensure that all observers see the same sequence of emitted items, even if they subscribe after the Observable has begun emitting items
* `Merge` -> combine multiple Observables into one by merging their emissions
* `SwitchMap` -> ransform the items emitted by an Observable into Observables, and mirror those items emitted by the most-recently transformed Observable
