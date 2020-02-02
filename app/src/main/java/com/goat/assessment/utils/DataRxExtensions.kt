package com.goat.assessment.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

inline fun Disposable.addTo(composite: CompositeDisposable) {
    composite.add(this)
}

/**
 * Similar to [LiveData.observe], but only calls [onChanged] if the value
 * emitted is non-null.
 */
inline fun <T : Any> LiveData<T>.observeNonNull(
    owner: LifecycleOwner,
    crossinline onChanged: (T) -> Unit
) = observe(owner, Observer { t ->
    if (t != null) {
        onChanged(t)
    }
})