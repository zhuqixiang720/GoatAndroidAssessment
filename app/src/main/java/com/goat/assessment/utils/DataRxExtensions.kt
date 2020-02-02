package com.goat.assessment.utils

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

inline fun Disposable.addTo(composite: CompositeDisposable) {
    composite.add(this)
}