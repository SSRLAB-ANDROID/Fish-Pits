package by.ssrlab.fishpits.utils.vm.ui.sub.tables.regriv

import by.ssrlab.fishpits.utils.base.BaseUIVM
import io.reactivex.rxjava3.disposables.Disposable

class RegRivUIVM: BaseUIVM() {

    var currentPos = 0

    var regLangListener: Disposable? = null
    var rivLangListener: Disposable? = null

    override fun onCleared() {
        super.onCleared()

        regLangListener?.dispose()
        rivLangListener?.dispose()
    }
}